package xml;

import com.jeedev.msdp.utlis.DateUtil;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Auther: xujunqian
 * @Date: 2019/12/18 0018 15:44
 * @Description:
 */
public class Read {
    //补丁文件包存放路径
    public static String desPath = String.format("D:\\xjq\\%s\\", DateUtil.getSystemDate(DateUtil.NORM_DATE_PATTERN).replace("-", ""));
    //idea patch文件
    public static String patch = "D:\\work0\\credit_20200428\\report1.txt";

    public static void main(String[] args) {
        try {
            List<String> c = Files.lines(Paths.get(patch))
                    .flatMap(line -> Arrays.stream(line.split("\n")))
                    .collect(Collectors.toList());
            StringBuffer sb = new StringBuffer();
            List<String> list = new ArrayList<>();
            c.forEach(e -> {

                if(e.indexOf("-api") != -1){
                    String str = e.substring(0, e.indexOf("-api") + 4).trim();
                    sb.setLength(0);
                    if(e.contains("-usecredit")){
                        sb.append(str, 0, str.indexOf("-usecredit") + 10);
                        sb.append("/");
                        sb.append(str, 0, str.indexOf("-api"));
                    }else{
                        sb.append(str, 0, str.indexOf("-api"));
                    }
                    sb.append("/");
                    sb.append(str);
                    sb.append("/src/main/java/");
                    //System.out.println(e.substring(0, e.indexOf("-api") + 4).trim());
                }else if(e.indexOf(".api") != -1){
                    String str = e.substring(0, e.indexOf(".api") + 4).trim();
                    if(sb.indexOf("com.") != -1){
                        if(sb.lastIndexOf(".java") != -1){
                            sb.delete(sb.indexOf("com."), sb.lastIndexOf(".java") + 5);
                        }else{
                            sb.delete(sb.indexOf("com."), sb.lastIndexOf(".api") + 4);
                        }
                    }
                    sb.append(str);
                    //System.out.println(e.substring(0, e.indexOf(".api") + 4).trim());
                }else if(e.indexOf(".java") != -1){
                    if(sb.indexOf(".api") != -1){
                        String str = e.substring(0, e.indexOf(".java") + 5).trim();
                        try {
                            if(sb.lastIndexOf(".java") != -1){
                                sb.delete(sb.indexOf(".api") + 4, sb.lastIndexOf(".java") + 5);
                            }
                        } catch (Exception ex) {
                            System.out.println(sb);
                            ex.printStackTrace();
                        }
                        sb.append("/");
                        sb.append(str);
                        list.add(sb.toString().replace(".java", "*java")
                                .replaceAll("\\.", "/").replace("*java", ".java"));
                    }
                   // System.out.println(e.substring(0, e.indexOf(".java") + 5).trim());
                }
            });
            list.forEach(e -> {
                if(!e.contains("-retail")){
                    String path = e;
                    String tempPath = path.substring(path.indexOf("/com") + 1, path.lastIndexOf(".java") + 5);
                    //先创建复制文件夹
                    String tempDesPath = desPath + tempPath.substring(0, tempPath.lastIndexOf("/"));
                    File desFilePath = new File(tempDesPath);
                    if(!desFilePath.exists()){
                        desFilePath.mkdirs();
                    }
                    //idea 源代码路径
                    String sourceFileNameStr = "D:\\work0\\credit_source_05\\" + path;
                    //String desFileNameStr = desPath + path;
                    String desFileNameStr = desPath + tempPath;
                    copyFile(sourceFileNameStr, desFileNameStr);
                }

            });
            System.out.println(list);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static void copyFile(String sourceFileNameStr, String desFileNameStr) {
        File srcFile = new File(sourceFileNameStr);
        File desFile = new File(desFileNameStr);
        try {
            copyFile(srcFile, desFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void copyFile(File sourceFile, File targetFile) throws IOException {
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        try {
            inBuff = new BufferedInputStream(new FileInputStream(sourceFile));

            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            outBuff.flush();
        } catch (FileNotFoundException e){
            System.out.println(e.getMessage());
        }finally {
            if (inBuff != null)
                inBuff.close();
            if (outBuff != null)
                outBuff.close();
        }
    }
}
