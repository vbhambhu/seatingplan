package uk.ac.ox.kir.seatingplan.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.ox.kir.seatingplan.entities.Group;
import uk.ac.ox.kir.seatingplan.entities.Role;
import uk.ac.ox.kir.seatingplan.repositories.GroupRepository;
import uk.ac.ox.kir.seatingplan.repositories.RoleRepository;

import java.util.List;

@Service
public class GroupService {

    @Autowired
    GroupRepository groupRepository;

    public Group findByName(String name) {
        return groupRepository.findByName(name);
    }

    public void create(Group group) {
        groupRepository.save(group);
    }

    public void update(Group group) {
        groupRepository.save(group);
    }

    public Group findByNameExceptId(String name, Long id) {
        return groupRepository.findByNameAndIdNot(name, id);
    }

    public Group getById(Long gid) {
        return groupRepository.findOne(gid);
    }


    public List<Group> findAll() {
        return groupRepository.findAll();
    }

    public void delete(Long id) {
        groupRepository.delete(id);
    }
}
