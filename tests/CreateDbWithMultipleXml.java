import cz.muni.fi.pb138.cvWebGen.BaseXClient;
import org.basex.core.BaseXException;
import org.basex.core.Context;
import org.basex.core.cmd.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.Assert.assertTrue;

public final class CreateDbWithMultipleXml {

    private BaseXClient baseXClient;
    private String projectDir = "/Volumes/Data/School/_Java/cvWebGenReborn/";

    @Before
    public void setUp() throws Exception {

        try {
            baseXClient = new BaseXClient("localhost", 1984, "admin", "admin");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testCreateDbWithMultipleXml() throws Exception {

        String dbName = "testCreateSampleDbAndExecuteBasicXQuery";

        System.out.println("=== CreateDbWithMultipleXml ===");

        System.out.println("\n ## Create a database.");
        System.out.println(baseXClient.execute("CREATE DATABASE " + dbName));
        System.out.println(baseXClient.execute("ADD " + projectDir + "tests/anna.xml"));
        System.out.println(baseXClient.execute("ADD " + projectDir + "tests/bara.xml"));
        System.out.println(baseXClient.execute("ADD " + projectDir + "tests/carlos.xml"));
        System.out.println(baseXClient.execute("OPTIMIZE"));
        System.out.println(baseXClient.execute("LIST"));
        assertTrue(baseXClient.execute("LIST").indexOf(dbName) > 0);

        System.out.println("\n ## Show database documents:");
        String query = baseXClient.query(
                "for $doc in collection('" + dbName + "')" +
                        "return <document path='{ base-uri($doc) }'/>"
        ).execute();
        System.out.println(query);
        assertTrue(query.indexOf("anna.xml") > 0);
        assertTrue(query.indexOf("bara.xml") > 0);
        assertTrue(query.indexOf("carlos.xml") > 0);

        System.out.println("## Deleting carlos");
        baseXClient.execute("DELETE carlos.xml");
        query = baseXClient.query(
                "for $doc in collection('" + dbName + "')" +
                        "return <document path='{ base-uri($doc) }'/>"
        ).execute();
        System.out.println(query);
        assertTrue(query.indexOf("carlos.xml") == -1);

        baseXClient.execute("DROP DATABASE " + dbName);
    }


    @After
    public void tearDown() throws Exception {
        baseXClient.close();
    }
}