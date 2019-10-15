package api.springsecurity.service;

import api.springsecurity.entities.AppRole;
import api.springsecurity.entities.AppUser;

public interface AccountService {
    public AppUser saveUser(AppUser user);
    public AppRole saveRole(AppRole role);
    public void addRole(String username, String roleName);
    public AppUser findUserByUsername(String username);
}
