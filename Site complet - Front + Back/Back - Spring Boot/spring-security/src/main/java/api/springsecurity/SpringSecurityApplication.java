package api.springsecurity;

import api.springsecurity.dao.TaskRepository;
import api.springsecurity.entities.AppRole;
import api.springsecurity.entities.AppUser;
import api.springsecurity.entities.Task;
import api.springsecurity.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.stream.Stream;

@SpringBootApplication()
public class SpringSecurityApplication implements CommandLineRunner {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private AccountService accountService;

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder getBCPE() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void run(String... args) throws Exception {
        accountService.saveUser(new AppUser("admin", "1234", null));
        accountService.saveUser(new AppUser("user", "1234", null));
        accountService.saveRole(new AppRole(null, "ADMIN"));
        accountService.saveRole(new AppRole(null, "USER"));
        accountService.addRole("admin", "ADMIN");
        accountService.addRole("user", "USER"); // Ajouter des utilisateurs dans la base de données au démarrage

        Stream.of("T1","T2","T3").forEach(t->{
            taskRepository.save(new Task(null, t));
        });
    }
}
