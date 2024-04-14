package contracts.owners;

import models.dto.CatDTO;
import models.dto.OwnerDTO;

import java.util.List;

public interface IOwnerService {
    OwnerDTO registerUser(OwnerDTO ownerDto);
    OwnerDTO findOwnerById(Long id);
    List<OwnerDTO> getAllOwners();
    List<CatDTO> getAllOwnerCats(Long ownerId);
    void deleteOwnerById(Long id);
}
