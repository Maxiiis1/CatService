package buisinessLogic.services;

import models.dto.OwnerDTO;
import models.owners.Owner;
import org.springframework.stereotype.Component;

@Component
public class OwnerConverterToDto {
    public OwnerDTO convertToDto(Owner owner) {
        OwnerDTO ownerDTO = new OwnerDTO();
        ownerDTO.setBornDate(owner.getBornDate());
        ownerDTO.setName(owner.getName());
        ownerDTO.setRole(owner.getRole());
        ownerDTO.setPassword("XXXX");
        return ownerDTO;
    }
}
