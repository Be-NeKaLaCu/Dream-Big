package Recruit;

import java.util.ArrayList;

public class RecruitFactory {
    public static ArrayList<RecruitAbstract<?>> makeAll()
    {
        var recruits = new ArrayList<RecruitAbstract<?>>();

        recruits.add(new SNURecruit());
        recruits.add(new YonseiRecruit());
        recruits.add(new SamsungRecruit());

        return recruits;
    }
}
