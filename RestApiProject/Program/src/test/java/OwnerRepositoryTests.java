import abstractions.IOwnerRepository;
import buisinessLogic.OwnerService;
import contracts.OwnerOperationResult;
import dao.OwnerJpaRepository;
import dto.OwnerDTO;
import models.Owner;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import repositories.OwnerRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class OwnerRepositoryTests {
    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @Mock
    private Transaction transaction;

    @Mock
    private IOwnerRepository ownerRepository;

    @InjectMocks
    private OwnerService ownerService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        ownerService = new OwnerService(ownerRepository);
    }

    @Test
    public void testRegisterOwner() {
        Owner owner = Owner.builder().name("Max").build();

        when(ownerRepository.registerOwner(owner)).thenReturn(owner);

        OwnerDTO ownerDTO = new OwnerDTO();
        ownerDTO.setName("Max");

        OwnerOperationResult result = ownerService.registerUser(ownerDTO);

        if (result instanceof OwnerOperationResult.Success res){
            assertEquals(owner, res.owner());
        }

       /* verify(sessionFactory, times(1)).openSession();
        verify(session, times(1)).beginTransaction();
        verify(session, times(1)).save(owner);*/
    }

    @Test
    public void testFindOwnerById() {
        Owner owner = Owner.builder().id(1L).name("Max").build();

        when(ownerRepository.findOwnerById(1L)).thenReturn(owner);

        OwnerDTO ownerDTO = new OwnerDTO();
        ownerDTO.setName("Max");

        ownerService.registerUser(ownerDTO);
        OwnerOperationResult result = ownerService.findOwnerById(1L);

        if (result instanceof OwnerOperationResult.Success res){
            assertEquals(owner, res.owner());
        }
    }

    @Test
    public void testGetAllOwners() {
        Owner owner1 = Owner.builder().id(1L).name("Max").build();
        Owner owner2 = Owner.builder().id(2L).name("Oleg").build();

        List<Owner> owners = new ArrayList<>();
        owners.add(owner1);
        owners.add(owner2);

        when(ownerRepository.getAllOwners()).thenReturn(owners);

        OwnerOperationResult result = ownerService.getAllOwners();

        if (result instanceof OwnerOperationResult.SuccessCheckingOwners res){
            assertEquals(owners, res.owners());
        }
    }
}
