package Recruit;

import java.time.LocalDate;

public class Job {
    public String subject;
    public LocalDate startDate;
    public LocalDate endDate;

    public Job (String subject, LocalDate startDate, LocalDate endDate) {
        this.subject = subject;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}