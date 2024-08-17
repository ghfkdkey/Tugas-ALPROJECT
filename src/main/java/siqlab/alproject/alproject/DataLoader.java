package siqlab.alproject.alproject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationListener<ContextRefreshedEvent>
{
	@Autowired
	UserRepository repository;

    @Override
    public void onApplicationEvent(@SuppressWarnings("null") ContextRefreshedEvent event)
    {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		repository.save(new User("admin",passwordEncoder.encode("Admin32!"),"Admin Alproject","admin"));
    }

}
