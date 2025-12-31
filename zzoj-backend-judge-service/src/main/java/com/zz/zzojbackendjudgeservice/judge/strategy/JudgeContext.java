package com.zz.zzojbackendjudgeservice.judge.strategy;


import com.zz.zzojbackendcommon.model.codesandbox.JudgeInfo;
import com.zz.zzojbackendcommon.model.dto.question.JudgeCase;
import com.zz.zzojbackendcommon.model.entity.Question;
import com.zz.zzojbackendcommon.model.entity.QuestionSubmit;
import lombok.Data;

import java.util.List;

@Data
public class JudgeContext {
    private JudgeInfo judgeInfo;
    private List<String> inputList;
    private List<String> outputList;
    private List<JudgeCase>judgeCaseList;
    private Question question;
    private QuestionSubmit questionSubmit;
}
