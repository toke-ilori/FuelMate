import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class FuelMateGUI {

    public static void main(String[] args) {

        JFrame frame = new JFrame("FuelMate");
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
    
        // USER INPUTS
        JTextField nameField = new JTextField();
        JTextField weightField = new JTextField();
        JTextField heightField = new JTextField();
        JTextField ageField = new JTextField();
        JTextField activityField = new JTextField();
    
        // USER LOAD
        JTextField userIdField = new JTextField();
    
        // FOOD INPUTS
        JTextField foodField = new JTextField();
        JTextField caloriesField = new JTextField();
        JTextField proteinField = new JTextField();
        JTextField carbsField = new JTextField();
        JTextField fatsField = new JTextField();
    
        // OUTPUT
        JTextArea output = new JTextArea();
        output.setEditable(false);
    
        // BUTTONS
        JButton createUserBtn = new JButton("Create User");
        JButton loadUserBtn = new JButton("Load User");
        JButton addFoodBtn = new JButton("Add Food");
        JButton viewFoodBtn = new JButton("View Food");
    
        // MAIN PANEL
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
    
        // USER PANEL
        JPanel userPanel = new JPanel(new GridLayout(5,2,10,10));
        userPanel.setBorder(BorderFactory.createTitledBorder("👤 Create User"));
    
        userPanel.add(new JLabel("Name")); userPanel.add(nameField);
        userPanel.add(new JLabel("Weight (kg)")); userPanel.add(weightField);
        userPanel.add(new JLabel("Height (cm)")); userPanel.add(heightField);
        userPanel.add(new JLabel("Age")); userPanel.add(ageField);
        userPanel.add(new JLabel("Activity(light,moderate,active)")); userPanel.add(activityField);
    
        // LOAD PANEL
        JPanel loadPanel = new JPanel(new GridLayout(2,2,10,10));
        loadPanel.setBorder(BorderFactory.createTitledBorder("🔑 Load User"));
    
        loadPanel.add(new JLabel("User ID"));
        loadPanel.add(userIdField);
        loadPanel.add(loadUserBtn);
    
        // FOOD PANEL
        JPanel foodPanel = new JPanel(new GridLayout(5,2,10,10));
        foodPanel.setBorder(BorderFactory.createTitledBorder("🍽 Food Entry"));
    
        foodPanel.add(new JLabel("Food Name")); foodPanel.add(foodField);
        foodPanel.add(new JLabel("Calories")); foodPanel.add(caloriesField);
        foodPanel.add(new JLabel("Protein")); foodPanel.add(proteinField);
        foodPanel.add(new JLabel("Carbs")); foodPanel.add(carbsField);
        foodPanel.add(new JLabel("Fats")); foodPanel.add(fatsField);
    
        // BUTTON PANEL
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(createUserBtn);
        buttonPanel.add(addFoodBtn);
        buttonPanel.add(viewFoodBtn);
    
        // ADD EVERYTHING 
        mainPanel.add(userPanel);
        mainPanel.add(createUserBtn);
        mainPanel.add(loadPanel);
        mainPanel.add(foodPanel);
        mainPanel.add(buttonPanel);
    
        frame.add(mainPanel, BorderLayout.NORTH);
        frame.add(new JScrollPane(output), BorderLayout.CENTER);
    

          // CREATE USER
          createUserBtn.addActionListener(e -> {
            try {
                String name = nameField.getText();
                double weight = Double.parseDouble(weightField.getText());
                double height = Double.parseDouble(heightField.getText());
                int age = Integer.parseInt(ageField.getText());
                String activity = activityField.getText();

                int id = UserDAO.createUser(name, weight, height, age, activity);
                output.setText("✅ User created! Your ID is: " + id);

            } catch (Exception ex) {
                output.setText("❌ Invalid input");
            }
        });

        // LOAD USER + TDEE
        loadUserBtn.addActionListener(e -> {
            try {
                String idText = userIdField.getText().trim();
                if (idText.isEmpty()) {
                    output.setText("❌ Please enter User ID");
                    return;
                }
                int userId = Integer.parseInt(idText);
                                // We print to terminal AND GUI (quick version)
                output.setText("Loading user...\n");

                String result = UserDAO.getUserAndTDEE(userId);
                output.setText(result);

            } catch (Exception ex) {
                output.setText("❌ Invalid ID");
            }
        });

        // ADD FOOD
        addFoodBtn.addActionListener(e -> {
            try {
                int userId = Integer.parseInt(userIdField.getText());
                String food = foodField.getText();
                int calories = Integer.parseInt(caloriesField.getText());
                double protein = Double.parseDouble(proteinField.getText());
                double carbs = Double.parseDouble(carbsField.getText());
                double fats = Double.parseDouble(fatsField.getText());
                FoodEntryDAO.addFoodEntry(userId, food, calories, protein, carbs, fats);

                output.setText("✅ Food added!");

            } catch (Exception ex) {
                output.setText("❌ Error adding food");
            }
        });

        // VIEW FOOD
        viewFoodBtn.addActionListener(e -> {
            try {
                int userId = Integer.parseInt(userIdField.getText());
                String result = FoodEntryDAO.viewFoodEntries(userId);
                output.setText(result);

            } catch (Exception ex) {
                output.setText("Error loading food");
            }
        });

        frame.setVisible(true);
    }
}
