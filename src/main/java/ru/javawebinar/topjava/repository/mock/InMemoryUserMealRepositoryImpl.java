package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.util.UserMealsUtil;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * GKislin
 * 15.09.2015.
 */
@Repository
public class InMemoryUserMealRepositoryImpl implements UserMealRepository {
    private Map<Integer, Map<Integer, UserMeal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {

        for (UserMeal um:UserMealsUtil.MEAL_LIST)
        {
            {
                save(um,1);
                save(um,2);
            }

        }

    }

    @Override
    public UserMeal save(UserMeal userMeal, int userID) {

        if (userMeal.isNew())
        {
            userMeal.setId(counter.incrementAndGet());
        }
       if (repository.get(userID)==null){repository.put(userID,new ConcurrentHashMap<>());}
       Map<Integer,UserMeal>  mealRep = repository.get(userID);
       mealRep.put(userMeal.getId(),userMeal);
       repository.put(userID, mealRep);
        return userMeal;
    }

    @Override
    public void delete(int id, int userID) {

        Map<Integer,UserMeal>  mealRep =  repository.get(userID);
        if(mealRep.get(id)!=null)
        mealRep.remove(id,mealRep.get(id));
    }

    @Override
    public UserMeal get(int id, int userID) {

        Map<Integer,UserMeal>  mealRep =  repository.get(userID);

        return (UserMeal) mealRep.get(id);
    }

    @Override
    public Collection<UserMeal> getAll(int userID) {

        Map<Integer,UserMeal>   mealRep =  repository.get(userID);
        return new ArrayList<>(mealRep.values());
    }


    @Override
    public Collection<UserMeal> getBetweenDateTimes(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return null;
    }
}

