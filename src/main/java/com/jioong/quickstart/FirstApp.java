package com.jioong.quickstart;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

/**
 * Created by jioong on 17-2-16.
 */
public class FirstApp {

    public static void main(String[] args) {

        // withCache
        // 第一个参数为 Cache 的别名，用于从CacheManager 中索引 Cache
        // 第二个参数用于配置 Cache
        CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder().withCache("preConfigured",
                CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class, ResourcePoolsBuilder.heap(10)))
                .build();

        // CacheManager 在使用之前需要先初始化，初始化有两种方法：
        // 1. 在CacheManager 实例上调用 init() 方法
        // 2. CacheManagerBuilder.build(boolean init), 将参数设置为 true
        cacheManager.init();

        // 索引Cache时，需要传递三个参数： 1）缓存的别名， 2）缓存key的类型， 3）缓存value的类型
        // 为了保证类型安全 type-safety，所以需要传递key和value的类型
        // 当其中有类型不匹配时，会抛出 ClassCastException
        Cache<Long, String> preConffigured = cacheManager.getCache("preConfigured", Long.class, String.class);

        // 当需要时CacheManger可以创建新的 Cache实例
        // 需要两个参数： 1） 缓存的别名， 2） 缓存的配置
        Cache<Long, String> myCache = cacheManager.createCache("myCache",
                CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class, ResourcePoolsBuilder.heap(10))
                        .build());

        // 存储实体
        // key 必须是唯一的，并且只能关联到一个value
        myCache.put(1L, "happy");

        // 从Cache中索引value
        // 当一个 key 没有对应的 value 时，返回 null
        System.out.println(myCache.get(1L));

        // 删除指定的缓存
        // 当调用该方法时， CacheManager 不仅会删除该 Cache的引用，还会 close它
        // 之后，该 cache 会释放所有的临时资源，如内存
        // 该 cache 的应用将不再可用
        cacheManager.removeCache("preConfigured");

        // 为了释放所有的临时资源，如内存，线程等
        // 该方法会 close 所有该 cacheManager 实例管理的 cache
        cacheManager.close();
    }
}
