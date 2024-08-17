package siqlab.alproject.alproject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService
{
    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        User user = userRepository.findByUsername(username);

		if (user == null)
			throw new UsernameNotFoundException("User not found");

        ArrayList<User> users = new ArrayList<>();
        users.add(user);
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),mapRolesToAuthorities(users));
    }

    private Collection < ? extends GrantedAuthority> mapRolesToAuthorities(Collection <User> users)
    {
        Collection < ? extends GrantedAuthority> mapRoles = users.stream()
                .map(user -> new SimpleGrantedAuthority(user.getRole()))
                .collect(Collectors.toList());
        return mapRoles;
    }
}
