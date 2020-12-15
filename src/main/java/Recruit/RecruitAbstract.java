package Recruit;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public abstract class RecruitAbstract<T> {
    public ArrayList<Job> getJobsOnTheDayOf(LocalDate date) throws IOException, InterruptedException {
        int page = 0;
        ArrayList<Job> jobs = new ArrayList<Job>();

        while (true) {
            page ++;
            String pageString = getRecruitPage(page);

            Iterable<T> list = getListSafely(pageString);

            ArrayList<Job> jobsInList = getJobsInList(list);

            jobs.addAll(jobsInList);

            if (isLastPage(jobsInList, date)) {
                break;
            }
        }

        jobs.removeIf(job -> !job.startDate.isEqual(date));

        return jobs;
    }

    private Iterable<T> getListSafely(String page) {
        Iterable<T> list;

        try {
            list = getList(page);
        } catch (Exception e) {
            list = new ArrayList<>();
        }

        return list;
    }

    private ArrayList<Job> getJobsInList(Iterable<T> list) {
        ArrayList<Job> jobs = new ArrayList<>();

        for (T item : list) {
            try {
                Job job = new Job(
                        getCompanyName(),
                        getSubject(item),
                        getLink(item),
                        getStartDate(item),
                        getEndDate(item)
                );
                jobs.add(job);
            } catch (Exception ignored) {}
        }

        return jobs;
    }

    private boolean isLastPage(ArrayList<Job> jobsInList, LocalDate date) {
        return jobsInList.size() == 0 || jobsInList.get(jobsInList.size() - 1).startDate.isBefore(date);
    }

    abstract protected String getCompanyName();
    abstract protected String getRecruitPage(int page) throws IOException, InterruptedException;
    abstract protected Iterable<T> getList(String page);
    abstract protected String getSubject(T item);
    abstract protected String getLink(T item);
    abstract protected LocalDate getStartDate(T item);
    abstract protected LocalDate getEndDate(T item);

}
