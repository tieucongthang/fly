package group.fly.business;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import group.fly.MainApplication;
import group.fly.cache.RedisCaching;
import group.fly.hibernate.entities.Group;
import group.fly.hibernate.home.GroupHome;
import group.fly.utilities.Logs;

import java.util.Date;

import org.json.JSONObject;

public class BuzTeleGroup implements Runnable {
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		///super.run();
		process();
	}

	static final Logs logs = new Logs(MainApplication.class);
	public BuzTeleGroup() {
		super();
		this.redisClient = RedisCaching.getInstance();
	}

	RedisCaching redisClient;

	public void process() {
		String jsonGroup = "";
		Gson gson = new GsonBuilder().create();
		JSONObject json;

		while (true) {
			try {
				jsonGroup = redisClient.getRedisConnection().lpop(redisClient.tele_group_queue);
				logs.info("get group: "+ jsonGroup);
				if (jsonGroup == null) {
					Thread.sleep(30000);
					continue;
				}
				if (jsonGroup.length() == 0) {
					Thread.sleep(30000);
					continue;
				}
				// jsonGroup = gson..
				json = new JSONObject(jsonGroup);
				Group group = new Group();
				group.setChatId(json.getInt("group_id"));
				group.setGroupName(json.getString("group_name"));
				group.setCreatedAt(new Date());
				insertGroup(group);
				logs.info("finish group: "+ jsonGroup);
			} catch (Exception e) {
				// Log here
				logs.fatal("Exception", e);
			}

		}
	}

	void insertGroup(Group group) {
		GroupHome home = new GroupHome();
		try {
			home.persist(group);
		}
		catch (Exception e) {
			// TODO: handle exception
		}

	}
}
