package com.zz.zzojbackendjudgeservice.judge.codesandbox.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.zz.zzojbackendcommon.common.ErrorCode;
import com.zz.zzojbackendcommon.exception.BusinessException;
import com.zz.zzojbackendcommon.model.codesandbox.ExecuteCodeRequest;
import com.zz.zzojbackendcommon.model.codesandbox.ExecuteCodeResponse;
import com.zz.zzojbackendjudgeservice.judge.codesandbox.CodeSandbox;
import org.apache.commons.lang3.StringUtils;

public class RemoteCodeSandbox implements CodeSandbox {
    private static final String AUTH_REQUEST_HEADER="auth";
    private static final String AUTH_REQUEST_SECRET="secretKey";
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("远程沙箱启动");
        String url="http://localhost:8888/executeCode";
        String json= JSONUtil.toJsonStr(executeCodeRequest);
        String responseStr = HttpUtil.createPost(url)
                .header(AUTH_REQUEST_HEADER,AUTH_REQUEST_SECRET)
                .body(json)
                .execute()
                .body();
        if(StringUtils.isBlank(responseStr)){
            throw new BusinessException(ErrorCode.API_REQUEST_ERROR,"executeCode remoteSandbox error,message ="+responseStr);
        }
        return JSONUtil.toBean(responseStr,ExecuteCodeResponse.class);
    }
}
