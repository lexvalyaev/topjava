package ru.javawebinar.topjava.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

/**
 * User: gkisline
 * Date: 26.08.2014
 */

@Repository
@Transactional(readOnly = true)
public class JpaUserMealRepositoryImpl implements UserMealRepository {

    @PersistenceContext
    private EntityManager em;


    @Override
    @Transactional
    public UserMeal save(UserMeal userMeal, int userId) {

       if (userMeal.isNew())
        {   userMeal.setUser(em.getReference(User.class,userId));
            em.persist(userMeal);
            return userMeal;
        }
        else
        {
            if(em.createNamedQuery(UserMeal.UPDATE)
                    .setParameter("calories",userMeal.getCalories())
                    .setParameter("datetime",userMeal.getDateTime())
                    .setParameter("description",userMeal.getDescription())
                    .setParameter("userId",userMeal.getUser().getId())
                    .executeUpdate()==0
                    )return null;
        }
        return userMeal;
      }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
          return em.createNamedQuery(UserMeal.DELETE)
                   .setParameter("id",id)
                   .setParameter("userId",userId)
                   .executeUpdate()!=0;
    }

    @Override
    public UserMeal get(int id, int userId) {
        return (UserMeal) em.createNamedQuery(UserMeal.GET)
                .setParameter("id",id)
                .setParameter("userId",id)
                .getSingleResult();
    }

    @Override
    public List<UserMeal> getAll(int userId) {
            return em.createNamedQuery(UserMeal.GET_ALL)
                    .setParameter("userId",userId)
                    .getResultList();

    }

    @Override
    public List<UserMeal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return em.createNamedQuery(UserMeal.GET_BETWEEN_DATETIME)
                .setParameter("userId",userId)
                .setParameter("startDateTime",startDate)
                .setParameter("endDateTime",endDate)
                .getResultList();


    }
}