
package org.kuali.student.lum.nlt.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.2
 * Fri Jul 10 15:41:26 PDT 2009
 * Generated source version: 2.2
 */

@XmlRootElement(name = "getNaturalLanguageForStatementInfoAsTreeResponse", namespace = "http://student.kuali.org/lum/nlt")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getNaturalLanguageForStatementInfoAsTreeResponse", namespace = "http://student.kuali.org/lum/nlt")

public class GetNaturalLanguageForStatementInfoAsTreeResponse {

    @XmlElement(name = "return")
    private org.kuali.student.lum.nlt.dto.NLTranslationNodeInfo _return;

    public org.kuali.student.lum.nlt.dto.NLTranslationNodeInfo getReturn() {
        return this._return;
    }

    public void setReturn(org.kuali.student.lum.nlt.dto.NLTranslationNodeInfo new_return)  {
        this._return = new_return;
    }

}

