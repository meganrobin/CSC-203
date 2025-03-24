public class Student {
    private String name;
    private double grade;
    public Student(String name, double grade){
        this.name = name;
        this.grade = grade;
    }
    public String getName() {
        return this.name;
    }
    public double getGrade() {
        return this.grade;
    }

    public char getLetterGrade(){
        if (this.grade >= 90) {
            return 'A';
        } else if (this.grade >= 80) {
            return 'B';
        } else if (this.grade >= 70) {
            return 'C';
        } else if (this.grade >= 60) {
            return 'D';
        } else {
            return 'F';
        }
    }

    @Override
    public String toString() {
        return String.format("%s (%.2f)", this.name, this.grade);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Student student) {
            return name == null ? student.name == null : name.equals(student.name) && grade == student.grade;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 1;
        hash = hash * 31 + (name == null ? 0 : name.hashCode());
        hash = hash * 31 + Double.hashCode(grade);
        return hash;
    }
}