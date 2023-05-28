package com.examsystem.service;

import com.examsystem.entity.Subject;
import com.examsystem.viewmodel.admin.education.SubjectPageRequestVM;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 学科相关
 */
public interface SubjectService {
    //搜索所有学科
    List<Subject> allSubject();

    //根据ID查询学科
    Subject selectById(Integer id);

    //分页查询学科
    PageInfo<Subject> page(SubjectPageRequestVM subjectPageRequestVM);

    //添加学科
    void insertSubject(Subject subject);

    //更新学科
    void updateSubject(Subject subject);

    //根据学科以及年级查询
    int selectLevelAndName(Subject subject);
}
