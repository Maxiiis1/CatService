package contracts;

import dto.OwnerDTO;
import models.Cat;
import models.Owner;

import java.util.List;

public interface IOwnerService {
    OwnerOperationResult registerUser(OwnerDTO ownerDto);
    OwnerOperationResult findOwnerById(Long id);
    OwnerOperationResult getAllOwners();
    OwnerOperationResult getAllOwnerCats(Long ownerId);
    OwnerOperationResult deleteOwnerById(Long id);
}
