package com.dkdp.utils;

import com.dkdp.dto.UserDTO;

/**
 *  每个用户都是找tomcat线程池中的一个线程来完成工作的，使用完成后在进行回收。
 * 	每个请求都是独立的，可以使用threadlocal来做线程隔离，每个线程操作自己的一份数据
 * 	在threadlocal中，无论他的put和get方法，都是先获取当前用户的线程，然后从线程中取出线程的成员变量map，只要线程不一样，map就不一样。
 */
public class UserHolder {
    private static final ThreadLocal<UserDTO> tl = new ThreadLocal<>();

    public static void saveUser(UserDTO user){
        tl.set(user);
    }

    public static UserDTO getUser(){
        return tl.get();
    }

    public static void removeUser(){
        tl.remove();
    }
}
