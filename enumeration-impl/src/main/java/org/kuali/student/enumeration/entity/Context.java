package org.kuali.student.enumeration.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;

import org.kuali.student.poc.common.util.UUIDHelper;

@Entity
public class Context {
    @Id
    String id;
    String enumerationId;
    
    String key;
    String value;
    String enumerationKey;

    /**
     * AutoGenerate the id
     */
    @PrePersist
    public void prePersist() {
        this.id = UUIDHelper.genStringUUID();
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return key;
    }

    public void setType(String type) {
        this.key = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    public String getEnumerationKey() {
        return enumerationKey;
    }
    public void setEnumerationKey(String enumerationKey) {
        this.enumerationKey = enumerationKey;
    }



}
