package cz.muni.fi.pb138.cvWebGen;

import cz.muni.fi.pb138.cvWebGen.xml.CvDocument;
import org.apache.log4j.Logger;
import org.apache.log4j.Level;
import org.apache.xmlbeans.XmlOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Properties;

@Controller
@RequestMapping(value = "/")
public class DefaultController {

    private static final Logger LOGGER = Logger.getLogger(DefaultController.class);
    @Value("${storageDir}")
    private String storageDir;
    @Value("${mailUsername}")
    private String mailUsername;
    @Value("${mailPassword}")
    private String mailPassword;
    @Value("${baseUrl}")
    private String baseUrl;
    @Autowired
    private CvManager cvManager;
    @Autowired
    private Latex latex;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String homepage(ModelMap model) {
        model.addAttribute("recentPublicCvs", cvManager.getPublic());
        for (CvDocument cvDocument : cvManager.getPublic()) {
            LOGGER.log(Level.INFO, "Loaded public CV " + cvDocument.getCv().getMeta().getHash());
        }
        return "homepage";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET, params = "do=forget")
    public String homepageForget(ModelMap model, HttpServletRequest request) {
        String email = request.getParameter("email");
        LOGGER.log(Level.INFO, "homepageForget: getting CVs with email: " + email);
        sentMailToFool(cvManager.getByEmail(email), email);
        return "redirect:/";
    }

    @RequestMapping(value = "/about/", method = RequestMethod.GET)
    public String about(ModelMap model) {
        return "about";
    }

    @RequestMapping(value= "/editor/create/", method = RequestMethod.POST)
    public String editorCreate(ModelMap model, HttpServletRequest request) {
        CvDocument initedCvDocument = cvManager.init(request.getParameter("email"), request.getParameter("privacy"));
        model.addAttribute("cv", initedCvDocument.getCv());
        return "editor";
    }

    @RequestMapping(value="/editor/{hash}/*", method=RequestMethod.POST)
    public String editorSave(ModelMap model, @PathVariable String hash, HttpServletRequest request) {

        CvDocument cvDocument = cvManager.parseFromForm(request);

        if (request.getParameter("meta-key") != null && !request.getParameter("meta-key").isEmpty()) {
            // Create new CV
            if (cvManager.countCvsByHash(hash) > 0) {
                LOGGER.log(Level.INFO, "editorSave: create new CV:  Already exists CV with same hash");
                model.addAttribute("status", "error-duplicateHash");
            } else {
                LOGGER.log(Level.INFO, "editorSave: create: Everything seems OK, going to persist CV.");
                cvManager.persist(cvDocument);
                sentMailToOwner(cvDocument);
                model.addAttribute("status", "ok");
            }
        } else if (request.getParameter("urlKey") != null && !request.getParameter("urlKey").isEmpty()) {
            // Edit existing CV
            String urlKey = request.getParameter("urlKey");
            CvDocument.Cv currentCv = cvManager.getByHash(hash).getCv();
            String currentKey = currentCv.getMeta().getKey();
            if (urlKey.equals(currentKey)) {
                if (cvManager.countCvsByHash(hash) == 1) {
                    LOGGER.log(Level.INFO, "editorSave: edit: Found CV with given hash, going to delete and persist new.");
                    cvManager.unpersist(hash);
                    if (cvManager.countCvsByHash(hash) == 0) {
                        LOGGER.log(Level.INFO, "editorSave: edit: Deleting existing CV succeeded");
                        cvDocument.getCv().getMeta().setKey(urlKey); // Do not change key
                        cvDocument.getCv().getMeta().xsetCreated(currentCv.getMeta().xgetCreated()); // Do not change created
                        cvManager.persist(cvDocument);
                        model.addAttribute("status", "ok");
                    } else {
                        LOGGER.log(Level.INFO, "editorSave: edit: Deleting existing CV failed");
                        model.addAttribute("status", "error");
                    }
                } else {
                    LOGGER.log(Level.INFO, "editorSave: edit: Not found / or found many CVs with given hash.");
                    model.addAttribute("status", "error");
                }
            } else {
                LOGGER.log(Level.ERROR, "Keys does not match");
            }
        }

        model.addAttribute("cv", cvDocument.getCv());
        model.addAttribute("xml", cvManager.prettyXml(cvDocument));
        return "editor-submit";
    }


