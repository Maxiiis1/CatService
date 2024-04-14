package contracts.owners;

import models.dto.CatDTO;
import models.dto.OwnerDTO;

import java.util.List;

public sealed interface OwnerOperationResult permits
        OwnerOperationResult.Success,
        OwnerOperationResult.SuccessDelete,
        OwnerOperationResult.SuccessCheckingCats,
        OwnerOperationResult.SuccessCheckingOwners,
        OwnerOperationResult.OperationError {

    record SuccessDelete(String success) implements OwnerOperationResult { }
    record Success(OwnerDTO owner) implements OwnerOperationResult { }
    record SuccessCheckingCats(List<CatDTO> cats) implements OwnerOperationResult {}
    record SuccessCheckingOwners(List<OwnerDTO> owners) implements OwnerOperationResult {}
    record OperationError(String problem) implements OwnerOperationResult { }
}
