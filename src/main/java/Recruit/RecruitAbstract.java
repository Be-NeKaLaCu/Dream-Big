package Recruit;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public abstract class RecruitAbstract<T> {
    public ArrayList<Job> getJobsOnTheDayOf(LocalDate date) throws IOException, InterruptedException {
        int page = 0;
        ArrayList<Job> jobs = new ArrayList<Job>();

        boolean isNewRecruit = true;
        while (isNewRecruit) {
            page ++;
            String pageString = getRecruitPage(page);

            Iterable<T> list = getListSafely(pageString);

            if (list == null) {
                break;
            }

            for (T item : list) {
                LocalDate recruitStartDate = getStartDate(item);

                if (recruitStartDate.isAfter(date)) {
                    continue;
                }

                if (recruitStartDate.isBefore(date)) {
                    isNewRecruit = false;
                    break;
                }

                Job job = new Job(
                        getCompanyName(),
                        getSubject(item),
                        getLink(item),
                        getStartDate(item),
                        getEndDate(item)
                );
                jobs.add(job);
            }
        }

        return jobs;
    }

    private Iterable<T> getListSafely(String page) {
        Iterable<T> list;

        try {
            list = getList(page);
        } catch (Exception e) {
            list = null;
        }

        return list;
    }

    abstract protected String getCompanyName();
    abstract protected String getRecruitPage(int page) throws IOException, InterruptedException;
    abstract protected Iterable<T> getList(String page);
    abstract protected String getSubject(T item);
    abstract protected String getLink(T item);
    abstract protected LocalDate getStartDate(T item);
    abstract protected LocalDate getEndDate(T item);

}
