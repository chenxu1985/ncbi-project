import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.util.HashMap;

public class Check1 {

    public static void main(String[] args) throws IOException {
//        String projectFilePath = args[0];
//
//        String outPath1 = args[1];

        String projectFilePath = "/Users/laphael/Desktop/out.txt";


        String outPath1 = "/Users/laphael/Desktop/out2.txt";


        File toFile1 = new File(outPath1);
        toFile1.createNewFile();
        FileWriter toWriter1 = new FileWriter(toFile1);
        BufferedWriter out1 = new BufferedWriter(toWriter1);
        File file = new File(projectFilePath);
        InputStreamReader read = null;//考虑到编码格式
        try {
            read = new InputStreamReader(new FileInputStream(file),"GBK");
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = null;
            HashMap accMap = new HashMap();
            while((lineTxt = bufferedReader.readLine()) != null){
                String[] str = lineTxt.split(",");
                for(String s:str){
                    if(accMap.containsKey(s)){
                        out1.write(s+"\n");
                    } else {
                        accMap.put(s,s);
                    }
                }
            }
            out1.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
