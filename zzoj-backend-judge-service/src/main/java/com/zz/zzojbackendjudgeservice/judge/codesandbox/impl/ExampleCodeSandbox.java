package com.zz.zzojbackendjudgeservice.judge.codesandbox.impl;


import com.zz.zzojbackendcommon.model.codesandbox.ExecuteCodeRequest;
import com.zz.zzojbackendcommon.model.codesandbox.ExecuteCodeResponse;
import com.zz.zzojbackendcommon.model.codesandbox.JudgeInfo;
import com.zz.zzojbackendcommon.model.enums.JudgeInfoMessageEnum;
import com.zz.zzojbackendcommon.model.enums.QuestionSubmitEnum;
import com.zz.zzojbackendjudgeservice.judge.codesandbox.CodeSandbox;

import java.util.ArrayList;
import java.util.List;

public class ExampleCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("示例沙箱启动");
        List<String> inputList = executeCodeRequest.getInputList();
        List<String> outputList=new ArrayList<>();
        outputList.add("5");
        ExecuteCodeResponse executeCodeResponse=new ExecuteCodeResponse();
        executeCodeResponse.setOutputList(outputList);
        executeCodeResponse.setMessage("测试执行成功");
        executeCodeResponse.setStatus(QuestionSubmitEnum.SUCCEED.getValue());
        JudgeInfo judgeInfo=new JudgeInfo();
        judgeInfo.setMessage(JudgeInfoMessageEnum.ACCEPT.getText());
        judgeInfo.setMemory(100L);
        judgeInfo.setTime(100L);

        executeCodeResponse.setJudgeInfo(judgeInfo);

        return executeCodeResponse;
    }
}
