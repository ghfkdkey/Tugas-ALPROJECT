package siqlab.alproject.alproject;

import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface TaskRepository extends CrudRepository<Task,Long>
{
    public List<Task> findTaskByUserid(long userid);
}
