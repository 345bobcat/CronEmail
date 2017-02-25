package com.dark.service;

import com.alibaba.fastjson.JSON;
import com.dark.engine.data.ReportDataSource;
import com.dark.engine.query.MySqlQueryer;
import com.dark.model.CronMetaDatasource;
import com.dark.service.dbSource.CronSourceService;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

/**
 * Created by jinwenzhang on 17/2/6.
 */
public class QuerySqlTest extends BaseJunit{
    @Autowired
    private CronSourceService cronSourceService;

    @Test
    public void testQueryData() throws Exception {
        CronMetaDatasource rs = cronSourceService.queryById(15L);

        ReportDataSource rds = new ReportDataSource();
        BeanUtils.copyProperties(rs, rds, new String[] { "options" });
        rds.setDbPoolClass(rs.getPoolClass());
        rds.setOptions(JSON.parseObject(rs.getOptions()));

        MySqlQueryer msq = new MySqlQueryer(rds, "select * from dim_area_cn limit 10");
        List<List<Object>> t = msq.getQueryData();

        int a = 0;
    }
}
