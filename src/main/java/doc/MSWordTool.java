package doc;


import java.io.*;
import java.util.*;
import java.util.Map.Entry;

import com.jeedev.msdp.utlis.CollectionUtil;
import com.jeedev.msdp.utlis.MapUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlToken;
import org.openxmlformats.schemas.drawingml.x2006.main.CTGraphicalObject;
import org.openxmlformats.schemas.drawingml.x2006.main.CTNonVisualDrawingProps;
import org.openxmlformats.schemas.drawingml.x2006.main.CTPositiveSize2D;
import org.openxmlformats.schemas.drawingml.x2006.picture.CTPicture;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTInline;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import org.w3c.dom.Node;

/**
 * @Auther: xujunqian
 * @Date: 2019/9/9 0009 11:08
 * @Description:
 */
public class MSWordTool {
    //替换关键key
    public static final String REPLACE_KEYS = "replaceKeys";
    //替换的标识
    public static final String REPLACE_FLAG = "replaceFlag";
    public static final String IS_REPLACE = "isReplace";
    public static final boolean IS_REPLACE_FLASE = false;
    public static final boolean IS_REPLACE_TRUE = true;
    public static final String IMG_NAME = "imgName";
    public static final String IMG_DATA = "imgData";
    public static final String IMG_RID = "imgRid";
    /** 内部使用的文档对象 **/
    private XWPFDocument document;

    private BookMarks bookMarks = null;

    //配置参数
    private Map<String, Object> config;

    public void setConfig(Map<String, Object> config) {
        this.config = config;
    }

