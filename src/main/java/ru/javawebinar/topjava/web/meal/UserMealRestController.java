package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.service.UserMealService;
import ru.javawebinar.topjava.to.UserMealWithExceed;
import ru.javawebinar.topjava.util.UserMealsUtil;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
@Controller
public class UserMealRestController {
    @Autowired
    private UserMealService service;


    public List<UserMealWithExceed> getAll() {
        int userId = LoggedUser.id();
        return UserMealsUtil.getFilteredWithExceeded(service.getAll(userId), LocalTime.MIN, LocalTime.MAX, LoggedUser.getCaloriesPerDay());

    }


    public UserMeal create(UserMeal userMeal) {
        int userId = LoggedUser.id();
        service.save(userMeal, userId);
        return userMeal;
    }

    public void update(UserMeal userMeal) {
        int userId = LoggedUser.id();
        service.update(userMeal, userId);
    }

    public UserMeal get(int id) {
        int userId = LoggedUser.id();
        return service.get(id, userId);
    }

    public void delete(int id) {
        int userId = LoggedUser.id();
        service.delete(id, userId);

    }

    public List<UserMeal> getBetween(LocalDateTime startTime, LocalDateTime endTime) {
        int userId = LoggedUser.id();
        return service.getBeetwen(startTime, endTime, userId);
    }

    public List<UserMealWithExceed> getBetweenWithExceed(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
        LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime);
        return UserMealsUtil.getFilteredWithExceeded(getBetween(startDateTime, endDateTime), startTime, endTime, LoggedUser.getCaloriesPerDay());
    }


}
