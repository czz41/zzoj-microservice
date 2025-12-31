package com.zz.zzojbackendjudgeservice.Controller.inner;

import com.zz.zzojbackendcommon.model.entity.QuestionSubmit;
import com.zz.zzojbackendjudgeservice.judge.JudgeService;
import com.zz.zzojbackendserviceclient.service.JudgeFeignClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/inner")
public class JudgeInnerController implements JudgeFeignClient {

    @Resource
    private JudgeService judgeService;

    @PostMapping("/do")
    @Override
    public QuestionSubmit doJudge(@RequestParam("questionSubmitId") long questionSubmitId){
        return judgeService.doJudge(questionSubmitId);
    }

}
