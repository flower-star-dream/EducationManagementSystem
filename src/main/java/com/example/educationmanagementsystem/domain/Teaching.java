package com.example.educationmanagementsystem.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;

import java.io.Serializable;

/**
 * 
 * @TableName teaching
 */
@TableName(value ="teaching")
public class Teaching implements Serializable {
    /**
     * 
     */
    @MppMultiId
    private String teacherId;

    /**
     * 
     */
    @MppMultiId
    private Integer courseId;

    /**
     * 
     */
    private String teachingType;

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
    public String getTeachingType() {
        return teachingType;
    }

    /**
     * 
     */
    public void setTeachingType(String teachingType) {
        this.teachingType = teachingType;
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
        Teaching other = (Teaching) that;
        return (this.getTeacherId() == null ? other.getTeacherId() == null : this.getTeacherId().equals(other.getTeacherId()))
            && (this.getCourseId() == null ? other.getCourseId() == null : this.getCourseId().equals(other.getCourseId()))
            && (this.getTeachingType() == null ? other.getTeachingType() == null : this.getTeachingType().equals(other.getTeachingType()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getTeacherId() == null) ? 0 : getTeacherId().hashCode());
        result = prime * result + ((getCourseId() == null) ? 0 : getCourseId().hashCode());
        result = prime * result + ((getTeachingType() == null) ? 0 : getTeachingType().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", teacherId=").append(teacherId);
        sb.append(", courseId=").append(courseId);
        sb.append(", teachingType=").append(teachingType);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}