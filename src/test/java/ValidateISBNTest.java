import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ValidateISBNTest {

    private ValidateISBN validator;

    @Before
    public void setUp() {
        validator = new ValidateISBN();
    }

    @After
    public void tearDown() {
        validator = null;
    }

    @Test
    public void checkValide10DigitISBN() {
        boolean valid = validator.checkValidISBN("1861972717");
        assertTrue(valid);
    }

    @Test
    public void checkInvalid10DigitISBN() {
        boolean valid = validator.checkValidISBN("1861972716");
        assertFalse(valid);
    }

    @Test (expected = NumberFormatException.class)
    public void nineDigitISBNNotAllowed() {
        validator.checkValidISBN("186197271");
    }

    @Test (expected = NumberFormatException.class)
    public void nonNumericDigitNotAllowed() {
        boolean valid = validator.checkValidISBN("helloworld");
        assertFalse(valid);
    }

    @Test
    public void checkTenDigitISBNLastCharXIsAllowed() {
        boolean valid = validator.checkValidISBN("012000030X");
        assertTrue(valid);
    }

    @Test
    public void checkValid13DigitISBNNumber() {
        boolean valid = validator.checkValidISBN("9781853260087");
        assertTrue("First value", valid);

        valid = validator.checkValidISBN("9780553213508");
        assertTrue("Second value", valid);
    }

    @Test
    public void checkInValid13DigitISBNNumber() {
        boolean valid = validator.checkValidISBN("9781853260086");
        assertFalse(valid);
    }
}
