package cz.muni.fi.pb138.cvWebGen;

import cz.muni.fi.pb138.cvWebGen.xml.CvDocument;
import cz.muni.fi.pb138.cvWebGen.xml.MetaType;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlOptions;
import org.basex.core.BaseXException;
import org.basex.core.Context;
import org.basex.core.cmd.Add;
import org.basex.core.cmd.CreateDB;
import org.basex.core.cmd.Open;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Controller
@RequestMapping(value = "/")
public class DefaultController {

    private CvManager cvManager;
    private BaseXClient baseXClient;

    public DefaultController() {
        cvManager = new CvManager();
        try {
            baseXClient = new BaseXClient("localhost", 1984, "admin", "admin");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String homepage(ModelMap model) {
        model.addAttribute("recentPublicCvs", null); // TODO: implement
        return "homepage";
    }

    @RequestMapping(value = "/about/", method = RequestMethod.GET)
    public String about(ModelMap model) {
        return "about";
    }

    @RequestMapping(value= "/editor/create/", method = RequestMethod.POST)
    public String editorCreate(ModelMap model, HttpServletRequest request) {

        String email = request.getParameter("email");
        String privacy = request.getParameter("privacy");

        /* Generate hash & key */
        String hash = "";
        String key = "";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            String toHash = email + new Date().toString();
            String toKey = email + new Date().toString() + (new Random().nextInt());
            messageDigest.reset();
            hash = new BigInteger(1, messageDigest.digest(toHash.getBytes(Charset.forName("UTF-8")))).toString();
            messageDigest.reset();
            key = new BigInteger(1, messageDigest.digest(toKey.getBytes(Charset.forName("UTF-8")))).toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        CvDocument cvDocument = CvDocument.Factory.newInstance();
        CvDocument.Cv cv = cvDocument.addNewCv();
        MetaType newMeta = cv.addNewMeta();
        newMeta.setHash(hash);
        newMeta.setKey(key);
        newMeta.setEmail(email);
        if(privacy.equals("private")) {newMeta.setPrivacy(MetaType.Privacy.PRIVATE);} else {newMeta.setPrivacy(MetaType.Privacy.PUBLIC);}

        model.addAttribute("cv", cv);
        return "editor";
    }

    @RequestMapping(value="/editor/{hash}/*", method=RequestMethod.POST)
    public String editorSave(ModelMap model, @PathVariable String hash, HttpServletRequest request) {

        CvDocument cvDocument = cvManager.parseFromHtmlFormRequest(request);
        saveToDb(cvDocument);
        sentMailToOwner(cvDocument);

        model.addAttribute("cv", cvDocument.getCv());
        return "editor-submit";
    }


    @RequestMapping(value = "/editor/{hash}/*", method = RequestMethod.GET)
    public String editor(ModelMap model, @PathVariable String hash, HttpServletRequest request) {

        CvDocument cvDocument = cvManager.getCvDocumentByHash(hash);

        model.addAttribute("cv", cvDocument.getCv());
        return "editor";
    }

    @RequestMapping(value="/viewer/{hash}/", method=RequestMethod.GET)
    public String viewer(ModelMap model, @PathVariable String hash, HttpServletRequest request) {
        model.addAttribute("cv", cvManager.getCvDocumentByHash(hash).getCv());
        return "viewer";

    }

    @RequestMapping(value="/viewer/{hash}/", method=RequestMethod.GET, params="do=export")
    public String viewerExport(ModelMap model, @PathVariable String hash, HttpServletRequest request) {
        Latex.generatePdf(hash, cvManager.getCvDocumentByHash(hash).xmlText());
        return "viewer";

    }

    private void saveToDb(CvDocument cvDocument) {
        Map namespaceForwardMap = new HashMap();
        namespaceForwardMap.put("", "http://fi.muni.cz/pb138/cvWebGen/xml");
        String xml = cvDocument.xmlText((new XmlOptions()).setSavePrettyPrint().setUseDefaultNamespace().setSaveImplicitNamespaces(namespaceForwardMap));
        try {
            baseXClient.execute("OPEN JAVA_CVS");
            baseXClient.add(cvDocument.getCv().getMeta().getHash(), new ByteArrayInputStream(xml.getBytes("UTF-8")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sentMailToOwner(CvDocument cvDocument) {
//        SimpleMailMessage msg = new SimpleMailMessage();
//        msg.setTo(cvDocument.getCv().getMeta().getEmail());
//        msg.setFrom("admin@cvwebgen.cz");
//        msg.setText(
//                "You have created CV on cvWebGen" +
//                "Vier: " + "/viewer/" + cvDocument.getCv().getMeta().getHash() + "/" +
//                "Edit: " + "/editor/" + cvDocument.getCv().getMeta().getHash() + "/&key=" + cvDocument.getCv().getMeta().getKey() +
//                "End of message");
//        try{
//            this.mailSender.send(msg);
//        }
//        catch(MailException ex) {
//            System.err.println(ex.getMessage());
//        }
    }

}
