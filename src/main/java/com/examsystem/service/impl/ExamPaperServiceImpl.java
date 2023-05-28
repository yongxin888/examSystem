package com.examsystem.service.impl;

import com.examsystem.entity.ExamPaper;
import com.examsystem.entity.Question;
import com.examsystem.entity.Subject;
import com.examsystem.entity.TextContent;
import com.examsystem.entity.enums.ActionEnum;
import com.examsystem.entity.enums.ExamPaperTypeEnum;
import com.examsystem.entity.exam.ExamPaperQuestionItemObject;
import com.examsystem.entity.exam.ExamPaperTitleItemObject;
import com.examsystem.mapper.ExamPaperMapper;
import com.examsystem.mapper.QuestionMapper;
import com.examsystem.mapper.SubjectMapper;
import com.examsystem.mapper.TextContentMapper;
import com.examsystem.service.ExamPaperService;
import com.examsystem.service.QuestionService;
import com.examsystem.utility.*;
import com.examsystem.viewmodel.admin.exam.ExamPaperEditRequestVM;
import com.examsystem.viewmodel.admin.exam.ExamPaperPageRequestVM;
import com.examsystem.viewmodel.admin.exam.ExamPaperTitleItemVM;
import com.examsystem.viewmodel.admin.question.QuestionEditRequestVM;
import com.examsystem.viewmodel.admin.user.UserResponseVM;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: 试卷相关
 * @DATE: 2023/5/8 19:16
 */
@Service
public class ExamPaperServiceImpl implements ExamPaperService {

    @Autowired
    private ExamPaperMapper examPaperMapper;

    @Autowired
    private TextContentMapper textContentMapper;

    @Autowired
    private SubjectMapper subjectMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 查询试卷总数
     * @return
     */
    @Override
    public int selectAllCount() {
        return examPaperMapper.selectAllCount();
    }

    /**
     * 分页查询试卷列表
     * @param requestVM
     * @return
     */
    @Override
    public PageInfo<ExamPaper> page(ExamPaperPageRequestVM requestVM) {
        //开启分页，并根据id进行降序
        return PageHelper.startPage(requestVM.getPageIndex(), requestVM.getPageSize(), "id desc").doSelectPageInfo(
                () -> examPaperMapper.page(requestVM));
    }

    /**
     * 添加试卷
     * @param requestVM 请求数据
     * @return
     */
    @Override
    @Transactional
    public ExamPaper saveExamPaper(ExamPaperEditRequestVM requestVM, HttpServletRequest request) {

        //获取cookie里的uuid
        String uuid = CookieUtil.getCookie(request, RedisKeyUtil.getTicketKey());

        //从Redis获取个人信息数据
        String str = (String) redisTemplate.opsForValue().get(RedisKeyUtil.getUserKey(uuid));

        //反序列化将JSON数据转UserResponseVM对象
        UserResponseVM userResponse = JsonUtil.jsonToPojo(str, UserResponseVM.class);

        //查询前端传过来的数据是否有ID，如果有，则是更新，否则是添加
        ActionEnum actionEnum = (requestVM.getId() == null) ? ActionEnum.ADD : ActionEnum.UPDATE;

        //获取试题题干数据
        List<ExamPaperTitleItemVM> titleItemVMS = requestVM.getTitleItems();
        //将题干数据进行排序
        List<ExamPaperTitleItemObject> frameTextContentList = frameTextContentFromVM(titleItemVMS);
        //将JSON转换为字符串
        String frameTextContentStr = JsonUtil.toJsonStr(frameTextContentList);

        Date now = new Date();
        ExamPaper examPaper;

        //判断操作行为
        if (actionEnum == ActionEnum.ADD) {
            //将前端传过来的数据拷贝到试卷表中
            examPaper = ModelMapperSingle.Instance().map(requestVM, ExamPaper.class);

            //将题目数据保存到文本表中
            TextContent textContent = new TextContent();
            textContent.setContent(frameTextContentStr);
            textContent.setCreateTime(now);
            textContentMapper.insertTextContent(textContent);

            //设置试卷表数据
            examPaper.setFrameTextContentId(textContent.getId());
            examPaper.setCreateTime(now);
            examPaper.setCreateUser(userResponse.getId()); //添加的用户
            examPaper.setDeleted(false);

            //设置试卷表其他相关信息
            examPaperFromVM(requestVM, examPaper, titleItemVMS);

            //添加试卷
            examPaperMapper.insertExamPaper(examPaper);
        }else {
            //根据ID查询试卷
            examPaper = examPaperMapper.selectExamPaperById(requestVM.getId());
            //根据试卷框架ID查询题目数据
            TextContent frameTextContent = textContentMapper.selectById(examPaper.getFrameTextContentId());
            //重新设置题干数据
            frameTextContent.setContent(frameTextContentStr);
            //更新文本信息
            textContentMapper.updateTextContentById(frameTextContent);
            //将前端传过来的数据拷贝到试卷表中
            ModelMapperSingle.Instance().map(requestVM, examPaper);
            //重新设置试卷相关信息
            examPaperFromVM(requestVM, examPaper, titleItemVMS);
            //更新试卷表
            examPaperMapper.updateExamPaperById(examPaper);
        }
        return examPaper;
    }

