package test;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import beanHelper.ClassHelper;
import com.google.common.collect.Maps;

import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

public class test {
    public static void main(String[] args) throws IOException, SQLException {
        String url = "jdbc:mysql://172.19.80.76:3306/tcp_3_0_0?characterEncoding=UTF-8&useUnicode=true&useCompression=true&useSSL=false";
        String user = "root";
        String password = "tansun";

        Connection connection = DriverManager.getConnection(url, user, password);

    }

    }
