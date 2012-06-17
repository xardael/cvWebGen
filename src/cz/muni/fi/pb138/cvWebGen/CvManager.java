package cz.muni.fi.pb138.cvWebGen;

import cz.muni.fi.pb138.cvWebGen.xml.*;
import org.apache.xmlbeans.XmlException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CvManager {

    private BaseXClient baseXClient;

    public CvManager() {
        try {
            baseXClient = new BaseXClient("localhost", 1984, "admin", "admin");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public CvDocument parseFromHtmlFormRequest(HttpServletRequest request) {

        /* Build document */
        CvDocument cvDocument = CvDocument.Factory.newInstance();
        CvDocument.Cv cv = cvDocument.addNewCv();

        /* Meta */
        /********/
        MetaType meta = cv.addNewMeta();
        meta.setHash(request.getParameter("meta-hash"));
        meta.setKey(request.getParameter("meta-key"));
        meta.setEmail(request.getParameter("meta-email"));
        if (request.getParameter("meta-privacy").equals("private")) {
            meta.setPrivacy(MetaType.Privacy.PRIVATE);
        } else {
            meta.setPrivacy(MetaType.Privacy.PUBLIC);
        }
        meta.setCreated(Calendar.getInstance()); // TODO: Do not change when modify
        meta.setModified(Calendar.getInstance());


        /* Personal */
        /************/
        PersonalType personal = cv.addNewPersonal();
        /* firstName */
        if (!request.getParameter("personal-firstName").isEmpty()) {
            personal.setFirstName(request.getParameter("personal-firstName"));
        }
        /* middleName */
        if (!request.getParameter("personal-middleName").isEmpty()) {
            personal.setMiddleName(request.getParameter("personal-middleName"));
        }
        /* lastName */
        if (!request.getParameter("personal-lastName").isEmpty()) {
            personal.setLastName(request.getParameter("personal-lastName"));
        }
        /* dateofbirth */
        if (!request.getParameter("personal-dateofbirth").isEmpty()) {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
            try {
                cal.setTime(sdf.parse(request.getParameter("personal-dateofbirth")));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            personal.setDateofbirth(cal);
        }
        /* gender */
        if (request.getParameter("personal-gender").equals("male")) {
            personal.setGender(PersonalType.Gender.MALE);
        } else {
            personal.setGender(PersonalType.Gender.FEMALE);
        }
        /* nationality */
        if (!request.getParameter("personal-nationality").isEmpty()) {
            personal.setNationality(request.getParameter("personal-nationality"));
        }


        /* Address */
        /***********/
        AddressType address = cv.addNewAddress();
        /* street */
        if (!request.getParameter("address-street").isEmpty()) {
            address.setStreet(request.getParameter("address-street"));
        }
        /* city */
        if (!request.getParameter("address-city").isEmpty()) {
            address.setCity(request.getParameter("address-city"));
        }
        /* zip */
        if (!request.getParameter("address-zip").isEmpty()) {
            address.setZip(request.getParameter("address-zip"));
        }
        /* state */
        if (!request.getParameter("address-state").isEmpty()) {
            address.setState(request.getParameter("address-state"));
        }
        /* description */
        if (!request.getParameter("address-description").isEmpty()) {
            address.setDescription(request.getParameter("address-description"));
        }


        /* Contacts */
        /************/
        CvDocument.Cv.Contacts contacts = cv.addNewContacts();
        /* Phones */
        if (request.getParameterValues("contact-phone-content") != null) {
            String[] phonesPivot = request.getParameterValues("contact-phone-content");
            for (int i = 0; i < phonesPivot.length; i++) {
                CvDocument.Cv.Contacts.Phone newPhone = contacts.addNewPhone();
                newPhone.setType(request.getParameterValues("contact-phone-type")[i]);
                newPhone.setStringValue(request.getParameterValues("contact-phone-content")[i]);
            }
        }
        /* Emails */
        if (request.getParameterValues("contact-email-content") != null) {
            String[] emailsPivot = request.getParameterValues("contact-email-content");
            for (int i = 0; i < emailsPivot.length; i++) {
                contacts.addEmail(request.getParameterValues("contact-email-content")[i]);
            }
        }
        /* Websites */
        if (request.getParameterValues("contact-website-content") != null) {
            String[] websitesPivot = request.getParameterValues("contact-website-content");
            for (int i = 0; i < websitesPivot.length; i++) {
                contacts.addWebsite(request.getParameterValues("contact-website-content")[i]);
            }
        }


        /* Works */
        /*********/
        CvDocument.Cv.Works works = cv.addNewWorks();
        /* Items */
        if (request.getParameterValues("work-employer") != null) {
            String[] worksPivot = request.getParameterValues("work-employer");
            for (int i = 0; i < worksPivot.length; i++) {
                WorkType newWork = works.addNewWork();
                newWork.setPeriod(this._parsePeriod(request, "work", i));
                newWork.setEmployer(request.getParameterValues("work-employer")[i]);
                newWork.setPosition(request.getParameterValues("work-position")[i]);
                newWork.setActivities(request.getParameterValues("work-activities")[i]);
                newWork.setSector(request.getParameterValues("work-sector")[i]);
            }
        }


        /* Educations */
        /**************/
        CvDocument.Cv.Educations educations = cv.addNewEducations();
        /* Items */
        if (request.getParameterValues("education-organisation") != null) {
            String[] educationsPivot = request.getParameterValues("education-organisation");
            for (int i = 0; i < educationsPivot.length; i++) {
                EducationType newEducation = educations.addNewEducation();
                newEducation.setPeriod(this._parsePeriod(request, "education", i));
                newEducation.setOrganisation(request.getParameterValues("education-organisation")[i]);
                newEducation.setDescription(request.getParameterValues("education-description")[i]);
            }
        }

        /* Skills */
        /**********/
        CvDocument.Cv.Skills skills= cv.addNewSkills();
        /* Items */
        if (request.getParameterValues("skill-name") != null) {
            String[] skillsPivot = request.getParameterValues("skill-name");
            for (int i = 0; i < skillsPivot.length; i++) {
                SkillType newSkill = skills.addNewSkill();
                newSkill.setName(request.getParameterValues("skill-name")[i]);
                newSkill.setStringValue(request.getParameterValues("skill-content")[i]);
            }
        }


        /* Languages */
        /*************/
        CvDocument.Cv.Languages languages= cv.addNewLanguages();
        /* Items */
        if (request.getParameterValues("language-content") != null) {
            String[] languagesPivot = request.getParameterValues("language-content");
            for (int i = 0; i < languagesPivot.length; i++) {
                LanguageType newLanguage = languages.addNewLanguage();

                if (request.getParameterValues("language-level")[i].equals("novice")) { // NOTE: Cannot use Switch statement to String
                    newLanguage.setLevel(LanguageType.Level.NOVICE);
                } else if (request.getParameterValues("language-level")[i].equals("beginner")) {
                    newLanguage.setLevel(LanguageType.Level.BEGINNER);
                } else if (request.getParameterValues("language-level")[i].equals("intermediate")) {
                    newLanguage.setLevel(LanguageType.Level.INTERMEDIATE);
                } else if (request.getParameterValues("language-level")[i].equals("advanced")) {
                    newLanguage.setLevel(LanguageType.Level.ADVANCED);
                } else {
                    // TODO: REMOVE
                }

                newLanguage.setStringValue(request.getParameterValues("language-content")[i]);
            }
        }

        /* That's all folks! */
        /*********************/
        return cvDocument;

    }

    private PeriodType _parsePeriod(HttpServletRequest request, String parent, int i) {
        PeriodType newPeriod = PeriodType.Factory.newInstance();
        DateType newDateFrom = DateType.Factory.newInstance();
        DateType newDateTo = DateType.Factory.newInstance();

        if (request.getParameterValues(parent + "-period-from-year") != null && !request.getParameterValues(parent + "-period-from-year")[i].isEmpty()) { newDateFrom.setYear(BigInteger.valueOf(Integer.valueOf(request.getParameterValues(parent + "-period-from-year")[i]))); }
        if (request.getParameterValues(parent + "-period-from-month") != null && !request.getParameterValues(parent + "-period-from-month")[i].isEmpty()) { newDateFrom.setMonth(Integer.valueOf(request.getParameterValues(parent + "-period-from-month")[i])); }
        if (request.getParameterValues(parent + "-period-from-day") != null && !request.getParameterValues(parent + "-period-from-day")[i].isEmpty()) { newDateFrom.setDay(Integer.valueOf(request.getParameterValues(parent + "-period-from-day")[i])); }
        if (request.getParameterValues(parent + "-period-to-year") != null && !request.getParameterValues(parent + "-period-to-year")[i].isEmpty()) { newDateTo.setYear(BigInteger.valueOf(Integer.valueOf(request.getParameterValues(parent + "-period-to-year")[i]))); }
        if (request.getParameterValues(parent + "-period-to-month") != null && !request.getParameterValues(parent + "-period-to-month")[i].isEmpty()) { newDateTo.setMonth(Integer.valueOf(request.getParameterValues(parent + "-period-to-month")[i])); }
        if (request.getParameterValues(parent + "-period-to-day") != null && !request.getParameterValues(parent + "-period-to-day")[i].isEmpty()) { newDateTo.setDay(Integer.valueOf(request.getParameterValues(parent + "-period-to-day")[i])); }

        newPeriod.setFrom(newDateFrom);
        newPeriod.setTo(newDateTo);

        return newPeriod;
    }

    public CvDocument getCvDocumentByHash(String hash) {

        CvDocument cvDocument = null;
        try {
            baseXClient.execute("OPEN JAVA_CVS");
            BaseXClient.Query query = baseXClient.query(
                "for $cv in //cv " +
                "where $cv/meta/hash/text() = \"" + hash + "\" " +
                "return $cv"
            );
            String result = query.execute();
            result = result.replaceFirst("<cv>", "<cv xmlns=\"http://fi.muni.cz/pb138/cvWebGen/xml\">");
            cvDocument = CvDocument.Factory.parse(result);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlException e) {
            e.printStackTrace();
        }

        return cvDocument;
    }
}
