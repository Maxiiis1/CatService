package abstractions;

import models.Cat;
import models.Owner;

import java.util.List;

public interface IOwnerRepository {
    Owner registerOwner(Owner owner);
    Owner findOwnerById(Long id);
    List<Owner> getAllOwners();
    List<Cat> getAllOwnerCats(Owner owner);
    void deleteOwnerById(Long id);
}
