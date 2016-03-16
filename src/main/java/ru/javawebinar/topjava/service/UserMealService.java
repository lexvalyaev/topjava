package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import sun.util.resources.cldr.vai.LocaleNames_vai;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * GKislin
 * 15.06.2015.
 */
public interface UserMealService {

    UserMeal save(UserMeal userMeal, int userId);

    void delete(int id, int userId) throws NotFoundException;

    UserMeal get(int id, int userId) throws NotFoundException;

    List<UserMeal> getAll(int userId);

    void update(UserMeal userMeal, int userId);

    List<UserMeal> getBeetwen(LocalDateTime startTime, LocalDateTime endTime, int userId);


}

