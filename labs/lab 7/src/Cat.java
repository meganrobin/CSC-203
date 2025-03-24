public class Cat {
    private String name;
    private int age;

    public Cat(String name, int age) {
            this.name = name;
            this.age = age;
            }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void celebrateBirthday() {
        age++;
    }

    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
            return name.equals(((Cat) other).name) && age == ((Cat) other).age;
    }
}