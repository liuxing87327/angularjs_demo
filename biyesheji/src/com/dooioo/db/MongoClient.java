//package com.dooioo.db;
//
//import java.net.UnknownHostException;
//import java.util.ArrayList;
//import java.util.List;
//
//import com.mongodb.BasicDBObject;
//import com.mongodb.DB;
//import com.mongodb.DBCollection;
//import com.mongodb.DBObject;
//import com.mongodb.Mongo;
//import com.mongodb.MongoOptions;
//import com.mongodb.ServerAddress;
//
///**
// * 
// * 类功能说明：mongo连接池
// * Title: MongoClient.java
// * @author 刘兴
// * @date 2014年10月26日 下午4:26:01
// * @version V1.0
// */
//public class MongoClient {
//	
//    private static MongoClient client;
//
//    public static final String SERVER_IP;
//    public static final int SERVER_PORT;
//    public static final int CONNECTIONS;
//
//    public static final String DB_NAME;
//    public static final boolean AUTOCONNECTRETRY;
//    public static final int THREADSALLOWEDTOBLOCKFORCONNECTIONMULTIPLIER;
//    public static final int MAXWAITTIME;
//    public static final int SOCKETTIMEOUT;
//    public static final int CONNECTTIMEOUT;
//    public static final String username = "URrRt070VEvNA1ejIzNennVC";			//用户名(api key);
//    public static final String password = "0rSonRiz2GMg9PgBn9pEeQKceUO1ca0X";	//密码(secret key)
//
//    private Mongo connection;
//    private DB db;
//
//    static {
//        SERVER_IP = "127.0.0.1";
//        SERVER_PORT = 27017;
//        DB_NAME = "test";
//        CONNECTIONS = 10;
//        AUTOCONNECTRETRY = true;
//        THREADSALLOWEDTOBLOCKFORCONNECTIONMULTIPLIER = 20;
//        MAXWAITTIME = 5000;
//        SOCKETTIMEOUT = 3000;
//        CONNECTTIMEOUT = 5000;
//    }
//
//    private MongoClient() throws Exception {
//        MongoOptions options = new MongoOptions();
//        // 连接错误时，是否重试
//        options.autoConnectRetry = AUTOCONNECTRETRY;
//        // 连接池
//        options.connectionsPerHost = CONNECTIONS;
//        // 线程队列数
//        options.threadsAllowedToBlockForConnectionMultiplier = THREADSALLOWEDTOBLOCKFORCONNECTIONMULTIPLIER;
//        // 最大等待连接的线程阻塞时间
//        options.maxWaitTime = MAXWAITTIME;
//        // socket超时。0是默认和无限
//        options.socketTimeout = SOCKETTIMEOUT;
//        // 连接超时的毫秒。0是默认和无限
//        options.connectTimeout = CONNECTTIMEOUT;
//        options.cursorFinalizerEnabled = true;
//        connection = new Mongo(buildHostList(), options);
//        
//        db = connection.getDB(DB_NAME);
//    }
//    
//    private List<ServerAddress> buildHostList() throws UnknownHostException{
//		List<ServerAddress> mongoHostList = new ArrayList<ServerAddress>();
//		
//		String[] servers = SERVER_IP.split("/");
//		for (String serverIp : servers) {
//			mongoHostList.add(new ServerAddress(serverIp, SERVER_PORT));
//		}
//		
//		return mongoHostList;
//    }
//
//    public static synchronized MongoClient getInstance() {
//        if (client == null){
//        	try {
//        		client = new MongoClient();
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        }
//        return client;
//    }
//
//    public DBCollection getEmployee() {
//        return db.getCollection("Employee");
//    }
//
//    public DBCollection getCollection(String name) {
//        return db.getCollection(name);
//    }
//    
//    /**
//     * 
//     * 功能说明：打印索引数据
//     * 刘兴  2013-8-16 上午10:56:11
//     * 修改者名字：
//     * 修改日期：
//     * 修改内容：
//     */
//    public void printIndexInfo(DBCollection dbCollection){
//    	List<DBObject> list = dbCollection.getIndexInfo();
//	 
//    	for(DBObject index : list){
//    		System.out.println(index);
//    	}
//    }
//    
//    /**
//     * 
//     * 功能说明：删除表，使用该操作需要重建索引
//     * 刘兴  2013-8-16 上午10:42:37
//     * 修改者名字：
//     * 修改日期：
//     * 修改内容：
//     */
//    public void dropDatabase(DBCollection dbCollection){
//    	dbCollection.drop();
//    	this.createIndex(dbCollection);
//    }
//    
//    /**
//     * 
//     * 功能说明：设置索引
//     * 刘兴  2013-8-16 上午10:47:22
//     * 修改者名字：
//     * 修改日期：
//     * 修改内容：
//     */
//    private void createIndex(DBCollection dbCollection){
//    	dbCollection.createIndex(new BasicDBObject("userCode", 1)); // 1代表升序
//        dbCollection.createIndex(new BasicDBObject("userName", 1)); // 1代表升序
//        dbCollection.createIndex(new BasicDBObject("createdAt", 1)); // 1代表升序
//    }
//    
//    public static void main(String[] args) {
//    	DBCollection dbCollection = MongoClient.getInstance().getEmployee();
//    	MongoClient.getInstance().dropDatabase(dbCollection);
//	}
//}
