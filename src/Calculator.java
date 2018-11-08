import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This class takes a sub-expression in the form of a Token ArrayList and computes the result.
 * The class also maps any ids in the expression to their values, and saves any new ids.
 *
 * @author courtenay
 * @version 1.8
 * @since 1.8
 */
public class Calculator {
    /**
     * This class relies too heavily on loops, which would not be sustainable
     * for large inputs/sub-expressions.
     */

    private String[] operationOrder = {"divide", "multiply", "sum", "subtract"};
    private String[] functionOrder = {"sqrt", "log", "sin", "cos"};
    private ArrayList<Token> subExpression;
    private Token resultToken;
    private Map<String, Double> bindings = new HashMap<>();

    public Calculator(){}

    /**
     * Sets the sub-expression for the calculator to compute.
     * <p>
     *     This should be the first calculator method called, otherwise
     *     the calculator will have no expression to compute.
     * </p>
     *
     * @param subTokenList of a sub-expression.
     */
    public void setSubExpression(ArrayList<Token> subTokenList){
        this.subExpression = subTokenList;
    }

    /**
     * Calculates the provided sub-expression.
     * <p>
     *     First this method checks whether sub-expression passed is a
     *     single value, in which case value set as result.
     *     Then any ids in the expression are replaced with their values,
     *     functions are calculated, then operators, then assign statements.
     * </p>
     */
    public void calculateFinalResult(){

        while(subExpression.size()>=1){
            //sub-expression still to be calculated

            if(subExpression.size()==1){
                // single token has been passed
                // e.g. for cos(3.14), 3.14 would be passed
                resultToken = subExpression.get(0);
                subExpression.clear();
            }
            else{
                //subExpression contains multiple tokens

                //first replace any ids from bindings map
                replaceIds();

                //calculate functions
                for (int i = 0; i < functionOrder.length; i++) {
                    calculateFunctions(functionOrder[i]);
                }

                //calculate operators
                for (int i = 0; i < operationOrder.length; i++) {
                    calculateOperators(operationOrder[i]);
                }

                calculateAssign();
            }
        }
    }

    /**
     * Replaces ids in sub-expression with values from map.
     */
    private void replaceIds(){
        for (Token token:subExpression
        ) {
            if(token.getName().equals("id")){
                //check binding keys for token value
                if(bindings.containsKey(token.getValue())){
                    token.setValue(bindings.get(token.getValue()).toString());
                }
            }
        }
    }

    /**
     * Calculates any functions present in sub-expression.
     * <p>
     *     Calculates any functions present in the sub-expression
     *     using the Math library and refreshes the sub-expression
     *     with the result value.
     * </p>
     *
     * @param function stating type of calculation (log, sqrt, cos, or sin).
     */
    private void calculateFunctions(String function){
        String resultTokenType = "num";
        Double resultDouble = null;
        Token resultToken;
        Double nextTokenDouble;
        Token currentToken;
        Token nextToken;

        for (int i = 0; i < subExpression.size()-1; i++) { //function will never be the last token
            currentToken = subExpression.get(i);
            nextToken = subExpression.get(i+1);

            if(currentToken.getName().equals(function)){

                switch (function){
                    case "sqrt":
                        resultDouble = Math.sqrt(Double.parseDouble(nextToken.getValue()));
                        break;

                    case "log":
                        resultDouble = Math.log(Double.parseDouble(nextToken.getValue()));
                        break;

                    case "cos":
                        nextTokenDouble = Double.parseDouble(nextToken.getValue());
                        nextTokenDouble = Math.toRadians(nextTokenDouble);
                        resultDouble = Math.cos(nextTokenDouble);
                        break;

                    case "sin":
                        nextTokenDouble = Double.parseDouble(nextToken.getValue());
                        nextTokenDouble = Math.toRadians(nextTokenDouble);
                        resultDouble = Math.sin(nextTokenDouble);
                        break;

                }
                resultToken = new Token(resultTokenType,resultDouble.toString());
                refreshSubExpressionAfterFunctions(i,resultToken);
                i=0;
            }
        }
    }


    private void refreshSubExpressionAfterFunctions(int index, Token refreshToken){
        subExpression.set(index,refreshToken);
        subExpression.remove(index+1);
    }


    /**
     * Calculates any operators present in the sub-expression
     * <p>
     *     Calculates any operators in the sub-expression by finding the
     *     operator token (if any), finding the number tokens either side of
     *     the operator, constructing a CalcTree, and calling the eval function.
     *     The sub-expression is then refreshed with the result.
     * </p>
     *
     * @param operation stating the type of calculation (/,*,+,-).
     */
    private void calculateOperators(String operation){
        String resultTokenType = "num";
        Token resultToken;
        CalcTree calculation;

        for (int i = 1; i < subExpression.size()-1; i++) { //the operator will never be the first or last token

            Token currentToken = subExpression.get(i);
            Token previousToken = subExpression.get(i-1);
            Token nextToken = subExpression.get(i+1);

            if(currentToken.getName().equals(operation)){

                calculation = new CalcTree(currentToken.getValue(),
                        new CalcTree(previousToken.getValue(), null, null),
                        new CalcTree(nextToken.getValue(), null, null));

                Double result = calculation.eval(calculation);
                resultToken = new Token(resultTokenType,result.toString());
                refreshSubExpressionAfterOperators(i,resultToken);
                i=0;
            }
        }
    }

    private void refreshSubExpressionAfterOperators(int index, Token refreshToken){
        subExpression.set(index-1, refreshToken);
        subExpression.remove(index);
        subExpression.remove(index);
    }

    /**
     * Calculates a sub-expression containing an 'assign' token ("=").
     * <p>
     *     If this method is called there should only be three tokens
     *     remaining in the sub-expression. The number value is set as the
     *     result and is also saved to bindings map, with corresponding
     *     id key.
     * </p>
     */
    private void calculateAssign(){

        for (int i = 1; i < subExpression.size()-1; i++) {

            Token currentToken = subExpression.get(i);
            Token previousToken = subExpression.get(i-1);
            Token nextToken = subExpression.get(i+1);

            if(currentToken.getName().equals("assign")){
                Double bindValue = Double.parseDouble(nextToken.getValue());
                String bindKey = previousToken.getValue();
                bindings.put(bindKey,bindValue);
                refreshSubExpressionAfterAssign(i,nextToken);
            }
        }
    }

    private void refreshSubExpressionAfterAssign(int index, Token refreshToken){
        subExpression.set(index-1, refreshToken);
        subExpression.remove(index);
        subExpression.remove(index);
    }

    public Token getResultToken(){
        return resultToken;
    }

    public Map<String,Double> getBindings(){
        return bindings;
    }
}
