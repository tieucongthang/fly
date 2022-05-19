package group.fly.config;

public class RedisConfig {

	String redis_host;
	Integer redis_port;
	String redis_pass="";
	Integer redis_db;
	String tele_user_queue;
	String tele_group_queue;
	
	public String getTele_user_queue() {
		return tele_user_queue;
	}

	public String getTele_group_queue() {
		return tele_group_queue;
	}

	public void setTele_group_queue(String tele_group_queue) {
		this.tele_group_queue = tele_group_queue;
	}

	public void setTele_user_queue(String tele_user_queue) {
		this.tele_user_queue = tele_user_queue;
	}
	public Integer getRedis_db() {
		return redis_db;
	}

	public void setRedis_db(Integer redis_db) {
		this.redis_db = redis_db;
	}

	public String getRedis_host() {
		return redis_host;
	}

	public void setRedis_host(String redis_host) {
		this.redis_host = redis_host;
	}

	public Integer getRedis_port() {
		return redis_port;
	}

	public void setRedis_port(Integer redis_port) {
		this.redis_port = redis_port;
	}

	public String getRedis_pass() {
		return redis_pass;
	}

	public void setRedis_pass(String redis_pass) {
		this.redis_pass = redis_pass;
	}


}
