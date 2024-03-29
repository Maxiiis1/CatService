package contracts;

import dto.CatDTO;
import dto.OwnerDTO;
import models.Cat;
import models.Owner;

import java.util.List;

public interface ICatsService {
    CatsOperationResult registerCat(CatDTO catDto);
    CatsOperationResult getAllCats();
    CatsOperationResult getCatsByColor(String color);
    CatsOperationResult findCatById(Long id);
    CatsOperationResult getAllCatFriends(Long id);
    CatsOperationResult updateOwner(Long catId, Long ownerID);
    CatsOperationResult addFriendToCat(Long cat1Id, Long cat2Id);
    CatsOperationResult deleteCatById(Long id);
}
