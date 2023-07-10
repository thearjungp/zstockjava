package com.stockmarket;

import com.stockmarket.util.OutputUtil;
import com.stockmarket.util.RedisClient;
import com.stockmarket.util.URLUtil;
import redis.clients.jedis.Jedis;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CacheFilter implements Filter
{

    private Jedis redis;
    public static final int EXPIRATION_TIME = 120;

    public CacheFilter()
    {
        this.redis = RedisClient.getClient();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;

        if(!request.getMethod().equals("GET"))
        {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        String key = URLUtil.getFullURI(request);
        String data = redis.get(key);
        if(data != null)
        {
            System.out.println(" ----- Cache HIT for URL ---- " + key);
            OutputUtil.outputResponse((HttpServletResponse) servletResponse, data, HttpServletResponse.SC_OK);
        }
        else
        {
            System.out.println("Cache MISS for " + key);
            filterChain.doFilter(servletRequest, servletResponse);
        }

    }
}
