
package org.kuali.student.lum.lu.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.1.4
 * Tue Feb 24 12:25:30 EST 2009
 * Generated source version: 2.1.4
 */

@XmlRootElement(name = "validateReqComponent", namespace = "http://student.kuali.org/lum/lu")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "validateReqComponent", namespace = "http://student.kuali.org/lum/lu", propOrder = {"validationType","reqComponentInfo"})

public class ValidateReqComponent {

    @XmlElement(name = "validationType")
    private java.lang.String validationType;
    @XmlElement(name = "reqComponentInfo")
    private org.kuali.student.lum.lu.dto.ReqComponentInfo reqComponentInfo;

    public java.lang.String getValidationType() {
        return this.validationType;
    }

    public void setValidationType(java.lang.String newValidationType)  {
        this.validationType = newValidationType;
    }

    public org.kuali.student.lum.lu.dto.ReqComponentInfo getReqComponentInfo() {
        return this.reqComponentInfo;
    }

    public void setReqComponentInfo(org.kuali.student.lum.lu.dto.ReqComponentInfo newReqComponentInfo)  {
        this.reqComponentInfo = newReqComponentInfo;
    }

}

