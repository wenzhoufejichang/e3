package com.hzm.cart.service;

import java.util.Map;

import javax.annotation.Resource;

import e3_cart_interface.JedisClient;
import redis.clients.jedis.JedisCluster;

public class JedisClientCluster implements JedisClient {

	@Resource(name = "jedisCluster")
	private JedisCluster jedisCluster;

	@Override
	public String set(String key, String value) {
		return jedisCluster.set(key, value);
	}

	@Override
	public String get(String key) {
		return jedisCluster.get(key);
	}

	@Override
	public Boolean exists(String key) {
		return jedisCluster.exists(key);
	}

	@Override
	public Long expire(String key, int seconds) {
		return jedisCluster.expire(key, seconds);
	}

	@Override
	public Long ttl(String key) {
		return jedisCluster.ttl(key);
	}

	@Override
	public Long incr(String key) {
		return jedisCluster.incr(key);
	}

	@Override
	public Long hset(String key, String field, String value) {
		return jedisCluster.hset(key, field, value);
	}

	@Override
	public String hget(String key, String field) {
		return jedisCluster.hget(key, field);
	}

	@Override
	public Long hdel(String key, String... field) {
		return jedisCluster.hdel(key, field);
	}

	@Override
	public Long delete(String key) {
		// TODO Auto-generated method stub
		return jedisCluster.del(key);
	}

	@Override
	public Boolean hexists(String key, String field) {
		// TODO Auto-generated method stub
		return jedisCluster.hexists(key, field);
	}

	@Override
	public Map<String, String> hgtall(String key) {
		// TODO Auto-generated method stub
		return jedisCluster.hgetAll(key);
	}

}
