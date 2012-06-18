package cz.muni.fi.pb138.cvWebGen;

import cz.muni.fi.pb138.cvWebGen.xml.CvDocument;
import org.apache.log4j.Logger;
import org.apache.log4j.Level;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/")
public class DefaultController {

    private static final Logger LOGGER = Logger.getLogger(DefaultController.class);
    private CvManager cvManager = cvManager = new CvManager();

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String homepage(ModelMap model) {
        model.addAttribute("recentPublicCvs", cvManager.getPublic());
        for (CvDocument cvDocument : cvManager.getPublic()) {
            LOGGER.log(Level.INFO, "Loaded public CV " + cvDocument.getCv().getMeta().getHash());
        }
        return "homepage";
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
            String currentKey = cvManager.getByHash(hash).getCv().getMeta().getKey();
            if (urlKey.equals(currentKey)) {
                if (cvManager.countCvsByHash(hash) == 1) {
                    LOGGER.log(Level.INFO, "editorSave: edit: Found CV with given hash, going to delete and persist new.");
                    cvManager.unpersist(hash);
                    if (cvManager.countCvsByHash(hash) == 0) {
                        LOGGER.log(Level.INFO, "editorSave: edit: Deleting existing CV succeeded");
                        cvDocument.getCv().getMeta().setKey(urlKey);
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

    @RequestMapping(value="/viewer/{hash}/", method=RequestMethod.GET, params="do=export")
    public String viewerExport(ModelMap model, @PathVariable String hash, HttpServletRequest request) {
        Latex.generatePdf(hash, cvManager.getByHash(hash).xmlText());
        return "viewer";

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
