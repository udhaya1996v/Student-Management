import java.util.Scanner;

public class Studentmain {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        StudentManager manager = new StudentManager();

        // Load existing records when program starts
        manager.loadFromFile();

        while (true) {

            System.out.println("\n===== Student Management System =====");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Search Student");
            System.out.println("4. Update Student");
            System.out.println("5. Delete Student");
            System.out.println("6. Exit");

            System.out.print("Enter your choice: ");

            int choice;

            try {
                choice = sc.nextInt();
            } catch (Exception e) {

                System.out.println("Please enter a valid number.");
                sc.nextLine();
                continue;
            }

            switch (choice) {

                // ADD STUDENT
                case 1:

                    System.out.print("Enter ID (1-1000): ");

                    if (!sc.hasNextInt()) {
                        System.out.println("Invalid ID. Please enter a number.");
                        sc.nextLine();
                        break;
                    }

                    int id = sc.nextInt();

                    if (id < 1 || id > 1000) {
                        System.out.println("ID must be between 1 and 1000.");
                        break;
                    }

                    if (manager.studentExists(id)) {

                        System.out.println(
                                "Student ID already exists."
                        );

                        break;
                    }

                    sc.nextLine();

                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();

                    if (!name.matches("[a-zA-Z ]+")) {
                        System.out.println("Invalid name. Please enter letters only.");
                        break;
                    }

                    System.out.print("Enter Age: ");

                    if (!sc.hasNextInt()) {
                        System.out.println("Invalid age. Please enter a number.");
                        sc.nextLine();
                        break;
                    }

                    int age = sc.nextInt();

                    if (age < 18 || age > 50) {
                        System.out.println("Age must be between 18 and 50.");
                        break;
                    }
                    System.out.println("Select Course:");
                    System.out.println("1. Java");
                    System.out.println("2. Python");
                    System.out.println("3. C++");
                    System.out.println("4. JavaScript");
                    System.out.println("5. SQL");
                    System.out.println("6. Data Structures");

                    System.out.print("Enter course choice: ");

                    if (!sc.hasNextInt()) {
                        System.out.println("Invalid choice. Please enter a number.");
                        sc.nextLine();
                        break;
                    }

                    int courseChoice = sc.nextInt();

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
                            System.out.println("Invalid course choice. Please select 1 to 6.");
                            continue;
                    }

                    Student student =
                            new Student(id, name, age, course);

                    manager.addStudent(student);

                    break;


                // VIEW STUDENTS
                case 2:

                    manager.viewStudents();

                    break;


                // SEARCH STUDENT
                case 3:

                    System.out.print("Enter ID to search: ");
                    int searchId = sc.nextInt();

                    manager.searchStudent(searchId);

                    break;


                // UPDATE STUDENT
                case 4:

                    System.out.print("Enter ID to update: ");
                    int updateId = sc.nextInt();

                    if (!manager.studentExists(updateId)) {

                        System.out.println("Student not found.");
                        break;
                    }

                    sc.nextLine();

                    System.out.print("Enter new Name: ");
                    String newName = sc.nextLine();

                    if (newName.trim().isEmpty()) {

                        System.out.println("Name cannot be empty.");
                        break;
                    }

                    System.out.print("Enter new Age: ");
                    int newAge = sc.nextInt();

                    if (newAge <= 0 || newAge > 100) {

                        System.out.println("Invalid age.");
                        break;
                    }

                    System.out.println("\nSelect New Course:");
                    System.out.println("1. Java");
                    System.out.println("2. Python");
                    System.out.println("3. C++");
                    System.out.println("4. JavaScript");
                    System.out.println("5. SQL");
                    System.out.println("6. Data Structures");

                    System.out.print("Enter course choice: ");
                    int newCourseChoice = sc.nextInt();

                    String newCourse;

                    switch (newCourseChoice) {

                        case 1:
                            newCourse = "Java";
                            break;

                        case 2:
                            newCourse = "Python";
                            break;

                        case 3:
                            newCourse = "C++";
                            break;

                        case 4:
                            newCourse = "JavaScript";
                            break;

                        case 5:
                            newCourse = "SQL";
                            break;

                        case 6:
                            newCourse = "Data Structures";
                            break;

                        default:
                            System.out.println("Invalid course choice.");
                            continue;
                    }

                    manager.updateStudent(
                            updateId,
                            newName,
                            newAge,
                            newCourse
                    );

                    break;


                // DELETE STUDENT
                case 5:

                    System.out.print("Enter ID to delete: ");
                    int deleteId = sc.nextInt();

                    manager.deleteStudent(deleteId);

                    break;


                // EXIT
                case 6:

                    System.out.println(
                            "Thank you for using Student Management System."
                    );

                    sc.close();

                    return;


                default:

                    System.out.println(
                            "Invalid choice. Please select 1-6."
                    );
            }
        }
    }
}