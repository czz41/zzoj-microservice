package com.zz.zzojbackendquestionservice.controller.inner;

import com.zz.zzojbackendcommon.model.entity.Question;
import com.zz.zzojbackendcommon.model.entity.QuestionSubmit;
import com.zz.zzojbackendquestionservice.service.QuestionService;
import com.zz.zzojbackendquestionservice.service.QuestionSubmitService;
import com.zz.zzojbackendserviceclient.service.QuestionFeignClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/inner")
public class QuestionInnerController implements QuestionFeignClient {
    @Resource
    private QuestionService questionService;

    @Resource
    private QuestionSubmitService questionSubmitService;
    @GetMapping("get/id")
    @Override
    public Question getQuestionById(@RequestParam("questionId") long questionId){
        return questionService.getById(questionId);
    }

    @GetMapping("/question_submit/get/id")
    @Override
    public QuestionSubmit getQuestionSubmitById(@RequestParam("questionId") long questionId){
        return questionSubmitService.getById(questionId);
    }

    @GetMapping("/question_submit/update")
    @Override
    public boolean updateQuestionSubmitById(@RequestBody QuestionSubmit questionSubmit){
        return questionSubmitService.updateById(questionSubmit);
    }

    @GetMapping("/question/update")
    @Override
    public boolean updateQuestionById(@RequestBody Question question){
        return questionService.updateById(question);
    }
}
