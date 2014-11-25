package com.dooioo.api.controller;

import com.dooioo.employee.model.Employee;
import com.dooioo.employee.service.MongodbService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
* 功能说明：EmployeeApiController
* 作者：liuxing(2014-11-25 00:50)
*/
@RestController
@RequestMapping(value = "/api/v1/employee")
public class EmployeeApiController {

    private static final Logger logger = Logger.getLogger(EmployeeApiController.class);

    @Autowired
    private MongodbService mongodbService;

    /**
     * 初始化数据
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    public Map<String, Object> initDate(){
        Map<String, Object> map = new HashMap<>();

        if(mongodbService.initDate()){
            map.put("status", "ok");
        } else {
            map.put("status", "fail");
        }

        return map;
    }

    /**
     * 根据工号查询人员
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Employee view(@PathVariable("id") String id){
        return mongodbService.findOne(id);
    }

    /**
     * 新增一个数据
     * @param status
     * @param userName
     * @param orgName
     * @param position
     * @param createdAt
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Map<String, Object> add(@RequestParam(required = true, value = "status", defaultValue = "") String status,
                                   @RequestParam(required = true, value = "userName", defaultValue = "") String userName,
                                   @RequestParam(required = true, value = "orgName", defaultValue = "") String orgName,
                                   @RequestParam(required = true, value = "position", defaultValue = "") String position,
                                   @RequestParam(required = true, value = "createdAt", defaultValue = "") String createdAt){

        Map<String, Object> map = new HashMap<>();

        if(mongodbService.insert(userName, orgName, position, createdAt, status)){
            map.put("status", "ok");
        } else {
            map.put("status", "fail");
        }

        return map;
    }

    /**
     * 编辑一个数据
     * @param id
     * @param status
     * @param userName
     * @param orgName
     * @param position
     * @param createdAt
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public Map<String, Object> edit(@PathVariable("id") String id,
                                    @RequestParam(required = true, value = "status", defaultValue = "") String status,
                                    @RequestParam(required = true, value = "userName", defaultValue = "") String userName,
                                    @RequestParam(required = true, value = "orgName", defaultValue = "") String orgName,
                                    @RequestParam(required = true, value = "position", defaultValue = "") String position,
                                    @RequestParam(required = true, value = "createdAt", defaultValue = "") String createdAt){

        Map<String, Object> map = new HashMap<>();

        if(mongodbService.updateFirst(id, userName, orgName, position, createdAt, status)){
            map.put("status", "ok");
        } else {
            map.put("status", "fail");
        }

        return map;
    }

    /**
     * 删除一个记录
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Map<String, Object> delete(@PathVariable("id") String id){
        Map<String, Object> map = new HashMap<>();

        if(mongodbService.remove(id)){
            map.put("status", "ok");
        } else {
            map.put("status", "fail");
        }

        return map;
    }

    /**
     * 查询分页列表
     * @param keyword
     * @param status
     * @param dateFrom
     * @param dateTo
     * @param pageNo
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Map<String, Object> query(@RequestParam(required = false, value = "keyword") String keyword,
                                     @RequestParam(required = false, value = "status") String status,
                                     @RequestParam(required = false, value = "dateFrom") String dateFrom,
                                     @RequestParam(required = false, value = "dateTo") String dateTo,
                                     @RequestParam(required = false, value = "pageNo", defaultValue = "1") int pageNo){

        Map<String, Object> map = new HashMap<>();
        Map<String, Object> paginate = mongodbService.queryPaginate(keyword, dateFrom, dateTo, status, pageNo);

        map.put("paginate", paginate);

        return map;
    }

}
