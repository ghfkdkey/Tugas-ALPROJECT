package siqlab.alproject.alproject;

import java.time.LocalDate;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskDto
{
    long id;
    @NotNull
    @Positive
    private long userid;
    @NotEmpty
    private String taskname;
    @NotNull
    @FutureOrPresent
    private LocalDate duedate;
    private LocalDate datedone;
    private long severity;
}
