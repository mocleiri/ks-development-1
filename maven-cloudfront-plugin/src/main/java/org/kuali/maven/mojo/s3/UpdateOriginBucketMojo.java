package org.kuali.maven.mojo.s3;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.descriptor.PluginDescriptor;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.CopyObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;

/**
 * @goal updateoriginbucket
 */
public class UpdateOriginBucketMojo extends S3Mojo {
    private static final String S3_INDEX_METADATA_KEY = "maven-cloudfront-plugin-index";
    NumberFormat nf = getNumberFormatInstance();
    SimpleDateFormat dateFormatter;
    HtmlUtils html = new HtmlUtils();

    /**
     * @parameter expression="${css}" default-value="http://s3browse.ks.kuali.org/css/style.css"
     */
    private String css;

    /**
     * @parameter expression="${fileImage}" default-value="http://s3browse.ks.kuali.org/images/page_white.png"
     */
    private String fileImage;

    /**
     * @parameter expression="${directoryImage}" default-value="http://s3browse.ks.kuali.org/images/folder.png"
     */
    private String directoryImage;

    /**
     * @parameter expression="${timezone}" default-value="GMT"
     */
    private String timezone;

    /**
     * @parameter expression="${dateFormat}" default-value="EEE, dd MMM yyyy HH:mm:ss z"
     */
    private String dateFormat;

    /**
     * @parameter expression="${defaultObject}" default-value="index.html";
     */
    private String defaultObject;

    @Override
    public void executeMojo() throws MojoExecutionException, MojoFailureException {
        dateFormatter = getSimpleDateFormatInstance();
        try {
            updateCredentials();
            validateCredentials();
            AWSCredentials credentials = getCredentials();
            AmazonS3Client client = new AmazonS3Client(credentials);
            recurse(client, getPrefix(), getDelimiter());
        } catch (Exception e) {
            throw new MojoExecutionException("Unexpected error: ", e);
        }
    }

    protected PutObjectRequest getPutIndexObjectRequest(String html, String key) throws IOException {
        InputStream in = new ByteArrayInputStream(html.getBytes());
        ObjectMetadata om = new ObjectMetadata();
        String contentType = "text/html";
        om.setContentType(contentType);
        om.setContentLength(html.length());
        om.addUserMetadata("maven-cloudfront-plugin-index", "true");
        PutObjectRequest request = new PutObjectRequest(getBucket(), key, in, om);
        request.setCannedAcl(CannedAccessControlList.PublicRead);
        return request;
    }

    protected NumberFormat getNumberFormatInstance() {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(1);
        nf.setMinimumFractionDigits(1);
        nf.setGroupingUsed(false);
        return nf;
    }

    protected SimpleDateFormat getSimpleDateFormatInstance() {
        SimpleDateFormat sdf = new SimpleDateFormat(getDateFormat());
        sdf.setTimeZone(TimeZone.getTimeZone(getTimezone()));
        return sdf;
    }

    protected String getIndexHtml(ObjectListing objectListing) {
        StringBuffer sb = new StringBuffer();
        return sb.toString();
    }

    /**
     * Trim the prefix off of the text we display for this object.<br>
     * Display "style.css" instead of "css/style.css"
     */
    protected String getShow(String key, String prefix) {
        if (prefix == null) {
            return key;
        }
        getLog().info("key=" + key + " prefix=" + prefix);
        int index = prefix.length();
        String s = key.substring(index);
        return s;
    }

    protected String getUpOneDirectoryPrefix(String prefix, String delimiter) {
        if (prefix.endsWith(delimiter)) {
            prefix = prefix.substring(0, prefix.length() - 1);
        }
        int pos = prefix.lastIndexOf(delimiter);
        if (pos == -1) {
            return delimiter;
        } else {
            return prefix.substring(0, pos + 1);
        }
    }

    protected List<String[]> getData(ObjectListing objectListing, String prefix, String delimiter) {
        DisplayRow upOneDirectory = getUpOneDirectoryDisplayRow(prefix, delimiter);
        List<DisplayRow> objectDisplayRows = getObjectDisplayRows(objectListing, prefix, delimiter);
        List<DisplayRow> directoryDisplayRows = getDirectoryDisplayRows(objectListing, prefix, delimiter);
        List<String[]> data = new ArrayList<String[]>();
        addDisplayRow(upOneDirectory, data);
        addDisplayRows(directoryDisplayRows, data);
        addDisplayRows(objectDisplayRows, data);
        return data;
    }

    protected boolean isEmpty(List<?> list) {
        return list == null || list.size() == 0;
    }

