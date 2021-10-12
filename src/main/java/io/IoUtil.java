package io;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Double.POSITIVE_INFINITY;

/**
 * @Author: xujunqian
 * @Date: 2020/6/9 0009 11:12
 * @Description:
 */
public class IoUtil {

    public static void main(String[] args) throws IOException {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            list.add(i);
        }
        List<Integer> temp;
        int total = (list.size() - 1) / 10 + 1;
        for (int i = 0; i < total; i++) {
            if ((i + 1) * 10 > list.size()) {
                temp = list.subList(i * 10, list.size());
            } else {
                temp = list.subList(i * 10, (i + 1) * 10);
            }
            System.out.println(temp);
        }

    }

    public static void file() throws IOException {
        File f = File.createTempFile("temp", ".docx");
        FileUtils.deleteQuietly(new File(f.getPath()));
    }

    public static String getStringRandom(int length) {
        String val = "";
        Random random = new Random();
        //参数length，表示生成几位随机数
        for (int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if ("char".equalsIgnoreCase(charOrNum)) {
                //输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char) (random.nextInt(26) + temp);
            } else if ("num".equalsIgnoreCase(charOrNum)) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }
}
