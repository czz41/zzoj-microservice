package com.zz.zzojbackendjudgeservice.judge.codesandbox;


import com.zz.zzojbackendcommon.model.codesandbox.ExecuteCodeRequest;
import com.zz.zzojbackendcommon.model.codesandbox.ExecuteCodeResponse;

public interface CodeSandbox {
    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}
