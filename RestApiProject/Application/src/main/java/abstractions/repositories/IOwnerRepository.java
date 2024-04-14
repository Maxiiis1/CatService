package abstractions.repositories;

import models.cats.Cat;
import models.owners.Owner;

import java.util.List;

public interface IOwnerRepository {
    Owner registerOwner(Owner owner);
    Owner findOwnerById(Long id);
    Owner findOwnerByName(String name);
    List<Owner> getAllOwners();
    List<Cat> getAllOwnerCats(Owner owner);
    void deleteOwnerById(Long id);
}
