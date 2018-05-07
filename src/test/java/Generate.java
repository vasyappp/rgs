import java.util.ArrayList;
import java.util.Random;

public class Generate {
    private static ArrayList<String> lastNamePool;
    private static ArrayList<String> firstNamePool;
    private static ArrayList<String> middleNamePool;

    public static void generatePool() {
        lastNamePool = new ArrayList<String>();
        lastNamePool.add("Васильев");
        lastNamePool.add("Петров");
        lastNamePool.add("Иванов");

        firstNamePool = new ArrayList<String>();
        firstNamePool.add("Василий");
        firstNamePool.add("Иван");
        firstNamePool.add("Петр");

        middleNamePool = new ArrayList<String>();
        middleNamePool.add("Васильевич");
        middleNamePool.add("Петрович");
        middleNamePool.add("Иванович");
    }

    public static String generateLastName() {
        Random random = new Random();
        String lastName = lastNamePool.get(random.nextInt(lastNamePool.size()));
        return lastName;
    }

    public static String generateFirstName() {
        Random random = new Random();
        String firstName = firstNamePool.get(random.nextInt(firstNamePool.size()));
        return firstName;
    }

    public static String generateMiddleName() {
        Random random = new Random();
        String middleName = middleNamePool.get(random.nextInt(firstNamePool.size()));
        return middleName;
    }

    public static String generatePhone() {
        String phone = "9";
        Random random = new Random();

        for (int i = 0; i < 9; i++) {
            Integer value = random.nextInt(10);
            phone += value.toString();
        }

        return phone;
    }

    public static String generateDate() {
        String date = "";
        Random random = new Random();

        Integer day = random.nextInt(30) + 1;
        Integer month = random.nextInt(7) + 6;

        if (day < 10) {
            date += "0";
        }

        date += day.toString();

        if (month < 10) {
            date += "0";
        }

        date += month.toString();

        date += "2018";

        return date;
    }
}
