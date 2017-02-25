package com.dark.engine.dbpool;


import com.dark.engine.data.ReportDataSource;
import javax.sql.DataSource;

/**
 * 数据源连接包装器
 */
public interface DataSourcePoolWrapper {
    DataSource wrap(ReportDataSource rptDs);
}
