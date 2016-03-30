package ru.javawebinar.topjava.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * GKislin
 * 11.01.2015.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = UserMeal.DELETE, query = "DELETE FROM UserMeal um WHERE um.id=:id AND um.user.id=:userId"),
        @NamedQuery(name = UserMeal.GET, query = "SELECT um FROM UserMeal um WHERE um.id=:id AND um.user.id=:userId"),
        @NamedQuery(name = UserMeal.GET_ALL, query = "SELECT um FROM UserMeal um WHERE um.user.id=:userId ORDER BY um.dateTime DESC "),
        @NamedQuery(name = UserMeal.GET_BETWEEN_DATETIME,query = "SELECT um FROM UserMeal um " +
                "WHERE um.user.id=:userId AND um.dateTime BETWEEN :startDateTime AND :endDateTime " +
                "ORDER BY um.dateTime DESC"),
        @NamedQuery(name = UserMeal.UPDATE, query = "UPDATE UserMeal um " +
                "SET um.description=:description, um.dateTime=:datetime,um.calories=:calories " +
                "WHERE um.id =:id AND um.user.id=:userId")


})
@Table(name = "meals", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "date_time"}, name = "meals_unique_user_datetime_idx")})
public class UserMeal extends BaseEntity {

    public static final String DELETE = "UserMeal.delete";
    public static final String GET_ALL = "UserMeal.getAll";
    public static final String GET_BETWEEN_DATETIME = "UserMeal.getBetween";
    public static final String GET = "UserMeal.get";
    public static final String UPDATE = "UserMeal.update";
/*
    @Converter(autoApply = true)
    public static class LocalDateTimePersistenceConverter implements AttributeConverter<LocalDateTime, Timestamp> {
        @Override
        public Timestamp convertToDatabaseColumn(LocalDateTime localDateTime) {
            return (localDateTime == null ? null : Timestamp.valueOf(localDateTime));
        }

        @Override
        public LocalDateTime convertToEntityAttribute(Timestamp timeStamp) {
            return (timeStamp == null ? null : timeStamp.toLocalDateTime());
        }
    }*/

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @Column(name="description")
    @NotEmpty
    private String description;

    @Column(name="calories")
    @Digits(integer = 4, fraction = 0)
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
