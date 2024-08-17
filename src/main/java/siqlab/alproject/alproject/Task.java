package siqlab.alproject.alproject;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tasks")
public class Task
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private long userid;
    private String taskname;
    private LocalDate duedate;
    private LocalDate datedone;

    public Task()
    {
    }

    public Task(long userid,String taskname,LocalDate duedate)
    {
        this.userid = userid;
        this.taskname = taskname;
        this.duedate = duedate;
    }

    public long getSeverityTask()
    {
        long days;

        if (datedone == null)
        {
            days = LocalDate.now().until(duedate,ChronoUnit.DAYS);

            if (days < 0)
                return -2;

            if (days <= 2)
                return -1;
        }
        else
        {
            days = datedone.until(duedate,ChronoUnit.DAYS);

            if (days < 0)
                return -2;
        }

        return 0;
    }
}
