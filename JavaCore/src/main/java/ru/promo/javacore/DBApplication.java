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
        config.setUsername(USER);
        config.setPassword(PASSWORD);
        config.setJdbcUrl(URL);
        DataSource ds = new HikariDataSource(config);

        Connection connection = ds.getConnection();

//        Statement stmt = connection.createStatement();
        PreparedStatement stmt = connection.prepareStatement("SELECT * from companies where attr = ?");
//        boolean execute = stmt.execute("INSERT INTO companies (name, employee_count, attr) values ('Company1', 10, 'IT');");
//        stmt.setString(1, "companies");
        stmt.setString(1, "IT");
        boolean execute = stmt.execute();
//        boolean execute = stmt.execute("SELECT * from companies where attr = 'IT';");
        System.out.println(execute);

        ResultSet resultSet = stmt.getResultSet();
        List<Company<CompanyType>> companyList = new ArrayList<>();

        while (resultSet.next()) {
            String type = resultSet.getString("attr");
            CompanyType companyType = CompanyType.valueOf(type);

            String name = resultSet.getString("name");
            int employeeCount = resultSet.getInt("employee_count");
            Company<CompanyType> company = switch (companyType) {
                case IT -> new ITCompany(name, employeeCount);
                case BUILDER -> new BuilderCompany(name, employeeCount);
                case CONSULTING -> null;
            };
            companyList.add(company);
        }
        stmt.close();
        connection.close();

        System.out.println(companyList);
    }
}
