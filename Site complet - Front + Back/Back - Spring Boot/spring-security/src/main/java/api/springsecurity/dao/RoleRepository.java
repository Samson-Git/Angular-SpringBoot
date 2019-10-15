package api.springsecurity.dao;

import api.springsecurity.entities.AppRole;
import api.springsecurity.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<AppRole, Long> {
    public AppRole findByRoleName(String RoleName);
}
