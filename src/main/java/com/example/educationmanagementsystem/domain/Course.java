package com.example.educationmanagementsystem.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * 
 * @TableName course
 */
@TableName(value ="course")
public class Course implements Serializable {
    /**
     * 
     */
    @TableId
    private Integer courseId;

    /**
     * 
     */
    private String courseName;

    /**
     * 
     */
    private Integer courseCredit;

    /**
     * 
     */
    private Integer courseTime;

    /**
     * 
     */
    private String courseRoom;

    /**
     * 
     */
    private Integer affiliateTaroId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public Integer getCourseId() {
        return courseId;
    }

    /**
     * 
     */
    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    /**
     * 
     */
    public String getCourseName() {
        return courseName;
    }

    /**
     * 
     */
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    /**
     * 
     */
    public Integer getCourseCredit() {
        return courseCredit;
    }

    /**
     * 
     */
    public void setCourseCredit(Integer courseCredit) {
        this.courseCredit = courseCredit;
    }

    /**
     * 
     */
    public Integer getCourseTime() {
        return courseTime;
    }

    /**
     * 
     */
    public void setCourseTime(Integer courseTime) {
        this.courseTime = courseTime;
    }

    /**
     * 
     */
    public String getCourseRoom() {
        return courseRoom;
    }

    /**
     * 
     */
    public void setCourseRoom(String courseRoom) {
        this.courseRoom = courseRoom;
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
        Course other = (Course) that;
        return (this.getCourseId() == null ? other.getCourseId() == null : this.getCourseId().equals(other.getCourseId()))
            && (this.getCourseName() == null ? other.getCourseName() == null : this.getCourseName().equals(other.getCourseName()))
            && (this.getCourseCredit() == null ? other.getCourseCredit() == null : this.getCourseCredit().equals(other.getCourseCredit()))
            && (this.getCourseTime() == null ? other.getCourseTime() == null : this.getCourseTime().equals(other.getCourseTime()))
            && (this.getCourseRoom() == null ? other.getCourseRoom() == null : this.getCourseRoom().equals(other.getCourseRoom()))
            && (this.getAffiliateTaroId() == null ? other.getAffiliateTaroId() == null : this.getAffiliateTaroId().equals(other.getAffiliateTaroId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCourseId() == null) ? 0 : getCourseId().hashCode());
        result = prime * result + ((getCourseName() == null) ? 0 : getCourseName().hashCode());
        result = prime * result + ((getCourseCredit() == null) ? 0 : getCourseCredit().hashCode());
        result = prime * result + ((getCourseTime() == null) ? 0 : getCourseTime().hashCode());
        result = prime * result + ((getCourseRoom() == null) ? 0 : getCourseRoom().hashCode());
        result = prime * result + ((getAffiliateTaroId() == null) ? 0 : getAffiliateTaroId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", courseId=").append(courseId);
        sb.append(", courseName=").append(courseName);
        sb.append(", courseCredit=").append(courseCredit);
        sb.append(", courseTime=").append(courseTime);
        sb.append(", courseRoom=").append(courseRoom);
        sb.append(", affiliateTaroId=").append(affiliateTaroId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}