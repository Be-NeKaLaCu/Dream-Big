package Recruit;

import java.util.ArrayList;

public abstract class RecruitAbstract {

    private final Page page;

    RecruitAbstract(Page page) {
        this.page = page;
    }

    public ArrayList<Job> getJobs() {
        ArrayList<Job> jobs = new ArrayList<Job>();

        while (true) {
            String url = getRecruitUrl();
            String pageString = page.getPage(url);

            for (String item : getList(pageString)) {
                Job job = new Job(
                        getSubject(item),
                        getStartDate(item),
                        getEndDate(item)
                );
                jobs.add(job);
            }

            // TODO: startDate 가 어제면 break하기
            break;
        }

        return jobs;
    }

    abstract protected String getRecruitUrl();
    abstract protected ArrayList<String> getList(String page);
    abstract protected String getSubject(String item);
    abstract protected String getStartDate(String item);
    abstract protected String getEndDate(String item);

}
