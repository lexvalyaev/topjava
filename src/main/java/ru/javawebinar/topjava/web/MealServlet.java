package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.StringUtils;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.web.meal.UserMealRestController;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

/**
 * User: gkislin
 * Date: 19.08.2014
 */

public class MealServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);
    private ConfigurableApplicationContext appCtx;

    @Autowired
    private UserMealRestController controller;


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        controller = appCtx.getBean(UserMealRestController.class);
        // repository = appCtx.getBean(InMemoryUserMealRepositoryImpl.class);

    }

    @Override
    public void destroy() {
        appCtx.close();
        super.destroy();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action == null) {
            String id = request.getParameter("id");
            UserMeal userMeal = new UserMeal(id.isEmpty() ? null : Integer.valueOf(id),
                    LocalDateTime.parse(request.getParameter("dateTime")),
                    request.getParameter("description"),
                    Integer.valueOf(request.getParameter("calories")));
            LOG.info(userMeal.isNew() ? "Create {}" : "Update {}", userMeal);
            controller.create(userMeal);
            response.sendRedirect("meals");
        } else {
            String startDateString = request.getParameter("startDate");
            String endDateString = request.getParameter("endDate");
            String startTimeString = request.getParameter("startTime");
            String endTimeString = request.getParameter("endTime");

            LocalDate startDate = StringUtils.isEmpty(startDateString) ? LocalDate.MIN : LocalDate.parse(startDateString);
            LocalDate endDate = StringUtils.isEmpty(endDateString) ? LocalDate.MAX : LocalDate.parse(endDateString);
            LocalTime startTime = StringUtils.isEmpty(startTimeString) ? LocalTime.MIN : LocalTime.parse(startTimeString);
            LocalTime endTime = StringUtils.isEmpty(endTimeString) ? LocalTime.MAX : LocalTime.parse(endTimeString);

            request.setAttribute("mealList", controller.getBetweenWithExceed(startDate, endDate, startTime, endTime));
            request.getRequestDispatcher("/mealList.jsp").forward(request,response);
        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            LOG.info("getAll");
            request.setAttribute("mealList", controller.getAll());
            request.getRequestDispatcher("/mealList.jsp").forward(request, response);
        } else if (action.equals("delete")) {
            int id = getId(request);
            LOG.info("Delete {}", id);
            controller.delete(id);
            response.sendRedirect("meals");
        } else {
            final UserMeal meal = action.equals("create") ?
                    new UserMeal(LocalDateTime.now(), "", 1000) :
                    controller.get(getId(request));
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("mealEdit.jsp").forward(request, response);
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }


}
