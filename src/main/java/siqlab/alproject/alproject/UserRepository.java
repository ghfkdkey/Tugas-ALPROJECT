package siqlab.alproject.alproject;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long>
{
    User findByUsername(String username);
}
