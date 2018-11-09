/**
 * This class creates a tree structure that can recursively calculate
 * simple operator calculations (+,-,/,*).
 *
 * @author courtenay
 * @version 1.8
 * @since 1.8
 */
public class CalcTree {
    /**
     * I would have liked to have had a more impressive tree structure,
     * however other implementations I wrote could not handle
     * brackets, or I had trouble writing to the tree (e.g. top-down,
     * bottom-up, more than two branches, etc.).
     */

    private String value;
    private CalcTree left;
    private CalcTree right;

    public CalcTree(String value, CalcTree left, CalcTree right){
        this.value = value;
        this.left = left;
        this.right = right;
    }

    public CalcTree(String value){
        this.value = value;
        this.left = null;
        this.right = null;
    }

    public CalcTree getLeft(){
        return this.left;
    }

    public CalcTree getRight(){
        return this.right;
    }

    public String getValue(){
        return this.value;
    }

    /**
     * Recursively calculates operator expressions.
     *
     * @param tree of sub-expression to be calculated.
     * @return Double with result.
     */
    public Double eval(CalcTree tree){
        if(tree.getValue().equals("/")){
            return eval(tree.getLeft())/eval(tree.getRight());
        }
        if(tree.getValue().equals("*")){
            return eval(tree.getLeft())*eval(tree.getRight());
        }
        if(tree.getValue().equals("+")){
            return eval(tree.getLeft())+eval(tree.getRight());
        }
        if(tree.getValue().equals("-")){
            return eval(tree.getLeft())-eval(tree.getRight());
        }
        return Double.parseDouble(tree.getValue());
    }
}
