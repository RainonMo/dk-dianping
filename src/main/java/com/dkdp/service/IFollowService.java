package com.dkdp.service;

import com.dkdp.dto.Result;
import com.dkdp.entity.Follow;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  关注服务
 * </p>
 */
public interface IFollowService extends IService<Follow> {
    Result follow(Long followerId, Boolean isFollow);

    Result isFollow(Long followUserId);

    Result commonFollow(Long id);
}
