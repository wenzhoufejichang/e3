package com.test.jedis;

import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public class Jedistest {

	// @Test
	public void tt() throws UnsupportedEncodingException {

		JedisPool jp = new JedisPool("192.168.2.103", 6379);
		Jedis jedis = jp.getResource();
		Set<String> set = jedis.keys("*");
		for (Iterator<String> i = set.iterator(); i.hasNext();) {

			System.out.println(i.next());
		}
		Map<String, String> hgetAll = jedis.hgetAll("student");

		Set<Entry<String, String>> set2 = hgetAll.entrySet();

		for (Iterator<Entry<String, String>> i = set2.iterator(); i.hasNext();) {

			Entry<String, String> key = i.next();
			System.out.println(key.getKey() + "---" + new String(key.getValue().getBytes("ISO8859-1"), "utf-8"));

		}

		// System.out.println(hgetAll);
		jedis.close();
		jp.close();
	}

	// @Test
	public void tt3() throws UnsupportedEncodingException {
		Set<HostAndPort> nodes = new HashSet<>();
		HostAndPort hap1 = new HostAndPort("192.168.25.128", 7001);
		HostAndPort hap2 = new HostAndPort("192.168.25.128", 7002);
		HostAndPort hap3 = new HostAndPort("192.168.25.128", 7003);
		HostAndPort hap4 = new HostAndPort("192.168.25.128", 7004);
		HostAndPort hap5 = new HostAndPort("192.168.25.128", 7005);
		HostAndPort hap6 = new HostAndPort("192.168.25.128", 7006);
		nodes.add(hap1);
		nodes.add(hap2);
		nodes.add(hap3);
		nodes.add(hap4);
		nodes.add(hap5);
		nodes.add(hap6);
		JedisCluster jc = new JedisCluster(nodes);
		jc.set("hello", "你好");
		Long del = jc.del("test");
		System.out.println(del);
		String string = jc.get("hello");
		System.out.println(string);
		jc.close();

	}
}