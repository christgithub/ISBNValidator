public class ValidateISBN {

    public static final int LONG_ISBN_LENGTH = 13;
    public static final int SHORT_ISBN_LENGTH = 10;

    public boolean checkValidISBN(String isbn) {

        int sum = 0;

        if (isbn.length() == LONG_ISBN_LENGTH) {
            return isValidLongDigitISBN(isbn, sum);
        } else if(isbn.length() == SHORT_ISBN_LENGTH) {
            return isValidShortISBN(isbn, sum);
        } else throw new NumberFormatException("ISBN numbers must be 10 or 13 digits long");
    }

    private boolean isValidShortISBN(String isbn, int sum) {
        for (int i = 0; i < SHORT_ISBN_LENGTH; i++) {
            char currentChar = isbn.charAt(i);

            if (!Character.isDigit(currentChar)) {
                if(i == 9 && currentChar == 'X') {
                    sum+=10;
                }
                else throw new NumberFormatException("ISBN numbers must contain numerical values only");
            } else {
                sum += Character.getNumericValue(currentChar) * (SHORT_ISBN_LENGTH - i);
            }
        }
        return sum%11==0;
    }

    private boolean isValidLongDigitISBN(String isbn, int sum) {
        for (int i = 0; i < LONG_ISBN_LENGTH; i++) {
            int currentCharNum = Character.getNumericValue(isbn.charAt(i));
            if (i % 2 == 0)
                sum += currentCharNum * 1;
            else sum += currentCharNum * 3;
        }
        return sum%10 == 0;
    }
}