    @RequestMapping(value = "/editor/{hash}/*", method = RequestMethod.GET)
    public String editor(ModelMap model, @PathVariable String hash, HttpServletRequest request) {

        CvDocument cvDocument = cvManager.getByHash(hash);
        if (request.getParameter("key") != null && !request.getParameter("key").isEmpty()) {
            model.addAttribute("urlKey", request.getParameter("key"));
        } else {
            model.addAttribute("urlKey", "");
        }
        model.addAttribute("cv", cvDocument.getCv());
        return "editor";
    }

    @RequestMapping(value="/viewer/{hash}/", method=RequestMethod.GET)
    public String viewer(ModelMap model, @PathVariable String hash, HttpServletRequest request) {
        model.addAttribute("cv", cvManager.getByHash(hash).getCv());
        return "viewer";
    }

    @RequestMapping(value = "/export/{hash}.pdf", method = RequestMethod.GET)
    public void exportPdf(HttpServletRequest request, HttpServletResponse response, @PathVariable String hash) throws IOException {
        latex.generatePdf(hash, cvManager.getByHash(hash));
        File pdfFile = new File(storageDir + hash + ".pdf");
        response.setContentType("application/pdf");
        response.addHeader("Content-Disposition", "attachment; filename=" + hash + ".pdf");
        response.setContentLength((int) pdfFile.length());
        FileInputStream fileInputStream = new FileInputStream(pdfFile);
        OutputStream responseOutputStream = response.getOutputStream();
        int bytes;
        while ((bytes = fileInputStream.read()) != -1) {
            responseOutputStream.write(bytes);
        }
    }

    @RequestMapping(value="/viewer/{hash}/export.xml", method=RequestMethod.GET)
    public String viewerXml(ModelMap model, @PathVariable String hash, HttpServletRequest request, HttpServletResponse response) {
        CvDocument cvDocument = cvManager.getByHash(hash);
        model.addAttribute("cv", cvDocument.getCv());
        model.addAttribute("xml", cvDocument.xmlText(new XmlOptions().setUseDefaultNamespace()));
        response.setContentType("text/xml");
        return "viewer-xml";

    }

    private void sentMailToOwner(CvDocument cvDocument) {

        Session session = _initMailConnection();

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("cvwebgenbot@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(cvDocument.getCv().getMeta().getEmail()));
            message.setSubject("You have created CV on CVWebGen");
            message.setText(
                    "Hello, you have created CV on CVWebGen," +
                    "\n\n" +
                    "You can view your CV via this relative url: " + baseUrl + "/viewer/" + cvDocument.getCv().getMeta().getHash() + "/" +
                    "\n" +
                    "You can edit your CV via this relative url: " + baseUrl + "/editor/" + cvDocument.getCv().getMeta().getHash() + "/?key=" + cvDocument.getCv().getMeta().getKey() +
                    "\n\n" +
                    "Thanks for using CVWebGen"
            );
            Transport.send(message);
            LOGGER.log(Level.INFO, "Email about new CV to has been sent to " + cvDocument.getCv().getMeta().getEmail());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private void sentMailToFool(List<CvDocument> cvDocuments, String email) {

        Session session = _initMailConnection();

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("cvwebgenbot@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("Your CVs");
            String body = "";
            for (CvDocument cvDocument : cvDocuments) {
                body = body + "\n" + cvDocument.getCv().getPersonal().getFirstName() + " " + cvDocument.getCv().getPersonal().getLastName() + ": " + baseUrl + "/editor/" + cvDocument.getCv().getMeta().getHash() + "/?key=" + cvDocument.getCv().getMeta().getKey();
            }
            message.setText("Never mind you forget edit links to your CVs, here you go:," + "\n\n" + body + "\n\n" + "Thanks for using CVWebGen"
            );
            LOGGER.log(Level.INFO, "sentMailToFool: body:" + "\n" + body);
            Transport.send(message);
            LOGGER.log(Level.INFO, "Email about forgotten CVs to has been sent to " + email);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private Session _initMailConnection() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        LOGGER.log(Level.INFO, "Logging to Google Mail with " + mailUsername + ":" + mailPassword);
        return Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(mailUsername, mailPassword);
                    }
                });
    }

}
