package com.dkdp.controller;


import com.dkdp.dto.Result;
import com.dkdp.service.IFollowService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 *  关注模块
 * </p>
 */
@RestController
@RequestMapping("/follow")
public class FollowController {
    @Resource
    private IFollowService followService;

    //关注
    @PutMapping("/{id}/{isFollow}")
    public Result follow(@PathVariable("id") Long followerId, @PathVariable("isFollow") Boolean isFollow){
        return followService.follow(followerId,isFollow);
    }
    //取消关注
    @GetMapping("/or/not/{id}")
    public Result isFollow(@PathVariable("id") Long followUserId){
        return followService.isFollow(followUserId);
    }
    //共同关注
    @GetMapping("/common/{id}")
    public Result commonFollow(@PathVariable("id") Long id){
        return followService.commonFollow(id);
    }

}
