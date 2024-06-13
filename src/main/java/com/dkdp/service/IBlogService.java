package com.dkdp.service;

import com.dkdp.dto.Result;
import com.dkdp.entity.Blog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  笔记服务
 * </p>
 */
public interface IBlogService extends IService<Blog> {

    Result queryBlogById(Long id);

    Result queryBlogLikes(Long id);

    Result queryBlogOfFollow(Long max, Integer offset);

    Result saveBlog(Blog blog);
}
