package test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class test2 {
	public static void main(String[] args) {
		String path = "D:/CDCProject/upload";
		traverseFolder2(path);
	}
	public static void traverseFolder2(String path) {

        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (files.length == 0) {
                System.out.println("文件夹是空的!");
                return;
            } else {
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        traverseFolder2(file2.getAbsolutePath());
                    } else {
                    	Map<String, Object> map = new HashMap<String, Object>();
                        map.put("fileUrl", file2.getAbsolutePath().substring(13).replace("\\","/"));
                        map.put("fileName", file2.getName());
                        System.out.println(map);
                    }
                }
            }
        } else {
            System.out.println("文件不存在!");
        }
    }
}
