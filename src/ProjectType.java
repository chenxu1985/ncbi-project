import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;

public class ProjectType {

    public static void main(String[] args) {
        String projectFilePath = args[0];
        String outPath = args[1];
//        String projectFilePath = "/Users/laphael/Desktop/test.txt";
//        String outPath = "/Users/laphael/Desktop/testOut.txt";
        File file = new File(projectFilePath);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            File toFile = new File(outPath);
            toFile.createNewFile();
            FileWriter toWriter = new FileWriter(toFile);
            BufferedWriter toOut = new BufferedWriter(toWriter);
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);
            NodeList proPackage = doc.getElementsByTagName("Package");
            HashMap<String,String> typeMap = new HashMap();
            for (int i = 0; i < proPackage.getLength(); i++) {
                Element packE = (Element) proPackage.item(i);
                    NodeList typeList= packE.getElementsByTagName("ProjectType");
                    Element e = (Element) typeList.item(0);
                    String type = e.getFirstChild().getNextSibling().getNodeName();
                    if(!typeMap.containsKey(type)){
                        typeMap.put(type,type);
                    }
            }
            for(String key:typeMap.keySet()){
                toOut.write(key+"\n");
            }
            toOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
