package buisinessLogic.services;

import abstractions.repositories.IOwnerRepository;
import lombok.RequiredArgsConstructor;
import models.owners.Owner;
import models.owners.OwnerDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailsService implements UserDetailsService {
    @Autowired
    private IOwnerRepository ownerRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Owner owner = ownerRepository.findOwnerByName(username);
        return new OwnerDetails(owner);
    }
}
