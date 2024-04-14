package contracts.cats;

import models.dto.CatDTO;
import models.dto.OwnerDTO;

import java.util.List;

public sealed interface CatsOperationResult permits
        CatsOperationResult.Success,
        CatsOperationResult.SuccessLogin,
        CatsOperationResult.SuccessDelete,
        CatsOperationResult.SuccessCheckingCats,
        CatsOperationResult.OperationError {

    record SuccessDelete(String success) implements CatsOperationResult { }
    record Success(CatDTO cat) implements CatsOperationResult { }
    record SuccessLogin(OwnerDTO owner) implements CatsOperationResult { }
    record SuccessCheckingCats(List<CatDTO> cats) implements CatsOperationResult {}
    record OperationError(String problem) implements CatsOperationResult { }
}