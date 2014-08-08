package org.kuali.student.ap.framework.context;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: Kamal
 * Date: 11/4/11
 * Time: 10:22 AM
 */
public class CourseSearchConstants {


    public static final String COURSE_SEARCH_RESULT_PAGE = "course_search_result";

    public static final String GEN_EDU_REQUIREMENTS_PREFIX = "course.genEdRequirement.";

    public static final Pattern TERM_PATTERN = Pattern.compile("([a-zA-Z]+)[\\s]+[0-9][0-9]([0-9][0-9])");

    public static final String SUBJECT_AREA = "kuali.lu.subjectArea";

    public static final String CAMPUS_LOCATION = "kuali.org.College";

    public static final String DEGREE_CREDIT_ID_SUFFIX = "kuali.creditType.credit.degree.";

    public static final String ORG_QUERY_SEARCH_BY_TYPE_REQUEST = "org.search.orgInfoByType";

    public static final String ORG_QUERY_SEARCH_SUBJECT_AREAS = "org.search.orgSubjectAreas";

    public static final String ORG_TYPE_PARAM = "org_queryParam_orgType";

    public static final String COURSE_SEARCH_FOR_COURSE_ID = "ksap.course.getcluid";

    public static final String SEARCH_REQUEST_SUBJECT_PARAM = "subject";

    public static final String SEARCH_REQUEST_NUMBER_PARAM = "number";

    public static final String SEARCH_REQUEST_LAST_SCHEDULED_PARAM = "lastScheduledTerm";

    /*Regex to Split Digits and alphabets Eg: COM 348 --> COM  348*/
    public static final String SPLIT_DIGITS_ALPHABETS = "(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)";

    /*Regex for validating the course Code eg: COM 301*/
    public static final String FORMATTED_COURSE_CODE_REGEX = "^[A-Z]{1}[A-Z &]{2,6}\\s[0-9]{3}$";

    /*Regex for validating the un-formatted courses eq:com131 or com    131 */
    public static final String UNFORMATTED_COURSE_CODE_REGEX = "^[a-zA-Z]{1}[a-zA-Z &]{2,7}[0-9]{3}$";

    public static final String COURSE_CODE_WITH_SECTION_REGEX = "^[A-Z]{1}[A-Z &]{2,6}\\s[0-9]{3}\\s[A-Z]{1}[A-Z0-9]{0,1}$";

    // MISC Search params/keys
    public static final String COURSE_SEARCH_DIVISION_SPACEALLOWED = "ks.ap.search.division.parse.allowspace";
    public static final String COURSE_SEARCH_SCALE_CREDIT_DEGREE = "kuali.result.scale.credit.degree";

    // Search Keys
    public static final String COURSE_SEARCH_TYPE_DESCRIPTION = "ksap.lu.search.description";
    public static final String COURSE_SEARCH_TYPE_CO_DESCRIPTION = "ksap.lu.search.offering.description";
    public static final String COURSE_SEARCH_TYPE_TITLE = "ksap.lu.search.title";
    public static final String COURSE_SEARCH_TYPE_CO_TITLE = "ksap.lu.search.offering.title";
    public static final String COURSE_SEARCH_TYPE_DIVISION = "ksap.lu.search.division";
    public static final String COURSE_SEARCH_TYPE_DIVISIONANDCODE = "ksap.lu.search.divisionAndCode";
    public static final String COURSE_SEARCH_TYPE_DIVISIONANDLEVEL = "ksap.lu.search.divisionAndLevel";
    public static final String COURSE_SEARCH_TYPE_EXACTCODE = "ksap.lu.search.exactCode";
    public static final String COURSE_SEARCH_TYPE_EXACTLEVEL = "ksap.lu.search.exactLevel";
    public static final String COURSE_SEARCH_TYPE_COURSECODE = "ksap.lu.search.courseCode";

