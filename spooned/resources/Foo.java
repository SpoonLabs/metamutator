package resources;


public class Foo {
    final int varinchangeable = 1;

    public boolean op(java.lang.Boolean a, java.lang.Boolean b) {
        return a || b;
    }

    public boolean op2(java.lang.Integer a, java.lang.Integer b) {
        return a > b;
    }

    public boolean op3(java.lang.Class c) {
        return (resources.Foo.class) == c;
    }

    public int add(java.lang.Integer a, java.lang.Integer b) {
        int c = a + b;
        return (_numericExpressionMetaMutator1.is(metamutator.NumericVariableMetaMutator.UNARY.ABS)?( Math.abs(c)):_numericExpressionMetaMutator1.is(metamutator.NumericVariableMetaMutator.UNARY.MINUS)?( -(c)):_numericExpressionMetaMutator1.is(metamutator.NumericVariableMetaMutator.UNARY.INC)?( (++c)):_numericExpressionMetaMutator1.is(metamutator.NumericVariableMetaMutator.UNARY.DEC)?( (--c)):(c));
    }

    public int sum(java.lang.Integer a) {
        int b = 0;
        do {
            b++;
            a--;
        } while (a > 1 );
        return (_numericExpressionMetaMutator2.is(metamutator.NumericVariableMetaMutator.UNARY.ABS)?( Math.abs(b)):_numericExpressionMetaMutator2.is(metamutator.NumericVariableMetaMutator.UNARY.MINUS)?( -(b)):_numericExpressionMetaMutator2.is(metamutator.NumericVariableMetaMutator.UNARY.INC)?( (++b)):_numericExpressionMetaMutator2.is(metamutator.NumericVariableMetaMutator.UNARY.DEC)?( (--b)):(b));
    }

    private static final metamutator.Selector _numericExpressionMetaMutator1 = metamutator.Selector.of(41,new metamutator.NumericVariableMetaMutator.UNARY[]{metamutator.NumericVariableMetaMutator.UNARY.INIT,metamutator.NumericVariableMetaMutator.UNARY.ABS,metamutator.NumericVariableMetaMutator.UNARY.MINUS,metamutator.NumericVariableMetaMutator.UNARY.INC,metamutator.NumericVariableMetaMutator.UNARY.DEC}).in(resources.Foo.class).id("_numericExpressionMetaMutator1");

    private static final metamutator.Selector _numericExpressionMetaMutator2 = metamutator.Selector.of(42,new metamutator.NumericVariableMetaMutator.UNARY[]{metamutator.NumericVariableMetaMutator.UNARY.INIT,metamutator.NumericVariableMetaMutator.UNARY.ABS,metamutator.NumericVariableMetaMutator.UNARY.MINUS,metamutator.NumericVariableMetaMutator.UNARY.INC,metamutator.NumericVariableMetaMutator.UNARY.DEC}).in(resources.Foo.class).id("_numericExpressionMetaMutator2");
}

