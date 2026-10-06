import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Scanner;

public class StudentManager {

    private ArrayList<Student> students = new ArrayList<>();

    // Add student
    public void addStudent(Student student) {

        students.add(student);
        saveToFile();

        System.out.println("Student added successfully.");
    }

    // Check whether ID already exists
    public boolean studentExists(int id) {

        for (Student student : students) {

            if (student.getId() == id) {
                return true;
            }
        }

        return false;
    }

    // View all students
    public void viewStudents() {

        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }

        // Sort by ID in ascending order
        students.sort(Comparator.comparingInt(Student::getId));

        System.out.println("\n===== Student Records =====");

        for (Student student : students) {

            student.display();
            System.out.println("-------------------------");
        }
    }

    // Search student
    public void searchStudent(int id) {

        for (Student student : students) {

            if (student.getId() == id) {

                System.out.println("\nStudent found:");
                student.display();
                return;
            }
        }

        System.out.println("Student not found.");
    }
    // Update student
    public void updateStudent(int id, Scanner scanner) {

        for (Student student : students) {

            if (student.getId() == id) {

                // New Name
                System.out.print("Enter new name: ");
                String name = scanner.nextLine();

                if (!name.matches("[a-zA-Z ]+")) {
                    System.out.println(
                            "Invalid name. Please enter letters only."
                    );
                    return;
                }

                // New Age
                System.out.print("Enter new age: ");

                if (!scanner.hasNextInt()) {
                    System.out.println(
                            "Invalid age. Please enter a number."
                    );
                    scanner.nextLine();
                    return;
                }

                int age = scanner.nextInt();
                scanner.nextLine();

                if (age < 18 || age > 50) {
                    System.out.println(
                            "Age must be between 18 and 50."
                    );
                    return;
                }

                // New Course
                System.out.println("\nSelect New Course:");
                System.out.println("1. Java");
                System.out.println("2. Python");
                System.out.println("3. C++");
                System.out.println("4. JavaScript");
                System.out.println("5. SQL");
                System.out.println("6. Data Structures");

                System.out.print("Enter course choice: ");

                if (!scanner.hasNextInt()) {
                    System.out.println(
                            "Invalid choice. Please enter a number."
                    );
                    scanner.nextLine();
                    return;
                }

                int courseChoice = scanner.nextInt();
                scanner.nextLine();

                String course;

                switch (courseChoice) {

                    case 1:
                        course = "Java";
                        break;

                    case 2:
                        course = "Python";
                        break;

                    case 3:
                        course = "C++";
                        break;

                    case 4:
                        course = "JavaScript";
                        break;

                    case 5:
                        course = "SQL";
                        break;

                    case 6:
                        course = "Data Structures";
                        break;

                    default:
                        System.out.println(
                                "Invalid course choice. Please select 1 to 6."
                        );
                        return;
                }

                // Update student
                student.setName(name);
                student.setAge(age);
                student.setCourse(course);

                // Save updated record
                saveToFile();

                System.out.println(
                        "Student updated successfully!"
                );

                return;
            }
        }

        System.out.println("Student not found!");
    }
    // Delete student
    public void deleteStudent(int id) {

        Iterator<Student> iterator = students.iterator();

        while (iterator.hasNext()) {

            Student student = iterator.next();

            if (student.getId() == id) {

                iterator.remove();

                saveToFile();

                System.out.println("Student deleted successfully.");
                return;
            }
        }

        System.out.println("Student not found.");
    }

    // Save records to file
    public void saveToFile() {

        try {

            FileWriter writer = new FileWriter("Students.txt");

            for (Student student : students) {

                writer.write(
                        student.getId() + "," +
                                student.getName() + "," +
                                student.getAge() + "," +
                                student.getCourse() + "\n"
                );
            }

            writer.close();

        } catch (IOException e) {

            System.out.println("Error while saving records.");
        }
    }

    // Load records from file
    public void loadFromFile() {

        File file = new File("Students.txt");

        if (!file.exists()) {
            return;
        }

        try {

            BufferedReader reader =
                    new BufferedReader(new FileReader(file));

            String line;

            while ((line = reader.readLine()) != null) {

                String[] data = line.split(",");

                if (data.length != 4) {
                    continue;
                }

                int id = Integer.parseInt(data[0]);
                String name = data[1];
                int age = Integer.parseInt(data[2]);
                String course = data[3];

                Student student =
                        new Student(id, name, age, course);

                students.add(student);
            }

            reader.close();

        } catch (IOException | NumberFormatException e) {

            System.out.println("Error while loading records.");
        }
    }
}