    protected String getDirectory(String prefix, String delimiter) {
        if (prefix == null) {
            return delimiter;
        }
        if (prefix.endsWith(delimiter)) {
            return delimiter + prefix.substring(0, prefix.length() - delimiter.length());
        } else {
            return delimiter + prefix;
        }
    }

    protected String getHtml(List<String[]> data, String prefix, String delimiter) {
        String directory = getDirectory(prefix, delimiter);

        Tag html = new Tag("html");
        Tag title = new Tag("title");
        Tag head = new Tag("head");
        Tag body = new Tag("body");
        Tag div1 = new Tag("div", "title");
        Tag span1 = new Tag("span", null, "title");
        Tag div2 = new Tag("div", "data");
        Tag div3 = new Tag("div", "footer", "footer-left");
        Tag span2 = new Tag("span", null, "footer-text");

        StringBuffer sb = new StringBuffer();
        sb.append(this.html.openTag(html));
        sb.append(this.html.getTag(title, "Directory listing for " + directory));
        sb.append(this.html.openTag(head));
        sb.append(this.html.getIndentedContent("<link href=\"" + getCss() + "\" rel=\"stylesheet\" type=\"text/css\"/>\n"));
        sb.append(this.html.closeTag(head));
        sb.append(this.html.openTag(body));
        sb.append(this.html.openTag(div1));
        sb.append(this.html.getTag(span1, "Directory listing for " + directory));
        sb.append(this.html.closeTag(div1));
        sb.append(this.html.getIndentedContent("<hr>\n"));
        sb.append(this.html.openTag(div2));
        sb.append(getHtmlTable(data, getColumnDecorators()));
        sb.append(this.html.closeTag(div2));
        sb.append(this.html.getIndentedContent("<hr>\n"));
        sb.append(this.html.openTag(div3));
        sb.append(this.html.getTag(span2, getPoweredBy()));
        sb.append(this.html.closeTag(div3));
        sb.append(this.html.closeTag(body));
        sb.append(this.html.closeTag(html));
        return sb.toString();
    }

    protected String getPoweredBy() {
        PluginDescriptor descriptor = (PluginDescriptor) this.getPluginContext().get("pluginDescriptor");
        if (descriptor == null) {
            // Maven 2.2.1 on is returning a null descriptor
            return "Powered by the maven-cloudfront-plugin";
        } else {
            String name = descriptor.getArtifactId();
            String version = descriptor.getVersion();
            return "Powered by the " + name + " v" + version;
        }
    }

    protected String getHtmlTable(List<String[]> data, List<ColumnDecorator> columnDecorators) {
        if (isEmpty(data)) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        Tag table = new Tag("table", "mainTable");
        Tag thead = new Tag("thead");
        Tag tr = new Tag("tr");
        Tag tbody = new Tag("tbody");
        sb.append(html.openTag(table));
        sb.append(html.openTag(thead));
        sb.append(html.openTag(tr));
        sb.append(getTableHeaders(columnDecorators));
        sb.append(html.closeTag(tr));
        sb.append(html.closeTag(thead));
        sb.append(html.openTag(tbody));
        sb.append(getTableRows(data, columnDecorators));
        sb.append(html.closeTag(tbody));
        sb.append(html.closeTag(table));
        return sb.toString();
    }

    protected Tag getTableRowTag(int row) {
        if ((row % 2) == 0) {
            return new Tag("tr");
        } else {
            return new Tag("tr", "table-tr-odd");
        }
    }

    protected String getTableCell(String content, ColumnDecorator decorator) {
        Tag td = new Tag("td", decorator.getTableDataClass());
        return html.getTag(td, content);
    }

    protected String getTableRow(int row, String[] data, List<ColumnDecorator> columnDecorators) {
        StringBuffer sb = new StringBuffer();
        Tag tr = getTableRowTag(row);
        sb.append(html.openTag(tr));
        for (int i = 0; i < data.length; i++) {
            sb.append(getTableCell(data[i], columnDecorators.get(i)));
        }
        sb.append(html.closeTag(tr));
        return sb.toString();
    }

