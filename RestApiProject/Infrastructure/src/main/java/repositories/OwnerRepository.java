package repositories;

import abstractions.IOwnerRepository;
import jakarta.persistence.EntityNotFoundException;
import models.Cat;
import models.Owner;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("ownerRepositoryBean")
public class OwnerRepository implements IOwnerRepository {
    private final SessionFactory sessionFactory;

    @Autowired
    public OwnerRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

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
