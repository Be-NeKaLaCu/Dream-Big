package Notification;

import Recruit.Job;

import Facade.Http;

import java.util.HashMap;

public class SlackNotifier implements INotifier {
    @Override
    public void notify(Job job) throws Exception {
        String url = System.getenv("SLACK_HOOK_URL");

        String message = makeMessage(job);
        var map = new HashMap<String, String>();
        map.put("text", message);

        Http.postJson(url, map);
    }

    String makeMessage(Job job) {
        return "*<" + job.link + "|[" + job.company + "] " + job.subject + ">*\n*" + job.startDate.toString() + " ~ " + job.endDate.toString() + "*";
    }
}
