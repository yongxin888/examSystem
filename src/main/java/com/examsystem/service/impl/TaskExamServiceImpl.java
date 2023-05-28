package com.examsystem.service.impl;

import com.examsystem.entity.ExamPaper;
import com.examsystem.entity.TaskExam;
import com.examsystem.entity.TextContent;
import com.examsystem.entity.enums.ActionEnum;
import com.examsystem.entity.task.TaskItemObject;
import com.examsystem.mapper.ExamPaperMapper;
import com.examsystem.mapper.TaskExamMapper;
import com.examsystem.mapper.TextContentMapper;
import com.examsystem.service.TaskExamService;
import com.examsystem.utility.JsonUtil;
import com.examsystem.utility.ModelMapperSingle;
import com.examsystem.viewmodel.admin.exam.ExamResponseVM;
import com.examsystem.viewmodel.admin.task.TaskPageRequestVM;
import com.examsystem.viewmodel.admin.task.TaskRequestVM;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: 任务相关
 * @DATE: 2023/5/16 20:12
 */
@Service
public class TaskExamServiceImpl implements TaskExamService {

    @Autowired
    private TaskExamMapper taskExamMapper;

    @Autowired
    private TextContentMapper textContentMapper;

    @Autowired
    private ExamPaperMapper examPaperMapper;

    /**
     * 分页查询任务列表
     * @param requestVM 请求数据
     * @return
     */
    @Override
    public PageInfo<TaskExam> page(TaskPageRequestVM requestVM) {
        //开启分页
        return PageHelper.startPage(requestVM.getPageIndex(), requestVM.getPageSize(), "id desc").doSelectPageInfo(() ->
                taskExamMapper.page(requestVM)
        );
    }

    /**
     * 添加任务
     * @param taskRequest 请求数据
     */
    @Override
    @Transactional
    public void edit(TaskRequestVM taskRequest) {
        //判断传过来的数据是否有ID，如果有则进行更新操作，如果没有，则进行添加操作
        ActionEnum actionEnum = (taskRequest.getId() == null) ? ActionEnum.ADD : ActionEnum.UPDATE;
        TaskExam taskExam = null;

        //添加操作
        if (actionEnum == ActionEnum.ADD) {
            Date now = new Date();
            //数据taskRequest拷贝到taskExam
            taskExam = ModelMapperSingle.Instance().map(taskRequest, TaskExam.class);
            taskExam.setCreateUser(2);  //用户信息先手动填写，后期再自动获取
            taskExam.setCreateUserName("admin");    //用户名
            taskExam.setCreateTime(now);    //时间
            taskExam.setDeleted(false);     //是否删除

            //保存试卷任务结构（将内容转化为json）
            TextContent textContent = JsonUtil.jsonConvertInsert(taskRequest.getPaperItems(), now, p -> {
                TaskItemObject taskItemObject = new TaskItemObject();
                taskItemObject.setExamPaperId(p.getId());       //试卷ID
                taskItemObject.setExamPaperName(p.getName());   //试卷名称
                return taskItemObject;
            });

            //将试卷结构添加到文本表中
            textContentMapper.insertTextContent(textContent);
            //获取文本表中的ID
            taskExam.setFrameTextContentId(textContent.getId());
            //添加任务
            taskExamMapper.insertTaskExam(taskExam);
        }else {
            //根据ID查询任务
            taskExam = taskExamMapper.selectTaskExamById(taskRequest.getId());
            //数据拷贝taskRequest拷贝到taskExam
            ModelMapperSingle.Instance().map(taskRequest, taskExam);

            //获取任务的试卷ID，查询文本表
            TextContent textContent = textContentMapper.selectById(taskExam.getFrameTextContentId());
            //将Json数据转换成集合对象,遍历出试卷的ID
            List<Integer> collect = JsonUtil.toJsonListObject(textContent.getContent(), TaskItemObject.class).stream().map(
                    d -> d.getExamPaperId()).collect(Collectors.toList());

            //清空试卷任务的试卷Id，后面会统一设置
            examPaperMapper.clearTaskPaper(collect);

            //更新任务结构 将文本内容转化为json，然后set到textContent中
            JsonUtil.jsonConvertUpdate(textContent, taskRequest.getPaperItems(), p -> {
                TaskItemObject taskItemObject = new TaskItemObject();
                taskItemObject.setExamPaperId(p.getId());
                taskItemObject.setExamPaperName(p.getName());
                return taskItemObject;
            });

            //更新文本表
            textContentMapper.updateTextContentById(textContent);
            //更新任务表
            taskExamMapper.updateTaskExamById(taskExam);
        }

        //遍历任务的试卷ID，形成一个新的list集合
        List<Integer> paperIds = taskRequest.getPaperItems().stream().map(d -> d.getId()).collect(Collectors.toList());

        //更新试卷的任务Id
        examPaperMapper.updateTaskPaper(taskExam.getId(), paperIds);
        taskRequest.setId(taskExam.getId());
    }

    /**
     * 编辑任务，查询任务详情
     * @param id id
     * @return
     */
    @Override
    public TaskRequestVM taskExamToVM(Integer id) {
        //根据ID查询任务
        TaskExam taskExam = taskExamMapper.selectTaskExamById(id);

        //数据拷贝 将taskExam拷贝到TaskRequestVM
        TaskRequestVM map = ModelMapperSingle.Instance().map(taskExam, TaskRequestVM.class);
        //根据ID查询试卷框架（文本表）
        TextContent textContent = textContentMapper.selectById(taskExam.getFrameTextContentId());
        //将Json数据转换成集合对象
        List<ExamResponseVM> collect = JsonUtil.toJsonListObject(textContent.getContent(), TaskItemObject.class).stream().map(t -> {
            //根据试卷ID查询试卷
            ExamPaper examPaper = examPaperMapper.selectExamPaperById(t.getExamPaperId());
            ExamResponseVM examResponseVM = ModelMapperSingle.Instance().map(examPaper, ExamResponseVM.class);
            return examResponseVM;
        }).collect(Collectors.toList());

        //设置试卷
        map.setPaperItems(collect);
        return map;
    }

    /**
     * 根据ID查询任务
     * @param id id
     * @return
     */
    @Override
    public TaskExam selectTaskExamById(Integer id) {
        return taskExamMapper.selectTaskExamById(id);
    }

    @Override
    public void updateTaskExamById(TaskExam taskExam) {
        taskExamMapper.updateTaskExamById(taskExam);
    }
}
