package siqlab.alproject.alproject;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService
{
    @Autowired
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void saveUser(UserDto userDto)
    {
        User user = new User(userDto.getUsername(),passwordEncoder.encode(userDto.getPassword()),userDto.getFullname(),userDto.getRole());
        userRepository.save(user);
    }

    public User findByUsername(String username)
    {
        return userRepository.findByUsername(username);
    }

    public List<UserDto> findAllUsers()
    {
        List<User> users = (List<User>) userRepository.findAll();
        return users.stream().map((user)->convertEntityToDto(user)).collect(Collectors.toList());
    }

    private UserDto convertEntityToDto(User user)
    {
        return new UserDto(user.getId(),user.getUsername(),user.getPassword(),user.getFullname(),user.getRole());
    }

    public Optional<User> findById(long id)
    {
        return userRepository.findById(id);
    }

    public void removeUser(User user)
    {
        userRepository.delete(user);
    }
}
