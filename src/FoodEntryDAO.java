import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FoodEntryDAO {

    public static String viewFoodEntries(int userId) {

        StringBuilder result = new StringBuilder();
    
        try {
            Connection conn = DatabaseConnection.getConnection();
    
            String sql = "SELECT id, food_name, calories, protein, carbs, fats FROM food_entries WHERE user_id = ?";
            
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
    
            ResultSet rs = stmt.executeQuery();
    
            result.append("🍽 Your Food Entries:\n\n");
    
            while (rs.next()) {
                result.append(
                "ID: " + rs.getInt("id") + " | " +
                rs.getString("food_name") + " | " +
                rs.getInt("calories") + " kcal | " +
                "P: " + rs.getDouble("protein") + "g | " +
                "C: " + rs.getDouble("carbs") + "g | " +
                "F: " + rs.getDouble("fats") + "g\n"
            );
            }
    
            if (result.toString().equals("🍽 Your Food Entries:\n\n")) {
                result.append("No entries found.");
            }
    
        } catch (Exception e) {
            e.printStackTrace();
            return "Error loading food entries";
        }
    
        return result.toString();
    }
    public static void updateFoodEntry(int id, int newCalories) {

        try {
            Connection conn = DatabaseConnection.getConnection();
    
            String sql = "UPDATE food_entries SET calories = ? WHERE id = ?";
    
            PreparedStatement stmt = conn.prepareStatement(sql);
    
            stmt.setInt(1, newCalories);
            stmt.setInt(2, id);
    
            stmt.executeUpdate();
    
            System.out.println("Food entry updated!");
    
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void deleteFoodEntry(int id) {

        try {
            Connection conn = DatabaseConnection.getConnection();
    
            String sql = "DELETE FROM food_entries WHERE id = ?";
    
            PreparedStatement stmt = conn.prepareStatement(sql);
    
            stmt.setInt(1, id);
    
            stmt.executeUpdate();
    
            System.out.println("Food entry deleted!");
    
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void addFoodEntry(int userId, String food, int calories, double protein, double carbs, double fats) {

        try {
            Connection conn = DatabaseConnection.getConnection();
    
            String sql = "INSERT INTO food_entries (user_id, food_name, calories, protein, carbs, fats, entry_date) VALUES (?, ?, ?, ?, ?, ?, CURDATE())";
    
            PreparedStatement stmt = conn.prepareStatement(sql);
    
            stmt.setInt(1, userId);
            stmt.setString(2, food);
            stmt.setInt(3, calories);
            stmt.setDouble(4, protein);
            stmt.setDouble(5, carbs);
            stmt.setDouble(6, fats);
    
            stmt.executeUpdate();
    
            System.out.println("Food added!");
    
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}