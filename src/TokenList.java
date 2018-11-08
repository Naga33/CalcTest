import java.util.ArrayList;
import java.util.regex.Matcher;

/**
 * This class creates an ArrayList of Token objects based on a valid string input
 * (see formal grammar in Calc class for valid inputs).
 * @see Calc
 * This class also contains a boolean indicator stating whether brackets are
 * present in the ArrayList.
 * This class is singleton as only one TokenList need ever be made from input string.
 *
 * @author courtenay
 * @version 1.8
 * @since 1.8
 */
public class TokenList {

    private static TokenList uniqueInstance;
    private String expression;
    private RegexList regexList = RegexList.getInstance();
    private ArrayList<Token> tokenArrayList = new ArrayList<>();

    private TokenList(String expression){
        this.expression = expression;
        createTokenList();
    }

    //singleton
    public static synchronized TokenList getInstance(String expression){
        if(uniqueInstance==null){
            uniqueInstance = new TokenList(expression);
        }
        return uniqueInstance;
    }

    /**
     * Creates list of tokens from input string expression.
     * <p>
     * The input string is checked for corresponding Regex objects and if present,
     * the matching substring is extracted from the input string and added to the
     * token ArrayList. The substring is removed from the input string and the
     * search continues until input is an empty string.
     * </p>
     */
    private void createTokenList(){
        while(!expression.equals("")){

            for (Regex currentRegex:regexList.getRegexArrayList()
            ) {
                //compare regex pattern to expression
                Matcher matcher = currentRegex.getPattern().matcher(expression);

                if(matcher.find()){
                    //extract substring from expression
                    String exprExtract = matcher.group(0);

                    //add substring and token type to token list
                    tokenArrayList.add(new Token(currentRegex.getName(), exprExtract));

                    //remove substring from expression
                    expression = expression.replaceFirst(currentRegex.getValue(),"");
                }
                if(expression.equals("")){
                    break;
                }
            }
        }
    }

    /**
     * Returns true if token list contains any brackets.
     *
     * @return boolean stating whether token list contains a parenthesis character.
     */
    public boolean getBracketsExist(){
        for (Token token:tokenArrayList
        ) {
            if (token.isTokenBracket()){
                return true;
            }
        }
        return false;
    }

    public ArrayList<Token> getTokenArrayList(){
        return tokenArrayList;
    }
}
