package com.examsystem.service.impl;

import com.examsystem.entity.Question;
import com.examsystem.entity.Subject;
import com.examsystem.entity.TextContent;
import com.examsystem.entity.enums.QuestionStatusEnum;
import com.examsystem.entity.enums.QuestionTypeEnum;
import com.examsystem.entity.question.QuestionItemObject;
import com.examsystem.entity.question.QuestionObject;
import com.examsystem.mapper.QuestionMapper;
import com.examsystem.mapper.SubjectMapper;
import com.examsystem.mapper.TextContentMapper;
import com.examsystem.service.QuestionService;
import com.examsystem.utility.ExamUtil;
import com.examsystem.utility.JsonUtil;
import com.examsystem.utility.ModelMapperSingle;
import com.examsystem.viewmodel.admin.question.QuestionEditItemVM;
import com.examsystem.viewmodel.admin.question.QuestionEditRequestVM;
import com.examsystem.viewmodel.admin.question.QuestionPageRequestVM;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @PROJECT_NAME: examSystem
 * @DESCRIPTION: 题目相关
 * @DATE: 2023/5/8 18:23
 */
@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private TextContentMapper textContentMapper;

    @Autowired
    private SubjectMapper subjectMapper;

    /**
     * 查询题目总条数
     * @return
     */
    @Override
    public int selectAllCount() {
        return questionMapper.selectAllCount();
    }

    /**
     * 根据条件分页查询题目
     * @param requestVM 请求数据
     * @return
     */
    @Override
    public PageInfo<Question> page(QuestionPageRequestVM requestVM) {
        //开启分页
        return PageHelper.startPage(requestVM.getPageIndex(), requestVM.getPageSize(), "id desc").doSelectPageInfo(
                () -> questionMapper.page(requestVM)
        );
    }

    /**
     * 根据ID查询题目信息数据
     * @param questionId 题目表id
     * @return
     */
    @Override
    public QuestionEditRequestVM getQuestionEditRequestVM(Integer questionId) {
        Question question = questionMapper.selectQuestionById(questionId);
        return getQuestionEditRequestVM(question);
    }

    /**
     * 将题目表中的数据映射到 QuestionEditRequestVM 中
     * @param question 题目表数据
     * @return
     */
    public QuestionEditRequestVM getQuestionEditRequestVM(Question question) {
        //查询表中的具体题目信息
        TextContent textContent = textContentMapper.selectById(question.getInfoTextContentId());
        //将文本信息转换成对象
        QuestionObject questionObject = JsonUtil.toJsonObject(textContent.getContent(), QuestionObject.class);
        //将数据映射到 QuestionEditRequestVM 中
        QuestionEditRequestVM questionEditRequestVM = ModelMapperSingle.Instance().map(question, QuestionEditRequestVM.class);
        //设置题干信息
        questionEditRequestVM.setTitle(questionObject.getTitleContent());
        //获取试卷标题
        QuestionTypeEnum questionTypeEnum = QuestionTypeEnum.fromCode(question.getQuestionType());

        //根据题目类型获取答案 单选题则把答案设置到Correct中，多选则设置到CorrectArray中
        switch (questionTypeEnum) {
            case SingleChoice:
            case TrueFalse:
                //单选和判断，直接存入Correct
                questionEditRequestVM.setCorrect(question.getCorrect());
                break;
            case MultipleChoice:
                //多选存入CorrectArray集合中
                questionEditRequestVM.setCorrectArray(ExamUtil.contentToArray(question.getCorrect()));
                break;
            case GapFilling:
                //填空题则遍历每个集合拿到答案，并存入集合中
                List<String> correctContent = questionObject.getQuestionItemObjects().stream().map(
                        d -> d.getContent()).collect(Collectors.toList());
                questionEditRequestVM.setCorrectArray(correctContent);
                break;
            case ShortAnswer:
                //简答题
                questionEditRequestVM.setCorrect(questionObject.getCorrect());
                break;
            default:
                break;
        }

        //设置题目分数
        questionEditRequestVM.setScore(ExamUtil.scoreToVM(question.getScore()));
        //设置题目解析
        questionEditRequestVM.setAnalyze(questionObject.getAnalyze());

        //题目选项映射
        List<QuestionEditItemVM> collect = questionObject.getQuestionItemObjects().stream().map(p -> {
            //选项拷贝
            QuestionEditItemVM questionEditItemVM = ModelMapperSingle.Instance().map(p, QuestionEditItemVM.class);
            if (p.getScore() != null) {
                //设置选项分数
                questionEditItemVM.setScore(ExamUtil.scoreToVM(p.getScore()));
            }
            return questionEditItemVM;
        }).collect(Collectors.toList());

        //将选项信息设置到 questionEditRequestVM 中
        questionEditRequestVM.setItems(collect);
        return questionEditRequestVM;
    }

    /**
     * 添加题目
     * @param questionEditRequest 请求数据
     * @return
     */
    @Override
    @Transactional
    public Question insertQuestion(QuestionEditRequestVM questionEditRequest) {
        Date now = new Date();

        //根据ID查询学科
        Subject subject = subjectMapper.selectById(questionEditRequest.getSubjectId());

        //将题干、解析、选项等信息插入文本表
        TextContent textContent = new TextContent();
        textContent.setCreateTime(now);

        //将题目信息转换成文本
        setQuestionInfoTextContent(textContent, questionEditRequest);

        //新增文本信息
        textContentMapper.insertTextContent(textContent);

        //设置题目数据
        Question question = new Question();
        question.setSubjectId(questionEditRequest.getSubjectId());  //学科ID
        question.setGradeLevel(subject.getLevel()); //年级
        question.setCreateTime(now);    //发布时间
        question.setQuestionType(questionEditRequest.getQuestionType());   //题型
        question.setStatus(QuestionStatusEnum.OK.getCode());   //题目状态
        question.setCorrectFromVM(questionEditRequest.getCorrect(), questionEditRequest.getCorrectArray()); //题目答案
        question.setScore(ExamUtil.scoreFromVM(questionEditRequest.getScore()));    //题目总分
        question.setDifficult(questionEditRequest.getDifficult());  //题目难度
        question.setInfoTextContentId(textContent.getId()); //文本信息ID 题目、解析、答案等信息
        question.setCreateUser(2);  //添加的用户，暂时写为管理员，后续动态获取
        question.setDeleted(false); //是否删除

        //新增题目数据
        questionMapper.insertQuestion(question);
        return question;
    }

    /**
     * 更新题目
     * @param questionEditRequest 请求数据
     * @return
     */
    @Override
    @Transactional
    public Question updateQuestion(QuestionEditRequestVM questionEditRequest) {
        //根据ID查询学科
        Subject subject = subjectMapper.selectById(questionEditRequest.getSubjectId());
        //根据ID查询题目
        Question question = questionMapper.selectQuestionById(questionEditRequest.getId());

        //重新设置题目信息
        question.setSubjectId(questionEditRequest.getSubjectId());  //学科
        question.setGradeLevel(subject.getLevel()); //年级
        question.setScore(ExamUtil.scoreFromVM(questionEditRequest.getScore()));    //总分
        question.setDifficult(questionEditRequest.getDifficult());  //题目难度
        question.setCorrectFromVM(questionEditRequest.getCorrect(), questionEditRequest.getCorrectArray()); //正确答案

        //更新题目表
        questionMapper.updateQuestion(question);

        //将题干、解析、选项等信息在文本表中更新
        TextContent textContent = textContentMapper.selectById(question.getInfoTextContentId());
        setQuestionInfoTextContent(textContent, questionEditRequest);

        //更新文本表信息
        textContentMapper.updateTextContentById(textContent);
        return question;
    }

    /**
     * 根据ID查询题目信息
     * @param id
     * @return
     */
    @Override
    public Question selectById(Integer id) {
        return questionMapper.selectQuestionById(id);
    }

    /**
     * 更新题目（删除题目）
     * @param question
     * @return
     */
    @Override
    public int deleteQuestionById(Question question) {
        return questionMapper.updateQuestion(question);
    }

    /**
     * 将题目信息转换成文本
     * @param textContent 文本信息
     * @param questionEditRequest 题目请求数据
     */
    public void setQuestionInfoTextContent(TextContent textContent, QuestionEditRequestVM questionEditRequest) {
        //遍历题目选项数据
        List<QuestionItemObject> questionItemObjects = questionEditRequest.getItems().stream().map(i -> {

            //数据拷贝 题目选项数据
            QuestionItemObject questionItemObject = ModelMapperSingle.Instance().map(i, QuestionItemObject.class);
            if (i.getScore() != null) {
                //重新设置选项分数
                questionItemObject.setScore(ExamUtil.scoreFromVM(i.getScore()));
            }

            return questionItemObject;
        }).collect(Collectors.toList());

        //设置文本内容
        QuestionObject questionObject = new QuestionObject();
        questionObject.setQuestionItemObjects(questionItemObjects); //选项数据
        questionObject.setAnalyze(questionEditRequest.getAnalyze()); //解析
        questionObject.setTitleContent(questionEditRequest.getTitle()); //题干
        questionObject.setCorrect(questionEditRequest.getCorrect()); //正确选项

        //将对象转为字符串
        textContent.setContent(JsonUtil.toJsonStr(questionObject));
    }
}
