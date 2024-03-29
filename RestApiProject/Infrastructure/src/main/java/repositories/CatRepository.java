package repositories;


import abstractions.ICatRepository;
import jakarta.persistence.EntityNotFoundException;
import models.Cat;
import models.Owner;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("catRepositoryBean")
public class CatRepository implements ICatRepository {
    private final SessionFactory sessionFactory;

    @Autowired
    public CatRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Cat registerCat(Cat cat) {
        try (Session session = sessionFactory.openSession();){
            session.beginTransaction();

            session.save(cat);
            session.getTransaction().commit();

            return cat;
        }
        catch (Exception ex){
            return null;
        }
    }

    @Override
    public List<Cat> getAllCats() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Cat", Cat.class).getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public Cat findCatById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Cat.class, id);
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public Cat updateOwner(Cat cat, Owner owner) {
        try (Session session = sessionFactory.openSession();){
            session.beginTransaction();

            cat.setOwner(owner);

            session.update(cat);
            session.getTransaction().commit();

            return cat;
        }
        catch (Exception ex){
            return null;
        }
    }

    @Override
    public Cat addFriendToCat(Cat cat, Cat friend) {
        try (Session session = sessionFactory.openSession();){
            session.beginTransaction();

            session.createNativeQuery("INSERT INTO friendship (friend_1, friend_2) VALUES (:catId, :friendId)")
                    .setParameter("catId", cat.getId())
                    .setParameter("friendId", friend.getId())
                    .executeUpdate();

            session.createNativeQuery("INSERT INTO friendship (friend_1, friend_2) VALUES (:catId, :friendId)")
                    .setParameter("catId", friend.getId())
                    .setParameter("friendId",cat.getId())
                    .executeUpdate();

            session.getTransaction().commit();

            return cat;
        }
        catch (Exception ex){
            return null;
        }
    }

    @Override
    public List<Cat> getAllCatFriends(Cat cat) {
        try (Session session = sessionFactory.openSession()){
            List<Cat> friends = session
                    .createNativeQuery(
                            "SELECT c FROM cat c " +
                                    "JOIN friendship f ON c.id = f.friend_1 " +
                                    "WHERE f.friend_1 = :catId", Cat.class)
                    .setParameter("catId", cat.getId())
                    .getResultList();

            return friends;
        }
        catch (Exception ex){
            return null;
        }
    }

    @Override
    public List<Cat> getCatsByColor(String color) {
        try (Session session = sessionFactory.openSession()){
            List<Cat> cats = session
                    .createNativeQuery(
                            "SELECT * FROM cat c " +
                                    "WHERE c.color = :catColor", Cat.class)
                    .setParameter("catColor", color)
                    .getResultList();

            return cats;
        }
        catch (Exception ex){
            return null;
        }
    }

    @Override
    public void deleteCatById(Long id) throws EntityNotFoundException{
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Cat cat = session.get(Cat.class, id);
            if (cat != null) {
                session.delete(cat);
                session.getTransaction().commit();
            } else {
                throw new EntityNotFoundException("Кот с указанным ID не найден: " + id);
            }
        }
    }
}
