package ru.javawebinar.topjava.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * GKislin
 * 11.01.2015.
 */
@Entity
@Table (name = "meals")
@NamedQueries({
        @NamedQuery(name = UserMeal.DELETE, query = "DELETE FROM UserMeal um WHERE um.id=:id AND um.user.id=:userId"),
        @NamedQuery(name = UserMeal.GET, query = "SELECT um FROM UserMeal um WHERE um.id=:id AND um.user.id=:userId"),
        @NamedQuery(name = UserMeal.GET_ALL, query = "SELECT um FROM UserMeal um WHERE um.user.id=:userId ORDER BY um.dateTime DESC "),
        @NamedQuery(name = UserMeal.GET_BETWEEN_DATETIME,query = "SELECT um FROM UserMeal um " +
                "WHERE um.user.id=:userId AND um.dateTime BETWEEN :startDateTime AND :endDateTime " +
                "ORDER BY um.dateTime DESC"),
        @NamedQuery(name = UserMeal.UPDATE, query = "UPDATE UserMeal um " +
                "SET um.description=:description, um.dateTime=:darerime,um.calories=:calories " +
                "WHERE um.id =:id AND um.user.id=:userId")


})
public class UserMeal extends BaseEntity {

    public static final String DELETE = "UserMeal.delete";
    public static final String GET_ALL = "UserMeal.getAll";
    public static final String GET_BETWEEN_DATETIME = "UserMeal.getBetween";
    public static final String GET = "UserMeal.get";
    public static final String UPDATE = "UserMeal.update";

    @Column(name="datetime")
    @NotEmpty
    private LocalDateTime dateTime;

    @Column(name="description")
    @NotEmpty
    private String description;

    @Column(name="calories")
    @NotEmpty
    protected int calories;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public UserMeal() {
    }

    public UserMeal(LocalDateTime dateTime, String description, int calories) {
        this(null, dateTime, description, calories);
    }

    public UserMeal(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public LocalDateTime getDateTime() {

        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserMeal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}
