import cz.muni.fi.pb138.cvWebGen.BaseXClient;
import org.basex.core.BaseXException;
import org.basex.core.Context;
import org.basex.core.cmd.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.Assert.*;

public final class CreateDbAndExecuteBasicXQuery {

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
    public void testCreateSampleDbAndExecuteBasicXQuery() throws Exception {

        String dbName = "testCreateSampleDbAndExecuteBasicXQuery";

        System.out.println("=== testCreateSampleDbAndExecuteBasicXQuery ===");

        System.out.println("\n ## Create a database.");
        System.out.println(baseXClient.execute("CREATE DATABASE " + dbName + " " + projectDir + "tests/anna.xml"));

        System.out.println("\n ## Show existing databases:");
        assertTrue(baseXClient.execute("LIST").indexOf(dbName) > 0);
        System.out.println(baseXClient.execute("LIST"));

        System.out.println("\n ## Close and reopen database.");
        baseXClient.execute("CLOSE");
        baseXClient.execute("OPEN " + dbName);

        System.out.println("\n ## Create a full-text index.");
        baseXClient.execute("CREATE INDEX FULLTEXT");

        System.out.println("\n ## Test basic XQuery:");
        System.out.println( "Name: " + baseXClient.query("/cv/personal/firstName/text()").execute() + " " + baseXClient.query("/cv/personal/lastName/text()").execute());
        assertEquals("Anna Cermakova", baseXClient.query("/cv/personal/firstName/text()").execute() + " " + baseXClient.query("/cv/personal/lastName/text()").execute());

        System.out.println("\n ## Test advanced XQuery:");
        System.out.println( "Works: " + baseXClient.query(
                "for $x in //cv/works/work " +
                        "order by $x/employer " +
                        "return $x/position/text() || \" at \" || $x/employer/text() || \", \""
        ).execute());


        System.out.println("\n ## Drop the database.");
        System.out.println(baseXClient.execute("DROP DATABASE " + dbName));; // Drop the database

        System.out.println("\n ## Show existing databases:"); // Show all existing databases
        System.out.println(baseXClient.execute("LIST"));
        assertTrue(baseXClient.execute("LIST").indexOf(dbName) == -1);

    }

    @After
    public void tearDown() throws Exception {
        baseXClient.close();
    }
}