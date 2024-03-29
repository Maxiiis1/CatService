import abstractions.ICatRepository;
import abstractions.IOwnerRepository;
import buisinessLogic.CatsService;
import contracts.CatsOperationResult;
import dao.CatJpaRepository;
import dao.OwnerJpaRepository;
import dto.CatDTO;
import models.Cat;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import repositories.CatRepository;
import repositories.OwnerRepository;

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

    @InjectMocks
    private CatsService catService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        catService = new CatsService(catRepository, ownerRepository);
    }

    @Test
    public void testRegisterOwner() {
        Cat cat = Cat.builder().name("Vasya").build();
        CatDTO catDTO = new CatDTO();
        catDTO.setName("Vasya");

        when(catRepository.registerCat(cat)).thenReturn(cat);

        CatsOperationResult result = catService.registerCat(catDTO);

        if (result instanceof CatsOperationResult.Success res){
            assertEquals(cat, res.cat());
        }

       /* verify(sessionFactory, times(1)).openSession();
        verify(session, times(1)).beginTransaction();
        verify(session, times(1)).save(owner);*/
    }

    @Test
    public void testFindOwnerById() {
        Cat cat = Cat.builder().id(1L).name("Vasya").build();
        CatDTO catDTO = new CatDTO();
        catDTO.setName("Vasya");

        when(catRepository.findCatById(1L)).thenReturn(cat);

        catService.registerCat(catDTO);
        CatsOperationResult result = catService.findCatById(1L);

        if (result instanceof CatsOperationResult.Success res){
            assertEquals(cat, res.cat());
        }
    }
}
