package com.zz.zzojbackendserviceclient.service;

import com.zz.zzojbackendcommon.model.entity.Question;
import com.zz.zzojbackendcommon.model.entity.QuestionSubmit;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "zzoj-backend-question-service",path="/api/question/inner")
public interface QuestionFeignClient{

    @GetMapping("get/id")
    Question getQuestionById(@RequestParam("questionId") long questionId);

    @GetMapping("/question_submit/get/id")
    QuestionSubmit getQuestionSubmitById(@RequestParam("questionId") long questionId);

    @GetMapping("/question_submit/update")
    boolean updateQuestionSubmitById(@RequestBody QuestionSubmit questionSubmit);

    @GetMapping("/question/update")
    boolean updateQuestionById(@RequestBody Question question);
}
