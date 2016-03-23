package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;


/**
 * GKislin
 * 13.03.2015.
 */
public class MealTestData {

    public static final ModelMatcher<UserMeal, String> MATCHER = new ModelMatcher<>(UserMeal::toString);

    public static final int ID1 = START_SEQ + 2;
    public static final int ID2 = START_SEQ + 3;
    public static final int ID3 = START_SEQ + 4;
    public static final int ID4 = START_SEQ + 5;
    public static final int ID5 = START_SEQ + 6;
    public static final int ID6 = START_SEQ + 7;
    public static final int ID7 = START_SEQ + 8;
    public static final int ID8 = START_SEQ + 9;
    public static final int ID9 = START_SEQ + 10;

    public static final UserMeal M1 = new UserMeal(ID1, LocalDateTime.of(2016, 3, 18, 8, 0, 0), "Завтрак", 400);
    public static final UserMeal M2 = new UserMeal(ID2, LocalDateTime.of(2016, 3, 18, 12, 0, 0), "Обед", 400);
    public static final UserMeal M3 = new UserMeal(ID3, LocalDateTime.of(2016, 3, 18, 19, 0, 0), "Ужин", 300);
    public static final UserMeal M4 = new UserMeal(ID4, LocalDateTime.of(2016, 3, 19, 8, 0, 0), "Завтрак", 500);
    public static final UserMeal M5 = new UserMeal(ID5, LocalDateTime.of(2016, 3, 19, 12, 0, 0), "Обед", 500);
    public static final UserMeal M6 = new UserMeal(ID6, LocalDateTime.of(2016, 3, 19, 19, 0, 0), "Ужин", 400);
    public static final UserMeal M7 = new UserMeal(ID7, LocalDateTime.of(2016, 3, 19, 10, 0, 0), "Завтрак админа", 500);
    public static final UserMeal M8 = new UserMeal(ID8, LocalDateTime.of(2016, 3, 19, 16, 0, 0), "Обед админа", 800);
    public static final UserMeal M9 = new UserMeal(ID9, LocalDateTime.of(2016, 3, 19, 20, 0, 0), "Ужин админа", 600);

    public static final UserMeal MEAL_SAVE = new UserMeal(LocalDateTime.of(2016, 2, 12, 12, 0, 0), "Обед", 400);
    public static final UserMeal MEAL_UPDATE = new UserMeal(ID9,LocalDateTime.of(2016, 3, 20, 12, 0, 0), "Обед админа", 500);


    public static final List<UserMeal> MEALS_GET_ALL_USER = Arrays.asList(M6,M5,M4,M3,M2,M1);
    public static final List<UserMeal> MEALS_GET_ALL_USER_AFTER_DELETE = Arrays.asList(M6,M5,M4,M3,M2);
    public static final List<UserMeal> MEALS_GET_ALL_ADMIN_AFTER_UPDATE = Arrays.asList(MEAL_UPDATE,M8,M7);
    public static final List<UserMeal> MEALS_GET_BETWEEN_USER = Arrays.asList(M6,M5,M4,M3,M2,M1);
    public static final List<UserMeal> MEALS_GET_ADMIN_AFTER_SAVE = Arrays.asList(M9,M8,M7,MEAL_SAVE);
    public static final List<UserMeal> MEALS_GET_BETWEEN_TIME_USER = Arrays.asList(M5,M4,M3,M2);


}

