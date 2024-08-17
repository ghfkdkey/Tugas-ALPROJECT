package siqlab.alproject.alproject;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService
{
    @Autowired
    private TaskRepository taskRepository;

    public void saveTask(TaskDto taskDto)
    {
        Task task = new Task(taskDto.getUserid(),taskDto.getTaskname(),taskDto.getDuedate());
        taskRepository.save(task);
    }

    public void updateDateDone(Task task)
    {
        Optional<Task> optTask = taskRepository.findById(task.getId());

        if (!optTask.isPresent())
            return;

        Task taskUpdate = optTask.get();
        taskUpdate.setDatedone(task.getDatedone());
        taskRepository.save(taskUpdate);
    }

    public List<Task> findTaskByUserid(long userid)
    {
        return taskRepository.findTaskByUserid(userid);
    }

    public void removeTask(Task task)
    {
        taskRepository.delete(task);
    }

    public Optional<Task> findById(long id)
    {
        return taskRepository.findById(id);
    }
}
