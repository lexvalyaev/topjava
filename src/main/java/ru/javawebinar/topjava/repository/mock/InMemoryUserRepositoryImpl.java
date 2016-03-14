package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by usr on 11.03.2016.
 */
@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {
    private Map<Integer, User> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        save(new User(counter.incrementAndGet(),"Admin", "adminMail","adminPassword", Role.ROLE_ADMIN) );
        save(new User(counter.incrementAndGet(),"User","userMail","userPassword",Role.ROLE_USER));
    }

    @Override
    public User save(User user) {

        if (user.isNew())
        {
            user.setId(counter.incrementAndGet());
        }

        repository.put(user.getId(), user);

        return user;
    }

    @Override
    public boolean delete(int id) {

        return repository.remove(id, repository.get(id));

    }

    @Override
    public User get(int id) {

        return repository.get(id);
    }

    @Override
    public User getByEmail(String email) {
        User user = null;

        for (Map.Entry<Integer, User> pair : repository.entrySet()) {

            if (pair.getValue().getEmail().equalsIgnoreCase(email))
                user = pair.getValue();

        }

        return user;
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(repository.values());
    }
}
