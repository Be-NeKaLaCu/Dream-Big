package Recruit;

import java.util.ArrayList;

public abstract class RecruitAbstract<T> {

    private final Page page;

    RecruitAbstract(Page page) {
        this.page = page;
    }

    public ArrayList<Job> getJobs() {
        ArrayList<Job> jobs = new ArrayList<Job>();

        while (true) {
            String url = generateRecruitUrl();
            String pageString = page.getPage(url);

            for (T item : getList(pageString)) {
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

    abstract protected String generateRecruitUrl();
    abstract protected ArrayList<T> getList(String page);
    abstract protected String getSubject(T item);
    abstract protected String getStartDate(T item);
    abstract protected String getEndDate(T item);

}
