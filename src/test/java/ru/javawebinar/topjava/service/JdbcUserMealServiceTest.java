package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;

/**
 * Created by usr on 04.04.2016.
 */
@ActiveProfiles({Profiles.POSTGRES,Profiles.JDBC})
public class JdbcUserMealServiceTest extends UserMealServiceTest {
}
