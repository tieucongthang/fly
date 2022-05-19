package group.fly.cache;

import group.fly.config.RedisConfig;
import group.fly.config.ServicesRegister;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Dec 23, 2020-3:27:40 PM
 * 
 * @author Vietanh Lop xu ly giao tiếp với redis
 */
public class RedisCaching {

	private static RedisCaching instance;
	static Integer REDIS_DEFAULT_DB = 0;
	static public String tele_user_queue = "";
	static public String tele_group_queue = "";

	public static RedisCaching getInstance() {
		if (instance == null) {
			synchronized (RedisCaching.class) {
				new RedisCaching();
			}
		}
		return instance;
	}

	public RedisCaching() {
		super();
		RedisConfig applicationConfigure = (RedisConfig) ServicesRegister.shareInstance().context
				.getBean("RedisConfig");
		// TODO Auto-generated constructor stub
		if (!applicationConfigure.getRedis_pass().isEmpty()) {
			jedisPool = new JedisPool(poolConfig, applicationConfigure.getRedis_host(),
					applicationConfigure.getRedis_port(), 60000, applicationConfigure.getRedis_pass(),
					applicationConfigure.getRedis_db());
		} else {
			// Innit pool lên không sử dụng password
			jedisPool = new JedisPool(poolConfig, applicationConfigure.getRedis_host(),
					applicationConfigure.getRedis_port(), 60000);
		}
		REDIS_DEFAULT_DB = applicationConfigure.getRedis_db();
		tele_user_queue = applicationConfigure.getTele_user_queue();
		tele_group_queue = applicationConfigure.getTele_group_queue();
		instance = this;
	}

	final JedisPoolConfig poolConfig = buildPoolConfig();
	private static JedisPool jedisPool = null;

	public static JedisPool getJedisPool() {
		return jedisPool;
	}

	public static void setJedisPool(JedisPool jedisPool) {
		RedisCaching.jedisPool = jedisPool;
	}

	public Jedis getRedisConnection() throws Exception{
		Jedis jedis = RedisCaching.jedisPool.getResource();
		jedis.select(REDIS_DEFAULT_DB);
		return jedis;
	}

	private JedisPoolConfig buildPoolConfig() {
		final JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxTotal(10);
		poolConfig.setMaxIdle(5);
		poolConfig.setMinIdle(16);
		poolConfig.setTestOnBorrow(true);
		poolConfig.setTestOnReturn(true);
		poolConfig.setTestWhileIdle(true);
		poolConfig.setMinEvictableIdleTimeMillis(60000);
		poolConfig.setTimeBetweenEvictionRunsMillis(60000);
		poolConfig.setNumTestsPerEvictionRun(3);
		poolConfig.setBlockWhenExhausted(true);

		return poolConfig;
	}

	public static void main(String[] args) {
		try (Jedis jedis = RedisCaching.getInstance().jedisPool.getResource()) {
			// do operations with jedis resource
			try {
				System.out.println(jedis.set("Hello_world".getBytes(), "Chao xìn".getBytes("utf-8")));
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
}