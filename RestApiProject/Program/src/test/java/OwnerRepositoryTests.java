import abstractions.repositories.IOwnerRepository;
import buisinessLogic.owners.OwnerService;
import buisinessLogic.services.CatConverterToDto;
import buisinessLogic.services.OwnerConverterToDto;
import contracts.owners.OwnerOperationResult;
import models.dto.OwnerDTO;
import models.owners.Owner;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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
    @Mock
    private CatConverterToDto catConverterToDto;
    @Mock
    private OwnerConverterToDto ownerConverterToDto;


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

        OwnerDTO result = ownerService.registerUser(ownerDTO);

        assertEquals(owner.getName(), result.getName());
    }

    @Test
    public void testFindOwnerById() {
        Owner owner = Owner.builder().id(1L).name("Max").build();

        when(ownerRepository.findOwnerById(1L)).thenReturn(owner);

        OwnerDTO ownerDTO = new OwnerDTO();
        ownerDTO.setName("Max");

        OwnerDTO result = ownerService.findOwnerById(1L);

        assertEquals(owner.getName(), result.getName());
    }

    @Test
    public void testGetAllOwners() {
        Owner owner1 = Owner.builder().id(1L).name("Max").build();
        Owner owner2 = Owner.builder().id(2L).name("Oleg").build();

        List<Owner> owners = new ArrayList<>();
        owners.add(owner1);
        owners.add(owner2);

        when(ownerRepository.getAllOwners()).thenReturn(owners);

        List<OwnerDTO> result = ownerService.getAllOwners();

            assertEquals(owners.get(1).getName(), result.get(1).getName());
    }
}
