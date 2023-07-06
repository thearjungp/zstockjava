package com.stockmarket.util;

import redis.clients.jedis.Jedis;

public class RedisClient
{
    static Jedis jedis = new Jedis("http://localhost:6379");


    public static Jedis getClient()
    {
        return jedis;
    }

}
