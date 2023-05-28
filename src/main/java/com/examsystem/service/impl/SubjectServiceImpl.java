package com.examsystem.service.impl;

import com.examsystem.entity.Subject;
import com.examsystem.mapper.SubjectMapper;
import com.examsystem.service.SubjectService;
import com.examsystem.viewmodel.admin.education.SubjectPageRequestVM;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: 学科相关
 * @DATE: 2023/5/12 15:28
 */
@Service
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private SubjectMapper subjectMapper;

    /**
     * 搜索所有学科
     * @return
     */
    @Override
    public List<Subject> allSubject() {
        return subjectMapper.allSubject();
    }

    /**
     * 根据ID查询学科
     * @param id
     * @return
     */
    @Override
    public Subject selectById(Integer id) {
        return subjectMapper.selectById(id);
    }

    /**
     * 分页查询学科
     * @param subjectPageRequestVM 请求数据
     * @return
     */
    @Override
    public PageInfo<Subject> page(SubjectPageRequestVM subjectPageRequestVM) {
        //开启分页
        return PageHelper.startPage(subjectPageRequestVM.getPageIndex(), subjectPageRequestVM.getPageSize(),
                "id desc").doSelectPageInfo(() -> subjectMapper.page(subjectPageRequestVM)
        );
    }

    /**
     * 添加学科
     * @param subject 学科数据
     */
    @Override
    public void insertSubject(Subject subject) {
        subjectMapper.insertSubject(subject);
    }

    /**
     * 更新学科
     * @param subject 学科数据
     */
    @Override
    public void updateSubject(Subject subject) {
        subjectMapper.updateSubject(subject);
    }

    /**
     * 根据学科以及年级查询
     * @param subject 学科数据
     * @return
     */
    @Override
    public int selectLevelAndName(Subject subject) {
        return subjectMapper.selectLevelAndName(subject);
    }
}
