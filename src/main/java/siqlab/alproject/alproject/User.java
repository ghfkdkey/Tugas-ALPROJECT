package siqlab.alproject.alproject;

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
@Table(name = "users")
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String username;
    private String password;
    private String fullname;
    private String role;

    public User()
    {
    }
   
    public User(String username,String password,String fullname,String role)
    {
     this.username = username;
     this.password = password;
     this.fullname = fullname;
     this.role = role;
    }
   
    @Override
    public String toString()
    {
        return "User [id=" + id + ", username=" + username + ", password=" + password + ", fullname=" + fullname + ", role=" + role + "]";
    }
}
