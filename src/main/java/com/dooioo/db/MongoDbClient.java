package com.dooioo.db;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.MongoOptions;
import com.mongodb.ServerAddress;

/**
 * 
 * 类功能说明：mongo连接池
 * Title: MongoClient.java
 * @author 刘兴
 * @date 2014年10月26日 下午4:26:01
 * @version V1.0
 */
public class MongoDbClient {
	
private static MongoDbClient client;
	
    private DB db;
    private boolean isLocal = true;
    
    private MongoDbClient() throws Exception {
    	if(this.isLocal){
			this.initDbFromLocal();
		} else {
			this.initDbFromYun();
		}
    }	
    
    /**
     * 
     * 功能说明：初始化百度云的mongodb连接
     * @author 刘兴 
     * @Date 2014年10月31日 上午2:01:21
     * @throws UnknownHostException
     */
    private void initDbFromYun() throws UnknownHostException{
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
    }
    
    /**
     * 
     * 功能说明：初始化本地的mongodb连接
     * @author 刘兴 
     * @Date 2014年10月31日 上午2:01:45
     * @throws UnknownHostException
     */
    private void initDbFromLocal() throws UnknownHostException{
    	MongoOptions options = new MongoOptions();
        // 连接错误时，是否重试
        options.autoConnectRetry = true;
        // 连接池
        options.connectionsPerHost = 10;
        // 线程队列数
        options.threadsAllowedToBlockForConnectionMultiplier = 20;
        // 最大等待连接的线程阻塞时间
        options.maxWaitTime = 5000;
        // socket超时。0是默认和无限
        options.socketTimeout = 3000;
        // 连接超时的毫秒。0是默认和无限
        options.connectTimeout = 5000;
        options.cursorFinalizerEnabled = false;
        
        Mongo connection = new Mongo(buildHostList(), options);
        
        db = connection.getDB("test");
    }
    
    private List<ServerAddress> buildHostList() throws UnknownHostException{
		List<ServerAddress> mongoHostList = new ArrayList<ServerAddress>();
		
		String[] servers = "127.0.0.1".split("/");
		for (String serverIp : servers) {
			mongoHostList.add(new ServerAddress(serverIp, 27017));
		}
		
		return mongoHostList;
    }

    public static synchronized MongoDbClient getInstance() {
    	try {
    		client = new MongoDbClient();
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
    
}
