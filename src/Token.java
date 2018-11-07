public class Token {
    private String name;
    private String value;

    public Token(String aName, String aValue){

        name = aName;
        value = aValue;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return name+": "+value;
    }

    public boolean isTokenBracket(){
        return value.equals("(") || value.equals(")");
    }
}
