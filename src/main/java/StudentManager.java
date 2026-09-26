//package java;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
public class StudentManager
{

    ArrayList<Student> students = new ArrayList<>();

    void addStudent(Student student)
    {
        students.add(student);
        saveToFile();
        System.out.println("Student added successfully.");
    }
    void viewStudents() {

        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        students.sort((s1, s2) -> s1.id - s2.id);

        for (Student student : students) {
            student.display();
            System.out.println("----------------");
        }
    }
    void searchStudent(int id) {

        for (Student student : students) {

            if (student.id == id) {
                student.display();
                return;
            }
        }

        System.out.println("Student not found.");
    }
    boolean studentExists(int id) {

        for (Student student : students) {

            if (student.id == id) {
                return true;
            }
        }

        return false;
    }
    void updateStudent(int id, String name, int age, String course) {

        for (Student student : students) {

            if (student.id == id) {

                student.setName(name);
                student.setAge(age);
                student.setCourse(course);

                System.out.println("Student updated successfully.");
                return;
            }
        }

        System.out.println("Student not found.");
    }
    void deleteStudent(int id) {

        for (Student student : students) {

            if (student.id == id) {

                students.remove(student);

                saveToFile();

                System.out.println("Student deleted successfully.");
                return;
            }
        }

        System.out.println("Student not found.");
    }

    void saveToFile() {

        System.out.println(
                new java.io.File("src/main/java/Students.txt").getAbsolutePath()
        );

        try {

            FileWriter writer = new FileWriter("src/main/java/Students.txt");

            for (Student student : students) {

                writer.write(
                        student.id + "," +
                                student.name + "," +
                                student.age + "," +
                                student.course + "\n"
                );
            }

            writer.close();

            System.out.println("Students saved successfully.");

        } catch (IOException e) {

            System.out.println("Error while saving file.");
        }
    }
    void loadFromFile() {

        try {

            BufferedReader reader =
                    new BufferedReader(new FileReader("src/main/java/Students.txt"));

            String line;

            while ((line = reader.readLine()) != null) {

                String[] data = line.split(",");

                int id = Integer.parseInt(data[0]);
                String name = data[1];
                int age = Integer.parseInt(data[2]);
                String course = data[3];

                Student student =
                        new Student(id, name, age, course);

                students.add(student);
            }

            reader.close();

            System.out.println("Students information loaded successfully.");

        } catch (IOException e) {

            System.out.println("File not found.");
        }
    }
}


