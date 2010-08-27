package org.kuali.student.core.versionmanagement.service;

import java.util.Date;
import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.versionmanagement.dto.VersionInfo;


@WebService(name = "VersionManagementService", targetNamespace = "http://student.kuali.org/wsdl/versionmanagement")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface VersionManagementService {

    /**
     * Retrieves list of version associated with the objectId.
     * 
     * @param refObjectTypeURI
     *            reference object type URI 
     * @param refObjectId
     *            reference object Id
     * @return list of versions
     * @throws DoesNotExistException
     *             specified refObjectId, refObjectTypeURI not found
     * @throws InvalidParameterException
     *             invalid refObjectId, refObjectTypeURI
     * @throws MissingParameterException
     *            refObjectId, refObjectTypeURI not specified
     * @throws OperationFailedException
     *             unable to complete request
     * @throws PermissionDeniedException
     *             authorization failure
     */
    public List<VersionInfo> getVersions(@WebParam(name = "refObjectTypeURI") String refObjectTypeURI, @WebParam(name = "refObjectId") String refObjectId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;
    

    /**
     * Retrieves first version associated with the objectId.
     * 
     * @param refObjectTypeURI
     *            reference object type URI 
     * @param refObjectId
     *            reference object Id
     * @return first version
     * @throws DoesNotExistException
     *             specified refObjectId, refObjectTypeURI not found
     * @throws InvalidParameterException
     *             invalid refObjectId, refObjectTypeURI
     * @throws MissingParameterException
     *            refObjectId, refObjectTypeURI not specified
     * @throws OperationFailedException
     *             unable to complete request
     * @throws PermissionDeniedException
     *             authorization failure
     */
    public VersionInfo getFirstVersion(@WebParam(name = "refObjectTypeURI") String refObjectTypeURI, @WebParam(name = "refObjectId") String refObjectId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;
    
    
    /**
     * Retrieves current version associated with the objectId.
     * 
     * @param refObjectTypeURI
     *            reference object type URI 
     * @param refObjectId
     *            reference object Id
     * @return current version
     * @throws DoesNotExistException
     *             specified refObjectId, refObjectTypeURI not found
     * @throws InvalidParameterException
     *             invalid refObjectId, refObjectTypeURI
     * @throws MissingParameterException
     *            refObjectId, refObjectTypeURI not specified
     * @throws OperationFailedException
     *             unable to complete request
     * @throws PermissionDeniedException
     *             authorization failure
     */
    public VersionInfo getCurrentVersion(@WebParam(name = "refObjectTypeURI") String refObjectTypeURI, @WebParam(name = "refObjectId") String refObjectId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;


    /**
     * Retrieves the version associated with the objectId and the sequence number.
     * 
     * @param refObjectTypeURI
     *            reference object type URI 
     * @param refObjectId
     *            reference object Id
     * @param sequence
     *            sequence number
     * @return version matching the sequence
     * @throws DoesNotExistException
     *             specified refObjectId, refObjectTypeURI, sequence not found
     * @throws InvalidParameterException
     *             invalid refObjectId, refObjectTypeURI, sequence
     * @throws MissingParameterException
     *            refObjectId, refObjectTypeURI, sequence not specified
     * @throws OperationFailedException
     *             unable to complete request
     * @throws PermissionDeniedException
     *             authorization failure
     */
    public VersionInfo getVersionBySequenceNumber(@WebParam(name = "refObjectTypeURI") String refObjectTypeURI, @WebParam(name = "refObjectId") String refObjectId, @WebParam(name = "sequence") Integer sequence) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;


    /**
     * Retrieves the current version associated with the objectId on a given date.
     * 
     * @param refObjectTypeURI
     *            reference object type URI 
     * @param refObjectId
     *            reference object Id
     * @param date
     *            date
     * @return current version on date
     * @throws DoesNotExistException
     *             specified refObjectId, refObjectTypeURI not found
     * @throws InvalidParameterException
     *             invalid refObjectId, refObjectTypeURI, date
     * @throws MissingParameterException
     *            refObjectId, refObjectTypeURI, date not specified
     * @throws OperationFailedException
     *             unable to complete request
     * @throws PermissionDeniedException
     *             authorization failure
     */
    public VersionInfo getCurrentVersionOnDate(@WebParam(name = "refObjectTypeURI") String refObjectTypeURI, @WebParam(name = "refObjectId") String refObjectId, @WebParam(name = "date") Date date) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;


    /**
     * Retrieves the current version associated with the objectId on a given date.
     * 
     * @param refObjectTypeURI
     *            reference object type URI 
     * @param refObjectId
     *            reference object Id
     * @param from
     *            from date
     * @param to
     *            to date
     * @return current version on date
     * @throws DoesNotExistException
     *             specified refObjectId, refObjectTypeURI not found
     * @throws InvalidParameterException
     *             invalid refObjectId, refObjectTypeURI, date
     * @throws MissingParameterException
     *            refObjectId, refObjectTypeURI, date not specified
     * @throws OperationFailedException
     *             unable to complete request
     * @throws PermissionDeniedException
     *             authorization failure
     */
    public List<VersionInfo> getVersionsInDateRange(@WebParam(name = "refObjectTypeURI") String refObjectTypeURI, @WebParam(name = "refObjectId") String refObjectId, @WebParam(name = "from") Date from, @WebParam(name = "to") Date to) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;
   
}
