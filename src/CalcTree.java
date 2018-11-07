public class CalcTree {

    private String value;
    private CalcTree left;
    private CalcTree right;

    public CalcTree(String value, CalcTree left, CalcTree right){
        this.value = value;
        this.left = left;
        this.right = right;
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

    //lecture slides had this method as static rather than public. why?
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
