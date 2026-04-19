import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class FuelMateGUI {

    public static void main(String[] args) {

        JFrame frame = new JFrame("FuelMate 🍽️");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Top panel (inputs)
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JTextField userIdField = new JTextField();
        JTextField foodNameField = new JTextField();
        JTextField caloriesField = new JTextField();

        inputPanel.add(new JLabel("User ID:"));
        inputPanel.add(userIdField);
        inputPanel.add(new JLabel("Food Name:"));
        inputPanel.add(foodNameField);
        inputPanel.add(new JLabel("Calories:"));
        inputPanel.add(caloriesField);

        // Buttons
        JPanel buttonPanel = new JPanel();

        JButton addButton = new JButton("Add Food");
        JButton viewButton = new JButton("View Entries");

        buttonPanel.add(addButton);
        buttonPanel.add(viewButton);

        // Output area
        JTextArea outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.add(scrollPane, BorderLayout.SOUTH);

        // ADD FOOD
        addButton.addActionListener(e -> {
            try {
                int userId = Integer.parseInt(userIdField.getText());
                String foodName = foodNameField.getText();
                int calories = Integer.parseInt(caloriesField.getText());

                FoodEntryDAO.addFoodEntry(userId, foodName, calories, 0, 0, 0);

                outputArea.setText("✅ Food added successfully!");

            } catch (NumberFormatException ex) {
                outputArea.setText("❌ Enter valid numbers!");
            }
        });

        // VIEW ENTRIES 
        viewButton.addActionListener(e -> {
            try {
                Connection conn = DatabaseConnection.getConnection();

                String sql = "SELECT users.name, food_entries.food_name, food_entries.calories " +
                             "FROM users " +
                             "INNER JOIN food_entries ON users.id = food_entries.user_id";

                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery();

                outputArea.setText("👤 User | 🍽 Food | 🔥 Calories\n\n");

                while (rs.next()) {
                    outputArea.append(
                        rs.getString("name") + " | " +
                        rs.getString("food_name") + " | " +
                        rs.getInt("calories") + "\n"
                    );
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                outputArea.setText("Error loading data");
            }
        });

        frame.setVisible(true);
    }
}