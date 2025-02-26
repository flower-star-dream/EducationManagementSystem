package com.example.educationmanagementsystem.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * 
 * @TableName student
 */
@TableName(value ="student")
public class Student implements Serializable {
    /**
     * 
     */
    @TableId
    private String studentId;

    /**
     * 
     */
    private String studentName;

    /**
     * 
     */
    private String studentGender;

    /**
     * 
     */
    private Integer studentAge;

    /**
     * 
     */
    private String studentPhone;

    /**
     * 
     */
    private LocalDate studentBirthdate;

    /**
     * 
     */
    private Integer affiliateClassId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public String getStudentId() {
        return studentId;
    }

    /**
     * 
     */
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    /**
     * 
     */
    public String getStudentName() {
        return studentName;
    }

    /**
     * 
     */
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    /**
     * 
     */
    public String getStudentGender() {
        return studentGender;
    }

    /**
     * 
     */
    public void setStudentGender(String studentGender) {
        this.studentGender = studentGender;
    }

    /**
     * 
     */
    public Integer getStudentAge() {
        return studentAge;
    }

    /**
     * 
     */
    public void setStudentAge(Integer studentAge) {
        this.studentAge = studentAge;
    }

    /**
     * 
     */
    public String getStudentPhone() {
        return studentPhone;
    }

    /**
     * 
     */
    public void setStudentPhone(String studentPhone) {
        this.studentPhone = studentPhone;
    }

    /**
     * 
     */
    public LocalDate getStudentBirthdate() {
        return studentBirthdate;
    }

    /**
     * 
     */
    public void setStudentBirthdate(LocalDate studentBirthdate) {
        this.studentBirthdate = studentBirthdate;
    }

    /**
     * 
     */
    public Integer getAffiliateClassId() {
        return affiliateClassId;
    }

    /**
     * 
     */
    public void setAffiliateClassId(Integer affiliateClassId) {
        this.affiliateClassId = affiliateClassId;
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
        Student other = (Student) that;
        return (this.getStudentId() == null ? other.getStudentId() == null : this.getStudentId().equals(other.getStudentId()))
            && (this.getStudentName() == null ? other.getStudentName() == null : this.getStudentName().equals(other.getStudentName()))
            && (this.getStudentGender() == null ? other.getStudentGender() == null : this.getStudentGender().equals(other.getStudentGender()))
            && (this.getStudentAge() == null ? other.getStudentAge() == null : this.getStudentAge().equals(other.getStudentAge()))
            && (this.getStudentPhone() == null ? other.getStudentPhone() == null : this.getStudentPhone().equals(other.getStudentPhone()))
            && (this.getStudentBirthdate() == null ? other.getStudentBirthdate() == null : this.getStudentBirthdate().equals(other.getStudentBirthdate()))
            && (this.getAffiliateClassId() == null ? other.getAffiliateClassId() == null : this.getAffiliateClassId().equals(other.getAffiliateClassId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getStudentId() == null) ? 0 : getStudentId().hashCode());
        result = prime * result + ((getStudentName() == null) ? 0 : getStudentName().hashCode());
        result = prime * result + ((getStudentGender() == null) ? 0 : getStudentGender().hashCode());
        result = prime * result + ((getStudentAge() == null) ? 0 : getStudentAge().hashCode());
        result = prime * result + ((getStudentPhone() == null) ? 0 : getStudentPhone().hashCode());
        result = prime * result + ((getStudentBirthdate() == null) ? 0 : getStudentBirthdate().hashCode());
        result = prime * result + ((getAffiliateClassId() == null) ? 0 : getAffiliateClassId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", studentId=").append(studentId);
        sb.append(", studentName=").append(studentName);
        sb.append(", studentGender=").append(studentGender);
        sb.append(", studentAge=").append(studentAge);
        sb.append(", studentPhone=").append(studentPhone);
        sb.append(", studentBirthdate=").append(studentBirthdate);
        sb.append(", affiliateClassId=").append(affiliateClassId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}