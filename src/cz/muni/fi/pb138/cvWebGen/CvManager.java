package cz.muni.fi.pb138.cvWebGen;

import cz.muni.fi.pb138.cvWebGen.xml.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CvManager {

    public CvDocument parseFromHtmlFormRequest(HttpServletRequest request) {

        /* Build document */
        CvDocument cvDocument = CvDocument.Factory.newInstance();
        CvDocument.Cv cv = cvDocument.addNewCv();

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
        if (request.getParameter("personal-gender") == "male") {
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
        String[] phonesPivot = request.getParameterValues("contact-phone-content");
        for (int i = 0; i < phonesPivot.length; i++) {
            CvDocument.Cv.Contacts.Phone newPhone = contacts.addNewPhone();
            newPhone.setType(request.getParameterValues("contact-phone-type")[i]);
            newPhone.setStringValue(request.getParameterValues("contact-phone-content")[i]);
        }
        /* Emails */
        String[] emailsPivot = request.getParameterValues("contact-email-content");
        for (int i = 0; i < emailsPivot.length; i++) {
            contacts.addEmail(request.getParameterValues("contact-email-content")[i]);
        }
        /* Websites */
        String[] websitesPivot = request.getParameterValues("contact-website-content");
        for (int i = 0; i < websitesPivot.length; i++) {
            contacts.addWebsite(request.getParameterValues("contact-website-content")[i]);
        }

        /* Works */
        /*********/
        CvDocument.Cv.Works works = cv.addNewWorks();
        /* Items */
        String[] worksPivot = request.getParameterValues("work-employer");
        for (int i = 0; i < worksPivot.length; i++) {
            WorkType newWork = works.addNewWork();
            newWork.setPeriod(this._parsePeriod(request, "work", i));
            newWork.setEmployer(request.getParameterValues("work-employer")[i]);
            newWork.setPosition(request.getParameterValues("work-position")[i]);
            newWork.setActivities(request.getParameterValues("work-activities")[i]);
            newWork.setSector(request.getParameterValues("work-sector")[i]);
        }


        /* Educations */
        /**************/


        /* Skills */
        /**********/


        /* Languages */
        /*************/

        return cvDocument;

    }

    private PeriodType _parsePeriod(HttpServletRequest request, String parent, int i) {
        PeriodType newPeriod = PeriodType.Factory.newInstance();
        DateType newDateFrom = DateType.Factory.newInstance();
        DateType newDateTo = DateType.Factory.newInstance();

        if (request.getParameterValues(parent + "-period-from-year") != null && request.getParameterValues(parent + "-period-from-year")[i] != null) { newDateFrom.setYear(BigInteger.valueOf(Integer.valueOf(request.getParameterValues(parent + "-period-from-year")[i]))); }
        if (request.getParameterValues(parent + "-period-from-month") != null && request.getParameterValues(parent + "-period-from-month")[i] != null) { newDateFrom.setMonth(Integer.valueOf(request.getParameterValues(parent + "-period-from-month")[i])); }
        if (request.getParameterValues(parent + "-period-from-day") != null && request.getParameterValues(parent + "-period-from-day")[i] != null) { newDateFrom.setDay(Integer.valueOf(request.getParameterValues(parent + "-period-from-day")[i])); }
        if (request.getParameterValues(parent + "-period-to-year") != null && request.getParameterValues(parent + "-period-to-year")[i] != null) { newDateTo.setYear(BigInteger.valueOf(Integer.valueOf(request.getParameterValues(parent + "-period-to-year")[i]))); }
        if (request.getParameterValues(parent + "-period-to-month") != null && request.getParameterValues(parent + "-period-to-month")[i] != null) { newDateTo.setMonth(Integer.valueOf(request.getParameterValues(parent + "-period-to-month")[i])); }
        if (request.getParameterValues(parent + "-period-to-day") != null && request.getParameterValues(parent + "-period-to-day")[i] != null) { newDateTo.setDay(Integer.valueOf(request.getParameterValues(parent + "-period-to-day")[i])); }

        newPeriod.setFrom(newDateFrom);
        newPeriod.setTo(newDateTo);

        return newPeriod;
    }
}
