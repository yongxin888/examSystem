package com.examsystem.controller.admin;

import com.examsystem.common.RestResponse;
import com.examsystem.common.SystemCode;
import com.examsystem.entity.Subject;
import com.examsystem.service.SubjectService;
import com.examsystem.utility.ModelMapperSingle;
import com.examsystem.utility.PageInfoHelperUtil;
import com.examsystem.viewmodel.admin.education.SubjectEditRequestVM;
import com.examsystem.viewmodel.admin.education.SubjectPageRequestVM;
import com.examsystem.viewmodel.admin.education.SubjectResponseVM;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: 学科管理
 * @DATE: 2023/5/12 15:27
 */
@RestController
@RequestMapping(value = "/api/admin/education")
public class EducationController {

    @Autowired
    private SubjectService subjectService;

    /**
     * 搜索所有学科
     * @return
     */
    @PostMapping("/subject/list")
    public RestResponse<List<Subject>> list() {
        List<Subject> subjects = subjectService.allSubject();
        return RestResponse.ok(subjects);
    }

    /**
     * 分页查询学科
     * @param subjectPageRequest 请求数据
     * @return
     */
    @PostMapping("/subject/page")
    public RestResponse<PageInfo<SubjectResponseVM>> pageList(@RequestBody SubjectPageRequestVM subjectPageRequest) {
        //分页查询学科数据
        PageInfo<Subject> page = subjectService.page(subjectPageRequest);
        //pageInfo替换，并数据拷贝
        PageInfo<SubjectResponseVM> pageInfo = PageInfoHelperUtil.copyMap(page,
                p -> ModelMapperSingle.Instance().map(p, SubjectResponseVM.class));
        return RestResponse.ok(pageInfo);
    }

    /**
     * 添加/更新学科
     * @param subjectEditRequest 学科数据
     * @return
     */
    @PostMapping("/subject/edit")
    public RestResponse edit(@RequestBody @Valid SubjectEditRequestVM subjectEditRequest) {
        //数据拷贝
        Subject subject = ModelMapperSingle.Instance().map(subjectEditRequest, Subject.class);

        //判断id是否为空，如果是，则进行添加操作，如果不是，则进行删除操作
        if (subject.getId() == null) {
            //判断所添加的年级是否存在
            int count = subjectService.selectLevelAndName(subject);
            if (count > 0) {
                return RestResponse.fail(SystemCode.DataAlreadyExists.getCode(),SystemCode.DataAlreadyExists.getMessage());
            }

            subject.setDeleted(false);
            //添加学科
            subjectService.insertSubject(subject);
        }else {
            //更新学科
            subjectService.updateSubject(subject);
        }
        return RestResponse.ok();
    }

    /**
     * 删除学科
     * @param id
     * @return
     */
    @PostMapping(("/subject/delete/{id}"))
    public RestResponse delete(@PathVariable Integer id) {
        Subject subject = subjectService.selectById(id);
        subject.setDeleted(true);
        subjectService.updateSubject(subject);
        return RestResponse.ok();
    }
}
