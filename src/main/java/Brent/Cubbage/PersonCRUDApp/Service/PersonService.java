package Brent.Cubbage.PersonCRUDApp.Service;

import Brent.Cubbage.PersonCRUDApp.Entity.Person;
import Brent.Cubbage.PersonCRUDApp.Repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService {

    private PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person create(Person person) {
        return personRepository.save(person);
    }

    public Person readById(Long id) {
        return personRepository.findById(id).get();
    }

    public List<Person> readAll(){
        Iterable<Person> personIterable = personRepository.findAll();
        List<Person> personList = new ArrayList<>();

        personIterable.forEach(personList::add);
        return personList;
    }

    public Person update(Long id, Person person){
        Person updatePerson = readById(id);
        updatePerson.setName(person.getName());
        return personRepository.save(updatePerson);
    }

    public Person delete(Long id){
        Person deleted = readById(id);
        personRepository.deleteById(id);
        return deleted;
    }

}


