package ru.javawebinar.topjava.web.meal;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.service.UserMealService;
import ru.javawebinar.topjava.util.TimeUtil;
import ru.javawebinar.topjava.util.UserMealsUtil;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.stream.Collectors;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
/**
 * Created by usr on 20.04.2016.
 */
public class UserMealRestControllerTest extends AbstractControllerTest {

    static final String REST_URL = "/rest/meals/";

    @Autowired
    protected UserMealService service;

    @Test
    public void getAll() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER_EXCEED.contentListMatcher(UserMealsUtil.getWithExceeded(USER_MEALS, UserTestData.USER.getCaloriesPerDay())));
    }

    @Test
    public void getTest() throws Exception {
        mockMvc.perform(get(REST_URL + MEAL1_ID).param("id", String.valueOf(MEAL1_ID)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentMatcher(MEAL1));
    }

    @Test
    public void deleteTest() throws Exception {
        mockMvc.perform(delete(REST_URL + MEAL1_ID).param("id", String.valueOf(MEAL1_ID)))
                .andDo(print())
                .andExpect(status().isOk());
        MATCHER.assertCollectionEquals(Arrays.asList(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2), service.getAll(USER_ID));
    }

    @Test
    public void update() throws Exception {
        mockMvc.perform(put(REST_URL + MEAL1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(getUpdated())))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getBetween() throws Exception {
        final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        final LocalDateTime startDateTime = LocalDateTime.of(1990, 3, 5, 9, 0);
        final LocalDateTime endDateTime = LocalDateTime.of(2015, 5, 30, 13, 0);
        mockMvc.perform(get(REST_URL + "between")
                .contentType(MediaType.APPLICATION_JSON)
                .param("startDateTime", formatter.format(startDateTime))
                .param("endDateTime", formatter.format(endDateTime)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MATCHER_EXCEED.contentListMatcher(UserMealsUtil.getWithExceeded(USER_MEALS,
                        USER.getCaloriesPerDay())
                        .stream()
                        .filter(um -> TimeUtil.isBetween(um.getDateTime(), startDateTime, endDateTime))
                        .collect(Collectors.toList())));

    }

}