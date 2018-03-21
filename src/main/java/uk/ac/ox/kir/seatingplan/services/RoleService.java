package uk.ac.ox.kir.seatingplan.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.ox.kir.seatingplan.entities.Role;
import uk.ac.ox.kir.seatingplan.repositories.RoleRepository;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    public void create(Role role) {
        roleRepository.save(role);
    }

    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }

    public Role getById(Long id) {
        return roleRepository.getOne(id);
    }

    public Role findRoleByNameExceptId(String name, Long id) {
        return roleRepository.findByNameAndIdNot(name, id);
    }

    public void update(Role role) {
        roleRepository.save(role);
    }

    public void delete(Long id) {
        roleRepository.delete(id);
    }
}
