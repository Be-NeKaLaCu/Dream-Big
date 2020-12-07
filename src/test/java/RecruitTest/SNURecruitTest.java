package RecruitTest;


import Recruit.RecruitAbstract;
import Recruit.RecruitFactory;
import org.junit.jupiter.api.Test;


public class SNURecruitTest {

    @Test
    void test() {
        RecruitAbstract r = RecruitFactory.make("");
        r.getJobs();
    }
}
