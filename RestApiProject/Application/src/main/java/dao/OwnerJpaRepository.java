package dao;

import models.Cat;
import models.Owner;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface OwnerJpaRepository {
    Owner registerOwner(Owner owner);
    Owner findOwnerById(Long id);
    List<Owner> getAllOwners();
    List<Cat> getAllOwnerCats(Owner owner);
    void deleteOwnerById(Long id);
}
