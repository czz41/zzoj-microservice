package com.zz.zzojbackendjudgeservice.judge.codesandbox.impl;


import com.zz.zzojbackendcommon.model.codesandbox.ExecuteCodeRequest;
import com.zz.zzojbackendcommon.model.codesandbox.ExecuteCodeResponse;
import com.zz.zzojbackendjudgeservice.judge.codesandbox.CodeSandbox;

public class ThirdPartyCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("第三方沙箱启动");
        return null;
    }
}
