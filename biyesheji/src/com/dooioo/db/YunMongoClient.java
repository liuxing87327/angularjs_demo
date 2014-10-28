package com.dooioo.db;

import java.util.Arrays;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.ServerAddress;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;

/**
 * 
 * 类功能说明：百度云的Mongodb
 * Title: MongoClient.java
 * @author 刘兴
 * @date 2014年10月26日 下午4:26:01
 * @version V1.0
 */
public class YunMongoClient {
	
    private static YunMongoClient client;

    private DB db;

    private YunMongoClient() throws Exception {
		String databaseName = "zQVqlLrEWYIrbQyuEjID";
		String host = "mongo.duapp.com";
		String port = "8908";
		String username = "URrRt070VEvNA1ejIzNennVC";
		String password = "mWFaNXeU097nCPZLwG6V1yjPOI592oXn";
		String serverName = host + ":" + port;
      
      	MongoClient mongoClient = new MongoClient(new ServerAddress(serverName),
									Arrays.asList(MongoCredential.createMongoCRCredential(username, databaseName, password.toCharArray())),
									new MongoClientOptions.Builder().cursorFinalizerEnabled(false).build());
		db = mongoClient.getDB(databaseName);
		db.authenticate(username, password.toCharArray());
//		mongoClient.close();
		
//		String server = "mongo.duapp.com";
//		String port = "8908";
//		String host = server + ":" + port;
//		String user = "URrRt070VEvNA1ejIzNennVC";
//		String password = "mWFaNXeU097nCPZLwG6V1yjPOI592oXn";
//		String databaseName = "TEVLZmAPWMBCpAlVcKWz";
//		MongoClient mongoClient = new MongoClient(new ServerAddress(host),
//		Arrays.asList(MongoCredential.createMongoCRCredential(user, databaseName, password.toCharArray())),
//		new MongoClientOptions.Builder().cursorFinalizerEnabled(false).build());
//		DB testDB = mongoClient.getDB(databaseName);
//		testDB.authenticate(user, password.toCharArray());
    }
    
    public static synchronized YunMongoClient getInstance() {
    	try {
    		client = new YunMongoClient();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    	
        return client;
    }

    public DBCollection getEmployee() {
        return db.getCollection("Employee");
    }

    public DBCollection getCollection(String name) {
        return db.getCollection(name);
    }
    
    /**
     * 
     * 功能说明：打印索引数据
     * 刘兴  2013-8-16 上午10:56:11
     * 修改者名字：
     * 修改日期：
     * 修改内容：
     */
    public void printIndexInfo(DBCollection dbCollection){
    	List<DBObject> list = dbCollection.getIndexInfo();
	 
    	for(DBObject index : list){
    		System.out.println(index);
    	}
    }
    
    /**
     * 
     * 功能说明：删除表，使用该操作需要重建索引
     * 刘兴  2013-8-16 上午10:42:37
     * 修改者名字：
     * 修改日期：
     * 修改内容：
     */
    public void dropDatabase(DBCollection dbCollection){
    	dbCollection.drop();
    	this.createIndex(dbCollection);
    }
    
    /**
     * 
     * 功能说明：设置索引
     * 刘兴  2013-8-16 上午10:47:22
     * 修改者名字：
     * 修改日期：
     * 修改内容：
     */
    private void createIndex(DBCollection dbCollection){
    	dbCollection.createIndex(new BasicDBObject("userCode", 1)); // 1代表升序
        dbCollection.createIndex(new BasicDBObject("userName", 1)); // 1代表升序
        dbCollection.createIndex(new BasicDBObject("createdAt", 1)); // 1代表升序
    }
    
    public static void main(String[] args) {
    	DBCollection dbCollection = YunMongoClient.getInstance().getEmployee();
    	YunMongoClient.getInstance().dropDatabase(dbCollection);
	}
}
