package Brent_Cubbage.PersonCRUDApp.Repository;

import Brent_Cubbage.PersonCRUDApp.Entity.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {

}
