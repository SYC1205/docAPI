package com.e104.util;

import java.io.IOException;
import java.net.InetSocketAddress;

import net.spy.memcached.MemcachedClient;

public class redisService {
	public MemcachedClient redisClient(){
		String configEndpoint = "docurlcache.abjn5b.0001.apne1.cache.amazonaws.com";
        Integer clusterPort = 6379;

        MemcachedClient redis =null;
        try {
			redis = new MemcachedClient(
			        new InetSocketAddress(configEndpoint, 
			                              clusterPort));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //redis.get
        return redis;
		
	}
}
