package Notification;

import Recruit.Job;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;

public interface INotifier {
    public void notify(Job job) throws Exception;
}
