package com.dark.engine.query;


import com.dark.engine.data.ReportDataSource;

public class MySqlQueryer extends AbstractQueryer implements Queryer {
    public MySqlQueryer(ReportDataSource dataSource, String sqlText) {
        super(dataSource, sqlText);
    }
}
