package com.stockmarket.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.stockmarket.CacheFilter;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class CacheSetter
{
    private static Jedis redis = RedisClient.getClient();

    public static void setCache(HttpServletRequest req, String responseBody)
    {
        String key = URLUtil.getFullURI(req);
        redis.setex(key, CacheFilter.EXPIRATION_TIME, responseBody);
    }

//    public static void setCacheList(List list, String key) throws JsonProcessingException {
//        for (Object o : list) {
//            redis.rpush(key, OutputUtil.convertToJSONString(o));
//        }
//    }

//    public static boolean checkCacheIfKeyExists(String key)
//    {
//
//        return redis.exists(key);
//    }




    public static void flushCache()
    {
        redis.flushAll();
    }
}
