package newbbb.service;

import org.springframework.data.redis.core.ZSetOperations;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IRedisService {

	String getKey(String key);

	void setKey(String key, String value);

	void setKey(String key, String value, long activeTime);

	void expire(String key, long activeTime);

	Long incr(String key);

	void del(String key);

	void push(String key, String val);

	String pop(String key);


	// hash
	void hSet(String key, String field, String value);

	void hRemove(String key, String field);

	String hGet(String key, String field);

	List<String> hMultiGet(String key, Collection<String> fields);

	// zset
	void zAdd(String key, String value, double score);

	void zRemove(String key, String value);

	Set<String> zRange(String key, long count);

}
