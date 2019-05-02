import org.junit.Assert;
import org.junit.Test;

import java.awt.print.Book;

import static org.junit.Assert.fail;

public class StockManagementTest {
    @Test
    public void testCanGetACorrectLocatorCode() {

        ExternalISBNDataService externalWebServiceStub = new ExternalISBNDataService() {
            @Override
            public Book lookup(String isbn) {
                return new Book(isbn, "Of mice of men", "J Steinbeck");
            }
        };

        ExternalISBNDataService externalDatabaseServiceStub = new ExternalISBNDataService() {
            @Override
            public Book lookup(String isbn) {
                return null;
            }
        };

        String isbn = "0553213504";
        StockManager stockManager = new StockManager();
        stockManager.setWebService(externalWebServiceStub);
        stockManager.setDatabaseService(externalDatabaseServiceStub);
        String locatorCode = stockManager.getLocatorCode(isbn);
        Assert.assertEquals("3504J4", locatorCode);
    }

    @Test
    public void databaseIsUsedIfDataIsPresent() {
        //ExternalISBNDataService databaseService = mock(ExternalISBNDataService.class);
        fail("Not implemented");
    }

    @Test
    public void webServiceIsUsedIfDataIsNotPresent() {
        fail("Not implemented");
    }

}
