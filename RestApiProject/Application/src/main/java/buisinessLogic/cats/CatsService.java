package buisinessLogic.cats;

import abstractions.repositories.ICatRepository;
import abstractions.repositories.IOwnerRepository;
import contracts.cats.ICatsService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import models.dto.CatDTO;
import models.dto.OwnerDTO;
import models.cats.Cat;
import models.cats.CatsColor;
import models.owners.Owner;
//import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import buisinessLogic.services.CatConverterToDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service("catsServiceBean")
@RequiredArgsConstructor
public class CatsService implements ICatsService {
    private final ICatRepository catRepository;
    private final IOwnerRepository ownerRepository;
    private final CatConverterToDto catConverter;

    @Override
    public CatDTO registerCat(CatDTO catDto) throws EntityNotFoundException {
        Cat result = catRepository.registerCat(Cat.builder()
                .name(catDto.getName())
                .breed(catDto.getBreed())
                .color(CatsColor.valueOf(catDto.getColor()))
                .owner(ownerRepository.findOwnerById(catDto.getOwnerId()))
                .build());
        if (result == null) {
            throw new EntityNotFoundException("Cat was not registered!");
        }

        return catConverter.convertToDto(result);
    }

    @Override
    public List<CatDTO> getAllCats() throws EntityNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        List<Cat> result = catRepository.getAllCats();
        if (result == null) {
            throw new EntityNotFoundException("There are no cats!");
        }

        List<CatDTO> new_res = result.stream()
                .filter(cat -> cat.getOwner() != null && cat.getOwner().getName().equals(currentUsername))
                .map(catConverter::convertToDto)
                .collect(Collectors.toList());

        return new_res;
    }

    @Override
    public List<CatDTO> getCatsByColor(String color) throws EntityNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        List<Cat> result = catRepository.getCatsByColor(color);
        if (result == null) {
            throw new EntityNotFoundException("There are no cats with such color!");
        }
        List<CatDTO> new_res = result.stream()
                .filter(cat -> cat.getOwner() != null && cat.getOwner().getName().equals(currentUsername))
                .map(catConverter::convertToDto)
                .collect(Collectors.toList());

        return new_res;
    }

    @Override
    public CatDTO findCatById(Long id) throws EntityNotFoundException {
        Cat result = catRepository.findCatById(id);
        if (result == null) {
            throw new EntityNotFoundException("There is no cat with such id!");
        }

        return catConverter.convertToDto(result);
    }

    @Override
    public List<CatDTO> getAllCatFriends(Long id) throws EntityNotFoundException {
        Cat cat = catRepository.findCatById(id);
        if (cat != null) {
            List<Cat> result = catRepository.getAllCatFriends(cat);
            if (result == null) {
                throw new EntityNotFoundException("There are no friends!");
            }
            List<CatDTO> new_res = new ArrayList<>();
            result.forEach(cats -> new_res.add(catConverter.convertToDto(cats)));

            return new_res;
        }

        throw new EntityNotFoundException("No cat with such id!");
    }

    @Override
    public CatDTO updateOwner(Long catId, Long ownerId) throws EntityNotFoundException {
        Cat cat = catRepository.findCatById(catId);
        Owner owner = ownerRepository.findOwnerById(ownerId);
        if (owner != null && cat != null) {
            Cat result = catRepository.updateOwner(cat, owner);
            if (result == null) {
                throw new EntityNotFoundException("Can't update owner!");
            }

            return catConverter.convertToDto(result);
        }

        throw new EntityNotFoundException("No such cat or owner!");
    }

    @Override
    public CatDTO addFriendToCat(Long cat1Id, Long cat2Id) throws EntityNotFoundException {
        Cat cat = catRepository.findCatById(cat1Id);
        Cat friend = catRepository.findCatById(cat2Id);
        if (friend != null && cat != null) {
            Cat result = catRepository.addFriendToCat(cat, friend);
            if (result == null) {
                throw new EntityNotFoundException("Can't add friend!");
            }

            return catConverter.convertToDto(result);
        }

        throw new EntityNotFoundException("No such cat or friend!");
    }

    @Override
    public void deleteCatById(Long id) throws EntityNotFoundException {
        catRepository.deleteCatById(id);
    }
}
