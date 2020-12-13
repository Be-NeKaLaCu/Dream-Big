package Handler;

import Notification.SlackNotifier;
import Recruit.Job;
import Recruit.RecruitFactory;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.ScheduledEvent;

import java.time.LocalDate;
import java.util.ArrayList;

public class CWEventHandler implements RequestHandler<ScheduledEvent, String> {
    @Override
    public String handleRequest(ScheduledEvent event, Context context)
    {
        LocalDate yesterday = LocalDate.now().minusDays(1);

        try {
            for (var recruit : RecruitFactory.makeAll()) {
                ArrayList<Job> jobs = recruit.getJobsOnTheDayOf(yesterday);

                var noti = new SlackNotifier();
                for (Job job: jobs) {
                    noti.notify(job);
                }

            }
            return "200 OK";

        } catch (Exception e) {
            return "";
        }
    }
}