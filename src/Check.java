import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class Check {

    public static void main(String[] args) throws IOException {
        String projectFilePath = args[0];

        String outPath1 = args[1];

//        String projectFilePath = "/Users/laphael/Desktop/1000000.xml";
//
//
//        String outPath1 = "/Users/laphael/Desktop/out.txt";


        File toFile1 = new File(outPath1);
        toFile1.createNewFile();
        FileWriter toWriter1 = new FileWriter(toFile1);
        BufferedWriter out1 = new BufferedWriter(toWriter1);
        File file = new File(projectFilePath);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);
            NodeList proPackage = doc.getElementsByTagName("Package");
            for (int i = 0; i < proPackage.getLength(); i++) {
                Element packE = (Element) proPackage.item(i);
                //显示判断
                NodeList TopSingleList= packE.getElementsByTagName("ProjectTypeTopSingleOrganism");
                NodeList TopAdminList= packE.getElementsByTagName("ProjectTypeTopAdmin");

                Element TopSingleE = (Element) TopSingleList.item(0);
                Element TopAdminE = (Element) TopAdminList.item(0);
                if(TopSingleE==null&&TopAdminE==null){
                    NodeList subPackage = packE.getElementsByTagName("ArchiveID");
                    if(subPackage.getLength()>1){
                        Element subE1 = (Element) subPackage.item(0);
                        String accession1 =subE1.getAttribute("accession");
                        String acc = accession1;
                        for(int j=1;j<subPackage.getLength();j++){
                            Element subE = (Element) subPackage.item(j);
                            String accession =subE.getAttribute("accession");
                            acc = acc + "," + accession;
                        }
                        out1.write(acc+"\n");
                    }
                }
            }
            out1.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
