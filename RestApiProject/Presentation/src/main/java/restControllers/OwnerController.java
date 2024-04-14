package restControllers;

import buisinessLogic.owners.OwnerService;
import lombok.RequiredArgsConstructor;
import models.dto.CatDTO;
import models.dto.OwnerDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/owners")
public class OwnerController {
    private final OwnerService ownerService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody OwnerDTO ownerDTO) {
        ownerService.registerUser(ownerDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findOwnerById(@PathVariable("id") Long id) {
        OwnerDTO ownerDTO = ownerService.findOwnerById(id);
        return ResponseEntity.ok(ownerDTO);
    }

    @GetMapping
    public ResponseEntity<?> getAllOwners() {
        List<OwnerDTO> owners = ownerService.getAllOwners();
        return ResponseEntity.ok(owners);
    }

    @GetMapping("/{id}/cats")
    public ResponseEntity<?> getAllOwnerCats(@PathVariable("id") Long ownerId) {
        List<CatDTO> cats = ownerService.getAllOwnerCats(ownerId);
        return ResponseEntity.ok(cats);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOwnerById(@PathVariable("id") Long id) {
        ownerService.deleteOwnerById(id);
        return ResponseEntity.ok().build();
    }
}
