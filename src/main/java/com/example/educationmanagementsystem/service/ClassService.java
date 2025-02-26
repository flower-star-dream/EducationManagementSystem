package com.example.educationmanagementsystem.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.educationmanagementsystem.dto.ClassDto;
import java.util.List;

public interface ClassService {





    /**
     * 新建班级
     * @param classDto
     */
   void addClass(ClassDto classDto);

   /**
     * 更新班级
     * @param id
     * @param classDto
     */
   void updateClass(Integer id, ClassDto classDto);

   /**
     * 删除班级
     * @param id
     */
   void deleteClass(Integer id);

   /**
     * 批量删除班级ID列表
     * @param ids
     */
   void batchDelete(List<Integer> ids);

   /**
     * 分页查询班级信息
     * @param pageNum
     * @param pageSize
     * @param classId
     * @param className
     * @param affiliate_major_name
     * @return
     */
   IPage<ClassDto> getClassInfo(Integer pageNum, Integer pageSize, Integer classId, String className, String affiliate_major_name);

   /**
    * 检查专业是否存在
    * @param majorName
    * @return
    */
   Boolean getClassCheck(String majorName);
}
