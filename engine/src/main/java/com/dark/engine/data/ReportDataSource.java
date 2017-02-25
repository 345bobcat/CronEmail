package com.dark.engine.data;

import java.util.HashMap;
import java.util.Map;

/**
 * 报表数据源类
 */
public class ReportDataSource {
    private String uid;
    private String queryerClass;
    private String dbPoolClass;
    private String driverClass;
    private String jdbcUrl;
    private String user;
    private String password;
    private Map<String, Object> options;

    public ReportDataSource(){}

    public ReportDataSource(String uid, String driverClass, String jdbcUrl, String user, String password,
                            String queryerClass, String dbPoolClass) {
        this(uid, driverClass, jdbcUrl, user, password, queryerClass, dbPoolClass, new HashMap<>(3));
    }

    public ReportDataSource(String uid, String driverClass, String jdbcUrl, String user, String password,
                            String queryerClass, String dbPoolClass,
                            Map<String, Object> options) {
        this.uid = uid;
        this.driverClass = driverClass;
        this.jdbcUrl = jdbcUrl;
        this.user = user;
        this.password = password;
        this.queryerClass = queryerClass;
        this.dbPoolClass = dbPoolClass;
        this.options = options;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getQueryerClass() {
        return queryerClass;
    }

    public void setQueryerClass(String queryerClass) {
        this.queryerClass = queryerClass;
    }

    public String getDbPoolClass() {
        return dbPoolClass;
    }

    public void setDbPoolClass(String dbPoolClass) {
        this.dbPoolClass = dbPoolClass;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Map<String, Object> getOptions() {
        return options;
    }

    public void setOptions(Map<String, Object> options) {
        this.options = options;
    }
}
