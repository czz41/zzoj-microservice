package com.zz.zzojbackenduserservice.controller.inner;

import com.zz.zzojbackendcommon.model.entity.User;
import com.zz.zzojbackendserviceclient.service.UserFeignClient;
import com.zz.zzojbackenduserservice.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/inner")
public class UserInnerController implements UserFeignClient {
    @Resource
    private UserService userService;

    @GetMapping("/get/id")
    @Override
    public User getById(@RequestParam("userId") long userId){
        return userService.getById(userId);
    }

    @GetMapping("/get/ids")
    @Override
    public List<User> listByIds(@RequestParam("idList") Collection<Long> idList){
        return userService.listByIds(idList);
    }
}
