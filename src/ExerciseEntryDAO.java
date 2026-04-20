import java.sql.Connection;
import java.sql.PreparedStatement;

public class ExerciseEntryDAO {

    public static void addExercise(int userId, String type, int duration, int caloriesBurned) {

        try {
            Connection conn = DatabaseConnection.getConnection();

            String sql = "INSERT INTO exercise_entries (user_id, exercise_type, duration_minutes, calories_burned, entry_date) VALUES (?, ?, ?, ?, CURDATE())";

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, userId);
            stmt.setString(2, type);
            stmt.setInt(3, duration);
            stmt.setInt(4, caloriesBurned);

            stmt.executeUpdate();

            System.out.println("Exercise added!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}