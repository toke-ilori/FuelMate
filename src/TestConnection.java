import java.util.Scanner;

public class TestConnection {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n--- FuelMate Menu ---");
            System.out.println("1. View Food Entries");
            System.out.println("2. Add Food Entry");
            System.out.println("3. Update Food Entry");
            System.out.println("4. Delete Food Entry");
            System.out.println("5. Exit");
            System.out.print("Choose: ");

            choice = scanner.nextInt();

            switch (choice) {

                case 1:
                    FoodEntryDAO.viewFoodEntries();
                    break;

                case 2:
                    System.out.print("User ID: ");
                    int userId = scanner.nextInt();

                    scanner.nextLine(); // clear buffer

                    System.out.print("Food name: ");
                    String foodName = scanner.nextLine();

                    System.out.print("Calories: ");
                    int calories = scanner.nextInt();

                    FoodEntryDAO.addFoodEntry(userId, foodName, calories, 0, 0, 0);
                    break;

                case 3:
                    System.out.print("Entry ID: ");
                    int updateId = scanner.nextInt();

                    System.out.print("New Calories: ");
                    int newCalories = scanner.nextInt();

                    FoodEntryDAO.updateFoodEntry(updateId, newCalories);
                    break;

                case 4:
                    System.out.print("Entry ID: ");
                    int deleteId = scanner.nextInt();

                    FoodEntryDAO.deleteFoodEntry(deleteId);
                    break;

            }

        } while (choice != 5);

        System.out.println("Goodbye!");
    }
}