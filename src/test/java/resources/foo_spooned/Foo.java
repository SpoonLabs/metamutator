package resources.foo_spooned;


public class Foo {
    public boolean op(java.lang.Boolean a, java.lang.Boolean b) {
        return ((_binaryLogicalOperatorHotSpot1.is(spoon.reflect.code.BinaryOperatorKind.OR) && (a || b)) || (_binaryLogicalOperatorHotSpot1.is(spoon.reflect.code.BinaryOperatorKind.AND) && (a && b)));
    }

    public boolean op2(java.lang.Integer a, java.lang.Integer b) {
        return ((_binaryLogicalOperatorHotSpot2.is(spoon.reflect.code.BinaryOperatorKind.EQ) && (a == b)) || (_binaryLogicalOperatorHotSpot2.is(spoon.reflect.code.BinaryOperatorKind.NE) && (a != b)) || (_binaryLogicalOperatorHotSpot2.is(spoon.reflect.code.BinaryOperatorKind.LT) && (a < b)) || (_binaryLogicalOperatorHotSpot2.is(spoon.reflect.code.BinaryOperatorKind.GT) && (a > b)) || (_binaryLogicalOperatorHotSpot2.is(spoon.reflect.code.BinaryOperatorKind.LE) && (a <= b)) || (_binaryLogicalOperatorHotSpot2.is(spoon.reflect.code.BinaryOperatorKind.GE) && (a >= b)));
    }

    public boolean op3(java.lang.Class c) {
        return ((_binaryLogicalOperatorHotSpot3.is(spoon.reflect.code.BinaryOperatorKind.EQ) && ((resources.foo.Foo.class) == c)) || (_binaryLogicalOperatorHotSpot3.is(spoon.reflect.code.BinaryOperatorKind.NE) && ((resources.foo.Foo.class) != c)));
    }

    public boolean op4() {
        java.lang.String medt = ((_variableNullHotSpot1.is(metamutator.Null.NO))?"Theobaldie":null);
        return ((_binaryLogicalOperatorHotSpot4.is(spoon.reflect.code.BinaryOperatorKind.EQ) && (medt == null)) || (_binaryLogicalOperatorHotSpot4.is(spoon.reflect.code.BinaryOperatorKind.NE) && (medt != null)));
    }

    private static final metamutator.Selector _binaryLogicalOperatorHotSpot1 = metamutator.Selector.of(new spoon.reflect.code.BinaryOperatorKind[]{spoon.reflect.code.BinaryOperatorKind.OR,spoon.reflect.code.BinaryOperatorKind.AND}).in(resources.foo.Foo.class).id("_binaryLogicalOperatorHotSpot1");

    private static final metamutator.Selector _binaryLogicalOperatorHotSpot2 = metamutator.Selector.of(new spoon.reflect.code.BinaryOperatorKind[]{spoon.reflect.code.BinaryOperatorKind.GT,spoon.reflect.code.BinaryOperatorKind.EQ,spoon.reflect.code.BinaryOperatorKind.NE,spoon.reflect.code.BinaryOperatorKind.LT,spoon.reflect.code.BinaryOperatorKind.LE,spoon.reflect.code.BinaryOperatorKind.GE}).in(resources.foo.Foo.class).id("_binaryLogicalOperatorHotSpot2");

    private static final metamutator.Selector _binaryLogicalOperatorHotSpot3 = metamutator.Selector.of(new spoon.reflect.code.BinaryOperatorKind[]{spoon.reflect.code.BinaryOperatorKind.EQ,spoon.reflect.code.BinaryOperatorKind.NE}).in(resources.foo.Foo.class).id("_binaryLogicalOperatorHotSpot3");

    private static final metamutator.Selector _binaryLogicalOperatorHotSpot4 = metamutator.Selector.of(new spoon.reflect.code.BinaryOperatorKind[]{spoon.reflect.code.BinaryOperatorKind.NE,spoon.reflect.code.BinaryOperatorKind.EQ}).in(resources.foo.Foo.class).id("_binaryLogicalOperatorHotSpot4");

    private static final metamutator.Selector _variableNullHotSpot1 = metamutator.Selector.of(new metamutator.Null[]{metamutator.Null.NO,metamutator.Null.YES}).in(resources.foo.Foo.class).id("_variableNullHotSpot1");
}

