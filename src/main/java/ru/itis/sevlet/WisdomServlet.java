package ru.itis.sevlet;

import ru.itis.db.PostgresConnection;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import ru.itis.FreemarkerConfiguration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Bulat Giniyatullin
 * 26 Октябрь 2017
 */
@WebServlet(name = "WisdomServlet", urlPatterns = {"/*", "/wisdom"})
public class WisdomServlet extends HttpServlet {
    private String defaultWisdom = "Nothing to say";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userWisdom = request.getParameter("user_wisdom");

        response.setContentType("text/html; charset=utf8");

        String wisdom = getWisdom();

        try {
            Map<String, Object> dataSet = new HashMap<>();
            dataSet.put("wisdom", wisdom);

            Template template = FreemarkerConfiguration.getConfiguration().getTemplate("wisdom.ftl");
            template.process(dataSet, response.getWriter());
        } catch (TemplateException e) {
            e.printStackTrace();
        }

        if (userWisdom != null) {
            storeWisdom(userWisdom);
        }
    }

    private String getWisdom() {
        String foundedWisdom = null;
        String query = "SELECT wisdom, RANDOM() FROM wisdoms ORDER BY 2 FETCH FIRST 1 ROWS ONLY";

        try (PreparedStatement preparedStatement = PostgresConnection
                .getConnection()
                .prepareStatement(query);)
        {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                foundedWisdom = resultSet.getString("wisdom");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (foundedWisdom == null) {
            foundedWisdom = defaultWisdom;
        }
        return foundedWisdom;
    }

    private void storeWisdom(String userWisdom) {
        String insertQuery = "INSERT INTO wisdoms (wisdom) VALUES (?)";

        try (PreparedStatement preparedStatement = PostgresConnection
                .getConnection()
                .prepareStatement(insertQuery);)
        {
            preparedStatement.setString(1, userWisdom);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
