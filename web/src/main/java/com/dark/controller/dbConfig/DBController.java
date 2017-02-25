package com.dark.controller.dbConfig;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.dark.common.ObjectConvertUtil.ReflectBeanUtils;
import com.dark.engine.data.ReportDataSource;
import com.dark.engine.utils.JdbcUtils;
import com.dark.model.CronMetaDatasource;
import com.dark.model.base.DtParam;
import com.dark.model.base.DtResponse;
import com.dark.service.base.JsonResult;
import com.dark.service.dbSource.CronSourceService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by jinwenzhang on 17/1/22.
 */
@Controller
@RequestMapping("dbConfig")
public class DBController {
    Logger logger = LoggerFactory.getLogger(DBController.class);

    @Autowired
    private CronSourceService cronSourceService;

/*    *//**
     * 自动转换日期类型的字段格式
     *//*
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
    }*/

    /**
     * 数据源页面
     *
     * @return
     */
    @RequestMapping(value = "list",method = RequestMethod.GET)
    public String dbList() {
        return "dbConfig/dbList";
    }

    /**
     * 数据源信息
     *
     * @return
     */
    @RequestMapping(value = "sourceData", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult dbSourceData(DtParam dtParam) {
        PageInfo<CronMetaDatasource> results = null;
        try {
            int rows = dtParam.getLength();
            int page = dtParam.getStart()/rows + 1;//页数
            results = cronSourceService.queryPageListByWhere(page, rows, new CronMetaDatasource());
        } catch (Exception e) {
            logger.error("======error======e:",e);
        }
        return new JsonResult(DtResponse.of(results, dtParam));
    }

    /**
     * 新增或编辑
     *
     * @return
     */
    @RequestMapping(value = "editInfo",method = RequestMethod.POST)
    public String editInfo(Integer id, Model view) {
        if(id!=null){
            CronMetaDatasource cronMetaDatasource = cronSourceService.queryById(id);
            view.addAttribute("cronMetaDatasource", cronMetaDatasource);
        }
        return "dbConfig/dbEdit";
    }

    /**
     * 根据id获取信息详情
     *
     * @return
     */
    @RequestMapping(value = "getInfo", method = RequestMethod.POST)
    public String getInfo(Model view, Integer id) {
        CronMetaDatasource result = null;
        try {
            result = cronSourceService.queryById(id);
            String options = result.getOptions();
            Map<String, Object> optionsMap = JSON.parseObject(options, new TypeReference<LinkedHashMap<String, Object>>(){});
            view.addAttribute("options", optionsMap);
            view.addAttribute("cronDataSource", ReflectBeanUtils.objectToMap(result));
        } catch (Exception e) {
            logger.error("======error======e:", e);
        }
        return "dbConfig/dbInfo";
    }

    /**
     * 根据id删除信息
     *
     * @return
     */
    @RequestMapping(value = "delById", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult delById(Integer id) {
        try {
            cronSourceService.deleteById(id);
        } catch (Exception e) {
            logger.error("======error======e:", e);
            return new JsonResult(false,"");
        }
        return new JsonResult();
    }

    /**
     * 新增或者修改
     *
     * @return
     */
    @RequestMapping(value = "saveEdit", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult saveEdit(CronMetaDatasource source) {
        try {
            if (source.getId()==null){
                cronSourceService.save(source);
            }else{
                cronSourceService.updateSelective(source);
            }
        } catch (Exception e) {
            logger.error("======error======e:", e);
            return new JsonResult(false, "");
        }
        return new JsonResult();
    }

    /**
     * 测试数据库连接
     */
    @RequestMapping(value = "testDb", method = RequestMethod.POST)
    @ResponseBody
    public Boolean testDb(Integer id) {
        Connection conn = null;
        try {
            CronMetaDatasource rs = cronSourceService.queryById(id);
            ReportDataSource rds = new ReportDataSource();
            BeanUtils.copyProperties(rs, rds, new String[] { "options" });
            rds.setDbPoolClass(rs.getPoolClass());
            rds.setOptions(JSON.parseObject(rs.getOptions()));

            Class.forName(rds.getDriverClass());
            conn = JdbcUtils.getDataSource(rds).getConnection();
            return true;
        } catch (Exception ex) {
            logger.error("==============e:"+ex);
            return false;
        }finally {
            JdbcUtils.releaseJdbcResource(conn, null , null);
        }
    }
}
