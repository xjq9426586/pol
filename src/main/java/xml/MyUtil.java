package xml;

import com.jeedev.msdp.utlis.DateUtil;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Auther: xujunqian
 * @Date: 2019/12/18 0018 15:44
 * @Description:
 */
public class MyUtil {
    //补丁文件包存放路径
    public static String desPath = String.format("D:\\xjq\\电子文档修改%s\\", DateUtil.getSystemDate(DateUtil.NORM_DATE_PATTERN).replace("-", ""));
    //idea patch文件
    public static String patch = "D:\\work\\dev_credit2020\\dev_credit2020\\电子文档修改.patch";

    public static void main(String[] args) {
        try {
            List<String> c = Files.lines(Paths.get(patch))
                    .filter(line -> line.startsWith("Index:"))
                    .collect(Collectors.toList());
            c.forEach(e -> {
                if(e.lastIndexOf("/com") != -1){
                    System.out.println(e.substring(e.lastIndexOf("/com")));
                }else{
                    System.out.println(e.substring(e.lastIndexOf(":") + 1));
                }

                String path = e.substring(e.indexOf(":") + 1).trim();
                //先创建复制文件夹
                String tempDesPath = desPath + path.substring(0, path.lastIndexOf("/"));
                File desFilePath = new File(tempDesPath);
                if(!desFilePath.exists()){
                    desFilePath.mkdirs();
                }
                //idea 源代码路径
                String sourceFileNameStr = "D:\\work\\dev_credit2020\\dev_credit2020\\" + path;
                String desFileNameStr = desPath + path;
                copyFile(sourceFileNameStr, desFileNameStr);
            });
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
        } finally {
            if (inBuff != null)
                inBuff.close();
            if (outBuff != null)
                outBuff.close();
        }
    }
}
