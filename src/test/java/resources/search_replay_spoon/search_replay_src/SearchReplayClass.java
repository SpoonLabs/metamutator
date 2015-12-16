package resources.search_replay_spoon.search_replay_src;


public class SearchReplayClass {
    public SearchReplayClass() {
    }

    public boolean op() {
        int a = 2;
        java.lang.String b = ((_variableNullHotSpot3.is(metamutator.Null.NO))?"2":null);
        return ((_binaryLogicalOperatorHotSpot3.is(spoon.reflect.code.BinaryOperatorKind.EQ) && (a == (java.lang.Integer.parseInt(b)))) || (_binaryLogicalOperatorHotSpot3.is(spoon.reflect.code.BinaryOperatorKind.NE) && (a != (java.lang.Integer.parseInt(b)))) || (_binaryLogicalOperatorHotSpot3.is(spoon.reflect.code.BinaryOperatorKind.LT) && (a < (java.lang.Integer.parseInt(b)))) || (_binaryLogicalOperatorHotSpot3.is(spoon.reflect.code.BinaryOperatorKind.GT) && (a > (java.lang.Integer.parseInt(b)))) || (_binaryLogicalOperatorHotSpot3.is(spoon.reflect.code.BinaryOperatorKind.LE) && (a <= (java.lang.Integer.parseInt(b)))) || (_binaryLogicalOperatorHotSpot3.is(spoon.reflect.code.BinaryOperatorKind.GE) && (a >= (java.lang.Integer.parseInt(b)))));
    }

    private static final metamutator.Selector _binaryLogicalOperatorHotSpot3 = metamutator.Selector.of(13,new spoon.reflect.code.BinaryOperatorKind[]{spoon.reflect.code.BinaryOperatorKind.EQ,spoon.reflect.code.BinaryOperatorKind.NE,spoon.reflect.code.BinaryOperatorKind.LT,spoon.reflect.code.BinaryOperatorKind.GT,spoon.reflect.code.BinaryOperatorKind.LE,spoon.reflect.code.BinaryOperatorKind.GE}).in(resources.search_replay_src.SearchReplayClass.class).id("_binaryLogicalOperatorHotSpot3");

    private static final metamutator.Selector _variableNullHotSpot3 = metamutator.Selector.of(23,new metamutator.Null[]{metamutator.Null.NO,metamutator.Null.YES}).in(resources.search_replay_src.SearchReplayClass.class).id("_variableNullHotSpot3");
}

