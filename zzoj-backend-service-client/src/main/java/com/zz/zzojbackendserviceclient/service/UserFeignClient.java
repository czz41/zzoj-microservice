package com.zz.zzojbackendserviceclient.service;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zz.zzojbackendcommon.common.ErrorCode;
import com.zz.zzojbackendcommon.exception.BusinessException;
import com.zz.zzojbackendcommon.model.dto.user.UserQueryRequest;
import com.zz.zzojbackendcommon.model.entity.User;
import com.zz.zzojbackendcommon.model.enums.UserRoleEnum;
import com.zz.zzojbackendcommon.model.vo.LoginUserVO;
import com.zz.zzojbackendcommon.model.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.zz.zzojbackendcommon.constant.UserConstant.USER_LOGIN_STATE;

/**
 * 用户服务
 *
 * @author <a href="https://github.com/lizz">程序员鱼皮</a>
 * @from <a href="https://zz.icu">编程导航知识星球</a>
 */
@FeignClient(name="zzoj-backend-user-service",path="/api/user/inner")
public interface UserFeignClient{
    @GetMapping("/get/id")
    User getById(@RequestParam("userId") long userId);

    @GetMapping("/get/ids")
    List<User> listByIds(@RequestParam("idList") Collection<Long> idList);



    /**
     * 获取当前登录用户
     *
     * @param request
     * @return
     */
    default User getLoginUser(HttpServletRequest request){
        // 先判断是否已登录
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser == null || currentUser.getId() == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        return currentUser;
    }

    /**
     * 是否为管理员
     *
     * @param user
     * @return
     */
    default boolean isAdmin(User user){
        return user != null && UserRoleEnum.ADMIN.getValue().equals(user.getUserRole());
    }

    /**
     * 获取脱敏的用户信息
     *
     * @param user
     * @return
     */
    default UserVO getUserVO(User user){
        if (user == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }

    /**
     * 获取脱敏的用户信息
     *
     * @param userList
     * @return
     */
    default List<UserVO> getUserVO(List<User> userList){
        if (CollUtil.isEmpty(userList)) {
            return new ArrayList<>();
        }
        return userList.stream().map(this::getUserVO).collect(Collectors.toList());
    }

}
