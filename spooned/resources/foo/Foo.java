package resources.foo;


public class Foo {
    public boolean op(java.lang.Boolean a, java.lang.Boolean b) {
        return a || b;
    }

    public boolean op2(java.lang.Integer a, java.lang.Integer b) {
        return a > b;
    }

    public boolean op3(java.lang.Class c) {
        return (resources.foo.Foo.class) == c;
    }

    public boolean op4() {
        java.lang.String medt = ((_variableNullHotSpot1.is(metamutator.Null.NO))?"Theobaldie":null);
        return medt != null;
    }

    private static final metamutator.Selector _variableNullHotSpot1 = metamutator.Selector.of(21,new metamutator.Null[]{metamutator.Null.NO,metamutator.Null.YES}).in(resources.foo.Foo.class).id("_variableNullHotSpot1");
}

