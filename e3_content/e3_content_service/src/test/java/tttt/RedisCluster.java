package tttt;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

public class RedisCluster {

	@Test
	public void tt1() {

		// ApplicationContext ac = new
		// ClassPathXmlApplicationContext("spring/applicationContext_redis.xml");
		// JedisClient bean = ac.getBean(JedisClient.class);
		// String string = bean.get("hello");
		// System.out.println(string);
		Set<HostAndPort> set = new HashSet<>();
		set.add(new HostAndPort("192.168.1.128", 7001));
		set.add(new HostAndPort("192.168.1.128", 7002));
		set.add(new HostAndPort("192.168.1.128", 7003));
		set.add(new HostAndPort("192.168.1.128", 7004));
		set.add(new HostAndPort("192.168.1.128", 7005));
		set.add(new HostAndPort("192.168.1.128", 7006));
		JedisCluster jc = new JedisCluster(set);
		//jc.set("hello", "dddd");
		String string = jc.get("hello");
		System.out.println(string);
		jc.close();

	}

}
