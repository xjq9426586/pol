package jdbc;

/**
 * @Auther: xujunqian
 * @Date: 2019/11/5 0005 10:51
 * @Description:
 */

import com.alibaba.fastjson.JSONArray;
import com.jeedev.msdp.utlis.CollectionUtil;
import com.jeedev.msdp.utlis.ExcelReadUtil;
import com.jeedev.msdp.utlis.MapUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class DatabaseUtil {
    private final static Logger LOGGER = LoggerFactory.getLogger(DatabaseUtil.class);
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://27.154.242.142:13006/tansun_workflow?useUnicode=true&characterEncoding=utf8";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "credit2019!@#";

    private static final String SQL = "SELECT * FROM ";// 数据库操作

    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            LOGGER.error("can not load jdbc driver", e);
        }
    }

    /**
     * 获取数据库连接
     *
     * @return
     */
    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            LOGGER.error("get connection failure", e);
        }
        return conn;
    }

    /**
     * 关闭数据库连接
     *
     * @param conn
     */
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                LOGGER.error("close connection failure", e);
            }
        }
    }

    /**
     * 获取数据库下的所有表名
     */
    public static List<String> getTableNames() {
        List<String> tableNames = new ArrayList<>();
        Connection conn = getConnection();
        ResultSet rs = null;
        try {
            //获取数据库的元数据
            DatabaseMetaData db = conn.getMetaData();
            String url = db.getURL();
            String table_schema = url.substring(url.lastIndexOf("/") + 1, url.indexOf("?"));
            System.out.println(table_schema);
            //从元数据中获取到所有的表名
            rs = db.getTables(null, null, null, new String[]{"TABLE"});
            while (rs.next()) {
                tableNames.add(rs.getString(3));
            }
        } catch (SQLException e) {
            LOGGER.error("getTableNames failure", e);
        } finally {
            try {
                rs.close();
                closeConnection(conn);
            } catch (SQLException e) {
                LOGGER.error("close ResultSet failure", e);
            }
        }
        return tableNames;
    }

    /**
     * 获取表中字段的所有注释
     *
     * @param tableName
     * @return
     */
    public static List<String> getColumnComments(String tableName) {
        List<String> columnTypes = new ArrayList<>();
        //与数据库的连接
        Connection conn = getConnection();
        PreparedStatement pStemt = null;
        String tableSql = SQL + tableName;
        List<String> columnComments = new ArrayList<>();//列名注释集合
        ResultSet rs = null;
        try {
            pStemt = conn.prepareStatement(tableSql);
            rs = pStemt.executeQuery("show full columns from " + tableName);
            while (rs.next()) {
                columnComments.add(rs.getString("Comment"));
//                String field = rs.getString("Field");//获取字段名
//                System.out.println(field);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    closeConnection(conn);
                } catch (SQLException e) {
                    LOGGER.error("getColumnComments close ResultSet and connection failure", e);
                }
            }
        }
        return columnComments;
    }

    public static void test() {
        //启动IoC容器
        ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
        //获取IoC容器中JdbcTemplate实例
        JdbcTemplate jdbcTemplate = (JdbcTemplate) ctx.getBean("jdbcTemplate");
        List<Map<String, Object>> fieldList = new ArrayList<>();
        //获取表名和表注释
        String tableSql = "SELECT distinct A.OBJECT_NAME as \"tableName\",B.comments as \"descr\" FROM USER_OBJECTS A" +
                ", USER_TAB_COMMENTS B where A.OBJECT_NAME=B.TABLE_NAME";
        List<Map<String, Object>> tableList = jdbcTemplate.queryForList(tableSql);
        tableList.forEach(tableInfo -> {
            Map<String, Object> reMap = new HashMap<>();
            String tableName = MapUtil.getString(tableInfo, "tableName");
            String descr = MapUtil.getString(tableInfo, "descr");
            //获取表字段与注释
            String sql = "select t.column_name as \"Field\",t.comments as \"Comment\" from user_col_comments t " +
                    "where t.table_name = '" + tableName + "'";
            List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
            convert(list, reMap);
            reMap.put("tableName", tableName.toLowerCase());//表名
            reMap.put("tableComment", descr);//表注释
            if (tableList.indexOf(tableInfo) > 5) {
                return;
            }
            fieldList.add(reMap);

        });
        System.out.println(JSONArray.toJSONString(fieldList));
    }

    public static void updateInfo(String tableName, String wfClass) {
        //与数据库的连接
        Connection conn = getConnection();
        PreparedStatement pStemt = null;
        String tableSql = SQL + tableName + " where wf_class_id = ?";
        String updateSql = "update " + tableName + " set FIELD_KEY = ? where ID = ?";
        ResultSet rs = null;
        try {
            List<List> finaceList = ExcelReadUtil.readExcel(new File("C:\\Users\\xjq\\Desktop\\heshun2_20201119_7(1).xls"));
            List<Map<String, Object>> list = finaceList.get(0);

            pStemt = conn.prepareStatement(tableSql);
            pStemt.setString(1, wfClass);
            rs = pStemt.executeQuery();
            while (rs.next()) {
                String id = rs.getString("ID");
                String fieldKey = rs.getString("FIELD_KEY");
                String fieldName = rs.getString("FIELD_NAME");
                List<Map<String, Object>> es = list.stream().filter(map -> map.get("4").equals(fieldName)).collect(Collectors.toList());
                if (CollectionUtil.isNotEmpty(es)) {
                    if (es.get(0).get("6").equals(fieldKey)) {
                        System.out.println("原值：" + fieldKey);
                    } else {
                        System.out.println("原值：" + fieldKey + "     " + "新值：" + es.get(0).get("6"));
                        fieldKey = (String) es.get(0).get("6");
                    }
                } else {
                    System.out.println("原值：" + fieldKey + "     " + "未找到值：");
                }
                pStemt = conn.prepareStatement(updateSql);
                pStemt.setString(1, fieldKey);
                pStemt.setString(2, id);
                pStemt.executeUpdate();

            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    closeConnection(conn);
                } catch (SQLException e) {
                    LOGGER.error("getColumnComments close ResultSet and connection failure", e);
                }
            }
        }
    }

    public static Map<String, Object> convert(List<Map<String, Object>> list, Map<String, Object> reMap) {
        list.forEach(item -> {
            String field = MapUtil.getString(item, "Field").toLowerCase();
            String comment = MapUtil.getString(item, "Comment");
            StringBuilder sb = new StringBuilder();
            List<String> arrs = Arrays.asList(field.split("_"));
            arrs.forEach(str -> sb.append(arrs.indexOf(str) == 0 ? str : StringUtils.capitalize(str)));
            reMap.put(sb.toString(), comment);
        });
        return reMap;
    }

    public static void main(String[] args) {
        //test();
//        List<String> tableNames = getTableNames();
//        System.out.println("tableNames:" + tableNames);
//        for (String tableName : tableNames) {
////            System.out.println("ColumnTypes:" + getColumnTypes(tableName));
//            System.out.println("ColumnComments:" + getColumnComments(tableName));
//        }
        updateInfo("wf_task_expand_plan", "ec5736a071674855ace0a2401a321569");
    }
}