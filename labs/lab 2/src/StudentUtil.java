public class StudentUtil {
    public static char getLetterGrade(Student student){
        if (student.getGrade() >= 90) {
            return 'A';
        } else if (student.getGrade() >= 80) {
            return 'B';
        } else if (student.getGrade() >= 70) {
            return 'C';
        } else if (student.getGrade() >= 60) {
            return 'D';
        } else {
            return 'F';
        }
    }
}