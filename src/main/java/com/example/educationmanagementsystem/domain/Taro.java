package com.example.educationmanagementsystem.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * 
 * @TableName taro
 */
@TableName(value ="taro")
public class Taro implements Serializable {
    /**
     * 
     */
    @TableId
    private Integer taroId;

    /**
     * 
     */
    private String taroName;

    /**
     * 
     */
    private Integer affiliateFacultyId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public Integer getTaroId() {
        return taroId;
    }

    /**
     * 
     */
    public void setTaroId(Integer taroId) {
        this.taroId = taroId;
    }

    /**
     * 
     */
    public String getTaroName() {
        return taroName;
    }

    /**
     * 
     */
    public void setTaroName(String taroName) {
        this.taroName = taroName;
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
        Taro other = (Taro) that;
        return (this.getTaroId() == null ? other.getTaroId() == null : this.getTaroId().equals(other.getTaroId()))
            && (this.getTaroName() == null ? other.getTaroName() == null : this.getTaroName().equals(other.getTaroName()))
            && (this.getAffiliateFacultyId() == null ? other.getAffiliateFacultyId() == null : this.getAffiliateFacultyId().equals(other.getAffiliateFacultyId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getTaroId() == null) ? 0 : getTaroId().hashCode());
        result = prime * result + ((getTaroName() == null) ? 0 : getTaroName().hashCode());
        result = prime * result + ((getAffiliateFacultyId() == null) ? 0 : getAffiliateFacultyId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", taroId=").append(taroId);
        sb.append(", taroName=").append(taroName);
        sb.append(", affiliateFacultyId=").append(affiliateFacultyId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}