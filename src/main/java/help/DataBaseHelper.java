package help;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import util.CollectionUtil;
import util.PropsUtil;

/**
 * 数据库连接
 *
 * @author 方宗庆
 * @create 2018-02-01 16:58
 */
public final class DataBaseHelper {
    /**
     * log4j
     */
    private static final Logger LOG = LoggerFactory.getLogger(DataBaseHelper.class);
    /**
     * DbUtils提供的
     */
    private static final QueryRunner QUERY_RUNNER;

    /**
     * 本地线程
     */
    private static final ThreadLocal<Connection> CONNECTION_HOLDER;

    /**
     * DBCP连接池
     */
    private static final BasicDataSource DATA_SOURCE;


    static {
        CONNECTION_HOLDER = new ThreadLocal<Connection>();
        QUERY_RUNNER = new QueryRunner();

        Properties conf = PropsUtil.loadProps("config.properties");
        String driver = conf.getProperty("jdbc.driver");
        String url = conf.getProperty("jdbc.url");
        String username = conf.getProperty("jdbc.username");
        String password = conf.getProperty("jdbc.password");
        //dbcp数据连接池
        DATA_SOURCE = new BasicDataSource();
        DATA_SOURCE.setDriverClassName(driver);
        DATA_SOURCE.setUrl(url);
        DATA_SOURCE.setUsername(username);
        DATA_SOURCE.setPassword(password);
    }


    /***
     *
     * 获取数据库连接
     * @param
     * @return java.sql.Connection
     * @author 方宗庆
     * @date 2018/2/1 17:10
     * @since 1.0.0
     */
    public static Connection getConnection() {
        Connection conn = CONNECTION_HOLDER.get();
        if (conn == null) {
            try {
                conn = DATA_SOURCE.getConnection();
            } catch (SQLException e) {
                LOG.error("连接失败", e);
                throw new RuntimeException(e);
            } finally {
                CONNECTION_HOLDER.set(conn);
            }
        }
        return conn;
    }

    /***
     * 获取表名
     *
     * @param entityClass
     * @return java.lang.String
     * @author 方宗庆
     * @date 2018/2/1 19:21
     * @since 1.0.0
     */
    private static <T> String getTableName(Class<T> entityClass) {
        return entityClass.getSimpleName();
    }


    /***
     *
     * 关闭连接
     * @param
     * @return void
     * @author 方宗庆
     * @date 2018/2/1 17:11
     * @since 1.0.0
     */
    public static void close() {
        Connection conn = CONNECTION_HOLDER.get();
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                LOG.error("关闭连接失败", e);
                throw new RuntimeException(e);
            } finally {
                CONNECTION_HOLDER.remove();
            }
        }
    }

    /***
     * 查询实体列表
     *
     * @param entityClass
     * @param sql
     * @param params
     * @return java.util.List<T>
     * @author 方宗庆
     * @date 2018/2/1 17:39
     * @since 1.0.0
     */
    public static <T> List<T> queryEntityList(Class<T> entityClass, String sql, Object... params) {
        List<T> entityList = null;
        try {
            Connection conn = getConnection();
            entityList = QUERY_RUNNER.query(conn, sql, new BeanListHandler<T>(entityClass), params);
        } catch (SQLException e) {
            LOG.error("查询出现错误", e);
            throw new RuntimeException(e);
        } finally {
            close();
        }
        return entityList;
    }

    /**
     * 查询实体
     */
    public static <T> T queryEntity(Class<T> entityClass, String sql, Object... params) {
        T entity = null;
        Connection conn = getConnection();
        try {
            entity = QUERY_RUNNER.query(conn, sql, new BeanHandler<T>(entityClass), params);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        } finally {
            close();
        }
        return entity;
    }

    /**
     * 执行查询语句
     */
    public static List<Map<String, Object>> executeQuery(String sql, Object... params) {
        List<Map<String, Object>> result;
        try {
            Connection conn = getConnection();
            result = QUERY_RUNNER.query(conn, sql, new MapListHandler(), params);
        } catch (Exception e) {
            LOG.error("查询失败", e);
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * 执行更新语句
     */
    public static int executeUpdate(String sql, Object... params) {
        int rows = 0;
        try {
            Connection conn = getConnection();
            rows = QUERY_RUNNER.update(conn, sql, params);
        } catch (SQLException e) {
            LOG.error("执行更新语句失败", e);
            throw new RuntimeException(e);
        } finally {
            close();
        }
        return rows;
    }

    /***
     * 插入实体
     *
     * @param entityClass
     * @param fieldMap
     * @return boolean
     * @author 方宗庆
     * @date 2018/2/1 18:52
     * @since 1.0.0
     */
    public static <T> boolean insertEntity(Class<T> entityClass, Map<String, Object> fieldMap) {
        if (CollectionUtil.isEmpty(fieldMap)) {
            LOG.error("不能插入为空的实体");
            return false;
        }
        String sql = "INSERT INTO" + getTableName(entityClass);
        StringBuilder columns = new StringBuilder("(");
        StringBuilder values = new StringBuilder("(");
        for (String fieldName : fieldMap.keySet()) {
            columns.append(fieldName).append(",");
            values.append("?,");
        }
        columns.replace(columns.lastIndexOf(","), columns.length(), ")");
        values.replace(values.lastIndexOf(","), values.length(), ")");
        sql += columns + "VALUES" + values;
        Object[] params = fieldMap.values().toArray();
        return executeUpdate(sql, params) == 1;
    }

    /**
     * 更新实体
     */
    public static <T> boolean updateEntity(Class<T> entityClass, long id, Map<String, Object> fieldMap) {
        if (CollectionUtil.isEmpty(fieldMap)) {
            LOG.error("不能更新空的实体");
            return false;
        }
        String sql = "UPDATE" + getTableName(entityClass) + "SET";
        StringBuilder columns = new StringBuilder();
        for (String fieldName : fieldMap.keySet()) {
            columns.append(fieldName).append("=?,");
        }
        sql += columns.substring(0, columns.lastIndexOf(",")) + "WHERE id=?";
        List<Object> paramsList = new ArrayList<>();
        paramsList.addAll(fieldMap.values());
        paramsList.add(id);
        Object[] params = paramsList.toArray();
        return executeUpdate(sql, params) == 1;
    }

    /**
     * 删除实体
     */
    public static <T> boolean deleteEntity(Class<T> entityClass, long id) {
        String sql = "DELETE FROM" + getTableName(entityClass) + "WHERE ID=?";
        return executeUpdate(sql, id) == 1;
    }

    /**
     * 执行sql文件
     *
     * @return void
     * @author 方宗庆
     * @date 2018/2/5 18:55
     * @since 1.0.0
     */
    public static void executeSqlFile(String filePath) {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String sql;
        try {
            while ((sql = reader.readLine()) != null) {
                executeUpdate(sql);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("执行sql文件错误", e);
            throw new RuntimeException(e);
        }
    }

}