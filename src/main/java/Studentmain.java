//package java;
import java.util.Scanner;

public class Studentmain {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        StudentManager manager = new StudentManager();
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
            int choice = sc.nextInt();

            switch (choice) {

                case 1:

                    System.out.print("Enter ID: ");
                    int id = sc.nextInt();

                    if (id < 1 || id > 1000) {
                        System.out.println("Invalid ID. Enter a number between 1 and 1000.");
                        break;
                    }
                    if (manager.studentExists(id)) {
                        System.out.println("Student ID already exists.");
                        break;
                    }

                    sc.nextLine();

                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Age: ");
                    int age = sc.nextInt();

                    if (age <18) {
                        System.out.println("Invalid age.");
                        break;
                    }
                    sc.nextLine();

                    System.out.print("Enter Course: ");
                    String course = sc.nextLine();

                    Student student =
                            new Student(id, name, age, course);

                    manager.addStudent(student);

                    break;


                case 2:

                    manager.viewStudents();

                    break;


                case 3:

                    System.out.print("Enter ID to search: ");
                    int searchId = sc.nextInt();

                    manager.searchStudent(searchId);

                    break;


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

                    System.out.print("Enter new Age: ");
                    int newAge = sc.nextInt();

                    sc.nextLine();

                    System.out.print("Enter new Course: ");
                    String newCourse = sc.nextLine();

                    manager.updateStudent(
                            updateId,
                            newName,
                            newAge,
                            newCourse
                    );

                    break;


                case 5:

                    System.out.print("Enter ID to delete: ");
                    int deleteId = sc.nextInt();

                    manager.deleteStudent(deleteId);

                    break;

                case 6:

                    System.out.println("Thank you!");

                    sc.close();

                    return;


                default:

                    System.out.println("Invalid choice.");
            }
        }
    }
}