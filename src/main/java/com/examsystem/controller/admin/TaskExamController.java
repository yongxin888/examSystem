package com.examsystem.controller.admin;

import com.examsystem.common.RestResponse;
import com.examsystem.entity.TaskExam;
import com.examsystem.service.TaskExamService;
import com.examsystem.utility.ModelMapperSingle;
import com.examsystem.utility.PageInfoHelperUtil;
import com.examsystem.viewmodel.admin.task.TaskPageRequestVM;
import com.examsystem.viewmodel.admin.task.TaskPageResponseVM;
import com.examsystem.viewmodel.admin.task.TaskRequestVM;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: 任务管理
 * @DATE: 2023/5/15 20:13
 */
@RestController
@RequestMapping(value = "/api/admin/task")
public class TaskExamController {

    @Autowired
    private TaskExamService taskExamService;

    /**
     * 任务列表分页
     * @param taskPageRequest
     * @return
     */
    @PostMapping("/page")
    public RestResponse<PageInfo<TaskPageResponseVM>> pageList(@RequestBody TaskPageRequestVM taskPageRequest) {
        //分页查询任务列表
        PageInfo<TaskExam> pageInfo = taskExamService.page(taskPageRequest);
        //PageInfo转换
        PageInfo<TaskPageResponseVM> taskPageResponseVM = PageInfoHelperUtil.copyMap(pageInfo, t -> {
            //对象拷贝映射  PageInfo<TaskExam>拷贝到TaskPageResponseVM
            TaskPageResponseVM map = ModelMapperSingle.Instance().map(t, TaskPageResponseVM.class);
            return map;
        });
        return RestResponse.ok(taskPageResponseVM);
    }

    /**
     * 添加任务
     * @param taskRequest 请求数据
     * @return
     */
    @PostMapping("/edit")
    public RestResponse edit(@RequestBody @Valid TaskRequestVM taskRequest) {
        taskExamService.edit(taskRequest);
        return RestResponse.ok();
    }

    /**
     * 编辑任务
     * @param id
     * @return
     */
    @PostMapping("/select/{id}")
    public RestResponse<TaskRequestVM> select(@PathVariable Integer id) {
        TaskRequestVM taskRequestVM = taskExamService.taskExamToVM(id);
        return RestResponse.ok(taskRequestVM);
    }

    /**
     * 删除任务
     * @param id
     * @return
     */
    @PostMapping("/delete/{id}")
    public RestResponse delete(@PathVariable Integer id) {
        TaskExam taskExam = taskExamService.selectTaskExamById(id);
        taskExam.setDeleted(true);
        taskExamService.updateTaskExamById(taskExam);
        return RestResponse.ok();
    }
}
