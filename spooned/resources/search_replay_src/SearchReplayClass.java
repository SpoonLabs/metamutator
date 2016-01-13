package resources.search_replay_src;


public class SearchReplayClass {
    public SearchReplayClass() {
    }

    public boolean op() {
        int a = 2;
        java.lang.String b = "2";
        return ((_binaryLogicalOperatorHotSpot72.is(spoon.reflect.code.BinaryOperatorKind.EQ) && (a == (java.lang.Integer.parseInt(b)))) || (_binaryLogicalOperatorHotSpot72.is(spoon.reflect.code.BinaryOperatorKind.NE) && (a != (java.lang.Integer.parseInt(b)))) || (_binaryLogicalOperatorHotSpot72.is(spoon.reflect.code.BinaryOperatorKind.LT) && (a < (java.lang.Integer.parseInt(b)))) || (_binaryLogicalOperatorHotSpot72.is(spoon.reflect.code.BinaryOperatorKind.GT) && (a > (java.lang.Integer.parseInt(b)))) || (_binaryLogicalOperatorHotSpot72.is(spoon.reflect.code.BinaryOperatorKind.LE) && (a <= (java.lang.Integer.parseInt(b)))) || (_binaryLogicalOperatorHotSpot72.is(spoon.reflect.code.BinaryOperatorKind.GE) && (a >= (java.lang.Integer.parseInt(b)))));
    }

    private static final metamutator.Selector _binaryLogicalOperatorHotSpot72 = metamutator.Selector.of(172,new spoon.reflect.code.BinaryOperatorKind[]{spoon.reflect.code.BinaryOperatorKind.EQ,spoon.reflect.code.BinaryOperatorKind.NE,spoon.reflect.code.BinaryOperatorKind.LT,spoon.reflect.code.BinaryOperatorKind.GT,spoon.reflect.code.BinaryOperatorKind.LE,spoon.reflect.code.BinaryOperatorKind.GE}).in(resources.search_replay_src.SearchReplayClass.class).id("_binaryLogicalOperatorHotSpot72");
}

