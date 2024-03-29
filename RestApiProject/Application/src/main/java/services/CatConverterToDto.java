package services;

import dto.CatDTO;
import models.Cat;

public class CatConverterToDto {
    public CatDTO convertToDto(Cat cat) {
        CatDTO catDTO = new CatDTO();
        catDTO.setName(cat.getName());
        catDTO.setBreed(cat.getBreed());
        catDTO.setColor(cat.getColor().toString());
        catDTO.setOwnerId(cat.getOwner().getId());
        return catDTO;
    }
}
