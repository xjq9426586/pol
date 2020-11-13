package doc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: xujunqian
 * @Date: 2019/9/9 0009 10:06
 * @Description:
 */
public class test {
    /**
     * @param args
     */
    public static void main(String[] args) {
        //fill2();
       // fill3();
        fill();
        List<String> paths = mergeDoc2();
        //MergeDoc.mergeDocs(paths, "D:\\merge.docx");

    }
    public static void fill2(){
        MSWordTool changer = new MSWordTool();
        changer.setTemplate("D:\\secondTemplate.docx");
        Map<String,String> content = new HashMap<String,String>();
        content.put("a", "格式规范、标准统一、利于阅览");
        content.put("b", "规范会议操作、提高会议质量");
        content.put("c", "公司会议、部门之间业务协调会议");

        content.put("d", "**有限公司");
        content.put("e", "机场路2号");
        content.put("f", "3021170207");
        List<Map<String ,String>> content2 = new ArrayList<Map<String, String>>();

        for(int i = 0; i < 3; i++){
            content2.add(content);
        }
        List<CellRange> crs = new ArrayList<>();
        CellRange cr = new CellRange(0, 0 , 3, content2.size() + 3, 1);
        crs.add(cr);
        changer.fillTableAtBookMark("list", content2, crs);
        changer.saveAs();

    }
    public static void fill3(){
        long startTime = System.currentTimeMillis();
        MSWordTool changer = new MSWordTool();
        changer.setTemplate("C:\\Users\\Administrator\\Desktop\\审批表.docx");


        //替换表格标签
        List<Map<String ,String>> content2 = new ArrayList<Map<String, String>>();
        Map<String, String> table1 = new HashMap<String, String>();

        table1.put("opinion", "*月份");
        table1.put("p", "*23");


        for(int i = 0; i < 3; i++){
            content2.add(table1);
        }
        List<CellRange> crs = new ArrayList<>();
        changer.fillSpecailTableAtBookMark("opin" ,content2, null);

        //保存替换后的WORD
        changer.saveAs();
        System.out.println("time=="+(System.currentTimeMillis() - startTime));
    }
    public static void fill(){
        long startTime = System.currentTimeMillis();
        MSWordTool changer = new MSWordTool();
        changer.setTemplate("D:\\Word.docx");
        Map<String, Object> config = new HashMap<>();
        config.put(MSWordTool.IS_REPLACE, MSWordTool.IS_REPLACE_TRUE);
        config.put(MSWordTool.REPLACE_FLAG, "abc");
        config.put(MSWordTool.REPLACE_KEYS, new String[]{"Principles", "Purpose", "Scope"});
        changer.setConfig(config);
        Map<String,String> content = new HashMap<String,String>();
        content.put("Principles_abc", "格式规范、标准统一、利于阅览");
        content.put("Purpose_abc", "规范会议操作、提高会议质量");
        content.put("Scope_abc", "公司会议、部门之间业务协调会议");

        content.put("customerName_abc", "**123有限公司");
        content.put("address", "机场路2号");
        content.put("userNo", "3021170207");
        content.put("tradeName", "水泥制造");
        content.put("price1", "1.085");
        content.put("price2", "0.906");
        content.put("price3", "0.433");
        content.put("numPrice", "0.675");

        content.put("company_name", "**有限公司");
        content.put("company_address", "机场路2号");
        content.put("name", "替换？");
        changer.replaceBookMark(content);


        //替换表格标签
        List<Map<String ,String>> content2 = new ArrayList<Map<String, String>>();
        Map<String, String> table1 = new HashMap<String, String>();

        table1.put("MONTH", "*月份");
        table1.put("SALE_DEP", "75分");
        table1.put("TECH_CENTER", "80分");
        table1.put("CUSTOMER_SERVICE", "85分");
        table1.put("HUMAN_RESOURCES", "90分");
        table1.put("FINANCIAL", "95分");
        table1.put("WORKSHOP", "80分");
        table1.put("TOTAL", "85分");

        for(int i = 0; i < 3; i++){
            content2.add(table1);
        }
        List<CellRange> crs = new ArrayList<>();
        CellRange cr = new CellRange(0, 0 , 2, content2.size() + 2, 1);
        crs.add(cr);
        changer.fillSpecailTableAtBookMark("Table" ,content2, null);
        changer.fillSpecailTableAtBookMark("month", content2, null);
        changer.fillSpecailTableAtBookMark("month1", content2, crs);

        //表格中文本的替换
        Map<String, String> table = new HashMap<String, String>();
        table.put("CUSTOMER_NAME", "**有限公司");
        table.put("ADDRESS", "机场路2号");
        table.put("USER_NO", "3021170207");
        table.put("tradeName", "水泥制造");
        table.put("PRICE_1", "1.085");
        table.put("PRICE_2", "0.906");
        table.put("PRICE_3", "0.433");
        table.put("NUM_PRICE", "0.675");
        changer.replaceText(table,"Table2");

        //保存替换后的WORD
        changer.saveAs();
        System.out.println("time=="+(System.currentTimeMillis() - startTime));
    }


    public static List<String> mergeDoc(){

        List<String> paths = new ArrayList<>();
        MSWordTool changer = new MSWordTool();
        changer.setTemplate("D:\\test.docx");
        Map<String,String> content = new HashMap<String,String>();
        content.put("Principles", "格式规范、标准统一、利于阅览");
        content.put("Purpose", "规范会议操作、提高会议质量");
        content.put("Scope", "公司会议、部门之间业务协调会议");

        content.put("customerName", "**有限公司");
        content.put("address", "机场路2号");
        content.put("userNo", "3021170207");
        content.put("tradeName", "水泥制造");
        content.put("price1", "1.085");
        content.put("price2", "0.906");
        content.put("price3", "0.433");
        content.put("numPrice", "0.675");
        changer.replaceBookMark(content);
        String path = "D:\\c.docx";
        paths.add(path);
        changer.saveAs(path);
        for (int j = 0; j < 3; j++) {
            changer.setTemplate("D:\\test1.docx");
            //        //表格中文本的替换
            Map<String, String> table = new HashMap<String, String>();
            table.put("CUSTOMER_NAME", "**有限公司");
            table.put("ADDRESS", "机场路2号");
            table.put("USER_NO", "3021170207");
            table.put("tradeName", "水泥制造");
            table.put("PRICE_1", "1.085");
            table.put("PRICE_2", "0.906");
            table.put("PRICE_3", "0.433");
            table.put("NUM_PRICE", "0.675");
            changer.replaceText(table,"Table2");
            String path1 = "D:\\p" + j + ".docx";
            changer.saveAs(path1);
            paths.add(path1);
        }

        return paths;
    }

    public static List<String> mergeDoc2(){
        List<String> paths = new ArrayList<>();
        MSWordTool changer = new MSWordTool();
        for (int j = 0; j < 3; j++) {

            changer.setTemplate("D:\\test.docx");
            Map<String,String> content = new HashMap<String,String>();
            content.put("Principles", "格式规范、标准统一、利于阅览");
            content.put("Purpose", "规范会议操作、提高会议质量");
            content.put("Scope", "公司会议、部门之间业务协调会议");

            content.put("customerName", "**有限公司");
            content.put("address", "机场路2号");
            content.put("userNo", "3021170207");
            content.put("tradeName", "水泥制造");
            content.put("price1", "1.085");
            content.put("price2", "0.906");
            content.put("price3", "0.433");
            content.put("numPrice", "0.675");
            changer.replaceBookMark(content);
            String path = "D:\\p" + j + ".docx";
            paths.add(path);
            changer.saveAs(path);
        }
        return paths;
    }
}
