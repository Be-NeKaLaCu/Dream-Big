package Recruit;

public class RecruitFactory {
    public static RecruitAbstract<?> make(String type)
    {
        return new SNURecruit(new Page());
    }
}
