package group.fly.mongo.dao;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;

public class BaseMongo {

	int port_no = 27017;
	String host_name = "localhost", db_name = "fintech_data";

	// Mongodb connection string.
	String client_url = "mongodb://" + host_name + ":" + port_no + "/" + db_name;
	MongoClientURI uri = new MongoClientURI(client_url);

	// Connecting to the mongodb server using the given client uri.
	MongoClient mongo_client = new MongoClient(uri);
	// Fetching the database from the mongodb.
	MongoDatabase db = mongo_client.getDatabase(db_name);

	/**
	 * static Singleton instance.
	 */
	private static volatile BaseMongo instance;

	/**
	 * Private constructor for singleton.
	 */
	private BaseMongo() {

	}

	/**
	 * Return a singleton instance of TblWardDao.
	 */
	public static BaseMongo getInstance() {
		// Double lock for thread safety.
		if (instance == null) {
			synchronized (BaseMongo.class) {
				if (instance == null) {
					instance = new BaseMongo();
				}
			}
		}
		return instance;
	}

	public Boolean insertDocument(ArrayList<Document> listDoc, String collection) {
		// TODO Auto-generated method stub
		try {
			// Fetching the collection from the mongodb.
			MongoCollection<Document> coll = db.getCollection(collection);
			coll.insertMany(listDoc);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public FindIterable<Document> selectAllDocs(String collection) {
		// TODO Auto-generated method stub
		try {
			// Fetching the collection from the mongodb.
			BasicDBObject whereQuery = new BasicDBObject();
			whereQuery.put("product_status", 1);
			System.out.println("start search");
			MongoCollection<Document> coll = db.getCollection(collection);
			FindIterable<Document> cursor = coll.find();
//		    for (Document document : cursor) {
//				System.out.println(document);
//			}
			return cursor;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();

		}
		return null;
	}

	public void removeRecordIdInAList(String collection, ArrayList<String> list) {
		// TODO Auto-generated method stub

		MongoCollection<Document> coll = db.getCollection(collection);

		coll.drop();
		DeleteResult delR = coll.deleteOne(Filters.eq("product_status", "1"));
		System.out.println("delRs" + delR.getDeletedCount());
	}
}
