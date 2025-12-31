package com.zz.zzojbackendserviceclient.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zz.zzojbackendcommon.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.zz.zzojbackendcommon.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.zz.zzojbackendcommon.model.entity.QuestionSubmit;
import com.zz.zzojbackendcommon.model.entity.User;
import com.zz.zzojbackendcommon.model.vo.QuestionSubmitVO;


/**
* @author 陈长江
* @description 针对表【question_submit(题目提交)】的数据库操作Service
* @createDate 2025-12-17 23:01:00
*/
public interface QuestionSubmitService extends IService<QuestionSubmit> {
    /**
     * 题目提交
     *
     * @param questionSubmitAddRequest  题目提交信息
     * @param loginUser
     * @return
     */
    Long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser);

    /**
     * 获取查询条件
     *
     * @param questionSubmitQueryRequest
     * @return
     */
    QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest);

    /**
     * 获取题目封装
     *
     * @param questionSubmit
     * @param loginUser
     * @return
     */
    QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit, User loginUser);

    /**
     * 分页获取题目封装
     *
     * @param questionSubmitPage
     * @param loginUser
     * @return
     */
    Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage, User loginUser);
}
