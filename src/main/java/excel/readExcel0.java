package excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class readExcel0 {
    public static List<Map> read(File file) throws Exception {
        List<Map> list = new ArrayList<>();
        HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(file));
        int sc = workbook.getNumberOfSheets();
        for (int s = 0; s < sc; s++) {
            HSSFSheet sheet = workbook.getSheetAt(s);
            double sumDj = 0;//该职位所在等级
            double sumDf = 0;//得分
            int countDj = 0;
            int countDf = 0;
            double vagDj = 0;
            double vagDf = 0;
            String strDj = "";
            String strDf = "";
            List<Double> listDj = new ArrayList<>();
            List<Double> listDf = new ArrayList<>();
            double maxDj;
            double minDj;
            double maxDf;
            double minDf;
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                HSSFRow row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }
                HSSFCell cell = row.getCell(1);
                String content = getCellContent(cell);
                double numDj = Double.parseDouble(content);
                if (!content.equals("0.0")) {
                    countDj++;
                    listDj.add(numDj);
                }
                sumDj += numDj;
                vagDj = sumDj / countDj;

                HSSFCell cell0 = row.getCell(2);
                String content0 = getCellContent(cell0);
                double numDf = Double.parseDouble(content0);
                if (!content0.equals("0.0")) {
                    countDf++;
                    listDf.add(numDf);
                }
                sumDf += numDf;
                vagDf = sumDf / countDf;

            }
            System.out.println("------------" + sheet.getSheetName());
            System.out.println("该职位所在等级{求和：" + sumDj + "\0计数：" + countDj + "\0平均值:" + vagDj + "}");
            System.out.println("得分{求和：" + sumDf + "\0计数：" + countDf + "\0平均值:" + vagDf + "}");
            maxDf = max(listDf);
            minDf = min(listDf);
            maxDj = max(listDj);
            minDj = min(listDj);
            Map map = new HashMap();
            map.put("maxDf", maxDf);
            map.put("minDf", minDf);
            map.put("maxDj", maxDj);
            map.put("minDj", minDj);
            map.put("vagDf", vagDf);
            map.put("vagDj", vagDj);
            map.put("sheetName", sheet.getSheetName());
            list.add(map);
        }
        return list;

    }


    public static void write(File file, List<Map> list) throws InvalidFormatException, FileNotFoundException, IOException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        XSSFWorkbook workbook = null;
        if (file.exists()) {
            FileInputStream fis = new FileInputStream(file);
            workbook = new XSSFWorkbook(fis);
        } else {
            workbook = new XSSFWorkbook();
        }
        for (int s = 0; s < list.size(); s++) {
            XSSFSheet sheet = null;
            if (!file.exists()) {
                sheet = workbook.createSheet(list.get(s).get("sheetName").toString());
            } else {
                sheet = workbook.getSheetAt(s);
            }

            XSSFRow headRow = sheet.createRow(0);
            String titles[] = {"等级min", "等级max", "等级vag", "得分min", "得分max", "得分vag"};
            String fieldNames[] = {"minDj", "maxDj", "vagDj", "minDf", "maxDf", "vagDf"};
            for (int i = 0; i < titles.length; i++) {
                XSSFCell cell = headRow.createCell(i);
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                cell.setCellValue(titles[i]);
                XSSFCellStyle cellStyle = workbook.createCellStyle();
                cellStyle.setWrapText(true);
                cell.setCellStyle(cellStyle);
                sheet.setColumnWidth(i, titles[i].length() * 1000);
            }

            XSSFRow row = sheet.createRow(1);
            for (int j = 0; j < fieldNames.length; j++) {
                XSSFCell cell = row.createCell(j);
                cell.setCellValue(StringUtil.toString(list.get(s).get(
                        fieldNames[j])));
            }


        }
        FileOutputStream fos = new FileOutputStream(file);
        try {
            workbook.write(new FileOutputStream(file));
        } finally {
            if (fos != null) {
                fos.close();
            }
        }
    }

    private static String getCellContent(Cell cell) {
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

    public static double min(List<Double> list) {
        if (list != null && list.size() > 0) {
            double min = list.get(0);
            for (int i = 1; i < list.size(); i++) {
                if (list.get(i) < min) {
                    min = list.get(i);
                }
            }
            return min;
        } else {
            return 0;
        }


    }

    public static double max(List<Double> list) {
        if (list != null && list.size() > 0) {
            double max = list.get(0);
            for (int i = 1; i < list.size(); i++) {
                if (list.get(i) > max) {
                    max = list.get(i);
                }
            }
            return max;
        } else {
            return 0;
        }

    }

    public static void main(String[] args) throws Exception {
        String path = "D:\\excel";
        File file = new File(path);
        File[] tempList = file.listFiles();
        for (int i = 0; i < tempList.length; i++) {
            System.out.println("EXCEL:" + tempList[i].getName());
            readExcel0.read(tempList[i]);
            write(new File("D:\\excelNew\\" + tempList[i].getName()), read(tempList[i]));
        }

    }


}
