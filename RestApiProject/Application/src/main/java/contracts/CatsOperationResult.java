package contracts;

import dto.CatDTO;
import models.Cat;

import java.util.List;

public sealed interface CatsOperationResult permits
        CatsOperationResult.Success,
        CatsOperationResult.SuccessDelete,
        CatsOperationResult.SuccessCheckingCats,
        CatsOperationResult.OperationError {

    record SuccessDelete(String success) implements CatsOperationResult { }
    record Success(CatDTO cat) implements CatsOperationResult { }
    record SuccessCheckingCats(List<CatDTO> cats) implements CatsOperationResult {}
    record OperationError(String problem) implements CatsOperationResult { }
}