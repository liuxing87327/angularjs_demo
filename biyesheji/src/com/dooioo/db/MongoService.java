/**   
 * @Title: MongoService.java
 * @Package com.dooioo.db
 * @Description: TODO
 * @author 刘兴 
 * @date 2014年10月26日 下午5:06:38
 * @version V1.0   
 */
package com.dooioo.db;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.joda.time.DateTime;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

/**
 * 类功能说明：
 * Title: MongoService.java
 * @author 刘兴
 * @date 2014年10月26日 下午5:06:38
 * @version V1.0
 */
public class MongoService {

	private DBCollection getDBCollection(){
		return MongoDbClient.getInstance().getEmployee();
	}
	
    /**
     * 新增一个数据
     * 功能说明：
     * @author 刘兴 
     * @Date 2014年10月26日 下午5:13:17
     * @return
     */
    public boolean insert(String userName, String orgName, String position, String createdAt, String status){
    	DBCursor list = getDBCollection().find(new BasicDBObject()).sort(new BasicDBObject("userCode", -1)).limit(1);
    	
    	int maxUserCode = 0;
    	
		Iterator<DBObject> cursor = list.iterator();
		while (cursor.hasNext()) {
			DBObject dbObject = cursor.next();
			maxUserCode = Integer.parseInt(dbObject.get("userCode").toString());
			break;
		}
		
    	DBObject dbObject = this.buildDBObject(String.valueOf(maxUserCode + 1), userName, orgName, position, createdAt, status);
    	getDBCollection().insert(dbObject).getN();
    	return true;
    }
    
    /**
     * 根据工号删除记录
     * @param userCode
     * @return
     */
    public boolean remove(String userCode) {
    	DBObject dbObject = getDBCollection().findOne(new BasicDBObject("userCode", userCode));
        if(dbObject == null){
        	return true;
        }
        
        return getDBCollection().remove(new BasicDBObject("userCode", userCode)).getN() > 0;
    }
    
    /**
     * 编辑
     * 功能说明：
     * @author 刘兴 
     * @Date 2014年10月26日 下午5:13:17
     * @return
     */
    public boolean update(String userCode, String userName, String orgName, String position, String createdAt, String status){
    	DBObject dbObject = getDBCollection().findOne(new BasicDBObject("userCode", userCode));
    	
    	if (dbObject == null) {
    		dbObject = this.buildDBObject(userCode, userName, orgName, position, createdAt, status);
    		return getDBCollection().insert(dbObject).getN() > 0;
    	} 
    	
		DBObject updateCondition = new BasicDBObject();
		updateCondition.put("userCode", userCode);
		
		DBObject updatedValue = new BasicDBObject();
		updatedValue.put("userName", userName);
		updatedValue.put("orgName", orgName);
		updatedValue.put("position", position);
		updatedValue.put("status", status);
		updatedValue.put("createdAt", DateTime.parse(createdAt).getMillis());
		updatedValue.put("updatedAt", DateTime.now().getMillis());
		
		DBObject updateSetValue = new BasicDBObject("$set", updatedValue);
        
        return getDBCollection().update(updateCondition, updateSetValue).getN() > 0;
    }
    
    /**
     * 
     * 功能说明：根据关键字搜索数据
     * @author 刘兴 
     * @Date 2014年10月26日 下午5:20:22
     * @return
     */
	public Map<String, Object> query(String keyword, String dateFrom, String dateTo, String empStatus, int pageNo) {
		Map<String, Object> paginate = new HashMap<>();
		List<Map<String, Object>> pageList = new ArrayList<>();
		
		BasicDBObject condition = this.buildFilter(keyword, dateFrom, dateTo, empStatus);
		
		DBCursor dbCursor = getDBCollection().find(condition).sort(new BasicDBObject("userCode", 1));
		
		int count = dbCursor.count();
		DBCursor list = dbCursor.skip((pageNo - 1) * 50).limit(50);
		
		DBObject dbObject = null;
		Iterator<DBObject> cursor = list.iterator();
		while (cursor.hasNext()) {
			dbObject = cursor.next();
			
			String userCode = dbObject.get("userCode").toString();
			String userName = dbObject.get("userName").toString();
			String orgName = dbObject.get("orgName").toString();
			String position = dbObject.get("position").toString();
			String status = dbObject.get("status").toString();
			long createdAt = Long.parseLong(dbObject.get("createdAt").toString());
			
			Map<String, Object> map = new LinkedHashMap<>();
			map.put("userCode", userCode);
			map.put("userName", userName);
			map.put("orgName", orgName);
			map.put("position", position);
			map.put("status", status);
			map.put("createdAt", createdAt);
			
			pageList.add(map);
		}
		
		int totalPage = count / 50;
		if (count % 50 > 0) {
			totalPage += 1;
		}
		
		paginate.put("pageList", pageList);
		paginate.put("totalCount", count);
		paginate.put("totalPage", totalPage);
		paginate.put("pageSize", 50);
		
		return paginate;
	}
	
