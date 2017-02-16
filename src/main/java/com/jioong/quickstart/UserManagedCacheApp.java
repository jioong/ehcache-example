package com.jioong.quickstart;

import org.ehcache.UserManagedCache;
import org.ehcache.config.builders.UserManagedCacheBuilder;

/**
 * Created by jioong on 17-2-16.
 */
public class UserManagedCacheApp {
    public static void main(String[] args) {

        /*
        *  UserManagedCache 是 EhCache3 的新特性
        * */
        UserManagedCache<Long, String> userManagedCache = UserManagedCacheBuilder
                .newUserManagedCacheBuilder(Long.class, String.class)
                .build(false);

        userManagedCache.init();
        userManagedCache.put(1L, "hello");
        System.out.println(userManagedCache.get(1L));
        userManagedCache.close();
    }
}
