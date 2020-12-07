package Recruit;

import java.util.ArrayList;

public class SNURecruit extends RecruitAbstract {

    SNURecruit(Page page) {
        super(page);
    }

    @Override
    protected final String getRecruitUrl() {
        return "http://google.com";
    }

    @Override
    protected final ArrayList<String> getList(String page) {
        return null;
    }

    @Override
    protected final String getSubject(String item) {
        return null;
    }

    @Override
    protected final String getStartDate(String item) {
        return null;
    }

    @Override
    protected final String getEndDate(String item) {
        return null;
    }
}
