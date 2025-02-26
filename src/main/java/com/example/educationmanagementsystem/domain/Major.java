package com.example.educationmanagementsystem.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * 
 * @TableName major
 */
@TableName(value ="major")
public class Major implements Serializable {
    /**
     * 
     */
    @TableId
    private Integer majorId;

    /**
     * 
     */
    private String majorName;

    /**
     * 
     */
    private Integer affiliateFacultyId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public Integer getMajorId() {
        return majorId;
    }

    /**
     * 
     */
    public void setMajorId(Integer majorId) {
        this.majorId = majorId;
    }

    /**
     * 
     */
    public String getMajorName() {
        return majorName;
    }

    /**
     * 
     */
    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    /**
     * 
     */
    public Integer getAffiliateFacultyId() {
        return affiliateFacultyId;
    }

    /**
     * 
     */
    public void setAffiliateFacultyId(Integer affiliateFacultyId) {
        this.affiliateFacultyId = affiliateFacultyId;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Major other = (Major) that;
        return (this.getMajorId() == null ? other.getMajorId() == null : this.getMajorId().equals(other.getMajorId()))
            && (this.getMajorName() == null ? other.getMajorName() == null : this.getMajorName().equals(other.getMajorName()))
            && (this.getAffiliateFacultyId() == null ? other.getAffiliateFacultyId() == null : this.getAffiliateFacultyId().equals(other.getAffiliateFacultyId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getMajorId() == null) ? 0 : getMajorId().hashCode());
        result = prime * result + ((getMajorName() == null) ? 0 : getMajorName().hashCode());
        result = prime * result + ((getAffiliateFacultyId() == null) ? 0 : getAffiliateFacultyId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", majorId=").append(majorId);
        sb.append(", majorName=").append(majorName);
        sb.append(", affiliateFacultyId=").append(affiliateFacultyId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}