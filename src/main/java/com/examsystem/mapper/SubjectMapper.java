package com.examsystem.mapper;

import com.examsystem.entity.Subject;
import com.examsystem.viewmodel.admin.education.SubjectPageRequestVM;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 学科相关
 */
@Mapper
public interface SubjectMapper {
    //搜索所有学科
    List<Subject> allSubject();

    //根据ID查询学科
    Subject selectById(Integer id);

    //根据条件查询学科
    List<Subject> page(SubjectPageRequestVM subjectPageRequest);

    //添加学科
    void insertSubject(Subject subject);

    //更新学科
    void updateSubject(Subject subject);

    //根据学科以及年级查询
    int selectLevelAndName(Subject subject);
}
