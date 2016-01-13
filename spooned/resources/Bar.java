package resources;


public class Bar {
    public boolean op(java.lang.Boolean a, java.lang.Boolean b) {
        return ((_returnReplacementOperatorHotSpot1.is(metamutator.ReturnReplacementOperatorMetaMutator.RETURN_REPLACEMENT_OBJECT.NULL)) ? ( null ) : (a || b));
    }

    public boolean op2(java.lang.Integer a, java.lang.Integer b) {
        return ((_returnReplacementOperatorHotSpot2.is(metamutator.ReturnReplacementOperatorMetaMutator.RETURN_REPLACEMENT_OBJECT.NULL)) ? ( null ) : (a > b));
    }

    public boolean op3(java.lang.Class c) {
        return ((_returnReplacementOperatorHotSpot3.is(metamutator.ReturnReplacementOperatorMetaMutator.RETURN_REPLACEMENT_OBJECT.NULL)) ? ( null ) : ((resources.Foo.class) == c));
    }

    public java.lang.Integer op_add(java.lang.Integer a, java.lang.Integer b) {
        return ((_returnReplacementOperatorHotSpot5.is(metamutator.ReturnReplacementOperatorMetaMutator.RETURN_REPLACEMENT_INT.INT_MIN)) ? ( -2147483647 ) : (_returnReplacementOperatorHotSpot5.is(metamutator.ReturnReplacementOperatorMetaMutator.RETURN_REPLACEMENT_INT.INT_MAX)) ? ( 2147483646 ) : (_returnReplacementOperatorHotSpot5.is(metamutator.ReturnReplacementOperatorMetaMutator.RETURN_REPLACEMENT_INT.ZERO)) ? ( 0 ) : (a + b));
    }

    public java.lang.Integer op_minus(java.lang.Integer a, java.lang.Integer b) {
        return ((_returnReplacementOperatorHotSpot6.is(metamutator.ReturnReplacementOperatorMetaMutator.RETURN_REPLACEMENT_INT.INT_MIN)) ? ( -2147483647 ) : (_returnReplacementOperatorHotSpot6.is(metamutator.ReturnReplacementOperatorMetaMutator.RETURN_REPLACEMENT_INT.INT_MAX)) ? ( 2147483646 ) : (_returnReplacementOperatorHotSpot6.is(metamutator.ReturnReplacementOperatorMetaMutator.RETURN_REPLACEMENT_INT.ZERO)) ? ( 0 ) : (a - b));
    }

    public java.lang.String op_concat_ss(java.lang.String a, java.lang.String b) {
        return ((_returnReplacementOperatorHotSpot8.is(metamutator.ReturnReplacementOperatorMetaMutator.RETURN_REPLACEMENT_OBJECT.NULL)) ? ( null ) : (a + b));
    }

    public java.lang.String op_concat_is(java.lang.Integer a, java.lang.String b) {
        return ((_returnReplacementOperatorHotSpot7.is(metamutator.ReturnReplacementOperatorMetaMutator.RETURN_REPLACEMENT_OBJECT.NULL)) ? ( null ) : (a + b));
    }

    public java.lang.String op_get_s(java.lang.String a) {
        return ((_returnReplacementOperatorHotSpot9.is(metamutator.ReturnReplacementOperatorMetaMutator.RETURN_REPLACEMENT_OBJECT.NULL)) ? ( null ) : (a));
    }

    public boolean op_get_b(java.lang.Boolean a) {
        return a;
    }

    public int op_constant() {
        int i = 42;
        return ((_returnReplacementOperatorHotSpot4.is(metamutator.ReturnReplacementOperatorMetaMutator.RETURN_REPLACEMENT_INT.INT_MIN)) ? ( -2147483647 ) : (_returnReplacementOperatorHotSpot4.is(metamutator.ReturnReplacementOperatorMetaMutator.RETURN_REPLACEMENT_INT.INT_MAX)) ? ( 2147483646 ) : (_returnReplacementOperatorHotSpot4.is(metamutator.ReturnReplacementOperatorMetaMutator.RETURN_REPLACEMENT_INT.ZERO)) ? ( 0 ) : (i));
    }

    private static final metamutator.Selector _returnReplacementOperatorHotSpot1 = metamutator.Selector.of(61,new metamutator.ReturnReplacementOperatorMetaMutator.RETURN_REPLACEMENT_OBJECT[]{metamutator.ReturnReplacementOperatorMetaMutator.RETURN_REPLACEMENT_OBJECT.INIT,metamutator.ReturnReplacementOperatorMetaMutator.RETURN_REPLACEMENT_OBJECT.NULL}).in(resources.Bar.class).id("_returnReplacementOperatorHotSpot1");

