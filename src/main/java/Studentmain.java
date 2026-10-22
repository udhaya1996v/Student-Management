
import java.util.Scanner;

public class Studentmain {

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        StudentManager manager = new StudentManager();

        manager.loadFromFile();

        System.out.println("Welcome to Student Management System!");

        while (true) {
            System.out.println("\n===== MAIN MENU =====");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Search Student");
            System.out.println("4. Update Student");
            System.out.println("5. Delete Student");
            System.out.println("6. Exit");

            int choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    addStudent(manager);
                    break;

                case 2:
                    manager.viewStudents();
                    break;

                case 3: {
                    int id = readId();
                    manager.searchStudent(id);
                    break;
                }

                case 4: {
                    int id = readId();

                    if (!manager.hasStudentId(id)) {
                        System.out.println("Student not found.");
                        break;
                    }

                    System.out.println("Enter new details:");
                    String name = readName();
                    int age = readAge();
                    String course = chooseCourse();

                    manager.updateStudent(id, name, age, course);
                    break;
                }

                case 5: {
                    int id = readId();
                    manager.deleteStudent(id);
                    break;
                }

                case 6:
                    System.out.println(
                            "Thank you for using Student Management System!");
                    sc.close();
                    return;

                default:
                    System.out.println(
                            "Invalid choice. Please select 1-6.");
            }
        }
    }

    private static void addStudent(StudentManager manager) {
        int id = readId();

        // Check duplicate ID before asking for other details.
        if (manager.hasStudentId(id)) {
            System.out.println("ID " + id + " already exists!");
            return;
        }

        String name = readName();
        int age = readAge();
        String course = chooseCourse();

        try {
            Student student = new Student(id, name, age, course);
            manager.addStudent(student);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid details: " + e.getMessage());
        }
    }

    private static int readInt(String message) {
        while (true) {
            System.out.print(message);

            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static int readId() {
        while (true) {
            int id = readInt("Enter student ID (1-1000): ");

            if (id >= 1 && id <= 1000) {
                return id;
            }

            System.out.println("ID must be between 1 and 1000.");
        }
    }

    private static String readName() {
        while (true) {
            System.out.print("Enter student name: ");
            String name = sc.nextLine().trim();

            if (name.matches("[\\p{L} .'-]+")) {
                return name;
            }

            System.out.println(
                    "Name must contain letters only. Please try again.");
        }
    }

    private static int readAge() {
        while (true) {
            int age = readInt("Enter age (18-50): ");

            if (age >= 18 && age <= 50) {
                return age;
            }

            System.out.println("Age must be between 18 and 50.");
        }
    }

    private static String chooseCourse() {
        String[] courses = {
                "Full Stack Development",
                "Java Development",
                "Python Development",
                "Data Analytics",
                "Cloud Computing"
        };

        System.out.println("\nAvailable Courses:");

        for (int i = 0; i < courses.length; i++) {
            System.out.println((i + 1) + ". " + courses[i]);
        }

        while (true) {
            int choice = readInt("Select course (1-5): ");

            if (choice >= 1 && choice <= courses.length) {
                return courses[choice - 1];
            }

            System.out.println("Invalid choice. Please select 1-5.");
        }
    }
}
