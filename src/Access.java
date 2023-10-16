import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;

public class Access {

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
            HashMap<String,String> typeMap = new HashMap();
            NodeList proPackage = doc.getElementsByTagName("Package");
            for (int i = 0; i < proPackage.getLength(); i++) {
                Element packE = (Element) proPackage.item(i);
                NodeList TopSingleList= packE.getElementsByTagName("ProjectTypeTopSingleOrganism");
                NodeList TopAdminList= packE.getElementsByTagName("ProjectTypeTopAdmin");
                NodeList SubList= packE.getElementsByTagName("ProjectTypeSubmission");
                Element TopSingleE = (Element) TopSingleList.item(0);
                Element TopAdminE = (Element) TopAdminList.item(0);
                Element SubE = (Element) SubList.item(0);
                if(TopSingleE==null&&TopAdminE==null){
//                if(TopSingleE!=null){
                    NodeList accessionList= packE.getElementsByTagName("ArchiveID");
                    Element e = (Element) accessionList.item(0);
                    String accession =e.getAttribute("accession");
                    NodeList subPackage = doc.getElementsByTagName("Submission");
                    Element subE = (Element) subPackage.item(0);
                    NodeList accList= subE.getElementsByTagName("Access");
                    Element  acc =  (Element) accList.item(0);
                    String Acc = "";
                    if(acc!=null){
                        Acc = acc.getFirstChild().getNodeValue();
                    } else {
                        Acc = "error";
                    }
                    if(!typeMap.containsKey(Acc)){
                        typeMap.put(Acc,Acc);
                    }
                    toOut.write(accession+"\t"+Acc+"\n");
                }
            }
//            for(String key:typeMap.keySet()){
//                toOut.write(key+"\n");
//            }
            toOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
