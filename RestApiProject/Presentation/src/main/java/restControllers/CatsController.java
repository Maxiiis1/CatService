package restControllers;

import jakarta.persistence.EntityNotFoundException;
import models.dto.CatDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import contracts.cats.ICatsService;
import org.springframework.http.ResponseEntity;

import java.util.List;


@RestController
@RequestMapping("/cats")
@RequiredArgsConstructor
public class CatsController {
    private final ICatsService catsService;

    @PostMapping("/register")
    public ResponseEntity<?> registerCat(@RequestBody CatDTO catDTO) {
        catsService.registerCat(catDTO);
        return ResponseEntity.ok("Success register!");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findCatById(@PathVariable("id") Long id) {
        CatDTO catDTO = catsService.findCatById(id);
        return ResponseEntity.ok(catDTO);
    }

    @GetMapping
    public ResponseEntity<?> getAllCats() {
        List<CatDTO> cats = catsService.getAllCats();
        return ResponseEntity.ok(cats);
    }

    @GetMapping("/color/{color}")
    public ResponseEntity<?> getCatsByColor(@PathVariable("color") String color) {
        List<CatDTO> cats = catsService.getCatsByColor(color);
        return ResponseEntity.ok(cats);
    }

    @GetMapping("/{id}/friends")
    public ResponseEntity<?> getAllCatFriends(@PathVariable("id") Long id) {
        List<CatDTO> friends = catsService.getAllCatFriends(id);
        return ResponseEntity.ok(friends);
    }

    @PutMapping("/{catId}/owner/{ownerId}")
    public ResponseEntity<?> updateOwner(@PathVariable("catId") Long catId, @PathVariable("ownerId") Long ownerId) {
        CatDTO updatedCat = catsService.updateOwner(catId, ownerId);
        return ResponseEntity.ok(updatedCat);
    }

    @PutMapping("/{cat1Id}/friend/{cat2Id}")
    public ResponseEntity<?> addFriendToCat(@PathVariable("cat1Id") Long cat1Id, @PathVariable("cat2Id") Long cat2Id) {
        CatDTO updatedCat = catsService.addFriendToCat(cat1Id, cat2Id);
        return ResponseEntity.ok(updatedCat);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCatById(@PathVariable("id") Long id) {
        catsService.deleteCatById(id);
        return ResponseEntity.ok().build();
    }
}
