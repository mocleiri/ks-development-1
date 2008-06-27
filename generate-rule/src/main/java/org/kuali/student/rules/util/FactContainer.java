/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.rules.util;

import org.kuali.student.rules.common.util.CourseEnrollmentRequest;
import org.kuali.student.rules.statement.PropositionContainer;

public class FactContainer {
    private String id;
    private PropositionContainer propositionContainer;
    private CourseEnrollmentRequest request;
    
    public FactContainer() { }

    public FactContainer(String id) {
        this.id = id;
    }
    
    public String getId() {
        return this.id;
    }
    
    public void setRequest(CourseEnrollmentRequest request) {
        this.request = request;
    }

    public CourseEnrollmentRequest getRequest() {
        return request;
    }
    
    public void setPropositionContainer(PropositionContainer propositionContainer) {
        this.propositionContainer = propositionContainer;
    }

    public PropositionContainer getPropositionContainer() {
        return propositionContainer;
    }
}
