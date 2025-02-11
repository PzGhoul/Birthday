import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String URL = "jdbc:sqlite:birthdays.db";

    public DatabaseManager() {
        createTable();
    }

    private void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS birthdays ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "name TEXT NOT NULL,"
                + "date TEXT NOT NULL,"
                + "note TEXT);";
        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Connection connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection(URL);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Не найден драйвер SQLite!", e);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка подключения к базе данных!", e);
        }
    }

    public void addBirthday(String name, String date, String note) {
        String sql = "INSERT INTO birthdays (name, date, note) VALUES (?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, date);
            pstmt.setString(3, note);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteBirthday(int id) {
        String sql = "DELETE FROM birthdays WHERE id = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateBirthday(int id, String name, String date, String note) {
        String sql = "UPDATE birthdays SET name = ?, date = ?, note = ? WHERE id = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, date);
            pstmt.setString(3, note);
            pstmt.setInt(4, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Birthday> getAllBirthdays() {
        List<Birthday> birthdays = new ArrayList<>();
        String sql = "SELECT * FROM birthdays ORDER BY date ASC";

        try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                birthdays.add(new Birthday(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("date"),
                        rs.getString("note")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return birthdays;
    }

    public List<Birthday> getUpcomingBirthdays(int daysAhead) {
        List<Birthday> upcomingBirthdays = new ArrayList<>();
        LocalDate today = LocalDate.now();
        LocalDate futureDate = today.plusDays(daysAhead);

        String sql = "SELECT * FROM birthdays WHERE date BETWEEN ? AND ? ORDER BY date ASC";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, today.toString());
            pstmt.setString(2, futureDate.toString());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                upcomingBirthdays.add(new Birthday(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("date"),
                        rs.getString("note")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return upcomingBirthdays;
    }
}