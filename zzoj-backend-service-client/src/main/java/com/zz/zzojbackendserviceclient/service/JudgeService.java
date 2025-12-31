package com.zz.zzojbackendserviceclient.service;


import com.zz.zzojbackendcommon.model.entity.QuestionSubmit;

public interface JudgeService {
    QuestionSubmit doJudge(long questionSubmitId);
}
