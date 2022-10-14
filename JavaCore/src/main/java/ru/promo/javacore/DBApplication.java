package ru.promo.javacore;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ru.promo.javacore.model.BuilderCompany;
import ru.promo.javacore.model.Company;
import ru.promo.javacore.model.CompanyType;
import ru.promo.javacore.model.ITCompany;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBApplication {
    private static final String URL = "jdbc:postgresql://127.0.0.1:5432/java_course";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    public static void main(String[] args) throws SQLException {
//        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(URL);
        config.setUsername(USER);
        config.setPassword(PASSWORD);
        DataSource ds = new HikariDataSource(config);
        Connection connection = ds.getConnection();

        Statement stmt = connection.createStatement();
        boolean execute = stmt.execute("SELECT * FROM companies");
        System.out.println(execute);

        List<Company<CompanyType>> companies = new ArrayList<>();
        ResultSet resultSet = stmt.getResultSet();
        while (resultSet.next()) {
            String attr = resultSet.getString("attr");
            CompanyType companyType = CompanyType.valueOf(attr);

            String name = resultSet.getString("name");
            int employee_count = resultSet.getInt("employee_count");

            Company<CompanyType> company = switch (companyType) {
                case BUILDER -> new BuilderCompany(name, employee_count);
                case IT -> new ITCompany(name, employee_count);
                case CONSULTING -> null;
            };

            companies.add(company);
        }

        stmt.close();
        connection.close();

        companies.forEach(System.out::println);
    }
}
