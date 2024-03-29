package services;

import dto.OwnerDTO;
import models.Owner;

public class OwnerConverterToDto {
    public OwnerDTO convertToDto(Owner owner) {
        OwnerDTO ownerDTO = new OwnerDTO();
        ownerDTO.setName(owner.getName());
        ownerDTO.setBornDate(owner.getBornDate());
        return ownerDTO;
    }
}
