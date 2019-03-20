import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.lang.*;

public class ExpressionCheck {
    String lineWithSpaces = null;

    // constructor
    public ExpressionCheck(String inputString){
      lineWithSpaces = inputString;
    }

    // isValid method
    public String validString(){
      StringBuilder line = new StringBuilder(lineWithSpaces);
      // all operators: includes parentheses
      String all_operators = "+-x*/^.()";
      String operators = "+-x*/^.";

      int open = 0, close = -1, done_par = 0;

      // FASE 1: Getting rid of white space characters
      // and check for invalid characters
      for (int i = 0; i < line.length(); i++){
        char c = line.charAt(i);
        // check if there are invalid characters (anything other than numbers, all_operators and white spaces)
        if (!Character.isDigit(c) && (all_operators.indexOf(c) < 0) && !Character.isWhitespace(c)) {
          System.out.println("invalid expression");
          return null;
        }
        // get rid of white spaces (will be needed for later)
        if (Character.isWhitespace(c)){
          line.deleteCharAt(i);
          i--;
        }
      }

      // FASE 2: check for wrong placement of parentheses or all_operators
      for (int i = 0; i < line.length(); i++){
        char c = line.charAt(i);

        if (i!=0 && i!=line.length()-1){
          char cPrev = line.charAt(i-1);
          char cNext = line.charAt(i+1);

          // if char is an operator, check that there are no other
          // operators next to it
          if (operators.indexOf(c) >= 0){
            if (operators.indexOf(cPrev) >= 0){
              System.out.println("invalid expression");
              return null;
            }
            if (operators.indexOf(cNext) >= 0){
              System.out.println("invalid expression");
              return null;
            }
          }
        }
        // don't begin and don't end with an operator (other than parentheses)
        else {
          if (operators.indexOf(c) >= 0){
            System.out.println("invalid expression");
            return null;
          }
        }

        // counting parentheses
        if (c == '('){
          // if there is no operator left of left parentheses
          // e.g. valid: ...+(... or ...((...
          // invalid:   ...5(...
          if (i!=0 && all_operators.indexOf(line.charAt(i-1)) < 0 ){
            System.out.println("invalid expression");
            return null;
          }

          // if there is operator right of left parentheses
          // e.g. valid: ...(5... or ...((...
          // invalid:   ...(+...
          if (i!=line.length()-1 && operators.indexOf(line.charAt(i+1)) >= 0 ){
            System.out.println("invalid expression");
            return null;
          }

          if (done_par == 1) {
            open = 0;
            close = -1;
          }
          open++;
        }

        if (c == ')'){
          // if there is no operator right of right parentheses
          // e.g. valid: ...)+... or ...))...
          // invalid:   ...)9...
          if (i!=line.length()-1 && all_operators.indexOf(line.charAt(i+1)) < 0 ){
            System.out.println("invalid expression");
            return null;
          }

          if (i!=0 && operators.indexOf(line.charAt(i-1)) >= 0 ){
            System.out.println("invalid expression");
            return null;
          }

          if (close == -1){
            close = open;
            if (close == 1)
              done_par = 1;
          }
          else {
            if (close == 1)
              done_par = 1;
          }
          close--;
        }
        // all open parentheses have closed, reset open,close counters
        // in case more parentheses open
      }

      if (open == 0 && close == -1){
        //System.out.println("valid expression");
        return line.toString();
      }

      else if (close != 0){
        System.out.println("invalid expression: wrong parentheses");
        return null;
      }
      else {
        //System.out.println("valid expression");
        return line.toString();
      }

    }
}
