package week1Basic;

public class Student extends AbstractPerson{

    private String job = "학생";

    public String getJob() {
        return job;
    }

    @Override
    public void speak() {
        System.out.println("저는 " + this.getName() +"이고, " + this.getAge() + "살 이며, 직업은 " + this.getJob() + "입니다." );
    }
}
