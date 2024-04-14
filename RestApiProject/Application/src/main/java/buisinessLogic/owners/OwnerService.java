package buisinessLogic.owners;

import contracts.owners.IOwnerService;
import contracts.owners.OwnerOperationResult;
import models.dto.CatDTO;
import models.dto.OwnerDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import models.cats.Cat;
import models.owners.Owner;
import abstractions.repositories.IOwnerRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import buisinessLogic.services.CatConverterToDto;
import buisinessLogic.services.OwnerConverterToDto;

import java.util.ArrayList;
import java.util.List;

@Service("ownerServiceBean")
@RequiredArgsConstructor
public class OwnerService implements IOwnerService {
    private final IOwnerRepository ownerRepository;
    private final PasswordEncoder passwordEncoder;
    private final OwnerConverterToDto ownerConverter;
    private final CatConverterToDto catConverter;

    @Override
    public OwnerDTO registerUser(OwnerDTO ownerDto) throws EntityNotFoundException {
        Owner result = ownerRepository.registerOwner(Owner.builder()
                .name(ownerDto.getName())
                .bornDate(ownerDto.getBornDate())
                .password(passwordEncoder.encode(ownerDto.getPassword()))
                .role(ownerDto.getRole())
                .build());

        if (result == null){
            throw new EntityNotFoundException("Owner was not registered!");
        }

        return ownerConverter.convertToDto(result);
    }

    @Override
    public OwnerDTO findOwnerById(Long id) throws EntityNotFoundException {
        Owner owner = ownerRepository.findOwnerById(id);
        if (owner == null){
            throw new EntityNotFoundException("There is no owner with such id!");
        }

        return ownerConverter.convertToDto(owner);
    }

    @Override
    public List<OwnerDTO> getAllOwners() throws EntityNotFoundException {
        List<Owner> result = ownerRepository.getAllOwners();
        if (result == null){
            throw new EntityNotFoundException("There are no owners!");
        }
        List<OwnerDTO> new_res = new ArrayList<>();
        result.forEach(owners -> new_res.add(ownerConverter.convertToDto(owners)));

        return new_res;
    }

    @Override
    public List<CatDTO> getAllOwnerCats(Long ownerId) throws EntityNotFoundException {
        Owner owner = ownerRepository.findOwnerById(ownerId);
        if (owner != null){
            List<Cat> result = ownerRepository.getAllOwnerCats(owner);;
            if (result == null){
                throw new EntityNotFoundException("There are no cats!");
            }
            List<CatDTO> new_res = new ArrayList<>();
            result.forEach(cats -> new_res.add(catConverter.convertToDto(cats)));

            return new_res;
        }

        throw new EntityNotFoundException("Owner wasn't found!");
    }

    @Override
    public void deleteOwnerById(Long id) throws EntityNotFoundException {
        try{
            ownerRepository.deleteOwnerById(id);
        }
        catch (EntityNotFoundException ex){
            throw new EntityNotFoundException(ex.getMessage());
        }
    }
}
