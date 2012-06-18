package cz.muni.fi.pb138.cvWebGen;

import cz.muni.fi.pb138.cvWebGen.xml.CvDocument;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;

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

    private static final Logger LOGGER = Logger.getLogger(Latex.class);
    private String storageDir;

    public void generatePdf(String fileName, CvDocument cvDocument) {

        /* Write XML to file */
        try {
            cvDocument.save(new File(storageDir + fileName + ".xml"));
        } catch (IOException e) {
            LOGGER.log(Level.ERROR, "Error saving XML file needed to render TEX file", e);
        }

        TransformerFactory tFactory = TransformerFactory.newInstance();
        try {
            Transformer transformer = tFactory.newTransformer(new StreamSource(new File(storageDir + "latex.xsl")));
            transformer.transform(new StreamSource(new File(storageDir + fileName + ".xml")),
                                  new StreamResult(new File(storageDir + fileName + ".tex")));
            Process p = Runtime.getRuntime().exec("/usr/texbin/pdflatex -output-directory=" + storageDir + " -interaction=batchmode " + storageDir + fileName + ".tex");
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
    }

    @Required
    public void setStorageDir(String storageDir) {
        this.storageDir = storageDir;
    }
}
