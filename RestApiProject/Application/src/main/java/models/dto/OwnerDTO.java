package models.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class OwnerDTO {
    private String name;
    private LocalDate bornDate;
    private String password;
    private String role;
}