    private static final metamutator.Selector _returnReplacementOperatorHotSpot2 = metamutator.Selector.of(62,new metamutator.ReturnReplacementOperatorMetaMutator.RETURN_REPLACEMENT_OBJECT[]{metamutator.ReturnReplacementOperatorMetaMutator.RETURN_REPLACEMENT_OBJECT.INIT,metamutator.ReturnReplacementOperatorMetaMutator.RETURN_REPLACEMENT_OBJECT.NULL}).in(resources.Bar.class).id("_returnReplacementOperatorHotSpot2");

    private static final metamutator.Selector _returnReplacementOperatorHotSpot3 = metamutator.Selector.of(63,new metamutator.ReturnReplacementOperatorMetaMutator.RETURN_REPLACEMENT_OBJECT[]{metamutator.ReturnReplacementOperatorMetaMutator.RETURN_REPLACEMENT_OBJECT.INIT,metamutator.ReturnReplacementOperatorMetaMutator.RETURN_REPLACEMENT_OBJECT.NULL}).in(resources.Bar.class).id("_returnReplacementOperatorHotSpot3");

    private static final metamutator.Selector _returnReplacementOperatorHotSpot4 = metamutator.Selector.of(64,new metamutator.ReturnReplacementOperatorMetaMutator.RETURN_REPLACEMENT_INT[]{metamutator.ReturnReplacementOperatorMetaMutator.RETURN_REPLACEMENT_INT.INIT,metamutator.ReturnReplacementOperatorMetaMutator.RETURN_REPLACEMENT_INT.INT_MIN,metamutator.ReturnReplacementOperatorMetaMutator.RETURN_REPLACEMENT_INT.INT_MAX,metamutator.ReturnReplacementOperatorMetaMutator.RETURN_REPLACEMENT_INT.ZERO}).in(resources.Bar.class).id("_returnReplacementOperatorHotSpot4");

    private static final metamutator.Selector _returnReplacementOperatorHotSpot5 = metamutator.Selector.of(65,new metamutator.ReturnReplacementOperatorMetaMutator.RETURN_REPLACEMENT_INT[]{metamutator.ReturnReplacementOperatorMetaMutator.RETURN_REPLACEMENT_INT.INIT,metamutator.ReturnReplacementOperatorMetaMutator.RETURN_REPLACEMENT_INT.INT_MIN,metamutator.ReturnReplacementOperatorMetaMutator.RETURN_REPLACEMENT_INT.INT_MAX,metamutator.ReturnReplacementOperatorMetaMutator.RETURN_REPLACEMENT_INT.ZERO}).in(resources.Bar.class).id("_returnReplacementOperatorHotSpot5");

    private static final metamutator.Selector _returnReplacementOperatorHotSpot6 = metamutator.Selector.of(66,new metamutator.ReturnReplacementOperatorMetaMutator.RETURN_REPLACEMENT_INT[]{metamutator.ReturnReplacementOperatorMetaMutator.RETURN_REPLACEMENT_INT.INIT,metamutator.ReturnReplacementOperatorMetaMutator.RETURN_REPLACEMENT_INT.INT_MIN,metamutator.ReturnReplacementOperatorMetaMutator.RETURN_REPLACEMENT_INT.INT_MAX,metamutator.ReturnReplacementOperatorMetaMutator.RETURN_REPLACEMENT_INT.ZERO}).in(resources.Bar.class).id("_returnReplacementOperatorHotSpot6");

    private static final metamutator.Selector _returnReplacementOperatorHotSpot7 = metamutator.Selector.of(67,new metamutator.ReturnReplacementOperatorMetaMutator.RETURN_REPLACEMENT_OBJECT[]{metamutator.ReturnReplacementOperatorMetaMutator.RETURN_REPLACEMENT_OBJECT.INIT,metamutator.ReturnReplacementOperatorMetaMutator.RETURN_REPLACEMENT_OBJECT.NULL}).in(resources.Bar.class).id("_returnReplacementOperatorHotSpot7");

    private static final metamutator.Selector _returnReplacementOperatorHotSpot8 = metamutator.Selector.of(68,new metamutator.ReturnReplacementOperatorMetaMutator.RETURN_REPLACEMENT_OBJECT[]{metamutator.ReturnReplacementOperatorMetaMutator.RETURN_REPLACEMENT_OBJECT.INIT,metamutator.ReturnReplacementOperatorMetaMutator.RETURN_REPLACEMENT_OBJECT.NULL}).in(resources.Bar.class).id("_returnReplacementOperatorHotSpot8");

    private static final metamutator.Selector _returnReplacementOperatorHotSpot9 = metamutator.Selector.of(69,new metamutator.ReturnReplacementOperatorMetaMutator.RETURN_REPLACEMENT_OBJECT[]{metamutator.ReturnReplacementOperatorMetaMutator.RETURN_REPLACEMENT_OBJECT.INIT,metamutator.ReturnReplacementOperatorMetaMutator.RETURN_REPLACEMENT_OBJECT.NULL}).in(resources.Bar.class).id("_returnReplacementOperatorHotSpot9");
}

