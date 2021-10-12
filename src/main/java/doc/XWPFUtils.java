package doc;

import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;
import org.openxmlformats.schemas.drawingml.x2006.main.CTGraphicalObject;
import org.openxmlformats.schemas.drawingml.x2006.picture.CTPicture;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTInline;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBookmark;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDrawing;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class XWPFUtils {

    //获取某一个段落中的所有图片索引
    public static List<String> readImageInParagraph(XWPFParagraph paragraph) {
        //图片索引List
        List<String> imageBundleList = new ArrayList<String>();

        //段落中所有XWPFRun
        List<XWPFRun> runList = paragraph.getRuns();
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
                        //
                        XmlCursor cursor = graphic.getGraphicData().newCursor();
                        cursor.selectPath("./*");
                        while (cursor.toNextSelection()) {
                            XmlObject xmlObject = cursor.getObject();// 如果子元素是<pic:pic>这样的形式
                            if (xmlObject instanceof CTPicture) {
                                CTPicture picture = (CTPicture) xmlObject;
                                //拿到元素的属性
                                imageBundleList.add(picture.getBlipFill().getBlip().getEmbed());
                                for (CTBookmark ctBookmark : paragraph.getCTP().getBookmarkStartList()) {
                                    System.out.println(ctBookmark.getName());
                                }
                            }
                        }
                    }
                }
                //使用CTObject保存图片　　　　　　　　　　//<w:object>形式
               /* if (o instanceof CTObject) {
                    CTObject object = (CTObject) o;
                    System.out.println(object);
                    XmlCursor w = object.newCursor();
                    w.selectPath("./*");
                    while (w.toNextSelection()) {
                        XmlObject xmlObject = w.getObject();
                        if (xmlObject instanceof CTShape) {
                            CTShape shape = (CTShape) xmlObject;
                            //imageBundleList.add(shape.getImagedataArray()[0].getId2());
                        }
                    }
                }*/
            }
        }
        return imageBundleList;
    }

    public static void byteToFile(byte[] bytes, String path) {
        try {
            // 根据绝对路径初始化文件
            File localFile = new File(path);
            if (!localFile.exists()) {
                localFile.createNewFile();
            }
            // 输出流
            OutputStream os = new FileOutputStream(localFile);
            os.write(bytes);
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException, InvalidFormatException {
        InputStream in = new FileInputStream("C:\\Users\\Administrator\\Desktop\\123.docx");
        CustomXWPFDocument xwpfDocument = new CustomXWPFDocument(in);
        /*List<XWPFParagraph> paragraphList = xwpfDocument.getParagraphs();
        System.out.println("图片的索引\t|图片名称\t|图片上一段文字的内容\t");
        for(int i = 0;i < paragraphList.size();i++){
            List<String> imageBundleList = XWPFUtils.readImageInParagraph(paragraphList.get(i));
            if(CollectionUtils.isNotEmpty(imageBundleList)){
                for(String pictureId:imageBundleList){
                    XWPFPictureData pictureData = xwpfDocument.getPictureDataByID(pictureId);
                    String imageName = pictureData.getFileName();
                   byteToFile(pictureData.getData(), "C:\\Users\\Administrator\\Desktop\\" + imageName);
                }
            }
        }*/
        List<XWPFParagraph> paragraphList = xwpfDocument.getParagraphs();
        for (XWPFParagraph xwpfParagraph : paragraphList) {
            List<CTBookmark> bookmarkList = xwpfParagraph.getCTP().getBookmarkStartList();
            //循环加入标签
            for (CTBookmark bookmark : bookmarkList) {
                if (bookmark.getName().equals("a")) {
                    File file = new File("C:/Users/Administrator/Desktop/123.png");
                    InputStream inputStream = new FileInputStream(file);
                    XWPFRun xwpfRun = xwpfParagraph.createRun();
                    XWPFPicture xwpfPicture = xwpfRun.addPicture(inputStream,
                            XWPFDocument.PICTURE_TYPE_JPEG, "123", 100, 100);
                    String ind = xwpfPicture.getCTPicture().getBlipFill().getBlip().getEmbed();
                    xwpfDocument.createPicture(ind, 259, 58, xwpfRun);
                    xwpfDocument.write(new FileOutputStream(new File("C:/Users/Administrator/Desktop/temp.docx")));
                }
            }
        }
       /* File file = new File("C:/Users/Administrator/Desktop/123.png");
        InputStream inputStream = new FileInputStream(file);
        String ind = xwpfDocument.addPictureData(inputStream, XWPFDocument.PICTURE_TYPE_PNG);
       xwpfDocument.createPicture(ind, 259, 58);
        xwpfDocument.write(new FileOutputStream(new File("C:/Users/Administrator/Desktop/abc.docx")));*/
    }
}