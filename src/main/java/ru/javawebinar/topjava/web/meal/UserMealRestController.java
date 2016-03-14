package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.service.UserMealService;

import java.util.Collection;

/**
 * GKislin
 * 06.03.2015.
 */
@Controller
public class UserMealRestController {
    @Autowired
    private UserMealService service;


    public Collection<UserMeal> getAll (int userID)
    {
        int userId = LoggedUser.id();
        return service.getByUser(userId);

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

    public void delete (int id)
    {
        int userId = LoggedUser.id();
        service.delete(id,userId);

    }
}
