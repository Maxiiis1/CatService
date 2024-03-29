package restControllers;

import contracts.CatsOperationResult;
import contracts.IOwnerService;
import contracts.OwnerOperationResult;
import dto.OwnerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/owners")
public class OwnerController {
    private final IOwnerService ownerService;

    @Autowired
    public OwnerController(IOwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerOwner(@RequestBody OwnerDTO ownerDTO) {
        return operationWithOwnerResult(ownerService.registerUser(ownerDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findOwnerById(@PathVariable("id") Long id) {
        return operationWithOwnerResult(ownerService.findOwnerById(id));
    }

    @GetMapping
    public ResponseEntity<?> getAllOwners() {
        return Optional.of(ownerService.getAllOwners())
                .map(result -> result instanceof OwnerOperationResult.SuccessCheckingOwners res ?
                        ResponseEntity.ok(res.owners()) :
                        ResponseEntity.badRequest().body(((OwnerOperationResult.OperationError) result).problem()))
                .orElse(ResponseEntity.internalServerError().build());
    }

    @GetMapping("/{ownerId}/cats")
    public ResponseEntity<?> getAllOwnerCats(@PathVariable("ownerId") Long ownerId) {
        return Optional.of(ownerService.getAllOwnerCats(ownerId))
                .map(result -> result instanceof OwnerOperationResult.SuccessCheckingCats res ?
                        ResponseEntity.ok(res.cats()) :
                        ResponseEntity.badRequest().body(((OwnerOperationResult.OperationError) result).problem()))
                .orElse(ResponseEntity.internalServerError().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOwnerById(@PathVariable("id") Long id) {
        return Optional.of(ownerService.deleteOwnerById(id))
                .map(result -> result instanceof OwnerOperationResult.SuccessDelete res ?
                        ResponseEntity.ok(res.success()) :
                        ResponseEntity.badRequest().body(((OwnerOperationResult.OperationError) result).problem()))
                .orElse(ResponseEntity.internalServerError().build());
    }

    private ResponseEntity<?> operationWithOwnerResult(OwnerOperationResult operationResult){
        return Optional.of(operationResult)
                .map(result -> result instanceof OwnerOperationResult.Success res ?
                        ResponseEntity.ok(res.owner()) :
                        ResponseEntity.badRequest().body(((OwnerOperationResult.OperationError) result).problem()))
                .orElse(ResponseEntity.internalServerError().build());
    }
}
