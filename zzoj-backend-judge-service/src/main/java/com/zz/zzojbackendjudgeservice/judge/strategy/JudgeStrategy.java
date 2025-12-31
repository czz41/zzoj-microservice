package com.zz.zzojbackendjudgeservice.judge.strategy;


import com.zz.zzojbackendcommon.model.codesandbox.JudgeInfo;

public interface JudgeStrategy {
    /**
     * 执行判题
     * @param judgeContext
     * @return
     */
    JudgeInfo doJudge(JudgeContext judgeContext);
}