    protected String getTableRows(List<String[]> data, List<ColumnDecorator> columnDecorators) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < data.size(); i++) {
            sb.append(getTableRow(i, data.get(i), columnDecorators));
        }
        return sb.toString();
    }

    protected String getTableHeaders(List<ColumnDecorator> columnDecorators) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < columnDecorators.size(); i++) {
            ColumnDecorator decorator = columnDecorators.get(i);
            Tag th = new Tag("th", decorator.getTableDataClass());
            sb.append(html.openTag(th));
            sb.append(html.getTag(new Tag("span", decorator.getSpanClass()), decorator.getColumnTitle()));
            sb.append(html.closeTag(th));
        }
        return sb.toString();
    }

    protected List<ColumnDecorator> getColumnDecorators() {
        List<ColumnDecorator> columnDecorators = new ArrayList<ColumnDecorator>();
        columnDecorators.add(new ColumnDecorator("image-column", "sort-header", ""));
        columnDecorators.add(new ColumnDecorator("name-column", "sort-header", "Name"));
        columnDecorators.add(new ColumnDecorator("last-modified-column", "sort-header", "Last Modified"));
        columnDecorators.add(new ColumnDecorator("size-column", "sort-header", "Size"));
        return columnDecorators;
    }

    protected void addDisplayRows(List<DisplayRow> displayRows, List<String[]> data) {
        for (DisplayRow displayRow : displayRows) {
            addDisplayRow(displayRow, data);
        }
    }

    protected void addDisplayRow(DisplayRow displayRow, List<String[]> data) {
        if (displayRow == null) {
            return;
        }
        String[] row = new String[4];
        row[0] = displayRow.getImage();
        row[1] = displayRow.getAhref();
        row[2] = displayRow.getLastModified();
        row[3] = displayRow.getSize();
        data.add(row);
    }

    /**
     * Convert a commonPrefix into a DisplayRow object for the UI
     */
    protected DisplayRow getUpOneDirectoryDisplayRow(String prefix, String delimiter) {
        if (StringUtils.isEmpty(prefix)) {
            return null;
        }

        // Create some UI friendly strings
        String image = "";
        String show = ".." + delimiter;
        String dest = getUpOneDirectoryPrefix(prefix, delimiter);
        String ahref = html.getHref(dest, show);
        String date = "";
        String size = "";

        // Store them in an object
        DisplayRow displayRow = new DisplayRow();
        displayRow.setImage(image);
        displayRow.setAhref(ahref);
        displayRow.setLastModified(date);
        displayRow.setSize(size);
        return displayRow;
    }

    /**
     * Convert a commonPrefix into a DisplayRow object for the UI
     */
    protected DisplayRow getDisplayRow(String commonPrefix, String prefix, String delimiter) {

        // Create some UI friendly strings
        String image = html.getImage(getDirectoryImage());
        String show = getShow(commonPrefix, prefix);
        String dest = delimiter + commonPrefix;
        String ahref = html.getHref(dest, show);
        String date = "-";
        String size = "-";

        // Store them in an object
        DisplayRow displayRow = new DisplayRow();
        displayRow.setImage(image);
        displayRow.setAhref(ahref);
        displayRow.setLastModified(date);
        displayRow.setSize(size);
        return displayRow;
    }

    protected List<DisplayRow> getDirectoryDisplayRows(ObjectListing objectListing, String prefix, String delimiter) {
        List<DisplayRow> displayRows = new ArrayList<DisplayRow>();
        for (String commonPrefix : objectListing.getCommonPrefixes()) {
            DisplayRow displayRow = getDisplayRow(commonPrefix, prefix, delimiter);
            if (displayRow == null) {
                continue;
            }
            displayRows.add(displayRow);
        }
        return displayRows;
    }

    protected boolean isDirectory(S3ObjectSummary summary, List<String> commonPrefixes, String delimiter) {
        String key = summary.getKey();
        for (String commonPrefix : commonPrefixes) {
            if ((delimiter + key).equals(commonPrefix)) {
                return true;
            }
        }
        return false;
    }

    protected List<DisplayRow> getObjectDisplayRows(ObjectListing objectListing, String prefix, String delimiter) {
        List<DisplayRow> displayRows = new ArrayList<DisplayRow>();
        for (S3ObjectSummary summary : objectListing.getObjectSummaries()) {
            if (isDirectory(summary, objectListing.getCommonPrefixes(), delimiter)) {
                continue;
            }
            DisplayRow displayRow = getDisplayRow(summary, prefix, delimiter);
            if (displayRow == null) {
                continue;
            }
            displayRows.add(displayRow);
        }
        return displayRows;
    }

    /**
     * Convert an S3ObjectSummary into a DisplayRow object for the UI
     */
    protected DisplayRow getDisplayRow(S3ObjectSummary summary, String prefix, String delimiter) {
        // Skip displaying "css" as a file if we are in the css directory
        String key = summary.getKey();
        if (key.equals(prefix)) {
            return null;
        }

        // Skip displaying "css/" as a file also
        if ((key + delimiter).equals(prefix)) {
            return null;
        }

        // Create some UI friendly strings
        String image = html.getImage(getFileImage());
        String show = getShow(key, prefix);
        String dest = delimiter + key;
        String ahref = html.getHref(dest, show);
        String date = dateFormatter.format(summary.getLastModified());
        String size = nf.format((summary.getSize() / 1024D)) + " KiB";

        // Store them in an object
        DisplayRow displayRow = new DisplayRow();
        displayRow.setImage(image);
        displayRow.setAhref(ahref);
        displayRow.setLastModified(date);
        displayRow.setSize(size);
        return displayRow;
    }

    protected void recurse(AmazonS3Client client, String prefix, String delimiter) throws IOException {
        ListObjectsRequest request = new ListObjectsRequest(getBucket(), prefix, null, delimiter, Integer.MAX_VALUE);
        ObjectListing objectListing = client.listObjects(request);
        List<String[]> data = getData(objectListing, prefix, delimiter);
        String html = getHtml(data, prefix, delimiter);
        String defaultFileKey = (prefix == null) ? getDefaultObject() : prefix + getDefaultObject();
        if (isUpdateIndex(client, objectListing, defaultFileKey)) {
            client.putObject(getPutIndexObjectRequest(html, defaultFileKey));
        }
        /*
         * List<String> commonPrefixes = objectListing.getCommonPrefixes(); for (String commonPrefix : commonPrefixes) { getLog().info("prefix=" + prefix + " commonPrefix=" + commonPrefix); updateDir(client, objectListing, getBucket(), commonPrefix,
         * delimiter); recurse(client, commonPrefix, delimiter); }
         */
    }

    protected boolean isExistingObject(ObjectListing objectListing, String key) {
        List<S3ObjectSummary> summaries = objectListing.getObjectSummaries();
        for (S3ObjectSummary summary : summaries) {
            if (key.equals(summary.getKey())) {
                return true;
            }
        }
        return false;
    }

    protected boolean isUpdateIndex(AmazonS3Client client, ObjectListing objectListing, String indexKey) {
        if (!isExistingObject(objectListing, indexKey)) {
            return true;
        }
        S3Object object = client.getObject(getBucket(), indexKey);
        ObjectMetadata metadata = object.getObjectMetadata();
        Map<String, String> userMetadata = metadata.getUserMetadata();
        String value = userMetadata.get(S3_INDEX_METADATA_KEY);
        boolean isOurIndex = "true".equals(value);
        getLog().info("##############");
        getLog().info("##############");
        getLog().info("isOurIndex=" + isOurIndex);
        getLog().info("##############");
        getLog().info("##############");
        // If it is our index, we need to update it
        return isOurIndex;
    }

    protected CopyObjectRequest getCopyObjectRequest(String bucket, String sourceKey, String destKey) {
        CopyObjectRequest request = new CopyObjectRequest(bucket, sourceKey, bucket, destKey);
        request.setCannedAccessControlList(CannedAccessControlList.PublicRead);
        return request;
    }

    protected void updateDir(AmazonS3Client client, ObjectListing objectListing, String bucket, String prefix, String delimiter) throws IOException {
        List<String[]> data = getData(objectListing, prefix, delimiter);
        String html = getHtml(data, prefix, delimiter);
        String defaultFileKey = prefix + getDefaultObject();
        /*
         * if (isExistingKey(objectListing, bucket, prefix, defaultFileKey, delimiter)) { getLog().info("###################"); getLog().info("###################"); getLog().info("###################"); getLog().info("Copying " + defaultFileKey + " to "
         * + prefix); getLog().info("###################"); getLog().info("###################"); getLog().info("###################"); client.copyObject(getCopyObjectRequest(bucket, defaultFileKey, prefix)); } else { PutObjectRequest request1 =
         * getPutIndexObjectRequest(html, prefix); client.putObject(request1); } PutObjectRequest request2 = getPutIndexObjectRequest(html, prefix.substring(0, prefix.length() - 1)); client.putObject(request2);
         */
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String getDefaultObject() {
        return defaultObject;
    }

    public void setDefaultObject(String defaultCloudFrontObject) {
        this.defaultObject = defaultCloudFrontObject;
    }

    public String getFileImage() {
        return fileImage;
    }

    public void setFileImage(String fileImage) {
        this.fileImage = fileImage;
    }

    public String getDirectoryImage() {
        return directoryImage;
    }

    public void setDirectoryImage(String directoryImage) {
        this.directoryImage = directoryImage;
    }

    public String getCss() {
        return css;
    }

    public void setCss(String css) {
        this.css = css;
    }

}
