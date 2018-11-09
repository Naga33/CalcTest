import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ArithmeticTest {

    @Test
    void testCalculatesSimpleOperator() {
        Arithmetic arithmetic = new Arithmetic("15/3", new HashMap<>());
        arithmetic.calculateFinalResult();
        Token finalResult = arithmetic.getResult();
        Double expected = 5.0;
        Double actual = Double.parseDouble(finalResult.getValue());
        assertEquals(expected,actual);
    }

    @Test
    void testCalculatesMultipleOperators() {
        Arithmetic arithmetic = new Arithmetic("10/2+6*3-1", new HashMap<>());
        arithmetic.calculateFinalResult();
        Token finalResult = arithmetic.getResult();
        Double expected = 22.0;
        Double actual = Double.parseDouble(finalResult.getValue());
        assertEquals(expected,actual);
    }

    @Test
    void testCalculatesMultipleFunctions() {
        String expression = "cos(0)+sin(0)-sqrt(9)+log(1)";
        Arithmetic arithmetic = new Arithmetic(expression, new HashMap<>());
        arithmetic.calculateFinalResult();
        Token finalResult = arithmetic.getResult();
        Double expected = -2.0;
        Double actual = Double.parseDouble(finalResult.getValue());
        assertEquals(expected,actual);
    }

    @Test
    void testCalculatesMultipleAssigns() {
        String expression = "(a=5)+(b=6)";
        Arithmetic arithmetic = new Arithmetic(expression, new HashMap<>());
        arithmetic.calculateFinalResult();
        Token finalResult = arithmetic.getResult();
        Double expected = 11.0;
        Double actual = Double.parseDouble(finalResult.getValue());
        assertEquals(expected,actual);
    }

    @Test
    void testCalculatesOperatorsAndFunctions() {
        String expression = "4+log(.5+0.5)";
        Arithmetic arithmetic = new Arithmetic(expression, new HashMap<>());
        arithmetic.calculateFinalResult();
        Token finalResult = arithmetic.getResult();
        Double expected = 4.0;
        Double actual = Double.parseDouble(finalResult.getValue());
        assertEquals(expected,actual);
    }

    @Test
    void testCalculatesOperatorsAndAssign() {
        String expression = "a=5*(2+2)";
        Arithmetic arithmetic = new Arithmetic(expression, new HashMap<>());
        arithmetic.calculateFinalResult();
        Token finalResult = arithmetic.getResult();
        Double expected = 20.0;
        Double actual = Double.parseDouble(finalResult.getValue());
        assertEquals(expected,actual);
    }

    @Test
    void testCalculatesFunctionsAndAssign() {
        String expression = "a=sqrt(49)";
        Arithmetic arithmetic = new Arithmetic(expression, new HashMap<>());
        arithmetic.calculateFinalResult();
        Token finalResult = arithmetic.getResult();
        Double expected = 7.0;
        Double actual = Double.parseDouble(finalResult.getValue());
        assertEquals(expected,actual);
    }

    @Test
    void testCalculatesOperatorsFunctionsAndAssign() {
        String expression = "a=cos(0)+4/2";
        Arithmetic arithmetic = new Arithmetic(expression, new HashMap<>());
        arithmetic.calculateFinalResult();
        Token finalResult = arithmetic.getResult();
        Double expected = 3.0;
        Double actual = Double.parseDouble(finalResult.getValue());
        assertEquals(expected,actual);
    }

    @Test
    void testCalculatesMultipleBrackets() {
        String expression = "(4+6)+(a=20)/(2-1)";
        Arithmetic arithmetic = new Arithmetic(expression, new HashMap<>());
        arithmetic.calculateFinalResult();
        Token finalResult = arithmetic.getResult();
        Double expected = 30.0;
        Double actual = Double.parseDouble(finalResult.getValue());
        assertEquals(expected,actual);
    }

    @Test
    void testCalculatesDoubleBrackets() {
        String expression = "((6/2))";
        Arithmetic arithmetic = new Arithmetic(expression, new HashMap<>());
        arithmetic.calculateFinalResult();
        Token finalResult = arithmetic.getResult();
        Double expected = 3.0;
        Double actual = Double.parseDouble(finalResult.getValue());
        assertEquals(expected,actual);
    }

    @Test
    void testBindings() {
        String expression = "(a=5)+(b=6)";
        Arithmetic arithmetic = new Arithmetic(expression, new HashMap<>());
        arithmetic.calculateFinalResult();

        String expression2 = "a-b";
        Arithmetic arithmetic2 = new Arithmetic(expression2, arithmetic.getBindings());
        arithmetic2.calculateFinalResult();

        Token finalResult = arithmetic2.getResult();
        Double expected = -1.0;
        Double actual = Double.parseDouble(finalResult.getValue());
        assertEquals(expected,actual);
    }

    @Test
    void testUnderscoreBindings() {
        String expression = "(a=5)+(b=6)";
        Arithmetic arithmetic = new Arithmetic(expression, new HashMap<>());
        arithmetic.calculateFinalResult();

        String expression2 = "_+a-b";
        Arithmetic arithmetic2 = new Arithmetic(expression2, arithmetic.getBindings());
        arithmetic2.calculateFinalResult();

        Token finalResult = arithmetic2.getResult();
        Double expected = 10.0;
        Double actual = Double.parseDouble(finalResult.getValue());
        assertEquals(expected,actual);
    }

}
