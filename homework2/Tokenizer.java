/*
 * Converts a string to tokens. Supports methods line getToken, peekNextToken
 * and skipToken.
 * 
 * Contributor: Klajdi Bodurri && Eirini Tsitsopoulou.
*/

import java.util.regex.*;

public class Tokenizer {
    
    String StringToToken = null;
    Token currentToken = null;
    int position;

    public Tokenizer(String newString) {
        StringToToken = newString;
        position = 0;
    }

    /*
     * Returns the operator or the number (float or integer) from the i-th position
     * of the StringToToken.
    */
    private String getStringAt(int position) {
        Pattern floatPattern = Pattern.compile("[0-9]*\\.?[0-9]*");
        Pattern operationPattern = Pattern.compile("[\\+\\-\\*x\\(\\)\\^\\/]");

        Matcher operationMatcher = operationPattern.matcher(StringToToken.substring(position, position+1));
        Matcher floatMatcher = floatPattern.matcher(StringToToken);

        // check if operation or float number
        if (operationMatcher.matches()) {
            return StringToToken.substring(position, position+1);
        }
        else if (floatMatcher.find(position)) {
            return floatMatcher.group(0);
        }
        return null;
    }
    
    // Converts the string from i-th position into token.
    private Token generateNextToken() {
        // end of string, return ENDOFSTRING token.
        if (position == StringToToken.length()) {
            currentToken = new Token(Token.ENDOFSTRING, null);
            return currentToken;
        } 

        // get next string to be tokenized.
        String nextString = getStringAt(position);
        if (nextString != null){
            position = position + nextString.length();
        }

        // convert it into token.
        if (Token.OPERATORS.contains(nextString)){
            currentToken = new Token(Token.OPERATOR, nextString);
        }else {
            currentToken = new Token(Token.NUMBER, nextString);
        }
        return currentToken;
    }

    // get the next token.
    public Token getToken() {
        Token tmpToken = null;
        
        if (currentToken == null) {
            tmpToken = generateNextToken();
            currentToken = null; // gets the token without saving it to the current.
            return tmpToken;
        }
        
        tmpToken = currentToken;
        currentToken = null;
        return tmpToken;
    }

    // peek next token.
    public Token peekToken() {
        if (currentToken == null) { // get the next token and save it to the current token.
            currentToken = generateNextToken();
        }
        return currentToken;
    }

    // skip next token.
    public void skipToken() {
        if (currentToken == null) {
            currentToken = generateNextToken();
        }
        currentToken = null;
    }
}
