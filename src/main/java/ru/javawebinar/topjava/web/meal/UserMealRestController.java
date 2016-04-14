package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.service.UserMealService;
import ru.javawebinar.topjava.to.UserMealWithExceed;
import ru.javawebinar.topjava.util.TimeUtil;
import ru.javawebinar.topjava.util.UserMealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
@Controller
public class UserMealRestController extends AbstractUserMealController {

    @Override
    public String getAll(Model model) {
        return super.getAll(model);
    }

    @Override
    public String delete(int id) {
        return super.delete(id);
    }

    @Override
    public String goToUpdate(int id, Model model) {
        return super.goToUpdate(id, model);
    }

    @Override
    public String goToCreate(Model model) {
        return super.goToCreate(model);
    }

    @Override
    public String updateOrCreate(@RequestParam(defaultValue = "0") int id, String dateTime, String description, int calories) {
        return super.updateOrCreate(id, dateTime, description, calories);
    }

    @Override
    public String getBetween(String startDate, String endDate, String startTime, String endTime, Model model) {
        return super.getBetween(startDate, endDate, startTime, endTime, model);
    }
}