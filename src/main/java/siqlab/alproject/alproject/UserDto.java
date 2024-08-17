package siqlab.alproject.alproject;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto
{
    private Long id;
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
    @NotEmpty
    private String fullname;
    @NotEmpty
    private String role;

    public UserDto()
    {
    }
    
    public UserDto(long id,String username,String password,String fullname,String role)
    {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.role = role;
    }
}
