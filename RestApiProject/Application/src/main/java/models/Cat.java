package models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "cat")
public class Cat{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String breed;

    @Enumerated(EnumType.STRING)
    @Column(name = "color")
    private CatsColor color;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "friendship",
            joinColumns = @JoinColumn(name = "friend_1"),
            inverseJoinColumns = @JoinColumn(name = "friend_2")
    )
    private List<Cat> friends;
}
