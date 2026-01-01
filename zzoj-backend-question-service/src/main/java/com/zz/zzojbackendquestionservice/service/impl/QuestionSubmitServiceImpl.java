package com.zz.zzojbackendquestionservice.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zz.zzojbackendcommon.common.ErrorCode;
import com.zz.zzojbackendcommon.constant.CommonConstant;
import com.zz.zzojbackendcommon.exception.BusinessException;
import com.zz.zzojbackendcommon.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.zz.zzojbackendcommon.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.zz.zzojbackendcommon.model.entity.Question;
import com.zz.zzojbackendcommon.model.entity.QuestionSubmit;
import com.zz.zzojbackendcommon.model.entity.User;
import com.zz.zzojbackendcommon.model.enums.QuestionSubmitEnum;
import com.zz.zzojbackendcommon.model.enums.QuestionSubmitLanguageEnum;
import com.zz.zzojbackendcommon.model.enums.QuestionSubmitStatusEnum;
import com.zz.zzojbackendcommon.model.vo.QuestionSubmitVO;
import com.zz.zzojbackendcommon.model.vo.UserVO;
import com.zz.zzojbackendcommon.utils.SqlUtils;
import com.zz.zzojbackendquestionservice.mapper.QuestionSubmitMapper;
import com.zz.zzojbackendquestionservice.rabbitmq.MyMessageProducer;
import com.zz.zzojbackendquestionservice.service.QuestionService;
import com.zz.zzojbackendquestionservice.service.QuestionSubmitService;
import com.zz.zzojbackendserviceclient.service.JudgeFeignClient;
import com.zz.zzojbackendserviceclient.service.UserFeignClient;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
* @author 陈长江
* @description 针对表【question_submit(题目提交)】的数据库操作Service实现
* @createDate 2025-12-17 23:01:00
*/
@Service
public class QuestionSubmitServiceImpl extends ServiceImpl<QuestionSubmitMapper, QuestionSubmit>
    implements QuestionSubmitService {

    @Resource
    private QuestionService questionService;

    @Resource
    private UserFeignClient userFeignClient;
    @Resource
    @Lazy
    private JudgeFeignClient judgeFeignClient;

    @Resource
    private MyMessageProducer myMessageProducer;

    /**
     * 提交题目
     *
     * @param questionSubmitAddRequest
     * @param loginUser
     * @return
     */
    @Override
    public Long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser) {
        String language= questionSubmitAddRequest.getLanguage();
        QuestionSubmitLanguageEnum languageEnum = QuestionSubmitLanguageEnum.getEnumByValue(language);
        if(languageEnum==null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"编程语言错误");
        }
        long questionId= questionSubmitAddRequest.getQuestionId();
        // 判断实体是否存在，根据类别获取实体
        Question question = questionService.getById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 是否已提交题目
        long userId = loginUser.getId();
        // 每个用户串行提交题目
        // 锁必须要包裹住事务方法
        QuestionSubmit questionSubmit = new QuestionSubmit();
        questionSubmit.setUserId(userId);
        questionSubmit.setQuestionId(questionId);
        questionSubmit.setCode(questionSubmitAddRequest.getCode());
        questionSubmit.setLanguage(language);
        questionSubmit.setStatus(QuestionSubmitStatusEnum.WATING.getValue());
        questionSubmit.setJudgeInfo("{}");
        boolean save = this.save(questionSubmit);
        if(!save){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"数据输入失败");
        }
        Long questionSubmitId = questionSubmit.getId();
        //执行判题服务
        /*CompletableFuture.runAsync(() -> {
            judgeFeignClient.doJudge(questionSubmitId);
        });*/
        myMessageProducer.sendMessage("code_exchange","my_routingKey",String.valueOf(questionSubmitId));
        return questionSubmitId;
    }


    /**
     * 获取查询包装类，根据前端请求对象，得到查询
     *
     * @param questionSubmitQueryRequest
     * @return
     */
    @Override
    public QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest) {
        QueryWrapper<QuestionSubmit> queryWrapper = new QueryWrapper<>();
        if (questionSubmitQueryRequest == null) {
            return queryWrapper;
        }

        String language = questionSubmitQueryRequest.getLanguage();
        Long userId = questionSubmitQueryRequest.getUserId();
        Long questionId = questionSubmitQueryRequest.getQuestionId();
        String code = questionSubmitQueryRequest.getCode();
        Integer status = questionSubmitQueryRequest.getStatus();
        String sortField = questionSubmitQueryRequest.getSortField();
        String sortOrder = questionSubmitQueryRequest.getSortOrder();


        //拼接查询条件
        queryWrapper.eq(StringUtils.isNotBlank(language), "language", language);
        queryWrapper.eq(ObjectUtils.isNotEmpty(userId), "userId", userId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(questionId), "questionId", questionId);
        queryWrapper.eq(QuestionSubmitEnum.getEnumByValue(status) != null, "status", status);
        queryWrapper.eq("isDelete", false);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

    @Override
    public QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit, User loginUser) {
        QuestionSubmitVO questionSubmitVO = QuestionSubmitVO.objToVo(questionSubmit);
        Long userId = questionSubmit.getUserId();
        User submitUser= userFeignClient.getById(userId);
        if(!Objects.equals(userId, questionSubmit.getUserId())&&!userFeignClient.isAdmin(loginUser)) {
            questionSubmitVO.setCode(null);
        }
        UserVO userVO = userFeignClient.getUserVO(submitUser);
        questionSubmitVO.setUserVO(userVO);
        return questionSubmitVO;
    }

    @Override
    public Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage, User loginUser) {
        List<QuestionSubmit> questionSubmitList = questionSubmitPage.getRecords();
        Page<QuestionSubmitVO> questionSubmitVOPage = new Page<>(questionSubmitPage.getCurrent(), questionSubmitPage.getSize(), questionSubmitPage.getTotal());
        if (CollUtil.isEmpty(questionSubmitList)) {
            return questionSubmitVOPage;
        }
        List<QuestionSubmitVO> questionSubmitVOList=questionSubmitList.stream().map(questionSubmit -> {
            return getQuestionSubmitVO(questionSubmit,loginUser);
        }).collect(Collectors.toList());
        questionSubmitVOPage.setRecords(questionSubmitVOList);
        return questionSubmitVOPage;
    }

}




