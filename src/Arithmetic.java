import java.util.ArrayList;

public class Arithmetic {
    //purpose of this class is to take subtoken from tokenlistsender and calculator from calculator and get result
    //also to save result to binding

    private TokenList tokenList;
    private TokenListSender listSender;
    private ArrayList<Token> subExpression;
    private Calculator calculator;
    private Token result;

    public Arithmetic(String expr){
        tokenList = TokenList.getInstance(expr);
        listSender = new TokenListSender(expr); //creates sub token list immediately
        listSender.createSubTokenListToCalculate();
        calculator = new Calculator(); //calculator does not do anything unless method is called
    }

    public void calculateFinalResult(){

        while(tokenList.getTokenArrayList().size()>1){
            calculator.setSubExpression(listSender.getSubTokenListToCalculate());
            calculator.calculateFinalResult();
            result = calculator.getResultToken();
            listSender.updateTokenList(result);
        }
    }

    public Token getResult(){
        return result;
    }

//TODO: assign, -terms, tests, docstrings, clean up code and make consistent




//    private void saveResultToBinding(){
//        if(tokenList.getTokenArrayList().size()==1){
//            String finalValue = tokenList.getTokenArrayList().get(0).getValue();
//            Double finalResult = Double.parseDouble(finalValue);
//            tokenList.updateBindings("_",finalResult);
//            //bindings.put("_",finalResult);
//        }//needs to be saved but won't need serialisation in their version.
//    }
}
