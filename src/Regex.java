import java.util.regex.Pattern;

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
