import abstractions.repositories.ICatRepository;
import abstractions.repositories.IOwnerRepository;
import buisinessLogic.cats.CatsService;
import buisinessLogic.services.CatConverterToDto;
import contracts.cats.CatsOperationResult;
import models.cats.CatsColor;
import models.dto.CatDTO;
import models.cats.Cat;
import models.owners.Owner;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class CatRepositoryTests {
    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @Mock
    private Transaction transaction;

    @Mock
    private ICatRepository catRepository;

    @Mock
    private IOwnerRepository ownerRepository;
    @Mock
    private CatConverterToDto converter;

    @InjectMocks
    private CatsService catService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        catService = new CatsService(catRepository, ownerRepository);
    }

    @Test
    public void testRegisterCat() {
        Cat cat = Cat.builder().name("Vasya").build();
        CatDTO catDTO = new CatDTO();
        catDTO.setBreed("Сиамская");
        catDTO.setColor("Red");
        catDTO.setName("Vasya");

        when(catRepository.registerCat(cat)).thenReturn(cat);

        CatDTO result = catService.registerCat(catDTO);

        assertEquals(cat.getName(), result.getName());
    }

    @Test
    public void testFindCatById() {
        Owner owner = Owner.builder().build();
        Cat cat = Cat.builder().id(1L).name("Vasya").breed("Сиамская").owner(owner).color(CatsColor.Red).build();
        CatDTO catDTO = new CatDTO();
                catDTO.setBreed("Сиамская");
                        catDTO.setColor("Red");
                                catDTO.setOwnerId(1L);
        catDTO.setName("Vasya");

        when(catRepository.findCatById(1L)).thenReturn(cat);
        when(ownerRepository.findOwnerById(1L)).thenReturn(owner);
        when(catRepository.registerCat(cat)).thenReturn(cat);

        catService.registerCat(catDTO);
        CatDTO result = catService.findCatById(1L);

        assertEquals(cat.getName(), result.getName());
    }
}
