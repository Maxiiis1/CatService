package buisinessLogic;

import abstractions.IOwnerRepository;
import contracts.CatsOperationResult;
import contracts.ICatsService;
import dao.CatJpaRepository;
import dao.OwnerJpaRepository;
import dto.CatDTO;
import jakarta.persistence.EntityNotFoundException;
import models.Cat;
import models.CatsColor;
import abstractions.ICatRepository;
import models.Owner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import services.CatConverterToDto;
import services.OwnerConverterToDto;

import java.util.ArrayList;
import java.util.List;

@Service("catsServiceBean")
public class CatsService implements ICatsService {
    private final ICatRepository catRepository;
    private final IOwnerRepository ownerRepository;
    private final CatConverterToDto catConverter;

    @Autowired
    public CatsService(ICatRepository catRepository, IOwnerRepository ownerRepository) {
        this.catRepository = catRepository;
        this.ownerRepository = ownerRepository;
        this.catConverter = new CatConverterToDto();
    }

    @Override
    public CatsOperationResult registerCat(CatDTO catDto) {
        Cat result = catRepository.registerCat(Cat.builder()
                .name(catDto.getName())
                .breed(catDto.getBreed())
                .color(CatsColor.valueOf(catDto.getColor()))
                .owner(ownerRepository.findOwnerById(catDto.getOwnerId()))
                .build());
        if (result == null){
            return new CatsOperationResult.OperationError("Cat was not registered!");
        }

        return new CatsOperationResult.Success(catConverter.convertToDto(result));
    }

    @Override
    public CatsOperationResult getAllCats() {
        List<Cat> result = catRepository.getAllCats();
        if (result == null){
            return new CatsOperationResult.OperationError("There are no cats!");
        }
        List<CatDTO> new_res = new ArrayList<>();
        result.forEach(cats -> new_res.add(catConverter.convertToDto(cats)));

        return new CatsOperationResult.SuccessCheckingCats(new_res);
    }

    @Override
    public CatsOperationResult getCatsByColor(String color) {
        List<Cat> result = catRepository.getCatsByColor(color);
        if (result == null){
            return new CatsOperationResult.OperationError("There are no cats with such color!");
        }
        List<CatDTO> new_res = new ArrayList<>();
        result.forEach(cats -> new_res.add(catConverter.convertToDto(cats)));

        return new CatsOperationResult.SuccessCheckingCats(new_res);
    }

    @Override
    public CatsOperationResult findCatById(Long id) {
        Cat result = catRepository.findCatById(id);
        if (result == null){
            return new CatsOperationResult.OperationError("There is no cat with such id!");
        }

        return new CatsOperationResult.Success(catConverter.convertToDto(result));
    }

    @Override
    public CatsOperationResult getAllCatFriends(Long id) {
        Cat cat = catRepository.findCatById(id);
        if (cat != null){
            List<Cat> result = catRepository.getAllCatFriends(cat);
            if (result == null){
                return new CatsOperationResult.OperationError("There are no friends!");
            }
            List<CatDTO> new_res = new ArrayList<>();
            result.forEach(cats -> new_res.add(catConverter.convertToDto(cats)));

            return new CatsOperationResult.SuccessCheckingCats(new_res);
        }

        return new CatsOperationResult.OperationError("No cat with such id!");
    }

    @Override
    public CatsOperationResult updateOwner(Long catId, Long ownerID) {
        Cat cat = catRepository.findCatById(catId);
        Owner owner = ownerRepository.findOwnerById(ownerID);
        if(owner != null && cat != null){
            Cat result = catRepository.updateOwner(cat, owner);
            if (result == null){
                return new CatsOperationResult.OperationError("Can`t update owner!");
            }

            return new CatsOperationResult.Success(catConverter.convertToDto(result));
        }

        return new CatsOperationResult.OperationError("No such cat or owner!");

    }

    @Override
    public CatsOperationResult addFriendToCat(Long cat1Id, Long cat2Id) {
        Cat cat = catRepository.findCatById(cat1Id);
        Cat friend = catRepository.findCatById(cat2Id);
        if(friend != null && cat != null) {
            Cat result = catRepository.addFriendToCat(cat, friend);
            if (result == null) {
                return new CatsOperationResult.OperationError("Can`t add friend!");
            }

            return new CatsOperationResult.Success(catConverter.convertToDto(result));
        }

        return new CatsOperationResult.OperationError("No such cat or friend!");
    }

    @Override
    public CatsOperationResult deleteCatById(Long id) {
        try{
            catRepository.deleteCatById(id);
            return new CatsOperationResult.SuccessDelete("Success!");
        }
        catch (EntityNotFoundException ex){
            return new CatsOperationResult.OperationError(ex.getMessage());
        }
    }
}
