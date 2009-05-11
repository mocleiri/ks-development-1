/*
 * Copyright 2009 The Kuali Foundation
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
package org.kuali.student.lum.lrc.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.kuali.student.core.entity.AttributeOwner;

@Entity
@Table(name = "KSLU_LRC_CREDENTIAL")
@NamedQueries( {
    @NamedQuery(name = "Credential.getCredentialsByIdList", query = "SELECT c FROM Credential c WHERE c.id IN (:credentialIdList)"),
    @NamedQuery(name = "Credential.getCredentialIdsByCredentialType", query = "SELECT c.id FROM Credential c JOIN c.type type WHERE type.id = :credentialTypeId")
})
public class Credential extends ResultValue implements AttributeOwner<CredentialAttribute> {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<CredentialAttribute> attributes;

    @ManyToOne
    @JoinColumn(name = "TYPE")
    private CredentialType type;

    /**
     * @return the type
     */
    public CredentialType getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(CredentialType type) {
        this.type = type;
    }

    @Override
    public List<CredentialAttribute> getAttributes() {
        if (attributes == null) {
            attributes = new ArrayList<CredentialAttribute>(0);
        }
        return attributes;
    }

    @Override
    public void setAttributes(List<CredentialAttribute> attributes) {
        this.attributes = attributes;
    }

}
