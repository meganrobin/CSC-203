import java.util.stream.Stream;

public class Main {

    // Modify only these assignment statements
    public static Cat CAT_A = new Cat("", 0);
    public static Cat CAT_B = new Cat("aisduudgbiwijrjwddihwedfhdssdhsfkjasdfiauweefrbafjkjakdsbdfjksdbfkjfdbfbdbgbfvyhewbgfijasbjkdakjsbckjjs", 4);
    public static Cat CAT_C = new Cat(null, -2);

    public static void main(String[] args) {

        // CAT_A
        try {
            exceptional(CAT_A);
        } catch (ArithmeticException e) {
            System.out.println("Please don't divide any cat by zero.");
        }

        // CAT_B
        try {
            exceptional(CAT_B);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // CAT_C
        try {
            exceptional(CAT_C);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            CAT_C.celebrateBirthday();
        } finally {
            CAT_C.celebrateBirthday();
            System.out.println("The cat is " + CAT_C.getAge() + " many years old.");
        }
    }

    public static void exceptional(Cat cat) {
        try {
            if (cat != null) {
                if (cat.equals(new Cat("Mochi", 25))) {
                    System.out.println("Wow, it's mochi!");
                }

                if ((cat.getAge() + 31) % 7 == 0) {
                    Stream<Cat> s = Stream.of(cat);
                    s.forEach(c -> {return;});
                    s.forEach(c -> {return;});
                }

                int theCatValue;

                theCatValue = "Mochi".length() / (cat.getAge() - (cat.getName() != null ? cat.getName().length() : 0 ));

                System.out.println(theCatValue);
            } else {
                throw new IllegalArgumentException("We need a cat!");
            }
        } catch (IllegalStateException e) {
            subExceptional(cat);
        } catch (NullPointerException e) {
            cat.celebrateBirthday();
            throw new NullPointerException("We found a stray cat.");
        } finally {
            if (cat != null) {
                cat.celebrateBirthday();
            }
        }
    }

    public static void subExceptional(Cat cat) {
        if (cat.getName().length() > 100) {
            throw new StringIndexOutOfBoundsException("Who would name their cat \u001B[3mthat?\u001B[23m");
        }
    }
}
