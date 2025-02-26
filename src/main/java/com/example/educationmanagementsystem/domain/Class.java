package com.example.educationmanagementsystem.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * 
 * @TableName class
 */
@TableName(value ="class")
public class Class implements Serializable {
    /**
     * 
     */
    @TableId
    private Integer classId;

    /**
     * 
     */
    private String className;

    /**
     * 
     */
    private Integer affiliateMajorId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public Integer getClassId() {
        return classId;
    }

    /**
     * 
     */
    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    /**
     * 
     */
    public String getClassName() {
        return className;
    }

    /**
     * 
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * 
     */
    public Integer getAffiliateMajorId() {
        return affiliateMajorId;
    }

    /**
     * 
     */
    public void setAffiliateMajorId(Integer affiliateMajorId) {
        this.affiliateMajorId = affiliateMajorId;
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
        Class other = (Class) that;
        return (this.getClassId() == null ? other.getClassId() == null : this.getClassId().equals(other.getClassId()))
            && (this.getClassName() == null ? other.getClassName() == null : this.getClassName().equals(other.getClassName()))
            && (this.getAffiliateMajorId() == null ? other.getAffiliateMajorId() == null : this.getAffiliateMajorId().equals(other.getAffiliateMajorId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getClassId() == null) ? 0 : getClassId().hashCode());
        result = prime * result + ((getClassName() == null) ? 0 : getClassName().hashCode());
        result = prime * result + ((getAffiliateMajorId() == null) ? 0 : getAffiliateMajorId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", classId=").append(classId);
        sb.append(", className=").append(className);
        sb.append(", affiliateMajorId=").append(affiliateMajorId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}