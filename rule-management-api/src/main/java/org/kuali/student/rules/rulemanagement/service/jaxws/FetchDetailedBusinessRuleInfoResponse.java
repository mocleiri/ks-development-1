
package org.kuali.student.rules.rulemanagement.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.1
 * Wed Sep 10 10:18:59 EDT 2008
 * Generated source version: 2.1
 */

@XmlRootElement(name = "fetchDetailedBusinessRuleInfoResponse", namespace = "http://student.kuali.org/poc/wsdl/brms/rulemanagement")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fetchDetailedBusinessRuleInfoResponse", namespace = "http://student.kuali.org/poc/wsdl/brms/rulemanagement")

public class FetchDetailedBusinessRuleInfoResponse {

    @XmlElement(name = "return")
    private org.kuali.student.rules.rulemanagement.dto.BusinessRuleInfoDTO _return;

    public org.kuali.student.rules.rulemanagement.dto.BusinessRuleInfoDTO getReturn() {
        return this._return;
    }

    public void setReturn(org.kuali.student.rules.rulemanagement.dto.BusinessRuleInfoDTO new_return)  {
        this._return = new_return;
    }

}

