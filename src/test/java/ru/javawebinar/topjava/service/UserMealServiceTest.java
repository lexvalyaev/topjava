package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;

import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;



/**
 * Created by usr on 21.03.2016.
 */
@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class UserMealServiceTest {

    @Autowired
    protected UserMealService userMealService;

    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }

    @Test
    public void testGet() throws Exception {
        UserMeal userMeal =  userMealService.get(ID1,USER_ID);
        MATCHER.assertEquals(userMeal,M1);
    }

    @Test
    public void testDelete() throws Exception {
         userMealService.delete(ID1,USER_ID);
         MATCHER.assertCollectionEquals(userMealService.getAll(USER_ID),MEALS_GET_ALL_USER_AFTER_DELETE);
    }

    @Test (expected = NotFoundException.class)
    public void testDeleteNotFount() throws Exception {
           userMealService.delete(ID7,USER_ID);
    }

    @Test
    public void testGetBetweenDates() throws Exception {
        Collection<UserMeal> between = userMealService.getBetweenDates(
                LocalDate.of(2016,3,18),
                LocalDate.of(2016,3,19),
                USER_ID);
        MATCHER.assertCollectionEquals(between,MEALS_GET_BETWEEN_USER);
    }

    @Test
    public void testGetBetweenDateTimes() throws Exception {
        Collection<UserMeal> between = userMealService.getBetweenDateTimes(
                LocalDateTime.of(2016, 3, 18, 12, 0, 0),
                LocalDateTime.of(2016, 3, 19, 12, 0, 0),
                USER_ID);
         MATCHER.assertCollectionEquals(between,MEALS_GET_BETWEEN_TIME_USER);
    }

    @Test
    public void testGetAll() throws Exception {
        MATCHER.assertCollectionEquals(userMealService.getAll(USER_ID),MEALS_GET_ALL_USER);
    }

    @Test
    public void testUpdate() throws Exception {
        userMealService.update(MEAL_UPDATE,ADMIN_ID);
        MATCHER.assertCollectionEquals(userMealService.getAll(ADMIN_ID),MEALS_GET_ALL_ADMIN_AFTER_UPDATE);

    }

    @Test
    public void testSave() throws Exception {
      userMealService.save(MEAL_SAVE,ADMIN_ID);
      MATCHER.assertCollectionEquals(userMealService.getAll(ADMIN_ID),MEALS_GET_ADMIN_AFTER_SAVE);
    }
}