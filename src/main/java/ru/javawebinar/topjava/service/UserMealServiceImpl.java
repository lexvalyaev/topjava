package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Collection;
import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */

@Service
public class UserMealServiceImpl implements UserMealService {

    @Autowired
    private UserMealRepository repository;

    @Override
    public UserMeal save(UserMeal userMeal, int userId) {
        repository.save(userMeal,userId);
        return userMeal;
    }

    @Override
    public void delete(int id, int userId) throws NotFoundException {
        if(repository.get(id,userId)==null) throw new NotFoundException("Meal not found");
        repository.delete(id,userId);

    }

    @Override
    public UserMeal get(int id, int userId) throws NotFoundException {
        return repository.get(id,userId);
    }

    @Override
    public Collection<UserMeal> getByUser(int userId) {
        return repository.getAll(userId);
    }

    @Override
    public void update(UserMeal userMeal, int userId) {
        repository.save(userMeal,userId);
    }
}
