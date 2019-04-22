package newbbb.service.impl;


import newbbb.service.IRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl implements IRedisService {

	@Autowired
	private RedisTemplate redisString;

	@Override
	public String getKey(String key) {
		ValueOperations<String, String> ops = this.redisString.opsForValue();
		if (redisString.hasKey(key)) {
			String value = ops.get(key);
			return value;
		}
		return null;
	}

	@Override
	public void setKey(String key, String value) {
		ValueOperations<String, String> ops = this.redisString.opsForValue();
		ops.set(key, value);
	}

	@Override
	public void setKey(String key, String value, long activeTime) {
		ValueOperations<String, String> ops = this.redisString.opsForValue();
		ops.set(key, value, activeTime, TimeUnit.SECONDS);
	}

	@Override
	public void expire(String key, long activeTime) {
		redisString.expire(key, activeTime, TimeUnit.SECONDS);
	}

	@Override
	public Long incr(String key) {
		ValueOperations<String, String> ops = this.redisString.opsForValue();
		return ops.increment(key, 1);
	}

	@Override
	public void del(String key) {
		this.redisString.delete(key);
	}

	@Override
	public void setHashKey(String key, String field, String value) {
		this.redisString.opsForHash().put(key, field, value);
	}

	@Override
	public String getHashKey(String key, String field) {
		return (String) this.redisString.opsForHash().get(key, field);
	}

	@Override
	public List<Map<String, String>> getHashKeys(String key) {
		return this.redisString.opsForHash().values(key);
	}

	@Override
	public void push(String key, String val) {
		this.redisString.opsForList().leftPush(key, val);
	}

	@Override
	public String pop(String key) {
		return (String) this.redisString.opsForList().rightPop(key);
	}

	public void zset(String key, double score, String value){
		ZSetOperations<String, String> ops = this.redisString.opsForZSet();
		ops.rangeWithScores("", 0, -1);

	}
}
