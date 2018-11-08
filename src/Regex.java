import java.util.regex.Pattern;

/**
 * This class associates a given string (name) with a corresponding regex string (value)
 * that will match the given string. The class also compiles the regex string to a
 * regex Pattern (pattern).
 *
 * @author courtenay
 * @version 1.8
 * @since 1.8
 */
public class Regex {

    private String name;
    private String value;
    private Pattern pattern;

    public Regex(String aName, String aValue){
        name = aName;
        value = aValue;
        pattern = Pattern.compile(aValue);
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public Pattern getPattern(){
        return pattern;
    }

    @Override
    public String toString() {
        return name+": "+value;
    }
}
