package doc;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.xmlbeans.XmlOptions;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBody;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: xujunqian
 * @Date: 2019/9/9 0009 15:27
 * @Description:
 */
public class MergeDoc {
    public static void main (String[] args) throws Exception {
        List<String> paths = new ArrayList<>();
        paths.add("D:\\test.docx");
        paths.add("D:\\test1.docx");
        paths.add("D:\\test2.docx");
        mergeDocs(paths, "D:\\dest.docx");
    }


    public static void mergeDocs(List<String> mergeDocPaths, String outPath){
        XWPFDocument doc = null;
        CTBody docBody = null;
        try {
            OutputStream out = new FileOutputStream(outPath);
            for (int i = 0; i < mergeDocPaths.size(); i++) {
                InputStream in = new FileInputStream(mergeDocPaths.get(i));
                OPCPackage srcPackage = OPCPackage.open(in);
                if(i == 0){
                    doc = new XWPFDocument(srcPackage);
                    docBody = doc.getDocument().getBody();
                }else{
                    XWPFDocument appendDoc = new XWPFDocument(srcPackage);
                    CTBody appendBody = appendDoc.getDocument().getBody();
                    appendBody(docBody, appendBody);
                }
            }
            doc.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void appendBody(CTBody src, CTBody append) throws Exception {
        XmlOptions optionsOuter = new XmlOptions();
        optionsOuter.setSaveOuter();
        String appendString = append.xmlText();
        String srcString = src.xmlText();
        String prefix = srcString.substring(0,srcString.indexOf(">")+1);
        String mainPart = srcString.substring(srcString.indexOf(">")+1,srcString.lastIndexOf("<"));
        String sufix = srcString.substring( srcString.lastIndexOf("<") );
        String addPart = appendString.substring(appendString.indexOf(">") + 1, appendString.lastIndexOf("<"));
        CTBody makeBody = CTBody.Factory.parse(prefix+mainPart+addPart+sufix);
        src.set(makeBody);
    }
}