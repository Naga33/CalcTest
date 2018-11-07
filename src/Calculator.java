import java.util.ArrayList;

public class Calculator {

    //this class takes sub token list, calculates answer immediately and can return ONE result

    private String[] operationOrder = {"divide", "multiply", "sum", "subtract"};
    private String[] functionOrder = {"sqrt", "log", "sin", "cos"};
    private ArrayList<Token> subExpression;
    private Token resultToken;

    public Calculator(){}

    public void setSubExpression(ArrayList<Token> subTokenList){
        this.subExpression = subTokenList;
    }

    public Token getResultToken(){
        return resultToken;
    }

    public void calculateFinalResult(){

        while(subExpression.size()>=1){

            //single token can be passed e.g. for cos(3.14), 3.14 would be passed
            if(subExpression.size()==1){
                resultToken = subExpression.get(0);
                subExpression.clear();
            }
            else{
                //subExpression contains multiple tokens

                //calculate functions
                for (int i = 0; i < functionOrder.length; i++) {
                    calculateFunctions(functionOrder[i]);
                }

                //calculate operators
                for (int i = 0; i < operationOrder.length; i++) {
                    calculateOperators(operationOrder[i]);
                }
                //TODO: calculate assign

            }
        }
    }

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
        //print
        System.out.println("\n\nBefore");
        for (Token token:subExpression
        ) {
            System.out.println(token.toString());
        }

        subExpression.set(index,refreshToken);
        subExpression.remove(index+1);

        //print
        System.out.println("\n\nAfter");
        for (Token token:subExpression
        ) {
            System.out.println(token.toString());
        }
    }


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

                Double result = calculation.eval(calculation);//this can't be good practice...
                System.out.println("\n\nResult = "+result);

                resultToken = new Token(resultTokenType,result.toString());
                refreshSubExpressionAfterOperators(i,resultToken);
                i=0;
            }
        }
    }


    private void refreshSubExpressionAfterOperators(int index, Token refreshToken){
        //print
        System.out.println("\n\nBefore");
        for (Token token:subExpression
        ) {
            System.out.println(token.toString());
        }
        subExpression.set(index-1, refreshToken);
        subExpression.remove(index);
        subExpression.remove(index);

        //print
        System.out.println("\n\nAfter");
        for (Token token:subExpression
        ) {
            System.out.println(token.toString());
        }
    }

    private void calculateAssign(){

        for (int i = 1; i < subExpression.size()-1; i++) {

            Token currentToken = subExpression.get(i);
            Token previousToken = subExpression.get(i-1);
            Token nextToken = subExpression.get(i+1);

            if(currentToken.getName().equals("assign")){
                Double bindValue = Double.parseDouble(nextToken.getValue());
                String bindKey = previousToken.getValue();
                //subExpression.updateBindings(bindKey,bindValue);
                //bindings.put(bindKey,bindValue);
                refreshSubExpressionAfterOperators(i,nextToken);
            }
        }
    }


//    private void saveResultToBinding(){
//        if(tokenList.getTokenArrayList().size()==1){
//            String finalValue = tokenList.getTokenArrayList().get(0).getValue();
//            Double finalResult = Double.parseDouble(finalValue);
//            tokenList.updateBindings("_",finalResult);
//            //bindings.put("_",finalResult);
//        }//needs to be saved but won't need serialisation in their version.
//    }


}
