import cz.muni.fi.pb138.cvWebGen.xml.AddressType;
import cz.muni.fi.pb138.cvWebGen.xml.CvDocument;
import cz.muni.fi.pb138.cvWebGen.xml.MetaType;
import cz.muni.fi.pb138.cvWebGen.xml.PersonalType;
import org.apache.log4j.Level;
import org.apache.xmlbeans.XmlOptions;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: strajk
 * Date: 6/19/12
 * Time: 1:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class XmlValidation {
    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testXmlValidation() throws Exception {
        CvDocument cvDocument = CvDocument.Factory.newInstance();
        CvDocument.Cv cv = cvDocument.addNewCv();

        MetaType newMeta = cv.addNewMeta();
        newMeta.setHash("111");
        newMeta.setKey("222");
        newMeta.setEmail("a@b.com");
        newMeta.setPrivacy(MetaType.Privacy.PUBLIC);
        newMeta.setCreated(Calendar.getInstance());
        newMeta.setModified(Calendar.getInstance());

        assertFalse(cvDocument.validate());

        PersonalType newPersonal = cv.addNewPersonal();
        newPersonal.setFirstName("Joe");
        newPersonal.setMiddleName("Johnatan");
        newPersonal.setLastName("Doe");
        newPersonal.setDateofbirth(Calendar.getInstance());
        newPersonal.setGender(PersonalType.Gender.MALE);
        newPersonal.setNationality("English");

        assertFalse(cvDocument.validate());

        AddressType newAddress = cv.addNewAddress();
        newAddress.setStreet("Street 1");
        newAddress.setCity("City");
        newAddress.setState("State");
        newAddress.setZip("666 11");

        assertTrue(cvDocument.validate());

        newAddress.setStreet(null);

        assertFalse(cvDocument.validate());
    }
}
