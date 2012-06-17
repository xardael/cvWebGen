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

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String homepage(ModelMap model) {
        model.addAttribute("recentPublicCvs", null);
        return "homepage";
    }

    @RequestMapping(value= "/", method = RequestMethod.POST, params = "do=create")
    public String homepageCreate(ModelMap model, HttpServletRequest request) {

        String hash = "";
        String key = "";
        String email = request.getParameter("email");
        String privacy = request.getParameter("privacy");
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

        return "redirect:editor/"+hash+"/?new=true&email="+email+"&privacy="+privacy+"&key="+key;
    }

    @RequestMapping(value = "/about/", method = RequestMethod.GET)
    public String about(ModelMap model) {
        return "about";
    }

    @RequestMapping(value = "/editor/{hash}/*", method = RequestMethod.GET)
    public String editor(ModelMap model, @PathVariable String hash, HttpServletRequest request) {
        model.addAttribute("hash", hash);
        if (request.getParameter("key") != null) { model.addAttribute("key", request.getParameter("key")); }

        CvDocument.Cv cv;
        if (request.getParameter("new") != null) {

            model.addAttribute("new", true);
            CvDocument cvDocument = CvDocument.Factory.newInstance();
            cv = cvDocument.addNewCv();
            MetaType newMeta = cv.addNewMeta();
            newMeta.setHash(hash);
            newMeta.setKey(request.getParameter("key"));
            newMeta.setEmail(request.getParameter("email"));
            if(request.getParameter("privacy").equals("private")) {
                newMeta.setPrivacy(MetaType.Privacy.PRIVATE);
            } else {
                newMeta.setPrivacy(MetaType.Privacy.PUBLIC);
            }
            newMeta.setCreated(Calendar.getInstance());

        } else {

            CvDocument cvDocument = null;
            try {
                cvDocument = CvDocument.Factory.parse("AAA");
            } catch (XmlException e) {
                e.printStackTrace();
            }
            cv = cvDocument.getCv();

        }


        model.addAttribute("cv", cv);
        return "editor";
    }



    @RequestMapping(value="/editor/{hash}/*", method=RequestMethod.POST)
    public String create(ModelMap model, HttpServletRequest request) {

        /* Parse form */

        CvManager cvManager = new CvManager();
        CvDocument doc = cvManager.parseFromHtmlFormRequest(request);
        Map namespaceForwardMap = new HashMap();
        namespaceForwardMap.put("", "http://fi.muni.cz/pb138/cvWebGen/xml");
        String xml = doc.xmlText((new XmlOptions()).setSavePrettyPrint().setUseDefaultNamespace().setSaveImplicitNamespaces(namespaceForwardMap));


        BaseXClient session = null;
        try {
            session = new BaseXClient("localhost", 1984, "admin", "admin");
            session.execute("OPEN JAVA_CVS");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            session.add(doc.getCv().getMeta().getHash(), new ByteArrayInputStream(xml.getBytes("UTF-8")));
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        model.addAttribute("xml", xml);

        return "editor-debug";
    }

    @RequestMapping(value="/viewer/{hash}/", method=RequestMethod.GET)
    public String viewer(ModelMap model, HttpServletRequest request) throws IOException {

        BaseXClient session = null;
        try {
            session = new BaseXClient("localhost", 1984, "admin", "admin");
            session.execute("OPEN JAVA_CVS");
        } catch (IOException e) {
            e.printStackTrace();
        }

        BaseXClient.Query query = session.query("for $cv in //cv " +
                "where $cv/meta/hash/text() = \"18191191494423699403328351497345304946\" " +
                "return $cv");

        CvDocument.Cv cv = null;
        try {
            String result = query.execute();
            result = result.replaceFirst("<cv>", "<cv xmlns=\"http://fi.muni.cz/pb138/cvWebGen/xml\">");
            CvDocument cvDocument = CvDocument.Factory.parse(result);
            cv = cvDocument.getCv();
        } catch (XmlException e) {
            e.printStackTrace();
        }

        model.addAttribute("cv", cv);

        return "viewer";

    }

}
