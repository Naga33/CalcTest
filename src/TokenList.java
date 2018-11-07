import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

public class TokenList {

    private static TokenList uniqueInstance;
    private String expression;
    private RegexList regexList = RegexList.getInstance();
    private ArrayList<Token> tokenArrayList = new ArrayList<>();
    private Map<String,Double> bindings = new HashMap<>();
    private boolean bracketsExist;


    private TokenList(String expression){
        this.expression = expression;
        createTokenArrayList();
    }

    //singleton
    public static synchronized TokenList getInstance(String expression){
        if(uniqueInstance==null){
            uniqueInstance = new TokenList(expression);
        }
        return uniqueInstance;
    }

    private void createTokenArrayList(){
        createInitialTokenList();
        replaceIds();
        printTokenArrayList();
        findBrackets();
        printDoBracketsExist();
    }


    private void createInitialTokenList(){
        while(!expression.equals("")){

            for (Regex currentRegex:regexList.getRegexArrayList()
            ) {
                //compile regex string and compare to expression
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
        //removeNumsWithoutValue();
    }

    private void removeNumsWithoutValue(){

        ArrayList<Token> removeTokenList = new ArrayList<>();

        //add valueless tokens to removeTokenList
        for (Token token:tokenArrayList
        ) {
            if(token.getValue().equals("")){
                removeTokenList.add(token);
            }
        }

        //remove valueless tokens from tokenArrayList
        for (Token token:removeTokenList
        ) {
            tokenArrayList.remove(token);
        }

    }

    private void replaceIds(){
        for (Token token:tokenArrayList
        ) {
            if(token.getName().equals("id")){
                //check binding keys for token value
                if(bindings.containsKey(token.getValue())){
                    token.setValue(bindings.get(token.getValue()).toString());
                }
            }
        }
    }

    public void findBrackets(){
        for (Token token:tokenArrayList
        ) {
            if (token.isTokenBracket()){
                bracketsExist = true;
                break;
            }
            else{
                bracketsExist = false;
            }
        }
    }

    private void printDoBracketsExist(){
        System.out.println("Brackets: "+bracketsExist);
    }

    public boolean getBracketsExist(){
        return this.bracketsExist;
    }

    public ArrayList<Token> getTokenArrayList(){
        return tokenArrayList;
    }
    public String getExpression(){
        return expression;
    }

    //sholuld this method be in this class?
    public void updateBindings(String key, Double value){
        this.bindings.put(key,value);
        printBindings();
    }

    private void printBindings() {
        System.out.println("\n\nPrinting bindings:");
        for (String bindingKey:bindings.keySet()
        ) {
            System.out.println(bindingKey+" : "+bindings.get(bindingKey));
        }
    }

    public void printTokenArrayList(){
        System.out.println("\n\nPrinting Token arraylist:");
        for (Token token:tokenArrayList
        ) {
            System.out.println(token.toString());
        }
    }

    public void printFinalResult(){
        if (tokenArrayList.size() == 1){
            //only one Token left in list: the final result!
            System.out.println("\nFinal Result!\n"+tokenArrayList.get(0).toString());
        }
    }

//    public void printSubTokenIndexListBrackets(){
//        //print
//        System.out.println("\n\nSubTokenList: ");
//        for (Integer index: subTokenIndexListBrackets
//        ) {
//            System.out.println("index: "+ index + "\nToken: "+ tokenArrayList.get(index));
//        }
//    }
//
//    public void printSubTokenArrayListBrackets(){
//        System.out.println("\n\nPrinting Sub Token arraylist:");
//        for (Token token: subTokenListBrackets
//        ) {
//            System.out.println(token.toString());
//        }
//    }
//
//    public void printTokenListToCalculate(){
//        System.out.println("\n\nPrinting tokenListToCalculate:");
//        for (Token token:tokenListToCalculate
//        ) {
//            System.out.println(token.toString());
//        }
//    }


}
