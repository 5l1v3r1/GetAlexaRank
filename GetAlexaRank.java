import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;

public class GetAlexaRank {
    private static final String baseUrl = "http://data.alexa.com/data?cli=10&url=";
    private static ArrayList<String> listUrl = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        System.out.println("------------");
        System.out.println("This small program will help you to get Alexa rank for a given list of ulr(s).\nPlease see the instruction at: https://github.com/scorta/GetAlexaRank");
        System.out.println("------------");
        String fileName = "input.txt";
        StringBuilder result = new StringBuilder();
        int pauseInterval = 2000;
        if (args.length > 0) {
            fileName = args[0];
        }

        if (args.length > 1) {
            try {
                pauseInterval = Integer.parseInt(args[1]);
            } catch (Exception e){
                System.out.println("Invalid argument. Please check again.");
                forceExit();
            }
        }

        readUrlList(fileName);
        System.out.println("Start getting Alexa rank for the list...");
        for (String url : listUrl) {
            result.append(getRank(url));
            result.append("\n");
            Thread.sleep(pauseInterval);
        }

        writeOutput(fileName, result.toString());
    }

    private static void forceExit(){
        System.out.println("------------");
        System.out.println("The program will now exit.");
        System.out.println("------------");
        System.exit(1);

    }

    private static void writeOutput(String fileName, String content){
        try {
            PrintWriter out = new PrintWriter(fileName + "_out.txt");
            out.print(content);
            out.close();
            System.out.println("Done. Please check the output file. It is named " + fileName + "_out.txt");
        } catch (Exception e){
            System.out.println("Error while writing result: " + e.toString());
            forceExit();
        }
    }

    private static void readUrlList(String fileName){
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            for (String line; (line = br.readLine()) != null;) {
                listUrl.add(line);
            }
            System.out.println("Done importing.");
        } catch (Exception e){
            System.out.println("Error while reading file: " + e.toString());
            forceExit();
        }
    }

    private static String getRank(String url){
        try {
            URL xmlURL = new URL(baseUrl + url);
            InputStream xml = xmlURL.openStream();
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(xml);
            doc.getDocumentElement().normalize();

            Element rank = (Element) doc.getDocumentElement().getElementsByTagName("POPULARITY").item(0);
            Element country = (Element) doc.getDocumentElement().getElementsByTagName("COUNTRY").item(0);
            StringBuilder result = new StringBuilder(url);
            result.append("\t");

            xml.close();

            if(country != null){
                result.append(country.getAttribute("NAME"));
                result.append("\t");
                result.append(country.getAttribute("RANK"));
                result.append("\t");
            } else {
                result.append("NULL");
                result.append("\t");
                result.append("NULL");
                result.append("\t");
            }

            if(rank != null){
                result.append(rank.getAttribute("TEXT"));
            } else {
                result.append("NULL");
            }

            return result.toString();
        } catch (Exception e){
            return "Error: " + e.toString();
        }
    }
}
