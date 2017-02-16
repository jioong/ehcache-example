package com.jioong.quickstart.storgage;

import org.ehcache.Cache;
import org.ehcache.PersistentCacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.EntryUnit;
import org.ehcache.config.units.MemoryUnit;

/**
 * Created by jioong on 17-2-16.
 */
public class DiskPersistence {

    public static void main(String[] args) {
        PersistentCacheManager persistentCacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                .with(CacheManagerBuilder.persistence("./path/to/myData")) // 指定数据存储的位置
                .withCache("persistenct-cache", CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class,
                        ResourcePoolsBuilder.newResourcePoolsBuilder().heap(10, EntryUnit.ENTRIES).disk(10, MemoryUnit.MB, true)))
                .build(true);

        Cache<Long, String> cache = persistentCacheManager.getCache("persistenct-cache", Long.class, String.class);

        for(Long i = 0L; i < 1000000; i ++) {
            cache.put(i, "nice to meet you");
            System.out.println(i);
        }

        System.out.println("Over");
    }
}
