package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * GKislin
 * 27.03.2015.
 */
@Repository
public class DataJpaUserMealRepositoryImpl implements UserMealRepository {

    @Autowired
    private ProxyUserMealRepository proxyUserMeal;

    @Autowired
    private ProxyUserRepository proxyUser;

    @Override
    public UserMeal save(UserMeal userMeal, int userId) {
        User user = proxyUser.getOne(userId);
        if (userMeal.isNew()) {
            userMeal.setUser(user);
            proxyUserMeal.save(userMeal);
            return userMeal;
        } else {
            if (userMeal.getUser().getId() != userId) return null;
            proxyUserMeal.save(userMeal);
            return userMeal;
        }
    }

    @Override
    public boolean delete(int id, int userId) {
        return proxyUserMeal.delete(id, userId) != 0;
    }

    @Override
    public UserMeal get(int id, int userId) {

        return proxyUserMeal.get(id, userId);
    }

    @Override
    public List<UserMeal> getAll(int userId) {
        return proxyUserMeal.getAll(userId);
    }

    @Override
    public List<UserMeal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {

        return proxyUserMeal.getBetween(startDate, endDate);
    }
}
