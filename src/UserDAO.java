import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

    public static int createUser(String name, double weight, double height, int age, String activity) {
        int userId = -1;
    
        try {
            Connection conn = DatabaseConnection.getConnection();
    
            String sql = "INSERT INTO users (name, weight, height, age, activity_level) VALUES (?, ?, ?, ?, ?)";
    
            PreparedStatement stmt = conn.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS);
    
            stmt.setString(1, name);
            stmt.setDouble(2, weight);
            stmt.setDouble(3, height);
            stmt.setInt(4, age);
            stmt.setString(5, activity);
    
            int rows = stmt.executeUpdate();
    
            System.out.println("Rows inserted: " + rows);
    
            ResultSet rs = stmt.getGeneratedKeys();            
            while (rs.next()) {
                System.out.println("Generated key: " + rs.getInt(1));
                userId = rs.getInt(1);
            }
    
            rs.close();
            stmt.close();
    
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        return userId;
    }
    

    public static String getUserAndTDEE(int userId) {

        String result = "";
    
        try {
            Connection conn = DatabaseConnection.getConnection();
    
            String sql = "SELECT * FROM users WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
    
            ResultSet rs = stmt.executeQuery();
    
            if (rs.next()) {
    
                double weight = rs.getDouble("weight");
                double height = rs.getDouble("height");
                int age = rs.getInt("age");
                String activity = rs.getString("activity_level");
    
                int tdee = TDEECalculator.calculateTDEE(weight, height, age, activity);
    
                int cut = tdee - 500;
                int bulk = tdee + 500;
    
                result = "👤 User: " + rs.getString("name") + "\n\n" +
                         "🔥 TDEE: " + tdee + "\n" +
                         "✂️ Cut: " + cut + "\n" +
                         "⚖️ Maintain: " + tdee + "\n" +
                         "💪 Bulk: " + bulk;
    
            } else {
                result = "❌ User not found!";
            }
    
        } catch (Exception e) {
            e.printStackTrace();
            result = "Error retrieving user";
        }
    
        return result;
    }
}