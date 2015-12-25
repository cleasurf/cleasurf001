package com.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class SurfRedis01 {
	JedisPool pool;
	Jedis jedis;
	
	private SurfRedis01(){
		System.out.println("构造函数！");
		pool = new JedisPool(new JedisPoolConfig(), "192.168.0.101");
		jedis = pool.getResource();
		// jedis.auth("password");
		System.out.println("excute the method setUp!");
	}

	/**
	 * @Description: TODO
	 * @param args   
	 * @return void  
	 * @throws
	 * @author liangl
	 * @date 2015-12-21
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new SurfRedis01();
		System.out.print("eee");
	}

}
