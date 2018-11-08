/**
 * This class associates a string (value) with that string's chosen type (name).
 * For example, a string with a value of "5.0" could have a token value of "5.0"
 * and a token name of "number".
 *
 * @author courtenay
 * @version 1.8
 * @since 1.8
 */
public class Token {

    private String name;
    private String value;

    public Token(String name, String value){
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return name+": "+value;
    }

    /**
     * Returns true if token refers to an open or closed parenthesis character.
     *
     * @return boolean stating whether token contains a parenthesis character.
     */
    public boolean isTokenBracket(){
        return value.equals("(") || value.equals(")");
    }
}
