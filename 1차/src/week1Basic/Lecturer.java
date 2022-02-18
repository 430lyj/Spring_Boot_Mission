package week1Basic;

public class Lecturer extends AbstractPerson{

    private String job = "강의자";

    public String getJob() {
        return job;
    }

    @Override
    public void speak() {
        System.out.println("저는 " + this.getName() +"이고, " + this.getAge() + "살 이며, 직업은 " + this.getJob() + "입니다." );
    }
}
