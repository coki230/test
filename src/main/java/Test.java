import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

public class Test {

    private int ek;

    public int getEk() {
        return ek;
    }

    public void setEk(int ek) {
        this.ek = ek;
    }

    public static void main(String[] args) throws ParseException {
        Test test1 = new Test();
        test1.setEk(1);
        Test test2 = new Test();
        test2.setEk(1);
        Map<Test, Test> map = new HashMap<>();
        map.put(test1, test1);
        System.out.println(map.containsKey(test2));

    }

    public int hashCode() {
        return 1;
    }

    public boolean equals(Object o) {
        if (o.getClass() == Test.class) {
            return this.ek == ((Test) o).getEk();
        }
        return false;
    }
}
