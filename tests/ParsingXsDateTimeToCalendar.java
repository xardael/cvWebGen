import cz.muni.fi.pb138.cvWebGen.xml.CvDocument;
import cz.muni.fi.pb138.cvWebGen.xml.MetaType;
import org.apache.xmlbeans.XmlDateTime;
import org.apache.xmlbeans.XmlException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created with IntelliJ IDEA.
 * User: strajk
 * Date: 6/18/12
 * Time: 4:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class ParsingXsDateTimeToCalendar {

    public static void main(String[] args) {
        CvDocument cvDocument = CvDocument.Factory.newInstance();
        CvDocument.Cv cv = cvDocument.addNewCv();
        MetaType newMeta = cv.addNewMeta();
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss.SSSzzzzzz");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(simpleDateFormat.parse("2012-06-18T13:14:07.524+02:00"));

            newMeta.setModified(calendar);
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        System.out.println(cv.getMeta().getModified());
    }


}