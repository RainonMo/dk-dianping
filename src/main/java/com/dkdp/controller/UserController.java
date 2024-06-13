package com.dkdp.controller;


import cn.hutool.core.bean.BeanUtil;
import com.dkdp.dto.LoginFormDTO;
import com.dkdp.dto.Result;
import com.dkdp.dto.UserDTO;
import com.dkdp.entity.User;
import com.dkdp.entity.UserInfo;
import com.dkdp.service.IUserInfoService;
import com.dkdp.service.IUserService;
import com.dkdp.utils.UserHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.dkdp.utils.RedisConstants.LOGIN_USER_KEY;

/**
 * <p>
 * 用户模块
 * </p>
 *
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;

    @Resource
    private IUserInfoService userInfoService;

    /**
     * 发送手机验证码
     */
    @PostMapping("code")
    public Result sendCode(@RequestParam("phone") String phone, HttpSession session) {
        return userService.sendCode(phone, session);
    }

    /**
     * 登录功能
     * @param loginForm 登录参数，包含手机号、验证码；或者手机号、密码
     */
    @PostMapping("/login")
    public Result login(@RequestBody LoginFormDTO loginForm, HttpSession session){
        return userService.login(loginForm, session);
    }

    /**
     * 登出功能
     * @return 无
     */
    @PostMapping("/logout")
    public Result logout(HttpSession session, HttpServletRequest request){
//        // 清除session中的用户id
//        session.removeAttribute("token");
//        // 清除redis中的token
//        // 1.获取请求头中的token
//        String token = request.getHeader("authorization");
//        // 2.基于TOKEN获取redis中的用户
//        String key  = LOGIN_USER_KEY + token;
//        System.out.println(key);
//        stringRedisTemplate.delete(key);
        UserHolder.removeUser();
        return Result.ok();
    }

    @GetMapping("/me")
    public Result me(){
        UserDTO user = UserHolder.getUser();
        return Result.ok(user);
    }

    @GetMapping("/info/{id}")
    public Result info(@PathVariable("id") Long userId){
        // 查询详情
        UserInfo info = userInfoService.getById(userId);
        if (info == null) {
            // 没有详情，应该是第一次查看详情
            return Result.ok();
        }
        info.setCreateTime(null);
        info.setUpdateTime(null);
        // 返回
        return Result.ok(info);
    }

    /**
     * 根据id查询用户
     */
    @GetMapping("/{id}")
    public Result getUserById(@PathVariable("id")Long userId){
        User user = userService.getById(userId);
        if (user == null){
            return  Result.ok();
        }
        UserDTO userDTO = BeanUtil.copyProperties(user, UserDTO.class);
        return Result.ok(userDTO);
    }
}
