package Recruit;

import Recruit.Company.*;

import java.util.ArrayList;

public class RecruitFactory {
    public static ArrayList<RecruitAbstract<?>> makeAll()
    {
        var recruits = new ArrayList<RecruitAbstract<?>>();

        recruits.add(new KangdongRecruit());
        recruits.add(new KoreaRecruit());
        recruits.add(new KyungHeeRecruit());
        recruits.add(new SamsungRecruit());
        recruits.add(new SeoulMaryRecruit());
        recruits.add(new SNURecruit());
        recruits.add(new YonseiRecruit());

        return recruits;
    }
}
