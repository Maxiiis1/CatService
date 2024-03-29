package restControllers;

import contracts.CatsOperationResult;
import contracts.OwnerOperationResult;
import dto.CatDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.*;
import contracts.ICatsService;
import org.springframework.http.ResponseEntity;

import java.util.Optional;


@RestController
@RequestMapping("/cats")
public class CatsController {
    private final ICatsService catsService;

    @Autowired
    public CatsController(ICatsService catsService) {
        this.catsService = catsService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerCat(@RequestBody CatDTO catDTO) {
        return operationWithCatsResult(catsService.registerCat(catDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findCatById(@PathVariable("id") Long id) {
        return operationWithCatsResult(catsService.findCatById(id));
    }

    @GetMapping
    public ResponseEntity<?> getAllCats() {
        return Optional.of(catsService.getAllCats())
                .map(result -> result instanceof CatsOperationResult.SuccessCheckingCats res ?
                        ResponseEntity.ok(res.cats()) :
                        ResponseEntity.badRequest().body(((CatsOperationResult.OperationError) result).problem()))
                .orElse(ResponseEntity.internalServerError().build());
    }

    @GetMapping("/color/{color}")
    public ResponseEntity<?> getCatsByColor(@PathVariable("color") String color) {
        return Optional.of(catsService.getCatsByColor(color))
                .map(result -> result instanceof CatsOperationResult.SuccessCheckingCats res ?
                        ResponseEntity.ok(res.cats()) :
                        ResponseEntity.badRequest().body(((CatsOperationResult.OperationError) result).problem()))
                .orElse(ResponseEntity.internalServerError().build());
    }

    @GetMapping("/{id}/friends")
    public ResponseEntity<?> getAllCatFriends(@PathVariable("id") Long id) {
        return Optional.of(catsService.getAllCatFriends(id))
                .map(result -> result instanceof CatsOperationResult.SuccessCheckingCats res ?
                        ResponseEntity.ok(res.cats()) :
                        ResponseEntity.badRequest().body(((CatsOperationResult.OperationError) result).problem()))
                .orElse(ResponseEntity.internalServerError().build());
    }

    @PutMapping("/{catId}/owner/{ownerId}")
    public ResponseEntity<?> updateOwner(@PathVariable("catId") Long catId, @PathVariable("ownerId") Long ownerId) {
        return operationWithCatsResult(catsService.updateOwner(catId, ownerId));
    }

    @PutMapping("/{cat1Id}/friend/{cat2Id}")
    public ResponseEntity<?> addFriendToCat(@PathVariable("cat1Id") Long cat1Id, @PathVariable("cat2Id") Long cat2Id) {
        return operationWithCatsResult(catsService.addFriendToCat(cat1Id, cat2Id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCatById(@PathVariable("id") Long id) {
        return Optional.of(catsService.deleteCatById(id))
                .map(result -> result instanceof CatsOperationResult.SuccessDelete res ?
                        ResponseEntity.ok(res.success()) :
                        ResponseEntity.badRequest().body(((CatsOperationResult.OperationError) result).problem()))
                .orElse(ResponseEntity.internalServerError().build());
    }

    private ResponseEntity<?> operationWithCatsResult(CatsOperationResult operationResult){
        return Optional.of(operationResult)
                .map(result -> result instanceof CatsOperationResult.Success res ?
                        ResponseEntity.ok(res.cat()) :
                        ResponseEntity.badRequest().body(((CatsOperationResult.OperationError) result).problem()))
                .orElse(ResponseEntity.internalServerError().build());
    }
}
