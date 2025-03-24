import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StudentTests {

    @Test
    public void testGetName() {
        Student testStudent = new Student("Jack", 99.32);
        Assertions.assertEquals("Jack", testStudent.getName());
    }

    @Test
    public void testGetGrade() {
        Student testStudent = new Student("Jamie", 44.7);
        Assertions.assertEquals(44.7, testStudent.getGrade());
    }

    @Test
    public void testGetLetterGrade() {
        Student testStudent1 = new Student("Jill", 103.71);
        Assertions.assertEquals('A', testStudent1.getLetterGrade());

        Student testStudent2 = new Student("Joe", 81.2357);
        Assertions.assertEquals('B', testStudent2.getLetterGrade());

        Student testStudent3 = new Student("Jules", 77.77);
        Assertions.assertEquals('C', testStudent3.getLetterGrade());

        Student testStudent4 = new Student("Julian", 61.9999999901);
        Assertions.assertEquals('D', testStudent4.getLetterGrade());

        Student testStudent5 = new Student("Julie", 13.089);
        Assertions.assertEquals('F', testStudent5.getLetterGrade());
    }
    @Test
    public void testToString() {
        Student testStudent1 = new Student("Josie", 55.77777777);
        Assertions.assertEquals("Josie (55.78)", testStudent1.toString());

        Student testStudent2 = new Student("Jane", 99.901);
        Assertions.assertEquals("Jane (99.90)", testStudent2.toString());
    }
    @Test
    public void testEquals() {
        Student testStudent3 = new Student("Jasper", 45.91803);
        Student testStudent4 = new Student("Jasper", 45.91803);
        Assertions.assertEquals(testStudent3, testStudent4);

        Student testStudent1 = new Student("Joseph", 55.77777777);
        Student testStudent2 = new Student("Jo", 90.9012);
        Assertions.assertNotEquals(testStudent1, testStudent2);

        Student testStudent5 = new Student(null,100.0003);
        Student testStudent6 = new Student("Jasmine", 100.0003);
        Assertions.assertNotEquals(testStudent5, testStudent6);

        Student testStudent7 = new Student(null,100.0003);
        Student testStudent8 = new Student(null, 100.0003);
        Assertions.assertEquals(testStudent7, testStudent8);

        Student testStudent9 = new Student("June", 100.0003);
        String testString = "June";
        Assertions.assertNotEquals(testStudent9, testString);
    }
    @Test
    public void testHashCode() {
        Student testStudent1 = new Student("Jordan", 68.12);
        Assertions.assertEquals(1654152803, testStudent1.hashCode());

        Student testStudent2 = new Student("Jacob", 91.00001);
        Assertions.assertEquals(-311172620, testStudent2.hashCode());

        Student testStudent3 = new Student(null, 84.587);
        Assertions.assertEquals(673683825, testStudent3.hashCode());
    }

    @Test
    public void testStaticGetLetterGrade() {
        Student testStudent1 = new Student("Jackson", 103.71);
        Assertions.assertEquals('A', StudentUtil.getLetterGrade(testStudent1));

        Student testStudent2 = new Student("Jose", 81.2357);
        Assertions.assertEquals('B', StudentUtil.getLetterGrade(testStudent2));

        Student testStudent3 = new Student("Jules", 77.77);
        Assertions.assertEquals('C', StudentUtil.getLetterGrade(testStudent3));

        Student testStudent4 = new Student("Julian", 61.9999999901);
        Assertions.assertEquals('D', StudentUtil.getLetterGrade(testStudent4));

        Student testStudent5 = new Student("Julie", 13.089);
        Assertions.assertEquals('F', StudentUtil.getLetterGrade(testStudent5));
    }
}
