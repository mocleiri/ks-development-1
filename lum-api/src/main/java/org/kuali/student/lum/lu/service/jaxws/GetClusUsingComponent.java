
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

@XmlRootElement(name = "getClusUsingComponent", namespace = "http://student.kuali.org/lum/lu")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getClusUsingComponent", namespace = "http://student.kuali.org/lum/lu")

public class GetClusUsingComponent {

    @XmlElement(name = "reqComponentId")
    private java.lang.String reqComponentId;

    public java.lang.String getReqComponentId() {
        return this.reqComponentId;
    }

    public void setReqComponentId(java.lang.String newReqComponentId)  {
        this.reqComponentId = newReqComponentId;
    }

}

