package com.dark.engine.query;

import com.dark.engine.data.ReportDataSource;
import com.dark.engine.exception.DbException;
import com.dark.engine.utils.JdbcUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.AttributeList;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractQueryer {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    protected final ReportDataSource dataSource;
    protected final String sqlText;

    protected AbstractQueryer(ReportDataSource dataSource, String sqlText) {
        this.dataSource = dataSource;
        this.sqlText = sqlText;
    }

    public List<List<Object>> getQueryData() throws Exception{
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<List<Object>> rows = new ArrayList<>();
        try{
            conn = this.getJdbcConnection();
            stmt = conn.createStatement();
            if(!(this.sqlText.trim().toLowerCase().startsWith("select"))){
                throw new DbException("只能做SQL查询！");
            }
            logger.info("=====开始执行:{}",new Object[]{this.sqlText});
            rs = stmt.executeQuery(this.sqlText);
            ResultSetMetaData rsmd = rs.getMetaData();

            int count=rsmd.getColumnCount();
            List<String> names=new ArrayList<>(count);

            for(int i=0;i<count;i++){
                names.add(rsmd.getColumnName(i+1));
            }
            rows = this.getMetaDataRows(rs, names);
            logger.info("=====执行完成:{}",new Object[]{this.sqlText});
        }catch(Exception e){
            logger.error("=============e:"+e);
        }finally {
            JdbcUtils.releaseJdbcResource(conn, stmt, rs);
        }
        return rows;
    }

    protected List<List<Object>> getMetaDataRows(ResultSet rs, List names) throws SQLException {
        List<List<Object>> rows = new ArrayList<>();
//        rows.add(names); //第一行放标题
        while (rs.next()) {
            List<Object> row = new ArrayList<>();
            for (Object name : names) {
                Object value = rs.getObject(name.toString());
                row.add(value);
            }
            rows.add(row);
        }
        return rows;
    }

    /**
     * 获取当前报表查询器的JDBC Connection对象
     *
     * @return Connection
     */
    protected Connection getJdbcConnection() {
        try {
            Class.forName(this.dataSource.getDriverClass());
            return JdbcUtils.getDataSource(this.dataSource).getConnection();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
