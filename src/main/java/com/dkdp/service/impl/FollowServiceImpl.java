package com.dkdp.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dkdp.dto.Result;
import com.dkdp.dto.UserDTO;
import com.dkdp.entity.Follow;
import com.dkdp.mapper.FollowMapper;
import com.dkdp.service.IFollowService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dkdp.service.IUserService;
import com.dkdp.utils.UserHolder;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 */
@Service
public class FollowServiceImpl extends ServiceImpl<FollowMapper, Follow> implements IFollowService {
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private IUserService userService;

    @Override
    public Result follow(Long followerId, Boolean isFollow) {
        //1. 获取当前用户
        Long userId = UserHolder.getUser().getId();
        String key = "follows:"+userId;
        //2. 判断是否关注
        if(isFollow){
            //3. 关注，新增数据
            Follow follow = new Follow();
            follow.setUserId(userId);
            follow.setFollowUserId(followerId);
            boolean isSuccess = save(follow);
            if(isSuccess){
                //3.1 保存到redis
                stringRedisTemplate.opsForSet().add(key,followerId.toString());
            }
        }else{
            //4. 取关，删除数据
            boolean isSuccess = remove(new QueryWrapper<Follow>().eq("user_id", userId).eq("follow_user_id", followerId));
            if(isSuccess){
                //4.1 删除redis中的数据
                stringRedisTemplate.opsForSet().remove(key,followerId.toString());
            }

        }
        return Result.ok();
    }

    @Override
    public Result isFollow(Long followUserId) {
        // 1.获取当前用户
        Long userId = UserHolder.getUser().getId();
        // 2.查询是否关注
        Integer count = query().eq("user_id", userId).eq("follow_user_id", followUserId).count();
        return Result.ok(count>0);
    }

    @Override
    public Result commonFollow(Long id) {
        //1. 获取当前用户
        Long userId = UserHolder.getUser().getId();
        String key1 = "follows:"+ userId;
        String key2 = "follows:"+ id;
        //2. 求交集
        Set<String> intersect = stringRedisTemplate.opsForSet().intersect(key1, key2);
        if(intersect ==null|| intersect.isEmpty()){
            return Result.ok(Collections.emptyList());
        }
        //3. 解析id
        List<Long> ids = intersect.stream().map(Long::valueOf).collect(Collectors.toList());
        //4. 根据id查询用户
        List<UserDTO> users = userService.listByIds(ids).stream().map(user-> BeanUtil.copyProperties(user, UserDTO.class)).collect(Collectors.toList());
        return Result.ok(users);
    }
}
