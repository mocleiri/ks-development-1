
package org.kuali.student.lum.lu.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.1.4
 * Wed Mar 11 10:08:47 PDT 2009
 * Generated source version: 2.1.4
 */

@XmlRootElement(name = "getRelatedLuiIdsByLuiId", namespace = "http://student.kuali.org/wsdl/lu")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getRelatedLuiIdsByLuiId", namespace = "http://student.kuali.org/wsdl/lu", propOrder = {"luiId","luLuRelationTypeKey"})

public class GetRelatedLuiIdsByLuiId {

    @XmlElement(name = "luiId")
    private java.lang.String luiId;
    @XmlElement(name = "luLuRelationTypeKey")
    private java.lang.String luLuRelationTypeKey;

    public java.lang.String getLuiId() {
        return this.luiId;
    }

    public void setLuiId(java.lang.String newLuiId)  {
        this.luiId = newLuiId;
    }

    public java.lang.String getLuLuRelationTypeKey() {
        return this.luLuRelationTypeKey;
    }

    public void setLuLuRelationTypeKey(java.lang.String newLuLuRelationTypeKey)  {
        this.luLuRelationTypeKey = newLuLuRelationTypeKey;
    }

}

