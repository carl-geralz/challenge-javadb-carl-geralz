package chvrches.jdbc.exercise.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class ConnectionUtil {
    private static HikariDataSource dataSource;

    private static String jdbcUrl = "jdbc:mysql://localhost:3306/PenjualanWarungSamudra";
    private static String username = "root";
    private static String password = "root";

    static {
        createDataSource();
    }

    private static void createDataSource() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(username);
        config.setPassword(password); 


        config.setMaximumPoolSize(10);
        config.setMinimumIdle(5);
        config.setIdleTimeout(60_000);
        config.setMaxLifetime(10 * 60_000);

        dataSource = new HikariDataSource(config);
    }

    public static HikariDataSource getDataSource() {
        return dataSource;
    }

    public static void setJdbcUrl(String newJdbcUrl) {
        jdbcUrl = newJdbcUrl;
        // Buat ulang DataSource dengan konfigurasi baru
        createDataSource();
    }

    public static void setUsername(String newUsername) {
        username = newUsername;
        // Buat ulang DataSource dengan konfigurasi baru
        createDataSource();
    }

    public static void setPassword(String newPassword) {
        password = newPassword;
        // Buat ulang DataSource dengan konfigurasi baru
        createDataSource();
    }

    public static String getJdbcUrl() {
        return jdbcUrl;
    }

    public static String getUsername() {
        return username;
    }

    public static String getPassword() {
        return password;
    }
}