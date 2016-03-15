package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.util.UserMealsUtil;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * GKislin
 * 15.09.2015.
 */
@Repository
public class InMemoryUserMealRepositoryImpl implements UserMealRepository {
    private Map<Integer, Map<Integer, UserMeal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        for (UserMeal um : UserMealsUtil.MEAL_LIST) {
            {
                save(um, 1);
                save(um, 2);
            }
        }
    }

    @Override
    public UserMeal save(UserMeal userMeal, int userId) {
       if (userMeal.isNew()) {
            userMeal.setId(counter.incrementAndGet());
        }
        if (repository.get(userId) == null) {
            repository.put(userId, new ConcurrentHashMap<>());
        }
        Map<Integer, UserMeal> mealRep = repository.get(userId);
        mealRep.put(userMeal.getId(), userMeal);
        repository.put(userId, mealRep);
        return userMeal;
    }

    @Override
    public boolean delete(int id, int userId) {
        Map<Integer, UserMeal> mealRep = repository.get(userId);
        return mealRep.remove(id, mealRep.get(id));

    }

    @Override
    public UserMeal get(int id, int userId) {
        Map<Integer, UserMeal> mealRep = repository.get(userId);
        return mealRep.get(id);
    }

    @Override
    public List<UserMeal> getAll(int userId) {
        Map<Integer, UserMeal> mealRep = repository.get(userId);
        return mealRep.values().stream()
                .sorted((userMeal1,userMeal2)->userMeal1.getDateTime().toLocalDate().compareTo(userMeal2.getDateTime().toLocalDate()))
                .collect(Collectors.toList());
    }


    @Override
    public List<UserMeal> getBetweenDateTimes(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        Map<Integer, UserMeal> mealRep = repository.get(userId);
        return mealRep.values()
                .stream()
                .filter(userMeal -> userMeal.getDateTime().compareTo(startDateTime)>=0&&userMeal.getDateTime().compareTo(endDateTime)<=0)
                .collect(Collectors.toList());

    }
}

