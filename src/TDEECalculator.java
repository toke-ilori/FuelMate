public class TDEECalculator {

    public static int calculateTDEE(double weight, double height, int age, String activityLevel) {

        // Mifflin-St Jeor Formula
        double bmr = (10 * weight) + (6.25 * height) - (5 * age) + 5;

        double multiplier = 1.2;

        switch (activityLevel.toLowerCase()) {
            case "light":
                multiplier = 1.375;
                break;
            case "moderate":
                multiplier = 1.55;
                break;
            case "active":
                multiplier = 1.725;
                break;
        }

        return (int) (bmr * multiplier);
    }
}