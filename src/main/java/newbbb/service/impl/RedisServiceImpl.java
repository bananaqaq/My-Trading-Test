package newbbb.service.impl;


import newbbb.service.IRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl implements IRedisService {

	@Autowired
	private RedisTemplate redisString;

	private ZSetOperations<String, String> opsForZSet;

	private HashOperations<String, String, String> opsForHash;

	@PostConstruct
	private void initOps(){
		opsForZSet = redisString.opsForZSet();
		opsForHash = redisString.opsForHash();
	}


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
	public void push(String key, String val) {
		this.redisString.opsForList().leftPush(key, val);
	}

	@Override
	public String pop(String key) {
		return (String) this.redisString.opsForList().rightPop(key);
	}



	// hash
	@Override
	public void hSet(String key, String field, String value){
		opsForHash.put(key, field, value);
	}

	@Override
	public void hRemove(String key, String field){
		opsForHash.delete(key, field);
	}

	@Override
	public String hGet(String key, String field){
		return opsForHash.get(key, field);
	}

	@Override
	public List<String> hMultiGet(String key, Collection<String> fields){
		return opsForHash.multiGet(key, fields);
	}

	// zset
	@Override
	public void zAdd(String key, String value, double score){
		opsForZSet.add(key, value, score);
	}

	@Override
	public void zRemove(String key, String value){
		opsForZSet.remove(key, value);
	}

	@Override
	public Set<String> zRange(String key, long count){
		return opsForZSet.rangeByScore(key, Long.MIN_VALUE, Long.MAX_VALUE, 0, count);
	}

}
