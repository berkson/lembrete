package gov.ce.fortaleza.lembrete.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.quartz.Job;
import org.quartz.JobDataMap;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Created by berkson
 * Date: 31/12/2021
 * Time: 00:45
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDataModel implements Serializable {


    public ScheduleDataModel(Class<? extends Job> jobClass, boolean isDurable,
                             String jobName, String jobGroup, String jobDescription,
                             String triggerName, String triggerGroup,
                             String triggerDescription,
                             LocalDate startTriggerDate, String cronExpression) {
        this.jobClass = jobClass;
        this.isDurable = isDurable;
        this.jobName = jobName;
        this.jobGroup = jobGroup;
        this.jobDescription = jobDescription;
        this.triggerName = triggerName;
        this.triggerGroup = triggerGroup;
        this.triggerDescription = triggerDescription;
        this.startTriggerDate = startTriggerDate;
        this.cronExpression = cronExpression;
    }

    private Class<? extends Job> jobClass;
    private boolean isDurable;
    private String jobName;
    private String jobGroup;
    private String jobDescription;
    private String triggerName;
    private String triggerGroup;
    private String triggerDescription;
    private LocalDate startTriggerDate;
    private String cronExpression;
    private JobDataMap jobDataMap;
}
