package resources.search_replay_spoon.search_replay_src;


public class SearchReplayClassBis {
    public SearchReplayClassBis() {
    }

    public boolean op() {
        int a = 1;
        java.lang.String b = ((_variableNullHotSpot4.is(metamutator.Null.NO))?"1":null);
        return ((_binaryLogicalOperatorHotSpot4.is(spoon.reflect.code.BinaryOperatorKind.EQ) && (a == (java.lang.Integer.parseInt(b)))) || (_binaryLogicalOperatorHotSpot4.is(spoon.reflect.code.BinaryOperatorKind.NE) && (a != (java.lang.Integer.parseInt(b)))) || (_binaryLogicalOperatorHotSpot4.is(spoon.reflect.code.BinaryOperatorKind.LT) && (a < (java.lang.Integer.parseInt(b)))) || (_binaryLogicalOperatorHotSpot4.is(spoon.reflect.code.BinaryOperatorKind.GT) && (a > (java.lang.Integer.parseInt(b)))) || (_binaryLogicalOperatorHotSpot4.is(spoon.reflect.code.BinaryOperatorKind.LE) && (a <= (java.lang.Integer.parseInt(b)))) || (_binaryLogicalOperatorHotSpot4.is(spoon.reflect.code.BinaryOperatorKind.GE) && (a >= (java.lang.Integer.parseInt(b)))));
    }

    private static final metamutator.Selector _binaryLogicalOperatorHotSpot4 = metamutator.Selector.of(new spoon.reflect.code.BinaryOperatorKind[]{spoon.reflect.code.BinaryOperatorKind.EQ,spoon.reflect.code.BinaryOperatorKind.NE,spoon.reflect.code.BinaryOperatorKind.LT,spoon.reflect.code.BinaryOperatorKind.GT,spoon.reflect.code.BinaryOperatorKind.LE,spoon.reflect.code.BinaryOperatorKind.GE}).in(resources.search_replay_src.SearchReplayClassBis.class).id("_binaryLogicalOperatorHotSpot4");

    private static final metamutator.Selector _variableNullHotSpot4 = metamutator.Selector.of(new metamutator.Null[]{metamutator.Null.NO,metamutator.Null.YES}).in(resources.search_replay_src.SearchReplayClassBis.class).id("_variableNullHotSpot4");
}

