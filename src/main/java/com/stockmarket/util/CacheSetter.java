package com.stockmarket.util;

import com.stockmarket.CacheFilter;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;

public class CacheSetter
{
    private static Jedis redis = RedisClient.getClient();

    public static void setCache(HttpServletRequest req, String responseBody)
    {
        String key = URLUtil.getFullURI(req);
        redis.setex(key, CacheFilter.EXPIRATION_TIME, responseBody);
    }
}