    /**
     * 为文档设置模板
     * @param templatePath  模板文件名称
     */
    public void setTemplate(String templatePath) {
        try {
            this.document = new XWPFDocument(
                    POIXMLDocument.openPackage(templatePath));

            bookMarks = new BookMarks(document);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    /**
     * 书签位置填充值,传入的Map中，key表示标签名称，value是替换的信息
     * @param indicator
     */
    public void  replaceBookMark(Map<String,String> indicator) {
        //循环进行替换
        Iterator<String> bookMarkIter = bookMarks.getNameIterator();
        while (bookMarkIter.hasNext()) {
            String bookMarkName = bookMarkIter.next();

            //得到标签名称
            BookMark bookMark = bookMarks.getBookmark(bookMarkName);
            if(config != null && (boolean)config.get(MSWordTool.IS_REPLACE)){
                bookMarkName = resetBookMarkName(bookMark, bookMarkName);
            }

            //进行替换
            if (indicator.get(bookMarkName)!=null) {
                bookMark.insertTextAtBookMark(indicator.get(bookMarkName), BookMark.INSERT_BEFORE);
            }

        }

    }

    /**
     * 表格数据填充
     * @param bookMarkName
     * @param content
     */
    public void fillTableAtBookMark(String bookMarkName,List<Map<String,String>> content, List<CellRange> crs) {

        //rowNum来比较标签在表格的哪一行
        int rowNum = 0;

        //首先得到标签
        BookMark bookMark = bookMarks.getBookmark(bookMarkName);
        Map<String, String> columnMap = new HashMap<String, String>();
        Map<String, Node> styleNode = new HashMap<String, Node>();
        Map<String, CTTcPr> tcPrMap = new HashMap<>();//保存单元格样式
        //标签是否处于表格内
        if(bookMark.isInTable()){

            //获得标签对应的Table对象和Row对象
            XWPFTable table = bookMark.getContainerTable();
            XWPFTableRow row = bookMark.getContainerTableRow();
            CTRow ctRow = row.getCtRow();
            List<XWPFTableCell> rowCell = row.getTableCells();
            for(int i = 0; i < rowCell.size(); i++){
                //获取单元格标签
                columnMap.put(i+"", rowCell.get(i).getText().trim());
                //System.out.println(rowCell.get(i).getParagraphs().get(0).createRun().getFontSize());
                //System.out.println(rowCell.get(i).getParagraphs().get(0).getCTP());
                //System.out.println(rowCell.get(i).getParagraphs().get(0).getStyle());
                CTTcPr tcPr = rowCell.get(i).getCTTc().getTcPr();
                tcPrMap.put(String.valueOf(i), tcPr);
                //获取该单元格段落的xml，得到根节点
                Node node1 = rowCell.get(i).getParagraphs().get(0).getCTP().getDomNode();
                //遍历根节点的所有子节点
                for (int x=0;x<node1.getChildNodes().getLength();x++) {
                    //System.out.println(node1.getChildNodes().item(x).getNodeName());
                    if (node1.getChildNodes().item(x).getNodeName().equals(BookMark.RUN_NODE_NAME)) {
                        Node node2 = node1.getChildNodes().item(x);

                        //遍历所有节点为"w:r"的所有自己点，找到节点名为"w:rPr"的节点
                        for (int y=0;y<node2.getChildNodes().getLength();y++) {
                            if(node2.getChildNodes().item(y).getNodeName().endsWith(BookMark.STYLE_NODE_NAME)){

                                //将节点为"w:rPr"的节点(字体格式)存到HashMap中
                                styleNode.put(i+"", node2.getChildNodes().item(y));
                            }
                        }
                    } else {
                        continue;
                    }
                }
            }

            //循环对比，找到该行所处的位置，删除改行
            for(int i = 0; i < table.getNumberOfRows(); i++){
                if(table.getRow(i).equals(row)){
                    rowNum = i;
                    break;
                }
            }
            table.removeRow(rowNum);

            for(int i = 0; i < content.size(); i++){
                //创建新的一行,单元格数是表的第一行的单元格数,
                //后面添加数据时，要判断单元格数是否一致
                XWPFTableRow tableRow = table.createRow();
            }

            //得到表格行数
            int rcount = table.getNumberOfRows();
            for(int i = rowNum; i < rcount; i++){
                XWPFTableRow newRow = table.getRow(i);

                //判断newRow的单元格数是不是该书签所在行的单元格数
                if(newRow.getTableCells().size() != rowCell.size()){

                    //计算newRow和书签所在行单元格数差的绝对值
                    //如果newRow的单元格数多于书签所在行的单元格数，不能通过此方法来处理，可以通过表格中文本的替换来完成
                    //如果newRow的单元格数少于书签所在行的单元格数，要将少的单元格补上
                    int sub= Math.abs(newRow.getTableCells().size() - rowCell.size());
                    //将缺少的单元格补上
                    for(int j = 0;j < sub; j++){
                        newRow.addNewTableCell();
                    }
                }

                List<XWPFTableCell> cells = newRow.getTableCells();

                for(int j = 0; j < cells.size(); j++){
                    XWPFParagraph para = cells.get(j).getParagraphs().get(0);
                    XWPFRun run = para.createRun();
                    CTTc pa = cells.get(j).getCTTc();
                    pa.setTcPr(tcPrMap.get(String.valueOf(j)));//重新设置单元格样式
                    if(content.get(i-rowNum).get(columnMap.get(j+"")) != null){

                        //改变单元格的值，标题栏不用改变单元格的值
                        run.setText(content.get(i-rowNum).get(columnMap.get(j+""))+"");

                        //将单元格段落的字体格式设为原来单元格的字体格式
                        run.getCTR().getDomNode().insertBefore(styleNode.get(j+"").cloneNode(true), run.getCTR().getDomNode().getFirstChild());
                    }

                    para.setAlignment(ParagraphAlignment.CENTER);
                }
            }
            if(CollectionUtils.isNotEmpty(crs)){
                for (CellRange cr : crs) {
                    cr.mergeCells(table);
                }
            }
        }
    }
    public void fillSpecailTableAtBookMark(String bookMarkName, List<Map<String, String>> content, List<CellRange> crs) {

        //rowNum来比较书签在表格的哪一行
        int rowNum = 0;

        //首先得到书签
        BookMark bookMark = bookMarks.getBookmark(bookMarkName);
        Map<String, String> columnMap = new HashMap<String, String>();
        //存放每行的样式
        Map<String, Node> styleNode = new HashMap<String, Node>();

        if(null == bookMark){
            return;
        }

        //书签是否处于表格内
        if (bookMark.isInTable()) {

            //获得书签对应的Table对象和Row对象
            XWPFTable table = bookMark.getContainerTable();
            XWPFTableRow row = bookMark.getContainerTableRow();
            CTRow ctRow = row.getCtRow();
            List<XWPFTableCell> rowCell = row.getTableCells();
            for (int i = 0; i < rowCell.size(); i++) {
                columnMap.put(i + "", rowCell.get(i).getText().trim());

                //获取该单元格段落的xml，得到根节点
                Node node1 = rowCell.get(i).getParagraphs().get(0).getCTP().getDomNode();

                //遍历根节点的所有子节点
                for (int x = 0; x < node1.getChildNodes().getLength(); x++) {
                    if (node1.getChildNodes().item(x).getNodeName().equals(BookMark.RUN_NODE_NAME)) {
                        Node node2 = node1.getChildNodes().item(x);

                        //遍历所有节点为"w:r"的所有自己点，找到节点名为"w:rPr"的节点
                        for (int y = 0; y < node2.getChildNodes().getLength(); y++) {
                            if (node2.getChildNodes().item(y).getNodeName().endsWith(BookMark.STYLE_NODE_NAME)) {

                                //将节点为"w:rPr"的节点(字体格式)存到HashMap中
                                styleNode.put(i + "", node2.getChildNodes().item(y));
                            }
                        }
                    } else {
                        continue;
                    }
                }
            }

            //循环对比，找到该行所处的位置
            for (int i = 0; i < table.getNumberOfRows(); i++) {
                if (table.getRow(i).equals(row)) {
                    rowNum = i;
                    break;
                }
            }

            XWPFTableRow sourceRow = table.getRow(rowNum);

            if(CollectionUtil.isEmpty(content)){
                copy(table, sourceRow, rowNum + 1, new HashMap<>(), styleNode);
            }else {
                //根据传递的参数集合，rowNum坐标，来创建新行
                for (int i = 0; i < content.size(); i++) {
                    //深拷贝一行，根据rowNum这一行拷贝，把content.get(i)这一行传递过去，在深拷贝的同时，给表格赋值
                    //styleNode表格的样式
                    copy(table, sourceRow, rowNum + i + 1, content.get(i), styleNode);
                }

            }

            table.removeRow(rowNum);
        }
    }
    public void copy(XWPFTable table, XWPFTableRow sourceRow, int rowIndex, Map<String, String> map, Map<String, Node> styleNode) {
        //在表格指定位置新增一行
        XWPFTableRow targetRow = table.insertNewTableRow(rowIndex);
        //复制行属性
        targetRow.getCtRow().setTrPr(sourceRow.getCtRow().getTrPr());
        List<XWPFTableCell> cellList = sourceRow.getTableCells();
        if (null == cellList) {
            return;
        }
        //复制列及其属性和内容
        XWPFTableCell targetCell = null;
        for (XWPFTableCell sourceCell : cellList) {
            targetCell = targetRow.addNewTableCell();
            //列属性
            targetCell.getCTTc().setTcPr(sourceCell.getCTTc().getTcPr());
            //段落属性
            if (sourceCell.getParagraphs() != null && sourceCell.getParagraphs().size() > 0) {
                targetCell.getParagraphs().get(0).getCTP().setPPr(sourceCell.getParagraphs().get(0).getCTP().getPPr());
                if (sourceCell.getParagraphs().get(0).getRuns() != null && sourceCell.getParagraphs().get(0).getRuns().size() > 0) {
                    XWPFRun cellR = targetCell.getParagraphs().get(0).createRun();
                    String s = map.get(sourceCell.getText().trim());
                    if (s != null) {
                        cellR.setText(s);//将值填充
                    } else {
                        cellR.setText("");//
                    }
                    Node domNode = cellR.getCTR().getDomNode();
                    if (styleNode.size() > 0) {
                        Set<String> strings = styleNode.keySet();
                        Node node = null;
                        for (String s1 : strings) {
                            node = styleNode.get(s1).cloneNode(true);//获取表格样式，赋值给新创建行
                        }
                        if (node != null) {
                            Node firstChild = cellR.getCTR().getDomNode().getFirstChild();
                            domNode.insertBefore(node, firstChild);
                        }

                    }
                } else {
                    targetCell.setText(sourceCell.getText());
                }
            } else {
                targetCell.setText(sourceCell.getText());
            }
        }
    }

    /**
     * 通过标签填充值
     * @param bookmarkMap
     * @param bookMarkName
     */
    public void replaceText(Map<String,String> bookmarkMap, String bookMarkName) {

        //首先得到标签
        BookMark bookMark = bookMarks.getBookmark(bookMarkName);
        //获得书签标记的表格
        XWPFTable table = bookMark.getContainerTable();
        //获得所有的表
        //Iterator<XWPFTable> it = document.getTablesIterator();

        if(table != null){
            //得到该表的所有行
            int rcount = table.getNumberOfRows();
            for(int i = 0 ;i < rcount; i++){
                XWPFTableRow row = table.getRow(i);

                //获到改行的所有单元格
                List<XWPFTableCell> cells = row.getTableCells();
                for(XWPFTableCell c : cells){
                    for(Entry<String,String> e : bookmarkMap.entrySet()){
                        if(c.getText().equals(e.getKey())){

                            //删掉单元格内容
                            c.removeParagraph(0);

                            //给单元格赋值
                            c.setText(e.getValue());
                        }
                    }
                }
            }
        }
    }

    public void addPic(Map<String, Object> data) throws Exception {
        Iterator<String> bookMarkIter = bookMarks.getNameIterator();
        while (bookMarkIter.hasNext()) {
            String bookMarkName = bookMarkIter.next();
            if(data.get(bookMarkName) != null){
                BookMark bookMark = bookMarks.getBookmark(bookMarkName);
                XWPFRun xwpfRun = bookMark.getPara().createRun();
                XWPFPicture xwpfPicture = xwpfRun.addPicture((InputStream) data.get(bookMarkName),
                        XWPFDocument.PICTURE_TYPE_JPEG, "123", 100, 100);
                String ind = xwpfPicture.getCTPicture().getBlipFill().getBlip().getEmbed();
                createPicture(ind, 259, 58, xwpfRun);
            }
        }
    }
    public void createPicture(String id, int width, int height, XWPFRun xwpfRun) {
        final int EMU = 9525;
        width *= EMU;
        height *= EMU;
        String blipId = id;

        CTInline inline = xwpfRun.getCTR()
                .addNewDrawing().addNewInline();

        String picXml = "<a:graphic xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">"
                + "   <a:graphicData uri=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">"
                + "      <pic:pic xmlns:pic=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">"
                + "         <pic:nvPicPr>"
                + "               <pic:cNvPr id=\"199\" name=\"Generated\"/>"
                + "            <pic:cNvPicPr/>"
                + "         </pic:nvPicPr>"
                + "         <pic:blipFill>"
                + "            <a:blip r:embed=\"" + blipId + "\" xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\"/>"
                + "            <a:stretch>"
                + "               <a:fillRect/>"
                + "            </a:stretch>"
                + "         </pic:blipFill>"
                + "         <pic:spPr>"
                + "            <a:xfrm>"
                + "               <a:off x=\"0\" y=\"0\"/>"
                + "               <a:ext cx=\"" + width + "\" cy=\"" + height + "\"/>"
                + "            </a:xfrm>"
                + "            <a:prstGeom prst=\"rect\">"
                + "               <a:avLst/>"
                + "            </a:prstGeom>"
                + "         </pic:spPr>"
                + "      </pic:pic>"
                + "   </a:graphicData>"
                + "</a:graphic>";

        // CTGraphicalObjectData graphicData =
        inline.addNewGraphic().addNewGraphicData();
        XmlToken xmlToken = null;
        try {
            xmlToken = XmlToken.Factory.parse(picXml);
        } catch (XmlException xe) {
            xe.printStackTrace();
        }
        inline.set(xmlToken);
        // graphicData.set(xmlToken);

        inline.setDistT(0);
        inline.setDistB(0);
        inline.setDistL(0);
        inline.setDistR(0);

        CTPositiveSize2D extent = inline.addNewExtent();
        extent.setCx(width);
        extent.setCy(height);

        CTNonVisualDrawingProps docPr = inline.addNewDocPr();
        docPr.setId(0);
        docPr.setName("图片" + id);
        docPr.setDescr("甩葱玩具");
    }
    public Map<String, Object> getPic(){
        List<Map<String, Object>> imageBundleList = new ArrayList<>();
        Map<String, Object> reMap = new HashMap<>();
        Iterator<String> bookMarkIter = bookMarks.getNameIterator();
        while (bookMarkIter.hasNext()) {
            String bookMarkName = bookMarkIter.next();
            if(bookMarkName.indexOf("tableCell") != -1){
                BookMark bookMark = bookMarks.getBookmark(bookMarkName);
                XWPFParagraph xwpfParagraph = bookMark.getPara();
                List<XWPFRun> runList = xwpfParagraph.getRuns();
                for (XWPFRun run : runList) {
                    //XWPFRun是POI对xml元素解析后生成的自己的属性，无法通过xml解析，需要先转化成CTR
                    CTR ctr = run.getCTR();
                    //对子元素进行遍历
                    XmlCursor c = ctr.newCursor();
                    //这个就是拿到所有的子元素：
                    c.selectPath("./*");
                    while (c.toNextSelection()) {
                        XmlObject o = c.getObject();
                        //如果子元素是<w:drawing>这样的形式，使用CTDrawing保存图片
                        if (o instanceof CTDrawing) {
                            CTDrawing drawing = (CTDrawing) o;
                            CTInline[] ctInlines = drawing.getInlineArray();
                            for (CTInline ctInline : ctInlines) {
                                CTGraphicalObject graphic = ctInline.getGraphic();
                                XmlCursor cursor = graphic.getGraphicData().newCursor();
                                cursor.selectPath("./*");
                                while (cursor.toNextSelection()) {
                                    XmlObject xmlObject = cursor.getObject();// 如果子元素是<pic:pic>这样的形式
                                    if (xmlObject instanceof org.openxmlformats.schemas.drawingml.x2006.picture.CTPicture) {
                                        Map<String, Object> map = new HashMap<>();
                                        org.openxmlformats.schemas.drawingml.x2006.picture.CTPicture picture = (CTPicture) xmlObject;
                                        //拿到元素的属性
                                        String blipId = picture.getBlipFill().getBlip().getEmbed();
                                        XWPFPictureData pictureData = this.document.getPictureDataByID(blipId);
                                        String imageName = pictureData.getFileName();
                                        map.put(MSWordTool.IMG_DATA, pictureData.getData());
                                        map.put(MSWordTool.IMG_NAME, imageName);
                                        map.put(MSWordTool.IMG_RID, blipId);
                                        imageBundleList.add(map);
                                    }
                                }
                            }
                        }

                    }
                }
                reMap.put(bookMarkName, imageBundleList);
            }
        }
        return reMap;
    }

    public void saveAs() {
        File newFile = new File("d:\\Word模版_REPLACE.docx");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(newFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            this.document.write(fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void saveAs(String path) {
        File newFile = new File(path);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(newFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            this.document.write(fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private String resetBookMarkName(BookMark bookMark, String bookMarkName){
        String[] replaceKeys = (String[]) config.get(MSWordTool.REPLACE_KEYS);
        String replaceFlag = (String) config.get(MSWordTool.REPLACE_FLAG);
        if(replaceKeys != null){
            for (String replaceKey : replaceKeys) {
                if(bookMarkName.indexOf(replaceKey) > -1){
                    XWPFParagraph xwpfParagraph = bookMark.getPara();
                    CTBookmark ctBookmark = xwpfParagraph.getCTP().getBookmarkStartList().get(0);
                    bookMarkName = bookMarkName + "_" + replaceFlag;
                    ctBookmark.setName(bookMarkName);
                }
            }
        }
        return bookMarkName;
    }
}