    /**
     * 根据ID查询试卷并查询题目信息
     * @param id id
     * @return
     */
    @Override
    public ExamPaperEditRequestVM examPaperToId(Integer id) {
        //根据ID查询试卷表
        ExamPaper examPaper = examPaperMapper.selectExamPaperById(id);

        //将 examPaper 映射拷贝到 ExamPaperEditRequestVM
        ExamPaperEditRequestVM examPaperEditRequestVM = ModelMapperSingle.Instance().map(examPaper, ExamPaperEditRequestVM.class);
        examPaperEditRequestVM.setLevel(examPaper.getGradeLevel());

        //查询试卷框架数据
        TextContent textContent = textContentMapper.selectById(examPaper.getFrameTextContentId());

        //将Json字符串数组转换成集合对象
        List<ExamPaperTitleItemObject> examPaperTitleItemObjects = JsonUtil.toJsonListObject(textContent.getContent(), ExamPaperTitleItemObject.class);
        //将子集合抽上来形成一个大集合 将题目列表ID取出来形成一个集合
        List<Integer> collect = examPaperTitleItemObjects.stream().flatMap(
                t -> t.getQuestionItems().stream().map(
                        q -> q.getId())
        ).collect(Collectors.toList());

        //根据ID查询题目表
        List<Question> questions = questionMapper.selectByIds(collect);

        //试卷题干数据
        List<ExamPaperTitleItemVM> examPaperTitleItemVMS = examPaperTitleItemObjects.stream().map(t -> {
            //将试卷试题题干对象拷贝到 ExamPaperTitleItemVM 题干数据中
            ExamPaperTitleItemVM tTitleVM = ModelMapperSingle.Instance().map(t, ExamPaperTitleItemVM.class);

            //遍历题目数据
            List<QuestionEditRequestVM> questionEditVMS = t.getQuestionItems().stream().map(i -> {
                //取出对象集合中指定条件的对象 findFirst().get();是拿第一个值
                Question question = questions.stream().filter(q -> q.getId().equals(i.getId())).findFirst().get();
                //将题目表中的数据进行处理得到完整的题目信息数据
                QuestionEditRequestVM questionEditRequestVM = questionService.getQuestionEditRequestVM(question);
                //设置题序
                questionEditRequestVM.setItemOrder(i.getItemOrder());
                return questionEditRequestVM;
            }).collect(Collectors.toList());

            //将题目数据设置到 ExamPaperTitleItemVM 中
            tTitleVM.setQuestionItems(questionEditVMS);
            return tTitleVM;

        }).collect(Collectors.toList());

        //题干数据
        examPaperEditRequestVM.setTitleItems(examPaperTitleItemVMS);
        //试卷总分
        examPaperEditRequestVM.setScore(ExamUtil.scoreToVM(examPaper.getScore()));

        //判断是否是时段试卷
        if (ExamPaperTypeEnum.TimeLimit.getCode() == examPaper.getPaperType()) {
            //将数组转换成集合
            List<String> limitDateTime = Arrays.asList(DateTimeUtil.dateFormat(examPaper.getLimitStartTime()), DateTimeUtil.dateFormat(examPaper.getLimitEndTime()));
            //设置时段试卷的开始时间和结束时间
            examPaperEditRequestVM.setLimitDateTime(limitDateTime);
        }

        return examPaperEditRequestVM;
    }

