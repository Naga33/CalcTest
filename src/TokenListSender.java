import java.util.ArrayList;

public class TokenListSender {
    //would be cool if this class were an observer or something
    //should be child of TokenList?

    private ArrayList<Token> subTokenListToCalculate = new ArrayList<>();
    private ArrayList<Integer> subIndexList = new ArrayList<>();
    private TokenList tokenList;
    private boolean lastExpressionToCalculate;

    public TokenListSender(String expression){//figure out best way to do this
        tokenList = TokenList.getInstance(expression);
    }


    public void createSubTokenListToCalculate(){
        //this method needs to:
        //go through token list and find brackets
        //if no brackets sends whole list

        //clear existing subTokenListToCalculate
        subTokenListToCalculate.clear();
        subIndexList.clear();

        if(tokenList.getTokenArrayList().size()>1){
            //there are still expressions to calculate

            if(tokenList.getBracketsExist()){
                //extract expression within brackets
                // TODO: remember to do check for one value
                subIndexList = createBracketsIndexList();
                ArrayList<Token> subTokenList = createBracketsTokenList(subIndexList); //with brackets removed

                subTokenListToCalculate.addAll(subTokenList);
                lastExpressionToCalculate = false;
            }
            else {
                //subTokenListToCalculate is the same as tokenlist
                //this should only be done at the end after all brackets
                //the result calculated should be the final answer

                subTokenListToCalculate.addAll(tokenList.getTokenArrayList());
                lastExpressionToCalculate = true;
            }
        }
    }

    public void updateTokenList(Token result){
        //arithmetic class can call this method after calculation
        //this method needs to update general token list with new result, which should be one token.

        //if sub list was final expression
        if(lastExpressionToCalculate){
            tokenList.getTokenArrayList().clear();
            tokenList.getTokenArrayList().add(result);
        }
        else{ //if sub list came from expression within brackets

            //update main token list: replace open bracket of sublist with result
            tokenList.getTokenArrayList().set(subIndexList.get(0),result);

            //remove following tokens until closing bracket of sublist
            int fromIndex = subIndexList.get(1);
            int toIndex = subIndexList.get(subIndexList.size()-1)+1; //plus one because sublist is exclusive
            tokenList.getTokenArrayList().subList(fromIndex, toIndex).clear();
        }
        tokenList.findBrackets();
        refreshSubTokenListToCalculate();
    }

    public void refreshSubTokenListToCalculate(){
        if (!lastExpressionToCalculate){
            createSubTokenListToCalculate();
        }
    }

    public ArrayList<Integer> createBracketsIndexList(){
        ArrayList<Integer> bracketsIndexList = new ArrayList<>();
        int outerCount = 0;
        int innerCount = 1;
        boolean innerCheck = true;

        //go through list of tokens to find brackets
        while(outerCount < tokenList.getTokenArrayList().size()){
            System.out.println("outercount = "+outerCount);
            Token currentToken = tokenList.getTokenArrayList().get(outerCount);

            if(currentToken.getValue().equals("(")){
                //add current index to sublist
                bracketsIndexList.add(outerCount);

                //check each token onwards from this point
                while (innerCheck){
                    System.out.println("innercount = "+innerCount);
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
            else{
                outerCount = outerCount + innerCount;
            }
        }
        return bracketsIndexList;
    }

    public ArrayList<Token> createBracketsTokenList(ArrayList<Integer> indexList){
        ArrayList<Token> bracketsTokenList = new ArrayList<>();

        for (Integer index: indexList
        ) {
            bracketsTokenList.add(tokenList.getTokenArrayList().get(index));
        }
        removeBracketsFromSubTokenList(bracketsTokenList);
        return bracketsTokenList;
    }

    public void removeBracketsFromSubTokenList(ArrayList<Token> tokenList){
        //we remove brackets from sub token list so calculator can compute expression
        //remove end bracket
        tokenList.remove(tokenList.size()-1);
        tokenList.remove(0);
    }

    public ArrayList<Token> getSubTokenListToCalculate(){
        return subTokenListToCalculate;
    }


}
