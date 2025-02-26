package com.example.educationmanagementsystem.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * 
 * @TableName teacher
 */
@TableName(value ="teacher")
public class Teacher implements Serializable {
    /**
     * 
     */
    @TableId
    private String teacherId;

    /**
     * 
     */
    private String teacherName;

    /**
     * 
     */
    private String teacherGender;

    /**
     * 
     */
    private Integer teacherAge;

    /**
     * 
     */
    private String teacherPhone;

    /**
     * 
     */
    private String teacherTitle;

    /**
     * 
     */
    private String teacherDegree;

    /**
     * 
     */
    private LocalDate teacherBirthdate;

    /**
     * 
     */
    private Integer affiliateTaroId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public String getTeacherId() {
        return teacherId;
    }

    /**
     * 
     */
    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    /**
     * 
     */
    public String getTeacherName() {
        return teacherName;
    }

    /**
     * 
     */
    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    /**
     * 
     */
    public String getTeacherGender() {
        return teacherGender;
    }

    /**
     * 
     */
    public void setTeacherGender(String teacherGender) {
        this.teacherGender = teacherGender;
    }

    /**
     * 
     */
    public Integer getTeacherAge() {
        return teacherAge;
    }

    /**
     * 
     */
    public void setTeacherAge(Integer teacherAge) {
        this.teacherAge = teacherAge;
    }

    /**
     * 
     */
    public String getTeacherPhone() {
        return teacherPhone;
    }

    /**
     * 
     */
    public void setTeacherPhone(String teacherPhone) {
        this.teacherPhone = teacherPhone;
    }

    /**
     * 
     */
    public String getTeacherTitle() {
        return teacherTitle;
    }

    /**
     * 
     */
    public void setTeacherTitle(String teacherTitle) {
        this.teacherTitle = teacherTitle;
    }

    /**
     * 
     */
    public String getTeacherDegree() {
        return teacherDegree;
    }

    /**
     * 
     */
    public void setTeacherDegree(String teacherDegree) {
        this.teacherDegree = teacherDegree;
    }

    /**
     * 
     */
    public LocalDate getTeacherBirthdate() {
        return teacherBirthdate;
    }

    /**
     * 
     */
    public void setTeacherBirthdate(LocalDate teacherBirthdate) {
        this.teacherBirthdate = teacherBirthdate;
    }

    /**
     * 
     */
    public Integer getAffiliateTaroId() {
        return affiliateTaroId;
    }

    /**
     * 
     */
    public void setAffiliateTaroId(Integer affiliateTaroId) {
        this.affiliateTaroId = affiliateTaroId;
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
        Teacher other = (Teacher) that;
        return (this.getTeacherId() == null ? other.getTeacherId() == null : this.getTeacherId().equals(other.getTeacherId()))
            && (this.getTeacherName() == null ? other.getTeacherName() == null : this.getTeacherName().equals(other.getTeacherName()))
            && (this.getTeacherGender() == null ? other.getTeacherGender() == null : this.getTeacherGender().equals(other.getTeacherGender()))
            && (this.getTeacherAge() == null ? other.getTeacherAge() == null : this.getTeacherAge().equals(other.getTeacherAge()))
            && (this.getTeacherPhone() == null ? other.getTeacherPhone() == null : this.getTeacherPhone().equals(other.getTeacherPhone()))
            && (this.getTeacherTitle() == null ? other.getTeacherTitle() == null : this.getTeacherTitle().equals(other.getTeacherTitle()))
            && (this.getTeacherDegree() == null ? other.getTeacherDegree() == null : this.getTeacherDegree().equals(other.getTeacherDegree()))
            && (this.getTeacherBirthdate() == null ? other.getTeacherBirthdate() == null : this.getTeacherBirthdate().equals(other.getTeacherBirthdate()))
            && (this.getAffiliateTaroId() == null ? other.getAffiliateTaroId() == null : this.getAffiliateTaroId().equals(other.getAffiliateTaroId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getTeacherId() == null) ? 0 : getTeacherId().hashCode());
        result = prime * result + ((getTeacherName() == null) ? 0 : getTeacherName().hashCode());
        result = prime * result + ((getTeacherGender() == null) ? 0 : getTeacherGender().hashCode());
        result = prime * result + ((getTeacherAge() == null) ? 0 : getTeacherAge().hashCode());
        result = prime * result + ((getTeacherPhone() == null) ? 0 : getTeacherPhone().hashCode());
        result = prime * result + ((getTeacherTitle() == null) ? 0 : getTeacherTitle().hashCode());
        result = prime * result + ((getTeacherDegree() == null) ? 0 : getTeacherDegree().hashCode());
        result = prime * result + ((getTeacherBirthdate() == null) ? 0 : getTeacherBirthdate().hashCode());
        result = prime * result + ((getAffiliateTaroId() == null) ? 0 : getAffiliateTaroId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", teacherId=").append(teacherId);
        sb.append(", teacherName=").append(teacherName);
        sb.append(", teacherGender=").append(teacherGender);
        sb.append(", teacherAge=").append(teacherAge);
        sb.append(", teacherPhone=").append(teacherPhone);
        sb.append(", teacherTitle=").append(teacherTitle);
        sb.append(", teacherDegree=").append(teacherDegree);
        sb.append(", teacherBirthdate=").append(teacherBirthdate);
        sb.append(", affiliateTaroId=").append(affiliateTaroId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}