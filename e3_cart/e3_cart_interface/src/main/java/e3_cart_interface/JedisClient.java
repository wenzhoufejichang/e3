package e3_cart_interface;

import java.util.Map;

public interface JedisClient {

	String set(String key, String value);

	String get(String key);

	Boolean exists(String key);

	Boolean hexists(String key, String field);

	Long expire(String key, int seconds);

	Long ttl(String key);

	Long incr(String key);

	Long delete(String key);

	Long hset(String key, String field, String value);

	String hget(String key, String field);

	Long hdel(String key, String... field);

	Map<String, String> hgtall(String key);

}
