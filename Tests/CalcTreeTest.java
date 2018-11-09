import static org.junit.jupiter.api.Assertions.*;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;

class CalcTreeTest {

    @Test
    void testSum() {
        CalcTree tree = new CalcTree("+",
                new CalcTree("2"),
                new CalcTree("4"));
        Double expected = 6.0;
        Double actual = tree.eval(tree);
        assertEquals(expected, actual);
    }

    @Test
    void testSubtract(){
        CalcTree tree = new CalcTree("-",
                new CalcTree("10"),
                new CalcTree("3"));
        Double expected = 7.0;
        Double actual = tree.eval(tree);
        assertEquals(expected, actual);
    }

    @Test
    void testDivide(){
        CalcTree tree = new CalcTree("/",
                new CalcTree("15"),
                new CalcTree("5"));
        Double expected = 3.0;
        Double actual = tree.eval(tree);
        assertEquals(expected, actual);
    }

    @Test
    void testMultiply(){
        CalcTree tree = new CalcTree("*",
                new CalcTree("6"),
                new CalcTree("7"));
        Double expected = 42.0;
        Double actual = tree.eval(tree);
        assertEquals(expected, actual);
    }

    //Divide by zero feature not implemented
    @Ignore
    @Test
    void testDivideByZero(){
        CalcTree tree = new CalcTree("/",
                new CalcTree("10"),
                new CalcTree("0"));
        Executable treeEval = () -> tree.eval(tree);
        assertThrows(Exception.class, treeEval);
    }
}