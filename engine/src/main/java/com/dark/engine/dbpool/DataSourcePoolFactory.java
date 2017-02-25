package com.dark.engine.dbpool;

/**
 * 数据源连接池工厂
 */
public class DataSourcePoolFactory {
    public static DataSourcePoolWrapper create(String className) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        return (DataSourcePoolWrapper) Class.forName(className).newInstance();
    }
}
