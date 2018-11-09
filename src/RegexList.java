import java.util.ArrayList;

/**
 * This class creates a list of Regex objects according to the formal grammar
 * provided in Calc class, and stores them in an ArrayList.
 * @see Calc
 * This class is singleton as only one RegexList need ever be made in the program.
 *
 * @author courtenay
 * @version 1.8
 * @since 1.8
 */
public class RegexList {

    private static RegexList uniqueInstance;
    private Regex cos = new Regex("cos", "\\Acos");
    private Regex sin = new Regex("sin", "\\Asin");
    private Regex log = new Regex("log", "\\Alog");
    private Regex sqrt = new Regex("sqrt", "\\Asqrt");
    private Regex num = new Regex("num", "\\A[\\.\\d]+(\\d+)*"); //ISSUE: also matches "." and "0.0.0.0" etc.
    private Regex openParen = new Regex("openParen", "\\A\\(");
    private Regex closeParen = new Regex("closeParen", "\\A\\)");
    private Regex assign = new Regex("assign", "\\A=");
    private Regex id = new Regex("id", "\\A[a-zA-Z_]+[a-zA-Z0-9_]*");
    private Regex sum = new Regex("sum", "\\A\\+");
    private Regex subtract = new Regex("subtract", "\\A-");
    private Regex multiply = new Regex("multiply", "\\A\\*");
    private Regex divide = new Regex("divide", "\\A\\/");
    private ArrayList<Regex> regexArrayList = new ArrayList<>();
    //TODO: allow for -, a term can be negative


    private RegexList(){
        addRegexToRegexList();
    }

    //singleton
    public static synchronized RegexList getInstance(){
        if(uniqueInstance==null){
            uniqueInstance = new RegexList();
        }
        return uniqueInstance;
    }

    private void addRegexToRegexList(){
        //order in which these Regex objects added is important
        regexArrayList.add(openParen);
        regexArrayList.add(assign);
        regexArrayList.add(cos);
        regexArrayList.add(sin);
        regexArrayList.add(log);
        regexArrayList.add(sqrt);
        regexArrayList.add(num);
        regexArrayList.add(id);
        regexArrayList.add(sum);
        regexArrayList.add(subtract);
        regexArrayList.add(multiply);
        regexArrayList.add(divide);
        regexArrayList.add(closeParen);
    }

    public ArrayList<Regex> getRegexArrayList(){
        return this.regexArrayList;
    }

}