    // Search Params
    public static final String COURSE_SEARCH_PARAM_DIVISION = "division";
    public static final String COURSE_SEARCH_PARAM_LEVEL = "level";
    public static final String COURSE_SEARCH_PARAM_CODE = "code";
    public static final String COURSE_SEARCH_PARAM_TERMLIST = "termList";
    public static final String COURSE_SEARCH_PARAM_QUERYTEXT = "queryText";

    // Custom Search Keys
    public static final String KSAP_COURSE_SEARCH_KEY = "kuali.search.type.ksap.coursesearch";
    public static final String KSAP_COURSE_SEARCH_COURSEIDS_BY_TERM_SCHEDULED_KEY = "kuali.search.type.ksap.coursesearch.cluid.by.term.scheduled";
    public static final String KSAP_COURSE_SEARCH_COURSEIDS_BY_TERM_OFFERED_KEY = "kuali.search.type.ksap.coursesearch.cluid.by.term.offered";
    public static final String KSAP_COURSE_SEARCH_OFFERED_REG_GROUP_IDS_BY_CO_ID_KEY = "kuali.search.type.ksap.offered.reg.group.ids.by.co.id";
    public static final String KSAP_COURSE_SEARCH_OFFERED_REG_GROUP_IDS_BY_AO_ID_KEY ="kuali.search.type.ksap.offered.reg.group.ids.by.ao.id";
    public static final String KSAP_COURSE_SEARCH_AO_IDS_BY_OFFERED_REG_GROUP_ID_KEY = "kuali.search.type.ksap.ao.ids.by.offered.reg.group.id";
    public static final String KSAP_COURSE_SEARCH_FO_IDS_BY_OFFERED_REG_GROUP_ID_KEY = "kuali.search.type.ksap.fo.ids.by.offered.reg.group.id";
    public static final String KSAP_COURSE_SEARCH_GENERAL_EDUCATION_VALUES_KEY = "kuali.search.type.ksap.coursesearch.general.education";
    public static final String KSAP_COURSE_SEARCH_COURSEIDS_BY_GENERAL_EDUCATION_KEY = "kuali.search.type.ksap.coursesearch.cluid.by.general.education";
    public static final String KSAP_COURSE_SEARCH_COURSEID_TITLE_AND_STATUS_BY_SUBJ_CD_KEY = "kuali.search.type.ksap.search.course.title.id.status.by.subj.code";
    public static final String KSAP_COURSE_SEARCH_ALL_DIVISION_CODES_KEY ="kuali.search.type.ksap.search.all.division" +
            ".codes";

    // Custom Search Result Column Keys
    public static final class SearchResultColumns {
        public static final String CLU_ID = "cluId";
        public static final String REG_GROUP_ID = "registrationGroupId";
        public static final String ACTIVITY_OFFERING_ID = "activityOfferingId";
        public static final String FORMAT_OFFERING_ID = "formatOfferingId";
        public static final String CLU_SET_ID = "cluSetId";
        public static final String CLU_SET_NAME = "cluSetName";
        public static final String CLU_SET_ATTR_VALUE = "cluSetAttrValue";
        public static final String CLU_TITLE = "cluTitle";
        public static final String CLU_STATUS = "cluStatus";
        public static final String DIVISION_CODE = "divisionCode";
    }

    // Custom Search Parameter Keys
    public static final class SearchParameters {
        public static final String ATP_ID = "atpId";
        public static final String ATP_TYPE_KEY = "atpTypeKey";
        public static final String GENED_KEY = "genEdKey";
        public static final String COURSE_OFFERING_ID = "courseOfferingId";
        public static final String ACTIVITY_OFFERING_ID = "activityOfferingId";
        public static final String REG_GROUP_ID = "registrationGroupId";
        public static final String COURSE_SUBJECT_PREFIX = "courseSubjectPrefix";  //e.g.  ENGL
        public static final String COURSE_SUBJECT_SUFFIX = "courseSubjectSuffix"; //e.g. 101
    }

    /**
     * Key to look up a configuration value to determine the sorted terms offered
     */
    public static final String TERMS_OFFERED_SORTED_KEY = "ks.ap.search.terms.offered.sorted";

}
