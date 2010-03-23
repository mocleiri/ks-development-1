package org.kuali.student.lum.lu.ui.course.server.gwt;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.rice.kew.dto.ActionRequestDTO;
import org.kuali.rice.kew.dto.RouteNodeInstanceDTO;
import org.kuali.rice.kew.util.KEWConstants;
import org.kuali.rice.kew.webservice.DocumentResponse;
import org.kuali.rice.kew.webservice.StandardResponse;
import org.kuali.student.common.ui.client.service.exceptions.OperationFailedException;
import org.kuali.student.common.ui.server.gwt.AbstractBaseDataOrchestrationRpcGwtServlet;
import org.kuali.student.core.assembly.data.AssemblyException;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.lum.lu.assembly.ModifyCreditCourseProposalManager;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseProposalHelper;
import org.kuali.student.lum.lu.dto.workflow.CluProposalCollabRequestDocInfo;
import org.kuali.student.lum.lu.dto.workflow.CluProposalDocInfo;
import org.kuali.student.lum.lu.dto.workflow.PrincipalIdRoleAttribute;
import org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService;

public class CreditCourseProposalRpcGwtServlet extends
		AbstractBaseDataOrchestrationRpcGwtServlet implements
		CreditCourseProposalRpcService {

	private static final long serialVersionUID = 1L;
	final Logger LOG = Logger.getLogger(CreditCourseProposalRpcGwtServlet.class);
    private static final String WF_TYPE_CLU_DOCUMENT = "CluCreditCourseProposal";
    private static final String WF_TYPE_CLU_COLLABORATOR_DOCUMENT =  "CluCollaboratorDocument";
	private static final String DEFAULT_METADATA_STATE = "draft";
	private static final String DEFAULT_METADATA_TYPE = null;

	private ModifyCreditCourseProposalManager modifyCourseManager;
	
	
	@Override
	public Data getNewProposalWithCopyOfClu(String dataId) throws OperationFailedException {
		try {
			return modifyCourseManager.getNewProposalWithCopyOfClu(dataId);
		} catch (AssemblyException e) {
			LOG.error("Copy Failed on id:"+dataId, e);
			throw new OperationFailedException("Copy Failed on id:"+dataId,e);
		}
	}
	
    @Override
	protected String deriveAppIdFromData(Data data) {
		CreditCourseProposalHelper cluProposal = CreditCourseProposalHelper.wrap(data);
		if(cluProposal!=null&&cluProposal.getProposal()!=null){
			return cluProposal.getProposal().getId();
		}
		return null;
	}

	@Override
	protected String deriveDocContentFromData(Data data) {
    	try{
    		CreditCourseProposalHelper cluProposal = CreditCourseProposalHelper.wrap(data);
    		
    		CluProposalDocInfo docContent = new CluProposalDocInfo();
    		
    		if(null == cluProposal.getCourse()){
    			throw new OperationFailedException("CluInfo must be set.");
    		}
    		
    		String cluId = cluProposal.getCourse().getId()==null?"":cluProposal.getCourse().getId(); 
    		String adminOrg = cluProposal.getCourse().getDepartment()==null?"":cluProposal.getCourse().getDepartment(); 
    		String proposalId = cluProposal.getProposal().getId()==null?"":cluProposal.getProposal().getId();
    		
    		docContent.setCluId(cluId);
            docContent.setOrgId(adminOrg);
            docContent.setProposalId(proposalId);
            
    		JAXBContext context = JAXBContext.newInstance(docContent.getClass());
    		Marshaller marshaller = context.createMarshaller();
            StringWriter writer = new StringWriter();
    		marshaller.marshal(docContent, writer);
    		return writer.toString();

    	} catch(Exception e) {
    		LOG.error("Error creating Document content for Clu. ", e);
    	}
    	return null;
	}

	@Override
	protected String getDefaultMetaDataState() {
		return DEFAULT_METADATA_STATE;
	}

	@Override
	protected String getDefaultMetaDataType() {
		return DEFAULT_METADATA_TYPE;
	}

	@Override
	protected String getDefaultWorkflowDocumentType() {
		return WF_TYPE_CLU_DOCUMENT;
	}


	@Override
	protected boolean checkDocumentLevelPermissions() {
		return true;
	}

	public ModifyCreditCourseProposalManager getModifyCourseManager() {
		return modifyCourseManager;
	}

	public void setModifyCourseManager(
			ModifyCreditCourseProposalManager modifyCourseManager) {
		this.modifyCourseManager = modifyCourseManager;
	}

}
