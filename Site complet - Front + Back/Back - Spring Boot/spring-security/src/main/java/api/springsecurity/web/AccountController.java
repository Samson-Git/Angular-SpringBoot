package api.springsecurity.web;

import api.springsecurity.entities.AppRole;
import api.springsecurity.entities.AppUser;
import api.springsecurity.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.management.relation.Role;
import java.util.ArrayList;
import java.util.Collection;

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;
    @PostMapping("/register")
    public AppUser register (@RequestBody RegisterForm registerForm) {

        if (!registerForm.getPassword().equals(registerForm.getRepassword())) {
            throw new RuntimeException("You must confirm your password !");
        }
        AppUser user = accountService.findUserByUsername(registerForm.getUsername());
        if (user != null) {
            throw new RuntimeException("This user already exist !");
        }
        AppUser appuser = new AppUser();
        appuser.setUsername(registerForm.getUsername());
        appuser.setPassword(registerForm.getPassword());
        accountService.saveUser(appuser);
        accountService.addRole(registerForm.getUsername(), "USER");
        return appuser;

    }
}
