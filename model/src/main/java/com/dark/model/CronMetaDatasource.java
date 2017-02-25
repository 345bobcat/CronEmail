package com.dark.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "cron_meta_datasource")
public class CronMetaDatasource implements Serializable {
    /**
     * 数据源ID
     */
    @Id
    private Integer id;

    /**
     * 数据源名称
     */
    private String name;

    /**
     * 数据源驱动类
     */
    @Column(name = "driver_class")
    private String driverClass;

    /**
     * 数据源连接字符串(JDBC)
     */
    @Column(name = "jdbc_url")
    private String jdbcUrl;

    /**
     * 数据源登录用户名
     */
    private String user;

    /**
     * 数据源登录密码
     */
    private String password;

    /**
     * 获取报表引擎查询器类名
     */
    @Column(name = "queryer_class")
    private String queryerClass;

    /**
     * 报表引擎查询器使用的数据源连接池类名
     */
    @Column(name = "pool_class")
    private String poolClass;

    /**
     * 数据源配置选项(JSON格式）
     */
    private String options;

    /**
     * 说明备注
     */
    private String comment;

    /**
     * 记录创建时间
     */
    @Column(name = "gmt_created")
    private Date gmtCreated;

    /**
     * 记录修改时间
     */
    @Column(name = "gmt_modified")
    private Date gmtModified;

    private static final long serialVersionUID = 1L;

    public CronMetaDatasource(Integer id, String uid, String name, String driverClass, String jdbcUrl, String user, String password, String queryerClass, String poolClass, String options, String comment, Date gmtCreated, Date gmtModified) {
        this.id = id;
        this.name = name;
        this.driverClass = driverClass;
        this.jdbcUrl = jdbcUrl;
        this.user = user;
        this.password = password;
        this.queryerClass = queryerClass;
        this.poolClass = poolClass;
        this.options = options;
        this.comment = comment;
        this.gmtCreated = gmtCreated;
        this.gmtModified = gmtModified;
    }

    public CronMetaDatasource() {
        super();
    }

    /**
     * 获取数据源ID
     *
     * @return id - 数据源ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置数据源ID
     *
     * @param id 数据源ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取数据源名称
     *
     * @return name - 数据源名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置数据源名称
     *
     * @param name 数据源名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取数据源驱动类
     *
     * @return driver_class - 数据源驱动类
     */
    public String getDriverClass() {
        return driverClass;
    }

    /**
     * 设置数据源驱动类
     *
     * @param driverClass 数据源驱动类
     */
    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass == null ? null : driverClass.trim();
    }

    /**
     * 获取数据源连接字符串(JDBC)
     *
     * @return jdbc_url - 数据源连接字符串(JDBC)
     */
    public String getJdbcUrl() {
        return jdbcUrl;
    }

    /**
     * 设置数据源连接字符串(JDBC)
     *
     * @param jdbcUrl 数据源连接字符串(JDBC)
     */
    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl == null ? null : jdbcUrl.trim();
    }

    /**
     * 获取数据源登录用户名
     *
     * @return user - 数据源登录用户名
     */
    public String getUser() {
        return user;
    }

    /**
     * 设置数据源登录用户名
     *
     * @param user 数据源登录用户名
     */
    public void setUser(String user) {
        this.user = user == null ? null : user.trim();
    }

    /**
     * 获取数据源登录密码
     *
     * @return password - 数据源登录密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置数据源登录密码
     *
     * @param password 数据源登录密码
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * 获取获取报表引擎查询器类名
     *
     * @return queryer_class - 获取报表引擎查询器类名
     */
    public String getQueryerClass() {
        return queryerClass;
    }

    /**
     * 设置获取报表引擎查询器类名
     *
     * @param queryerClass 获取报表引擎查询器类名
     */
    public void setQueryerClass(String queryerClass) {
        this.queryerClass = queryerClass == null ? null : queryerClass.trim();
    }

    /**
     * 获取报表引擎查询器使用的数据源连接池类名
     *
     * @return pool_class - 报表引擎查询器使用的数据源连接池类名
     */
    public String getPoolClass() {
        return poolClass;
    }

    /**
     * 设置报表引擎查询器使用的数据源连接池类名
     *
     * @param poolClass 报表引擎查询器使用的数据源连接池类名
     */
    public void setPoolClass(String poolClass) {
        this.poolClass = poolClass == null ? null : poolClass.trim();
    }

    /**
     * 获取数据源配置选项(JSON格式）
     *
     * @return options - 数据源配置选项(JSON格式）
     */
    public String getOptions() {
        return options;
    }

    /**
     * 设置数据源配置选项(JSON格式）
     *
     * @param options 数据源配置选项(JSON格式）
     */
    public void setOptions(String options) {
        this.options = options == null ? null : options.trim();
    }

    /**
     * 获取说明备注
     *
     * @return comment - 说明备注
     */
    public String getComment() {
        return comment;
    }

    /**
     * 设置说明备注
     *
     * @param comment 说明备注
     */
    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }

    /**
     * 获取记录创建时间
     *
     * @return gmt_created - 记录创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    public Date getGmtCreated() {
        return gmtCreated;
    }

    /**
     * 设置记录创建时间
     *
     * @param gmtCreated 记录创建时间
     */
    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    /**
     * 获取记录修改时间
     *
     * @return gmt_modified - 记录修改时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * 设置记录修改时间
     *
     * @param gmtModified 记录修改时间
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", driverClass=").append(driverClass);
        sb.append(", jdbcUrl=").append(jdbcUrl);
        sb.append(", user=").append(user);
        sb.append(", password=").append(password);
        sb.append(", queryerClass=").append(queryerClass);
        sb.append(", poolClass=").append(poolClass);
        sb.append(", options=").append(options);
        sb.append(", comment=").append(comment);
        sb.append(", gmtCreated=").append(gmtCreated);
        sb.append(", gmtModified=").append(gmtModified);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}