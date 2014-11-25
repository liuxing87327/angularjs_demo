package com.dooioo.employee.service;

import com.dooioo.employee.model.Employee;
import com.mongodb.*;
import org.joda.time.DateTime;
import org.nutz.lang.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
* 功能说明：MongodbService
* 作者：liuxing(2014-11-21 04:01)
*/
@Service
public class MongodbService {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 初始数据
     * @return
     */
    public boolean initDate(){
        mongoTemplate.dropCollection(Employee.class);
        DBCollection dbCollection = mongoTemplate.createCollection(Employee.class);
        createIndex(dbCollection);

        List<Employee> list = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            int maxUserCode = 80000 + i;

            Employee employee = new Employee();
            employee.setUserCode(String.valueOf(maxUserCode));
            employee.setUserName("用户" + DateTime.now().getMillis());
            employee.setOrgName("开发2组");
            employee.setPosition("软件工程师");
            employee.setCreatedAt(DateTime.now().getMillis());
            employee.setUpdatedAt(0L);
            employee.setStatus("正式");

            list.add(employee);

            try {
                TimeUnit.MILLISECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        mongoTemplate.insertAll(list);

        return true;
    }

    /**
     * 根据主键查询一个记录
     * @param userCode
     * @return
     */
    public Employee findOne(String userCode) {
        return mongoTemplate.findOne(new Query(Criteria.where("userCode").is(userCode)), Employee.class);
    }

    /**
     * 查询分页
     * @param keyword
     * @param dateFrom
     * @param dateTo
     * @param empStatus
     * @param pageNo
     * @return
     */
    public Map<String, Object> queryPaginate(String keyword, String dateFrom, String dateTo, String empStatus, int pageNo){
        Map<String, Object> paginate = new HashMap<>();

        Query query = this.buildFilter(keyword, dateFrom, dateTo, empStatus);
        query.with(new Sort(Sort.Direction.ASC, "userCode"));
        query.skip((pageNo - 1) * 50).limit(50);

        long count = mongoTemplate.count(query, Employee.class);
        List<Employee> pageList = mongoTemplate.find(query, Employee.class);

        long totalPage = count / 50L;
        if (count % 50L > 0L) {
            totalPage += 1L;
        }

        paginate.put("pageList", pageList);
        paginate.put("totalCount", count);
        paginate.put("totalPage", totalPage);
        paginate.put("pageSize", 50);

        return paginate;
    }

    /**
     * 组装查询条件
     * @param keyword
     * @param dateFrom
     * @param dateTo
     * @param empStatus
     * @return
     */
    private Query buildFilter(String keyword, String dateFrom, String dateTo, String empStatus){
        Query query = new Query();

        //筛选开始时间
        if(!Strings.isBlank(dateFrom)){
            long time = DateTime.parse(dateFrom).getMillis();
            query.addCriteria(Criteria.where("createdAt").gte(time));
        }

        //筛选结束时间
        if(!Strings.isBlank(dateTo)){
            long time = DateTime.parse(dateTo).getMillis();
            query.addCriteria(Criteria.where("createdAt").lte(time));
        }

        //筛选状态
        if(!Strings.isBlank(empStatus)){
            query.addCriteria(Criteria.where("status").is(empStatus));
        }

        //筛选工号和名字,模糊查询，不区分大小写
        if (!Strings.isBlank(keyword)) {
            Criteria keywordCriteria = new Criteria();
            query.addCriteria(keywordCriteria.orOperator(
                Criteria.where("userCode").regex(Pattern.compile("^.*" + filterTag(keyword) + ".*$", Pattern.CASE_INSENSITIVE)),
                Criteria.where("userName").regex(Pattern.compile("^.*" + filterTag(keyword) + ".*$", Pattern.CASE_INSENSITIVE))
            ));
        }

        return query;
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
     * 新增一个记录
     * @param userName
     * @param orgName
     * @param position
     * @param createdAt
     * @param status
     * @return
     */
    public boolean insert(String userName, String orgName, String position, String createdAt, String status) {
        Employee insertEmployee = new Employee();
        insertEmployee.setUserName(userName);
        insertEmployee.setOrgName(orgName);
        insertEmployee.setPosition(position);
        insertEmployee.setStatus(status);
        insertEmployee.setCreatedAt(DateTime.parse(createdAt).getMillis());

        BasicQuery query = new BasicQuery(new BasicDBObject());
        query.setSortObject(new BasicDBObject("userCode", -1));

        Employee employee = mongoTemplate.findOne(query, Employee.class);
        insertEmployee.setUserCode(String.valueOf(Integer.parseInt(employee.getUserCode()) + 1));
        insertEmployee.setUpdatedAt(0L);

        mongoTemplate.insert(insertEmployee);
        return true;
    }

    /**
     * 根据工号更新第一条数据
     * @param userCode
     * @param userName
     * @param orgName
     * @param position
     * @param createdAt
     * @param status
     * @return
     */
    public boolean updateFirst(String userCode, String userName, String orgName, String position, String createdAt, String status){
        Query query = new Query(Criteria.where("userCode").is(userCode));
        Update update = new Update().set("userName", userName)
                                    .set("orgName", orgName)
                                    .set("position", position)
                                    .set("status", status)
                                    .set("createdAt", DateTime.parse(createdAt).getMillis())
                                    .set("updatedAt", DateTime.now().getMillis());
        return mongoTemplate.updateFirst(query, update, Employee.class).getN() > 0;
    }

    /**
     * 根据主键删除一条记录
     * @param userCode
     * @return
     */
    public boolean remove(String userCode) {
        Criteria criteria = Criteria.where("userCode").in(userCode);
        if (criteria == null) {
            return false;
        }

        Query query = new Query(criteria);
        if (query == null) {
            return false;
        }

        Employee employee = mongoTemplate.findOne(query, Employee.class);
        if (employee == null) {
            return false;
        }

        return mongoTemplate.remove(query, Employee.class).getN() > 0;
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