	/**
	 * 
	 * 功能说明：根据工号查询
	 * @author 刘兴 
	 * @Date 2014年10月26日 下午9:05:44
	 * @param paramUserCode
	 * @return
	 */
	public Map<String, Object> findOne(String paramUserCode) {
		Map<String, Object> map = new LinkedHashMap<>();
		
		DBObject dbObject = getDBCollection().findOne(new BasicDBObject("userCode", paramUserCode));
		if(dbObject == null){
			return map;
		}
		
		String userCode = dbObject.get("userCode").toString();
		String userName = dbObject.get("userName").toString();
		String orgName = dbObject.get("orgName").toString();
		String position = dbObject.get("position").toString();
		String status = dbObject.get("status").toString();
		long createdAt = Long.parseLong(dbObject.get("createdAt").toString());
		
		
		map.put("userCode", userCode);
		map.put("userName", userName);
		map.put("orgName", orgName);
		map.put("position", position);
		map.put("status", status);
		map.put("createdAt", createdAt);
		
		return map;
	}
	
	/**
	 * 
	 * 功能说明：初始化数据
	 * @author 刘兴 
	 * @Date 2014年10月28日 下午7:18:55
	 * @return
	 */
	public boolean initDate(){
		MongoDbClient client = MongoDbClient.getInstance();
		
		DBCollection db = client.getEmployee();
    	
		client.dropDatabase(db);
		
		for (int i = 0; i < 1000; i++) {
			DBCursor list = db.find(new BasicDBObject()).sort(new BasicDBObject("userCode", -1)).limit(1);
	    	
	    	int maxUserCode = 80000;
	    	
			Iterator<DBObject> cursor = list.iterator();
			while (cursor.hasNext()) {
				DBObject dbObject = cursor.next();
				maxUserCode = Integer.parseInt(dbObject.get("userCode").toString());
				break;
			}
			
			long time =  new Date().getTime();
			
	    	DBObject dbObject = this.buildDBObject(String.valueOf(maxUserCode + 1), "用户" + time, "开发2组", "软件工程师", "2014-10-10", "正式");
	    	db.insert(dbObject).getN();
		}
    	
		return true;
	}
	
	private BasicDBObject buildFilter(String keyword, String dateFrom, String dateTo, String empStatus){
		BasicDBList condList = new BasicDBList();
		
		//筛选开始时间
		if(dateFrom != null && !"".equals(dateFrom)){
			long time = DateTime.parse(dateFrom).getMillis();
			BasicDBObject condDateFrom = new BasicDBObject("createdAt", new BasicDBObject("$gte", time));
			condList.add(condDateFrom);
		}
		
		//筛选结束时间
		if(dateTo != null && !"".equals(dateTo)){
			long time = DateTime.parse(dateTo).getMillis();
			BasicDBObject condDateTo = new BasicDBObject("createdAt", new BasicDBObject("$lte", time));
			condList.add(condDateTo);
		}
		
		//筛选状态
		if(empStatus != null && !"".equals(empStatus)){
			BasicDBObject condStatus = new BasicDBObject("status", empStatus);
			condList.add(condStatus);
		}
		
		BasicDBList userList = new BasicDBList();
		
		//筛选工号和名字
		if(keyword != null && !"".equals(keyword)){
			BasicDBObject condUserCode = new BasicDBObject("userCode", Pattern.compile("^" + filterTag(keyword)));
			userList.add(condUserCode);
		
			BasicDBObject condUserName = new BasicDBObject("userName", Pattern.compile("^" + filterTag(keyword)));
			userList.add(condUserName);
		}
		
		BasicDBObject condition = new BasicDBObject();
		if(condList.size() > 0){
			condition.put("$and", condList);
		}
		
		if(userList.size() > 0){
			condition.put("$or", userList);
		}
		
		System.out.println(condition);
		return condition;
	}
    
    /**
     * 
     * 功能说明：对关键字过滤
     * @author 刘兴 
     * @Date 2014年10月26日 下午5:18:04
     * @param src
     * @return
     */
    private String filterTag(String src) {
    	return Pattern.quote(src);
	}
    
    /**
     * 
     * 功能说明：构建标准的词组
     * @author 刘兴 
     * @Date 2014年10月26日 下午8:07:07
     * @param userName
     * @param orgName
     * @param position
     * @param createdAt
     * @param status
     * @return
     */
    private DBObject buildDBObject(String userCode, String userName, String orgName, String position, String createdAt, String status) {
        DBObject dbObject = new BasicDBObject();
        dbObject.put("userCode", userCode);
        dbObject.put("userName", userName);
        dbObject.put("orgName", orgName);
        dbObject.put("position", position);
        dbObject.put("status", status);
        dbObject.put("createdAt", DateTime.parse(createdAt).getMillis());
        dbObject.put("updatedAt", 0);
        return dbObject;
    }
    
    public static void main(String[] args) {
		MongoService mongoService = new MongoService();
		mongoService.initDate();
		Map<String, Object> paginate = mongoService.query("80001", "2014-01-01", "2014-01-10", "试用", 1);
		System.out.println(paginate.get("totalCount"));
	}
}
