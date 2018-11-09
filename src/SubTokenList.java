import java.util.ArrayList;

/**
 * This class creates a sublist of tokens from the main token list.
 * The sublist is an expression that the Calculator class can compute.
 * For example, the Calculator class cannot compute a result from "(4+5-1)/2",
 * but rather "4+5-1", and later, "8/2".
 * The main purpose of this class is to handle brackets within an expression.
 *
 * @author courtenay
 * @version 1.8
 * @since 1.8
 */
public class SubTokenList {
    /**
     * It might be cool if this class could observe the main token list,
     * so the sub-lists could be regenerated automatically.
     */

    private ArrayList<Token> subTokenListToCalculate = new ArrayList<>();
    private ArrayList<Integer> subIndexList = new ArrayList<>();
    private TokenList tokenList = TokenList.getInstance();
    private boolean lastExpressionToCalculate;

    /**
     * Creates sublist of tokens, able to be calculated by Calculator class.
     * <p>
     *      Current sub-lists (tokens and token indexes) are cleared, then
     *      main token list checked for presence of brackets. If brackets exist,
     *      sub token list and equivalent sub index list are extracted. If no
     *      brackets exist then the sublist created is the same as the main token list,
     *      which is the last expression that requires calculation.
     * </p>
     */
    public void createSubTokenListToCalculate(){

        //clear existing sub-lists
        subTokenListToCalculate.clear();
        subIndexList.clear();

        if(tokenList.getTokenArrayList().size()>1){
            //there are still expressions to calculate

            if(tokenList.getBracketsExist()){
                //extract expression within brackets
                subIndexList = createBracketsIndexList();

                ArrayList<Token> subTokenList = createBracketsTokenList(subIndexList); //with brackets removed
                subTokenListToCalculate.addAll(subTokenList);

                lastExpressionToCalculate = false;
            }
            else {
                //subTokenListToCalculate is the same as main token list
                //this should only be done at the end after all expressions within brackets
                //the result calculated should be the final answer
                subTokenListToCalculate.addAll(tokenList.getTokenArrayList());
                lastExpressionToCalculate = true;
            }
        }
    }

    /**
     * Creates ArrayList of indexes from sub-expression.
     * <p>
     *      The indexes of the tokens that are being extracted need to be recorded
     *      in order to insert the result token (after calculation of the sub-expression)
     *      back into the main token list at the correct position.
     *      The main token list is searched for the first open parenthesis token.
     *      This token index is saved to the ArrayList. Following tokens are saved until
     *      a closing parenthesis token is found (inclusive).
     *      If, after the first open parenthesis token is found, another open
     *      parenthesis token is found before a closing parenthesis token,
     *      the ArrayList is cleared, the inner open parenthesis token is saved,
     *      and the search restarts from that point until first closing parenthesis token
     *      is found.
     *      In this way, the inner-most nested bracket expression is extracted.
     * </p>
     *
     * @return ArrayList of indexes for inner-most expression within brackets.
     */
    public ArrayList<Integer> createBracketsIndexList(){
        ArrayList<Integer> bracketsIndexList = new ArrayList<>();
        int outerCount = 0;
        int innerCount = 1;
        boolean innerCheck = true;

        //go through list of tokens to find brackets
        while(outerCount < tokenList.getTokenArrayList().size()){
            Token currentToken = tokenList.getTokenArrayList().get(outerCount);

            if(currentToken.getValue().equals("(")){
                //add current index to sublist
                bracketsIndexList.add(outerCount);

                //check each token onwards from this point
                while (innerCheck){
                    Token nextToken = tokenList.getTokenArrayList().get(innerCount);

                    if(nextToken.getValue().equals("(")){
                        //clear bracket index list and restart
                        bracketsIndexList.clear();
                        bracketsIndexList.add(innerCount);
                        innerCount++;
                    }
                    if(nextToken.getValue().equals(")")){
                        //add to subTokenIndexListBrackets and finish searching
                        bracketsIndexList.add(innerCount);
                        innerCheck = false;
                    }
                    else{
                        bracketsIndexList.add(innerCount);
                        innerCount++;
                    }
                }
            }
            if(!innerCheck){
                break;
            }
            outerCount = outerCount + innerCount;

        }
        return bracketsIndexList;
    }

    /**
     * Creates ArrayList of tokens from sub-expression.
     * <p>
     *     The tokens are extracted from the the main token list at the indexes
     *     provided by the index list provided.
     * </p>
     *
     * @param indexList ArrayList of Integers containing sub-expression indexes.
     * @return ArrayList of Tokens that make the sub-expression to be calculated.
     */
    public ArrayList<Token> createBracketsTokenList(ArrayList<Integer> indexList){
        ArrayList<Token> bracketsTokenList = new ArrayList<>();

        for (Integer index: indexList
        ) {
            bracketsTokenList.add(tokenList.getTokenArrayList().get(index));
        }
        removeBracketsFromSubTokenList(bracketsTokenList);
        return bracketsTokenList;
    }

    /**
     * Removes brackets from token sub-expression list.
     *
     * @param tokenList sub-expression still containing brackets.
     */
    public void removeBracketsFromSubTokenList(ArrayList<Token> tokenList){
        //we remove brackets from sub token list so calculator can compute expression
        //remove end bracket first
        tokenList.remove(tokenList.size()-1);
        tokenList.remove(0);
    }

    public ArrayList<Token> getSubTokenListToCalculate(){
        return subTokenListToCalculate;
    }

    /**
     * Updates the main token list with calculation result and refreshes sub-expression.
     *
     * @param result token after calculation of a sub-expression.
     */
    public void updateTokenLists(Token result){

        refreshTokenList(result);
        refreshSubTokenListToCalculate();
    }

    /**
     * Updates the main token list with calculation result.
     *
     * @param result token after calculation of a sub-expression.
     */
    public void refreshTokenList(Token result){

        //if sub list was final expression
        if(lastExpressionToCalculate){
            tokenList.getTokenArrayList().clear();
            tokenList.getTokenArrayList().add(result);
        }
        else{
            //if sub list came from expression within brackets:

            //update main token list: replace open bracket of sublist with result
            tokenList.getTokenArrayList().set(subIndexList.get(0),result);

            //remove following tokens until closing bracket of sublist
            int fromIndex = subIndexList.get(1);
            int toIndex = subIndexList.get(subIndexList.size()-1)+1; //plus one because sublist is exclusive
            tokenList.getTokenArrayList().subList(fromIndex, toIndex).clear();
        }
    }

    public void refreshSubTokenListToCalculate(){
        if (!lastExpressionToCalculate){
            createSubTokenListToCalculate();
        }
    }
}
