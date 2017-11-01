package myProg.springMvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PersonServiceImpl implements PersonService {
    @Autowired
    PersonDAO personDAO;

    @Transactional
    @Override
    public PersonDTO create(PersonDTO dto) {
        Person entity = toEntity(dto);
        personDAO.persist(entity);
        return toDTO(entity);
    }

    @Transactional
    @Override
    public PersonDTO update(PersonDTO dto) {
        Person person = personDAO.find(dto.getId());
        if (person != null) {
            person.setUsername(dto.getUsername());
            personDAO.persist(person);
            return toDTO(person);
        } else {
            return null;
        }
    }

    @Transactional(readOnly = true)
    @Override
    public PersonDTO find(UUID id) {
        Person person = personDAO.find(id);
        if (person != null) {
            return toDTO(person);
        } else {
            return null;
        }
    }

    @Transactional
    @Override
    public void delete(UUID id) {
        Person person = personDAO.find(id);
        if (person != null) {
            personDAO.delete(person);
        }
    }

    @Override
    public List<PersonDTO> findAll() {
        List<Person> all = personDAO.findAll();
        List<PersonDTO> dtos = new ArrayList<>(all.size());
        for (Person person : all) {
            dtos.add(toDTO(person));
        }
        return dtos;
    }


    private PersonDTO toDTO(Person person) {
        return new PersonDTO(person.getId(), person.getUsername());
    }

    private Person toEntity(PersonDTO dto) {
        Person person = new Person();
        person.setId(dto.getId());
        person.setUsername(dto.getUsername());
        return person;
    }
}
