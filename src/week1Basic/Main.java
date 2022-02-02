package week1Basic;

public class Main {
    public static void main(String[] args) {
        Student student1 = new Student();
        Lecturer lecturer1 = new Lecturer();

        student1.setName("이연주");
        student1.setAge(24);
        student1.speak();

        lecturer1.setName("강의자");
        lecturer1.setAge(34);
        lecturer1.speak();
    }
}
