package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.service.UserMealService;
import ru.javawebinar.topjava.to.UserMealWithExceed;
import ru.javawebinar.topjava.util.TimeUtil;
import ru.javawebinar.topjava.util.UserMealsUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * Created by usr on 15.04.2016.
 */
public abstract class AbstractUserMealController {
    private static final Logger LOG = LoggerFactory.getLogger(UserMealRestController.class);

    @Autowired
    private UserMealService service;

    //Name of params and variables are equals, so I don't use @RequestParam(name = "...")

    @RequestMapping(method = RequestMethod.GET)
    public String getAll(Model model) {
        int userId = LoggedUser.id();
        LOG.info("getAll for User {}", userId);
        model.addAttribute("mealList", UserMealsUtil.getWithExceeded(service.getAll(userId),
                LoggedUser.getCaloriesPerDay()));
        return "mealList";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(int id) {
        int userId = LoggedUser.id();
        LOG.info("delete meal {} for User {}", id, userId);
        service.delete(id, userId);
        return "redirect:/meals";
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String goToUpdate(int id, Model model) {
        int userId = LoggedUser.id();
        LOG.info("get meal {} for User {}", id, userId);
        UserMeal meal = service.get(id, userId);
        LOG.info("get meal {} to update and send to mealList", meal);
        model.addAttribute("meal", meal);
        return "mealEdit";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String goToCreate(Model model) {
        LOG.info("create default meal and send to mealList");
        model.addAttribute("meal", new UserMeal(LocalDateTime.now(), "", 1000));
        return "mealEdit";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String updateOrCreate(@RequestParam(defaultValue = "0") int id, String dateTime, String description,
                                 int calories) {
        final UserMeal userMeal = new UserMeal(LocalDateTime.parse(dateTime), description, calories);
        if (id == 0) {
            userMeal.setId(null);
            int userId = LoggedUser.id();
            LOG.info("create {} for User {}", userMeal, userId);
            service.save(userMeal, userId);
        } else {
            userMeal.setId(id);
            int userId = LoggedUser.id();
            LOG.info("goToUpdate {} for User {}", userMeal, userId);
            service.update(userMeal, userId);
        }
        return "redirect:meals";
    }

    @RequestMapping(value = "/filter", method = RequestMethod.POST)
    public String getBetween(String startDate, String endDate, String startTime, String endTime, Model model) {
        final LocalDate beginDate = TimeUtil.parseLocalDate(startDate);
        final LocalDate finishDate = TimeUtil.parseLocalDate(endDate);
        final LocalTime beginTime = TimeUtil.parseLocalTime(startTime);
        final LocalTime finishTime = TimeUtil.parseLocalTime(endTime);
        int userId = LoggedUser.id();
        LOG.info("getBetween dates {} - {} for time {} - {} for User {}", beginDate, finishDate, beginTime, finishTime, userId);
        final List<UserMealWithExceed> mealWithExceeds = UserMealsUtil.getFilteredWithExceeded(
                service.getBetweenDates(
                        beginDate != null ? beginDate : TimeUtil.MIN_DATE, finishDate != null ? finishDate : TimeUtil.MAX_DATE, userId),
                beginTime != null ? beginTime : LocalTime.MIN, finishTime != null ? finishTime : LocalTime.MAX, LoggedUser.getCaloriesPerDay()
        );
        model.addAttribute("mealList", mealWithExceeds);
        return "mealList";
    }
}
