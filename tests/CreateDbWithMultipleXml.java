import org.basex.core.BaseXException;
import org.basex.core.Context;
import org.basex.core.cmd.*;

public final class CreateDbWithMultipleXml {

    public static void main(final String[] args) throws BaseXException {

        /** Database context. */
        Context context = new Context();

        System.out.println("=== CreateDbWithMultipleXml ===");

        System.out.println("\n ## Create a database.");
        new CreateDB("XmlCollection").execute(context);
        new Add("", "src/test/sample-anna.xml").execute(context);
        new Add("", "src/test/sample-bara.xml").execute(context);
        new Add("", "src/test/sample-carlos.xml").execute(context);
        new Optimize().execute(context);

        System.out.println("\n ## Show database documents:");
        System.out.println(new XQuery(
                "for $doc in collection('XmlCollection')" +
                "return <document path='{ base-uri($doc) }'/>"
        ).execute(context));


        System.out.println("\n ## Delete Carlos:");
        new Delete("sample-carlos.xml").execute(context);

        System.out.println("\n ## Show database documents: (should NOT contain Carlos)");
        System.out.println(new XQuery(
                "for $doc in collection('XmlCollection')" +
                        "return <document path='{ base-uri($doc) }'/>"
        ).execute(context));

        System.out.println("\n ## Drop the database.");
        new DropDB("XmlCollection").execute(context); // Drop the database

        context.close(); // Close the database context
    }

}