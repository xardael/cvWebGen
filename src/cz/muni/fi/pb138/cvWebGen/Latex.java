/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.cvWebGen;

import java.io.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 *
 * @author pyty
 */
public class Latex {
    /**
     * Perform transformation based on xml document
     * 
     * @param fileName
     * @return path to the PDF
     */
    public static String generatePdf(String fileName, String xml) {
        String baseDir = "/Volumes/Data/School/_Java/cvWebGenReborn/web/files";
        String pathToSchema = "cv.xsl";

        try {
            PrintWriter out = new PrintWriter(baseDir + "/xml/" + fileName + ".xml");
            out.print(xml);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        TransformerFactory tFactory = TransformerFactory.newInstance();
        try {
            Transformer transformer =
                tFactory.newTransformer(new StreamSource(new File(pathToSchema)));

            transformer.transform(new StreamSource(new File(baseDir + "/xml/" + fileName + ".xml")),
                                  new StreamResult(new File(baseDir + "/latex/" + fileName + ".tex")));
        
        
            Process p = Runtime.getRuntime().exec("pdflatex -output-directory=" + baseDir + "/pdf -interaction=batchmode " + baseDir + "/latex/" + fileName + ".tex");
            p.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = reader.readLine();
            while (line != null) {
                //System.out.println(line);
                line = reader.readLine();
            }
            
            

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return baseDir + "/pdf/" + fileName + ".pdf";
    }
}
