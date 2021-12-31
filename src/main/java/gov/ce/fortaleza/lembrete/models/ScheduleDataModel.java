package gov.ce.fortaleza.lembrete.models;

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
public class ScheduleDataModel implements Serializable {
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
