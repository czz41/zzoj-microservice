package com.zz.zzojbackendjudgeservice.judge;

import com.zz.zzojbackendcommon.model.codesandbox.JudgeInfo;
import com.zz.zzojbackendcommon.model.entity.QuestionSubmit;
import com.zz.zzojbackendjudgeservice.judge.strategy.DefaultJudgeStrategy;
import com.zz.zzojbackendjudgeservice.judge.strategy.JavaLanguageJudgeStrategy;
import com.zz.zzojbackendjudgeservice.judge.strategy.JudgeContext;
import com.zz.zzojbackendjudgeservice.judge.strategy.JudgeStrategy;
import org.springframework.stereotype.Service;

@Service
public class JudgeManager {
    /**
     * 执行判题
     * @param judgeContext
     * @return
     */
    JudgeInfo doJudge(JudgeContext judgeContext){
        QuestionSubmit questionSubmit = judgeContext.getQuestionSubmit();
        String language = questionSubmit.getLanguage();
        JudgeStrategy judgeStrategy=new DefaultJudgeStrategy();
        if("java".equals(language)){
            judgeStrategy=new JavaLanguageJudgeStrategy();
        }
        return judgeStrategy.doJudge(judgeContext);
    }
}
