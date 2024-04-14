package repositories;

import abstractions.repositories.IOwnerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import models.cats.Cat;
import models.owners.Owner;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("ownerRepositoryBean")
@RequiredArgsConstructor
public class OwnerRepository implements IOwnerRepository {
    private final SessionFactory sessionFactory;

    @Override
    public Owner registerOwner(Owner owner) {
        try (Session session = sessionFactory.openSession();){
            session.beginTransaction();

            session.save(owner);
            session.getTransaction().commit();

            return owner;
        }
        catch (Exception ex){
            return null;
        }
    }

    @Override
    public Owner findOwnerById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Owner.class, id);
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public Owner findOwnerByName(String name) {
        try (Session session = sessionFactory.openSession()) {
            Owner owner = session.createNativeQuery("SELECT * FROM Owner o WHERE o.name = :name", Owner.class)
                    .setParameter("name", name)
                    .uniqueResult();

            return owner;
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public List<Owner> getAllOwners() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Owner", Owner.class).getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public List<Cat> getAllOwnerCats(Owner owner) {
        try (Session session = sessionFactory.openSession()) {
            List<Cat> ownerCats = session.createQuery(
                            "SELECT c FROM Cat c WHERE c.owner.id = :ownerId", Cat.class)
                    .setParameter("ownerId", owner.getId())
                    .getResultList();

            return ownerCats;
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public void deleteOwnerById(Long id) throws EntityNotFoundException{
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Owner owner = session.get(Owner.class, id);
            if (owner != null) {
                session.delete(owner);
                session.getTransaction().commit();
            } else {
                throw new EntityNotFoundException("Владелец с указанным ID не найден: " + id);
            }
        }
    }
}
