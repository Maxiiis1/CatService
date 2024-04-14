package contracts.cats;

import models.dto.CatDTO;

import java.util.List;

public interface ICatsService {
    CatDTO registerCat(CatDTO catDto);
    List<CatDTO> getAllCats();
    List<CatDTO> getCatsByColor(String color);
    CatDTO findCatById(Long id);
    List<CatDTO> getAllCatFriends(Long id);
    CatDTO updateOwner(Long catId, Long ownerID);
    CatDTO addFriendToCat(Long cat1Id, Long cat2Id);
    void deleteCatById(Long id);
}
