/*
 * Describes basic structure of a token.
 * 
 * Contributors: Klajdi Bodurri && Eirini Tsitsopoulou.
*/

public class Token{

    public static final int OPERATOR = 0;
    public static final int NUMBER = 1;
    public static final int ENDOFSTRING = 2;
    public static final String OPERATORS = "+-*x/()^";

    private int type;
    private String value = null;

    public Token(int newType, String newValue){
        type = newType;
        value = newValue;
    }
    
    // returns the value of the token
    public String getValue() {
        return value;
    }
    
    // Checks whether the type is a number.
    public boolean isNumber() {
        if (type == NUMBER){
            return true;
        }
        return false;
    }
    
    // Checks whether the type is an operator.
    public boolean isOperator() {
        if (type == OPERATOR){
            return true;
        }
        return false;
    }
}
