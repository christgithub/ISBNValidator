import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class StockManagementTest {

    private String isbn = "0553213504";
    ExternalISBNDataService databaseService;
    ExternalISBNDataService webService;
    private StockManager stockManager;

    @Before
    public void setUp() {
        databaseService = mock(ExternalISBNDataService.class);
        webService = mock(ExternalISBNDataService.class);
        stockManager = new StockManager();
    }

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

        StockManager stockManager = new StockManager();
        stockManager.setWebService(externalWebServiceStub);
        stockManager.setDatabaseService(externalDatabaseServiceStub);
        String locatorCode = stockManager.getLocatorCode(isbn);
        Assert.assertEquals("3504J4", locatorCode);
    }

    @Test
    public void databaseIsUsedIfDataIsPresent() {

        when(databaseService.lookup(isbn)).thenReturn(new Book(isbn, "abc", "def"));

        stockManager.setWebService(webService);
        stockManager.setDatabaseService(databaseService);
        stockManager.getLocatorCode(isbn);

        verify(databaseService, times(1)).lookup(isbn);
        verify(webService, times(0)).lookup(anyString());
    }

    @Test
    public void webServiceIsUsedIfDataIsNotPresent() {

        when(databaseService.lookup(isbn)).thenReturn(null);
        when(webService.lookup(isbn)).thenReturn(new Book(isbn, "abc", "abc"));

        stockManager.setWebService(webService);
        stockManager.setDatabaseService(databaseService);
        stockManager.getLocatorCode(isbn);

        verify(databaseService, times(1)).lookup(isbn);
        verify(webService, times(1)).lookup(isbn);
    }

}
