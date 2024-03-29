package excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.text.Collator;
import java.util.*;


import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.*;


/**
 * 基于POI实现的Excel工具类
 */
public class XssfExcelHelper extends ExcelHelper {

    private static XssfExcelHelper instance = null; // 单例对象

    private File file; // 操作文件

    /**
     * 私有化构造方法
     *
     * @param file 文件对象
     */
    private XssfExcelHelper(File file) {
        super();
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    /**
     * 获取单例对象并进行初始化
     *
     * @param file 文件对象
     * @return 返回初始化后的单例对象
     */
    public static XssfExcelHelper getInstance(File file) {
        if (instance == null) {
            // 当单例对象为null时进入同步代码块
            synchronized (XssfExcelHelper.class) {
                // 再次判断单例对象是否为null，防止多线程访问时多次生成对象
                if (instance == null) {
                    instance = new XssfExcelHelper(file);
                }
            }
        } else {
            // 如果操作的文件对象不同，则重置文件对象
            if (!file.equals(instance.getFile())) {
                instance.setFile(file);
            }
        }
        return instance;
    }

    /**
     * 获取单例对象并进行初始化
     *
     * @param filePath 文件路径
     * @return 返回初始化后的单例对象
     */
    public static XssfExcelHelper getInstance(String filePath) {
        return getInstance(new File(filePath));
    }

    @Override
    public <T> List<T> readExcel(Class<T> clazz, String[] fieldNames,
                                 int sheetNo, boolean hasTitle) throws Exception {
        List<T> dataModels = new ArrayList<T>();
        // 获取excel工作簿
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(file));
        XSSFSheet sheet = workbook.getSheetAt(sheetNo);
        int start = sheet.getFirstRowNum() + (hasTitle ? 1 : 0); // 如果有标题则从第二行开始
        for (int i = start; i <= sheet.getLastRowNum(); i++) {
            XSSFRow row = sheet.getRow(i);
            if (row == null) {
                continue;
            }
            // 生成实例并通过反射调用setter方法
            T target = clazz.newInstance();
            for (int j = 0; j < fieldNames.length; j++) {
                String fieldName = fieldNames[j];
                if (fieldName == null || UID.equals(fieldName)) {
                    continue; // 过滤serialVersionUID属性
                }
                // 获取excel单元格的内容
                XSSFCell cell = row.getCell(j);
                if (cell == null) {
                    continue;
                }
                String content = getCellContent(cell);
                // 如果属性是日期类型则将内容转换成日期对象
                if (isDateType(clazz, fieldName)) {
                    // 如果属性是日期类型则将内容转换成日期对象
                    ReflectUtil.invokeSetter(target, fieldName,
                            DateUtil.parse(content));
                } else {
                    Field field = clazz.getDeclaredField(fieldName);
                    ReflectUtil.invokeSetter(target, fieldName,
                            parseValueWithType(content, field.getType()));
                }
            }
            dataModels.add(target);
        }
        return dataModels;
    }


    @Override
    public <T> void writeExcel(Class<T> clazz, List<T> dataModels,
                               String[] fieldNames, String[] titles) throws Exception {
        XSSFWorkbook workbook = null;
        // 检测文件是否存在，如果存在则修改文件，否则创建文件
        if (file.exists()) {
            FileInputStream fis = new FileInputStream(file);
            workbook = new XSSFWorkbook(fis);
        } else {
            workbook = new XSSFWorkbook();
        }
        // 根据当前工作表数量创建相应编号的工作表
        String sheetName = DateUtil.format(new Date(), "yyyyMMddHHmmssSS");
        XSSFSheet sheet = workbook.createSheet(sheetName);
        //XSSFSheet sheet = workbook.getSheetAt(0);
        XSSFRow headRow = sheet.createRow(0);
        // 添加表格标题
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = headRow.createCell(i);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            cell.setCellValue(titles[i]);
            // 设置字体加粗
            XSSFCellStyle cellStyle = workbook.createCellStyle();
            XSSFFont font = workbook.createFont();
            cellStyle.setFont(font);
            // 设置自动换行
            cellStyle.setWrapText(true);
            cell.setCellStyle(cellStyle);
            // 设置单元格宽度
            sheet.setColumnWidth(i, titles[i].length() * 1000);
        }

        // 添加表格内容
        for (int i = 0; i < dataModels.size(); i++) {
            T target = dataModels.get(i);
            XSSFRow row = sheet.createRow(i + 1);
            // 遍历属性列表
            for (int j = 0; j < fieldNames.length; j++) {
                // 通过反射获取属性的值域
                String fieldName = fieldNames[j];
                if (fieldName == null || UID.equals(fieldName)) {
                    continue; // 过滤serialVersionUID属性
                }
                Object result = ReflectUtil.invokeGetter(target, fieldName);
                XSSFCell cell = row.createCell(j);
                cell.setCellValue(StringUtil.toString(result));
                // 如果是日期类型则进行格式化处理
                if (isDateType(clazz, fieldName)) {
                    cell.setCellValue(DateUtil.format((Date) result));
                }
            }
        }
        // 将数据写到磁盘上
        FileOutputStream fos = new FileOutputStream(file);
        try {
            workbook.write(new FileOutputStream(file));
        } finally {
            if (fos != null) {
                fos.close(); // 不管是否有异常发生都关闭文件输出流
            }
        }
    }

    @Override
    protected <T> Object parseValueWithType(String value, Class<?> type) {
        // 由于Excel2007的numeric类型只返回double型，所以对于类型为整型的属性，要提前对numeric字符串进行转换
        if (Byte.TYPE == type || Short.TYPE == type || Short.TYPE == type
                || Long.TYPE == type) {
            value = String.valueOf((long) Double.parseDouble(value));
        }
        return super.parseValueWithType(value, type);
    }

    /**
     * 获取单元格的内容
     *
     * @param cell 单元格
     * @return 返回单元格内容
     */
    private String getCellContent(XSSFCell cell) {
        StringBuffer buffer = new StringBuffer();
        switch (cell.getCellType()) {
            case XSSFCell.CELL_TYPE_NUMERIC: // 数字
                buffer.append(cell.getNumericCellValue());
                break;
            case XSSFCell.CELL_TYPE_BOOLEAN: // 布尔
                buffer.append(cell.getBooleanCellValue());
                break;
            case XSSFCell.CELL_TYPE_FORMULA: // 公式
                buffer.append(cell.getCellFormula());
                break;
            case XSSFCell.CELL_TYPE_STRING: // 字符串
                buffer.append(cell.getStringCellValue());
                break;
            case XSSFCell.CELL_TYPE_BLANK: // 空值
            case XSSFCell.CELL_TYPE_ERROR: // 故障
            default:
                break;
        }
        return buffer.toString();
    }

    @Override
    public String checkExcel(String[] fieldNames, boolean hasTitle)
            throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String excelToJson(String[] fieldNames, boolean hasTitle)
            throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    public static void main(String[] args) {
        List<String> l = new ArrayList();
        l.add("打火机；");
        l.add("好久好久离开家");
        System.out.println(Collections.max(l));

        String s = "数据是是";
        String s1 = "好尴尬过过所";
        Collator instance = Collator.getInstance(Locale.CHINA);

        System.out.println(instance.compare(s, s1));
    }
}