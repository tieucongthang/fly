package group.fly.mongo.dao;

import java.util.ArrayList;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class TblWardDao {

	int port_no = 27017;
	String host_name = "localhost", db_name = "fee_data", db_coll_name = "base_ward";

	// Mongodb connection string.
	String client_url = "mongodb://" + host_name + ":" + port_no + "/" + db_name;
	MongoClientURI uri = new MongoClientURI(client_url);

	// Connecting to the mongodb server using the given client uri.
	MongoClient mongo_client = new MongoClient(uri);
	// Fetching the database from the mongodb.
	MongoDatabase db = mongo_client.getDatabase(db_name);

	// Fetching the collection from the mongodb.
	MongoCollection<Document> coll = db.getCollection(db_coll_name);
	/**
	 * static Singleton instance.
	 */
	private static volatile TblWardDao instance;

	/**
	 * Private constructor for singleton.
	 */
	private TblWardDao() {

	}

	/**
	 * Return a singleton instance of TblWardDao.
	 */
	public static TblWardDao getInstance() {
		// Double lock for thread safety.
		if (instance == null) {
			synchronized (TblWardDao.class) {
				if (instance == null) {
					instance = new TblWardDao();
				}
			}
		}
		return instance;
	}

	
	
	public Boolean insertDocument(ArrayList<Document> listDoc) {
		// TODO Auto-generated method stub
		try {
			coll.insertMany(listDoc);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
