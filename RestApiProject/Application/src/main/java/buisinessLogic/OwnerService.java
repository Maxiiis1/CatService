package buisinessLogic;

import contracts.IOwnerService;
import contracts.OwnerOperationResult;
import dao.OwnerJpaRepository;
import dto.CatDTO;
import dto.OwnerDTO;
import jakarta.persistence.EntityNotFoundException;
import models.Cat;
import models.Owner;
import abstractions.IOwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import services.CatConverterToDto;
import services.OwnerConverterToDto;

import java.util.ArrayList;
import java.util.List;

@Service("ownerServiceBean")
public class OwnerService implements IOwnerService {
    private final IOwnerRepository ownerRepository;
    private final OwnerConverterToDto ownerConverter;
    private final CatConverterToDto catConverter;

    @Autowired
    public OwnerService(IOwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
        this.ownerConverter = new OwnerConverterToDto();
        this.catConverter = new CatConverterToDto();
    }

    @Override
    public OwnerOperationResult registerUser(OwnerDTO ownerDto) {
        Owner result = ownerRepository.registerOwner(Owner.builder()
                .name(ownerDto.getName())
                .bornDate(ownerDto.getBornDate())
                .build());

        if (result == null){
            return new OwnerOperationResult.OperationError("Owner was not registered!");
        }

        return new OwnerOperationResult.Success(ownerConverter.convertToDto(result));
    }

    @Override
    public OwnerOperationResult findOwnerById(Long id) {
        Owner owner = ownerRepository.findOwnerById(id);
        if (owner == null){
            return new OwnerOperationResult.OperationError("There is no owner with such id!");
        }

        return new OwnerOperationResult.Success(ownerConverter.convertToDto(owner));
    }

    @Override
    public OwnerOperationResult getAllOwners() {
        List<Owner> result = ownerRepository.getAllOwners();
        if (result == null){
            return new OwnerOperationResult.OperationError("There are no owners!");
        }
        List<OwnerDTO> new_res = new ArrayList<>();
        result.forEach(owners -> new_res.add(ownerConverter.convertToDto(owners)));

        return new OwnerOperationResult.SuccessCheckingOwners(new_res);
    }

    @Override
    public OwnerOperationResult getAllOwnerCats(Long ownerId) {
        Owner owner = ownerRepository.findOwnerById(ownerId);
        if (owner != null){
            List<Cat> result = ownerRepository.getAllOwnerCats(owner);;
            if (result == null){
                return new OwnerOperationResult.OperationError("There are no cats!");
            }
            List<CatDTO> new_res = new ArrayList<>();
            result.forEach(cats -> new_res.add(catConverter.convertToDto(cats)));

            return new OwnerOperationResult.SuccessCheckingCats(new_res);
        }

        return new OwnerOperationResult.OperationError("Owner wasn`t found!");
    }

    @Override
    public OwnerOperationResult deleteOwnerById(Long id) {
        try{
            ownerRepository.deleteOwnerById(id);
           return new OwnerOperationResult.SuccessDelete("Success!");
        }
        catch (EntityNotFoundException ex){
            return new OwnerOperationResult.OperationError(ex.getMessage());
        }
    }
}
