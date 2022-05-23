package group.fly.business;

import java.util.Date;

import org.apache.commons.codec.language.bm.Rule.Phoneme;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import group.fly.MainApplication;
import group.fly.cache.RedisCaching;
import group.fly.hibernate.entities.Group;
import group.fly.hibernate.entities.SpamUsers;
import group.fly.hibernate.home.GroupHome;
import group.fly.hibernate.home.SpamUsersHome;
import group.fly.utilities.Logs;

public class BuzTeleUser implements Runnable{
	static final Logs logs = new Logs(MainApplication.class);
	RedisCaching redisClient;
	
	public BuzTeleUser() {
		super();
		this.redisClient = RedisCaching.getInstance();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
	//	super.run();
		process();
	}

	

	public void process() {
		String jsonUser = "";
		
		JSONObject json;
		String phoneNumber;
		String username;
		String accessHash;

		while (true) {
			try {
				jsonUser = redisClient.getRedisConnection().lpop(redisClient.tele_user_queue);
				logs.info("start user:" + jsonUser);
				if (jsonUser == null) {
					Thread.sleep(30000);
					continue;
				}
				if (jsonUser.length() == 0) {
					Thread.sleep(30000);
					continue;
				}
				// jsonGroup = gson..
				json = new JSONObject(jsonUser);
				SpamUsers user = new SpamUsers();
				user.setUserTeleId(json.getInt("user_id"));
				user.setUserFromGroupId(json.getInt("group_id"));
				phoneNumber = getJsonString(json, "phone_number");
				if (phoneNumber != null)
					user.setUserPhone(phoneNumber);	
				username = getJsonString(json, "username");
				if (username != null)
					user.setUserNameTele(username);
				accessHash = getJsonString(json, "access_hash");
				if (accessHash != null)
					user.setAccessHash(accessHash);
				user.setCreatedAt(new Date());
				insertGroup(user);
				logs.info("finish user:" + jsonUser);
			} catch (Exception e) {
				// Log here
				logs.fatal("Excetion: ", e);
			}

		}
	}

	void insertGroup(SpamUsers user) {
		SpamUsersHome home = new SpamUsersHome();
		try {
			home.persist(user);
		}
		catch (Exception e) {
			// TODO: handle exception
		}

	}
	String getJsonString(JSONObject json, String key) {
		try {
			return json.getString(key);
		}
		catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	Integer getJsonInt(JSONObject json, String key) {
		try {
			return json.getInt(key);
		}
		catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
}
