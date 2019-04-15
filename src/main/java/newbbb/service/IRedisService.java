package newbbb.service;

import java.util.List;
import java.util.Map;

public interface IRedisService {

	String getKey(String key);

	void setKey(String key, String value);

	void setKey(String key, String value, long activeTime);

	void expire(String key, long activeTime);

	Long incr(String key);

	void del(String key);

	void setHashKey(String key, String field, String value);

	String getHashKey(String key, String field);

	List<Map<String, String>> getHashKeys(String key);

	void push(String key, String val);

	String pop(String key);
}
