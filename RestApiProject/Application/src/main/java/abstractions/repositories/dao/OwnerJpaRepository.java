package abstractions.repositories.dao;

import models.cats.Cat;
import models.owners.Owner;

import java.util.List;

public interface OwnerJpaRepository {
    Owner registerOwner(Owner owner);
    Owner findOwnerById(Long id);
    List<Owner> getAllOwners();
    List<Cat> getAllOwnerCats(Owner owner);
    void deleteOwnerById(Long id);
}
