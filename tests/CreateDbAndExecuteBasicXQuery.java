import org.basex.core.*;
import org.basex.core.Context;
import org.basex.core.cmd.*;
import org.basex.core.cmd.Close;
import org.basex.core.cmd.CreateDB;
import org.basex.core.cmd.CreateIndex;
import org.basex.core.cmd.DropDB;
import org.basex.core.cmd.DropIndex;
import org.basex.core.cmd.InfoDB;
import org.basex.core.cmd.List;
import org.basex.core.cmd.Open;

public final class CreateDbAndExecuteBasicXQuery {

    /**
     * Runs the example code.
     * @param args (ignored) command-line arguments
     * @throws BaseXException if a database command fails
     */
    public static void main(final String[] args) throws BaseXException {
        /** Database context. */
        Context context = new Context();

        System.out.println("=== CreateDbAndExecuteBasicXQuery ===");

        System.out.println("\n ## Create a database.");
        new CreateDB("Nedele", "assets/xml/anna.xml").execute(context);

        System.out.println("\n ## Show existing databases: (should contain XmlDbExample)");
        System.out.print(new List().execute(context));

        System.out.println("\n ## Close and reopen database.");
        new Close().execute(context);
        new Open("Nedele").execute(context);

        System.out.println("\n ## Create a full-text index.");
        new CreateIndex("fulltext").execute(context); // Additionally create a full-text index

        System.out.println("\n ## Show database information:");
        System.out.print(new InfoDB().execute(context));

        System.out.println("\n ## Test basic XQuery:");
        System.out.println( "Name: " + new XQuery("/cv/personal/firstName/text()").execute(context) + " " + new XQuery("/cv/personal/lastName/text()").execute(context));

        System.out.println("\n ## Test advanced XQuery:");
        System.out.println( "Works: " + new XQuery(
                "for $x in //cv/works/work " +
                "order by $x/employer " +
                "return $x/position/text() || \" at \" || $x/employer/text() || \", \""
        ).execute(context));


        System.out.println("\n ## Drop indexes.");
        new DropIndex("text").execute(context);
        new DropIndex("attribute").execute(context);
        new DropIndex("fulltext").execute(context);

        System.out.println("\n ## Drop the database.");
        // new DropDB("XmlDbExample").execute(context); // Drop the database

        System.out.println("\n ## Show existing databases:  (should NOT contain XmlDbExample)"); // Show all existing databases
        System.out.print(new List().execute(context));

        context.close(); // Close the database context
    }

}