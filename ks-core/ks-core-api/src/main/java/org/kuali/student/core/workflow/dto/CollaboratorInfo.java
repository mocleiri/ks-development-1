package org.kuali.student.core.workflow.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CollaboratorInfo implements Serializable{

	private static final long serialVersionUID = 1L;

	List<WorkflowPersonInfo> collaborators;

	public List<WorkflowPersonInfo> getCollaborators() {
		if (collaborators == null){
			collaborators = new ArrayList<WorkflowPersonInfo>();
		}
		return collaborators;
	}

	public void setCollaborators(List<WorkflowPersonInfo> collaborators) {
		this.collaborators = collaborators;
	}
	
}