    /**
     * 根据ID查询试卷表
     * @param id
     * @return
     */
    @Override
    public ExamPaper selectById(Integer id) {
        return examPaperMapper.selectExamPaperById(id);
    }

    /**
     * 根据ID更新试卷表
     *
     * @param examPaper
     */
    @Override
    public void updateExamPaperById(ExamPaper examPaper) {
        examPaperMapper.updateExamPaperById(examPaper);
    }

    /**
     * 任务管理分页查询试卷
     * @param requestVM
     * @return
     */
    @Override
    public PageInfo<ExamPaper> taskExamPage(ExamPaperPageRequestVM requestVM) {
        //开启分页
        return PageHelper.startPage(requestVM.getPageIndex(), requestVM.getPageSize(), "id desc").doSelectPageInfo(() ->
                examPaperMapper.taskExamPage(requestVM));
    }

    /**
     * 更新试卷任务ID
     * @param taskId 任务ID
     * @param paperIds 试卷ID
     * @return
     */
    @Override
    public int updateTaskPaper(Integer taskId, List<Integer> paperIds) {
        return 0;
    }

    /**
     * 给试题排序
     * @param titleItems 试题题干数据
     * @return 题干数据对象
     */
    private List<ExamPaperTitleItemObject> frameTextContentFromVM(List<ExamPaperTitleItemVM> titleItems) {
        //设置默认序号为1
        AtomicInteger index = new AtomicInteger(1);
        return titleItems.stream().map(t -> {
            //对象映射拷贝
            ExamPaperTitleItemObject titleItem = ModelMapperSingle.Instance().map(t, ExamPaperTitleItemObject.class);

            //拷贝题目列表
            List<ExamPaperQuestionItemObject> examPaperQuestionItemObjects = t.getQuestionItems().stream().map(q -> {
                //将试题题干中的题目列表拷贝到题目列表对象中
                ExamPaperQuestionItemObject examPaperQuestionItemObject = ModelMapperSingle.Instance().map(q, ExamPaperQuestionItemObject.class);

                //设置题目序号 index.getAndIncrement()自增加1
                examPaperQuestionItemObject.setItemOrder(index.getAndIncrement());

                return examPaperQuestionItemObject;
                //Collectors.toList()将流中的所有元素导出到一个列表( List )中
            }).collect(Collectors.toList());

            //将试题题干中的题目列表数据设置到题干对象中
            titleItem.setQuestionItems(examPaperQuestionItemObjects);
            return titleItem;
        }).collect(Collectors.toList());
    }

    /**
     * 设置试卷相关信息
     * @param requestVM 添加试卷请求数据
     * @param examPaper 试卷表
     * @param titleItemsVM 试卷题干数据
     */
    private void examPaperFromVM(ExamPaperEditRequestVM requestVM, ExamPaper examPaper, List<ExamPaperTitleItemVM> titleItemsVM) {
        //查询学科
        Subject subject = subjectMapper.selectById(requestVM.getSubjectId());
        //获取题目数量并求和
        Integer questionCount = titleItemsVM.stream().mapToInt(t -> t.getQuestionItems().size()).sum();
        //获取试卷总分
        Integer score = titleItemsVM.stream().flatMapToInt(t -> t.getQuestionItems().stream().mapToInt(
                q -> ExamUtil.scoreFromVM(q.getScore()))).sum();

        examPaper.setQuestionCount(questionCount);
        examPaper.setScore(score);
        examPaper.setGradeLevel(subject.getLevel());

        //获取限时时间
        List<String> limitDateTime = requestVM.getLimitDateTime();

        //判断试卷类似是否是时段试卷
        if (ExamPaperTypeEnum.TimeLimit.getCode() == examPaper.getPaperType()) {
            //设置试卷开始时间
            examPaper.setLimitStartTime(DateTimeUtil.parse(limitDateTime.get(0), DateTimeUtil.STANDER_FORMAT));
            //设置试卷结束时间
            examPaper.setLimitEndTime(DateTimeUtil.parse(limitDateTime.get(1), DateTimeUtil.STANDER_FORMAT));
        }
    }
}
