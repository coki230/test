public class Function {
    public static void main(String[] args) {
//        Function f = new Function();
//        f.testCompose();

        String a = "aaa";
        String b = "aaa";
        String c = new String("aaa");
        String d = "aa";
        String e = d + "a";

        System.out.println(a.hashCode());
        System.out.println(b.hashCode());
        System.out.println(c.hashCode());
        System.out.println(d.hashCode());
        System.out.println(e.hashCode());
    }

    private void testApply() {
        /**
         * x -> x + 1 这个表达式对于java来说相当于new Function
         */
        java.util.function.Function<Integer, Integer> f = i -> i + 1;
        Integer apply = f.apply(5);
        System.out.println(apply);
    }

    private void test2Apply() {
        java.util.function.Function<Integer, Integer> f1 = i -> i + 1;
        java.util.function.Function<Integer, Integer> f2 = i -> i * i;
        System.out.println(calculate(f1, 2));
        System.out.println(calculate(f2, 2));
    }

    private Integer calculate(java.util.function.Function<Integer, Integer> a, Integer b) {
        return a.apply(b);
    }



    private void testCompose() {
        java.util.function.Function<Integer, Integer> f1 = i -> i + 1;
        java.util.function.Function<Integer, Integer> f2 = i -> i * i;
        System.out.println(f1.compose(f2).apply(2));
    }
}
