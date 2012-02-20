package org.kuali.student.enrollment.class1.lui.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "KSEN_LUI_CLUCLU_RELTN")
public class LuiCluCluRelationEntity {
    
    @Id
    @Column(name = "ID")
    private String id;

    @ManyToOne
    @JoinColumn(name = "LUI_ID")
    private LuiEntity lui;

    @Column(name = "CLUCLU_RELTN_ID", nullable = false)
    private String clucluRelationId;

    public LuiCluCluRelationEntity(LuiCluCluRelationEntity luiCluRelationEntity) {
        this.setId(luiCluRelationEntity.getId());
        this.setClucluRelationId(luiCluRelationEntity.getClucluRelationId());
        this.setLui(luiCluRelationEntity.getLui());
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LuiEntity getLui() {
        return lui;
    }

    public void setLui(LuiEntity lui) {
        this.lui = lui;
    }

    public String getClucluRelationId() {
        return clucluRelationId;
    }

    public void setClucluRelationId(String clucluRelationId) {
        this.clucluRelationId = clucluRelationId;
    }

}
