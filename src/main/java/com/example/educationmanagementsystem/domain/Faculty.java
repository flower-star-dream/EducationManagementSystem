package com.example.educationmanagementsystem.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * 
 * @TableName faculty
 */
@TableName(value ="faculty")
public class Faculty implements Serializable {
    /**
     * 
     */
    @TableId
    private Integer facultyId;

    /**
     * 
     */
    private String facultyName;

    /**
     * 
     */
    private String facultyAddress;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public Integer getFacultyId() {
        return facultyId;
    }

    /**
     * 
     */
    public void setFacultyId(Integer facultyId) {
        this.facultyId = facultyId;
    }

    /**
     * 
     */
    public String getFacultyName() {
        return facultyName;
    }

    /**
     * 
     */
    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    /**
     * 
     */
    public String getFacultyAddress() {
        return facultyAddress;
    }

    /**
     * 
     */
    public void setFacultyAddress(String facultyAddress) {
        this.facultyAddress = facultyAddress;
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
        Faculty other = (Faculty) that;
        return (this.getFacultyId() == null ? other.getFacultyId() == null : this.getFacultyId().equals(other.getFacultyId()))
            && (this.getFacultyName() == null ? other.getFacultyName() == null : this.getFacultyName().equals(other.getFacultyName()))
            && (this.getFacultyAddress() == null ? other.getFacultyAddress() == null : this.getFacultyAddress().equals(other.getFacultyAddress()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getFacultyId() == null) ? 0 : getFacultyId().hashCode());
        result = prime * result + ((getFacultyName() == null) ? 0 : getFacultyName().hashCode());
        result = prime * result + ((getFacultyAddress() == null) ? 0 : getFacultyAddress().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", facultyId=").append(facultyId);
        sb.append(", facultyName=").append(facultyName);
        sb.append(", facultyAddress=").append(facultyAddress);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}