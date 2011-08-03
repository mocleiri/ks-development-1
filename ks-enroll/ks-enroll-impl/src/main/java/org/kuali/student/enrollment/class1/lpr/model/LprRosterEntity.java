package org.kuali.student.enrollment.class1.lpr.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.common.entity.TimeAmount;

@Entity
@Table(name = "KSLP_LPR_ROSTER")
public class LprRosterEntity {

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name = "KSLP_LPRROSTER_LUI_RELTN", joinColumns = @JoinColumn(name = "LUI_ID"), inverseJoinColumns = @JoinColumn(name = "LPRROSTER_ID"))
    private List<LprRosterLuiRelationEntity> associatedLuis ;

    @Column(name = "MAX_CAPACITY")
    private Integer maximumCapacity;

    @Column(name = "CHECK_IN_REQ")
    private Boolean checkInRequired;

    @ManyToOne(optional = false)
    @JoinColumn(name = "TYPE_ID")
    private LuiPersonRelationTypeEntity lprRosterType;

    @ManyToOne(optional = false)
    @JoinColumn(name = "STATE_ID")
    private LuiPersonRelationStateEntity lprRosterState;

    @Embedded
    @Column(name = "CHECK_IN_FREQ")
    private TimeAmount checkInFrequency;

    public Integer getMaximumCapacity() {
        return maximumCapacity;
    }

    public void setMaximumCapacity(Integer maximumCapacity) {
        this.maximumCapacity = maximumCapacity;
    }

    public Boolean getCheckInRequired() {
        return checkInRequired;
    }

    public void setCheckInRequired(Boolean checkInRequired) {
        this.checkInRequired = checkInRequired;
    }

    public TimeAmount getCheckInFrequency() {
        return checkInFrequency;
    }

    public void setCheckInFrequency(TimeAmount checkInFrequency) {
        this.checkInFrequency = checkInFrequency;
    }

    public LuiPersonRelationTypeEntity getLprRosterType() {
        return lprRosterType;
    }

    public void setLprRosterType(LuiPersonRelationTypeEntity lprRosterType) {
        this.lprRosterType = lprRosterType;
    }

    public LuiPersonRelationStateEntity getLprRosterState() {
        return lprRosterState;
    }

    public void setLprRosterState(LuiPersonRelationStateEntity lprRosterState) {
        this.lprRosterState = lprRosterState;
    }

}
