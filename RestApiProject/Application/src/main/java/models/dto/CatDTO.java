package models.dto;

import lombok.Data;

@Data
public class CatDTO {
    private String name;
    private String breed;
    private String color;
    private Long ownerId;
    private String ownerName;
}
