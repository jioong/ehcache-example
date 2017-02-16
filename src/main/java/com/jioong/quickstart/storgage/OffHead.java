package com.jioong.quickstart.storgage;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.EntryUnit;
import org.ehcache.config.units.MemoryUnit;

/**
 * Created by jioong on 17-2-16.
 */
public class OffHead {

    public static void main(String[] args) {

        // 当使用 off-heap, 需要定义一个资源池，并指定想要分配的内存的大小
        // 存储在 off-heap 中的数据需要能被序列化，速度并 heap 要慢
        // 当有大量数据时可以使用 off-heap
        CacheManager cacheManager= CacheManagerBuilder.newCacheManagerBuilder()
                .withCache("tieredCache", CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class,
                        ResourcePoolsBuilder.newResourcePoolsBuilder()
                                .heap(10, EntryUnit.ENTRIES)
                                .offheap(10, MemoryUnit.MB)))
                .build();

        Cache<Long, String> tieredCache = cacheManager.getCache("tieredCache", Long.class, String.class);

        cacheManager.close();
    }
}
