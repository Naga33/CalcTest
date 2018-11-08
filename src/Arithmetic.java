import java.util.Map;

/**
 * This class takes sub-expressions from the SubTokenList class and
 * gives them to the Calculator class to compute. This class then stores the final
 * result of the whole expression and saves the final result to the bindings map
 * under the key "_".
 *
 * @author courtenay
 * @version 1.8
 * @since 1.8
 */
public class Arithmetic {

    private TokenList tokenList;
    private SubTokenList subTokenList;
    private Calculator calculator;
    private Token result;
    private Map<String,Double> bindings;

    public Arithmetic(String expr){
        tokenList = TokenList.getInstance(expr);
        subTokenList = new SubTokenList(expr);
        subTokenList.createSubTokenListToCalculate();
        calculator = new Calculator();
        bindings = calculator.getBindings();
    }

    /**
     * Calculates final result of whole expression.
     * <p>
     *     This method gets a sub-expression from the SubTokenList class
     *     and gives it to the calculator. Then any bindings set by the calculator
     *     are updated. The result of the sub-expression is retrieved from the
     *     calculator and the result is given to the SubTokenList to in turn
     *     update the main token list. This process repeats until the main token
     *     list only contains one value, which is the final result.
     *     The final result is saved to the bindings map.
     * </p>
     */
    public void calculateFinalResult(){

        while(tokenList.getTokenArrayList().size()>1){
            //there are still expressions to calculate

            calculator.setSubExpression(subTokenList.getSubTokenListToCalculate());
            calculator.calculateFinalResult();

            bindings = calculator.getBindings();//update bindings from calculator class, cool if this were observer
            result = calculator.getResultToken();
            subTokenList.updateTokenLists(result);
        }
        saveFinalResultToBinding();
        tokenList.clearTokenArrayList();
    }

    public Token getResult(){
        return result;
    }

    private void saveFinalResultToBinding(){
        Double finalResult = Double.parseDouble(result.getValue());
        bindings.put("_",finalResult);
    }

    public Map<String,Double> getBindings(){
        return bindings;
    }
}
