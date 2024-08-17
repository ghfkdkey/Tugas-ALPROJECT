package siqlab.alproject.alproject;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;

@Controller
public class AlprojectController
{
    private UserService userService;
    private TaskService taskService;

    public AlprojectController(UserService userService,TaskService taskService)
    {
        this.userService = userService;
        this.taskService = taskService;
    }
    
    @GetMapping("/")
    public String index(Model model)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        if (!(auth instanceof AnonymousAuthenticationToken))
            return "redirect:/home";

        model.addAttribute("pagename","Login");
        return "index";
    }

    @GetMapping("/home")
    public String home(Model model)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        model = setPageAttribute(model,"Beranda");

        if (user.getRole().equals("admin"))
        {
            List<UserDto> users = userService.findAllUsers();
            model.addAttribute("users", users);   
            return "homeadmin";
        }
        else
        {
            model.addAttribute("user",user);
            List<Task> tasks = taskService.findTaskByUserid(user.getId());
    
            if (tasks.size() > 0)
            {
                List<TaskDto> taskDtos = tasks.stream().map((task)->convertEntityToDto(task)).collect(Collectors.toList());
                model.addAttribute("tasks",taskDtos);
            }

            return "home";
        }
    }

    private TaskDto convertEntityToDto(Task task)
    {
        TaskDto taskDto = new TaskDto();
        taskDto.setId(task.getId());
        taskDto.setUserid(task.getUserid());
        taskDto.setTaskname(task.getTaskname());
        taskDto.setDuedate(task.getDuedate());
        taskDto.setDatedone(task.getDatedone());
        taskDto.setSeverity(task.getSeverityTask());
        return taskDto;
    }

    @GetMapping("/user/adduser")
    public String addUser(Model model)
    {
        model = setPageAttribute(model,"Add User");
        UserDto user = new UserDto();
        model.addAttribute("user",user);
        return "adduser";
    }

    @PostMapping("/user/saveuser")
    public String saveUser(@Valid @ModelAttribute("user") UserDto user,BindingResult result,Model model)
    {
        User userExist = userService.findByUsername(user.getUsername());

        if (userExist != null)
            result.rejectValue("username","1","There is already an account registered with that username");

        if (result.hasErrors())
        {
            model.addAttribute("user", user);
            return "adduser";
        }

        userService.saveUser(user);
        return "redirect:/home";
    }

    @PostMapping("/user/deluser")
    public String delUser(@RequestParam("userid") long userid)
    {
        Optional<User> user = userService.findById(userid);

        if (user.isPresent())
            userService.removeUser(user.get());
            
        return "redirect:/home";
    }

    @GetMapping("/userdetail")
    public String userDetail(@RequestParam("userid") long userid,Model model)
    {
        model = setPageAttribute(model,"User Detail");
        Optional<User> user = userService.findById(userid);

        if (!user.isPresent())
            return "redirect:/home";

        List<Task> tasks = taskService.findTaskByUserid(userid);
        model.addAttribute("user",user.get());

        if (tasks.size() > 0)
        {
            List<TaskDto> taskDtos = tasks.stream().map((task)->convertEntityToDto(task)).collect(Collectors.toList());
            model.addAttribute("tasks",taskDtos);
        }
            
        return "userdetail";
    }

    @GetMapping("/task/addtask")
    public String addTask(@RequestParam("userid") long userid,Model model)
    {
        Optional<User> user = userService.findById(userid);

        if (!user.isPresent())
            return "redirect:/home";

        model = setPageAttribute(model,"Add Task");
        model.addAttribute("user", user.get());
        TaskDto task = new TaskDto();
        task.setUserid(userid);
        model.addAttribute("task",task);
        return "addtask";
    }

    @PostMapping("/task/savetask")
    public String saveTask(@Valid @ModelAttribute("task") TaskDto task,BindingResult result,Model model)
    {
        taskService.saveTask(task);
        return "redirect:/userdetail?userid=" + task.getUserid();
    }

    @PostMapping("/task/deltask")
    public String delTask(@RequestParam("taskid") long taskid)
    {
        Optional<Task> task = taskService.findById(taskid);

        if (task.isPresent())
            taskService.removeTask(task.get());
            
        return "redirect:/userdetail?userid=" + task.get().getUserid();
    }

    @PostMapping("/adddonedate")
    public String addDoneDate(@RequestParam("taskid") long taskid,@RequestParam("datedone") String datedone)
    {
        Optional<Task> opttask = taskService.findById(taskid);
        Logger logger = LoggerFactory.getLogger(AlprojectController.class);

        if (!opttask.isPresent())
            return "redirect:/home";

        Task task = opttask.get();
        task.setDatedone(LocalDate.parse(datedone));
        logger.warn("datedone " + datedone + " " + opttask.get().getId() + " " + opttask.get().getTaskname() + " " + String.valueOf(task.getDatedone()));
        taskService.updateDateDone(task);
        return "redirect:/home";
    }

    private Model setPageAttribute(Model model,String pageName)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        model.addAttribute("pagename",pageName);
        model.addAttribute("userfullname",user.getFullname());
        return model;
    }
}
