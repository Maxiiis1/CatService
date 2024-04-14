package buisinessLogic.services;

import models.dto.CatDTO;
import models.cats.Cat;
import org.springframework.stereotype.Component;

@Component
public class CatConverterToDto {
    public CatDTO convertToDto(Cat cat) {
        CatDTO catDTO = new CatDTO();
        catDTO.setName(cat.getName());
        catDTO.setBreed(cat.getBreed());
        catDTO.setColor(cat.getColor().toString());
        catDTO.setOwnerId(cat.getOwner().getId());
        catDTO.setOwnerName(cat.getOwner().getName());
        return catDTO;
    }
}
