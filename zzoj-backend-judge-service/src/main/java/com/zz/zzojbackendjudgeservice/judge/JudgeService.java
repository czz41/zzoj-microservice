package com.zz.zzojbackendjudgeservice.judge;


import com.zz.zzojbackendcommon.model.entity.QuestionSubmit;

public interface JudgeService {
    QuestionSubmit doJudge(long questionSubmitId);
}
