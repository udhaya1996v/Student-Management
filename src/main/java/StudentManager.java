
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class StudentManager {

    private final ArrayList<Student> students = new ArrayList<>();
    private final File file = new File("students.txt");

    // Load student records from the file.
    public void loadFromFile() {
        if (!file.exists()) {
            return;
        }

        Set<Integer> loadedIds = new HashSet<>();
        int lineNumber = 0;

        try (BufferedReader br =
                     new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = br.readLine()) != null) {
                lineNumber++;

                if (line.trim().isEmpty()) {
                    System.out.println(
                            "Skipping empty line: " + lineNumber);
                    continue;
                }

                try {
                    String[] data = line.split(",", -1);

                    // Support old records with 3 fields and new records
                    // with 4 fields.
                    if (data.length != 3 && data.length != 4) {
                        throw new IllegalArgumentException(
                                "Incorrect number of fields");
                    }

                    int id = Integer.parseInt(data[0].trim());
                    String name = data[1].trim();
                    int age = Integer.parseInt(data[2].trim());

                    String course = data.length == 4
                            ? data[3].trim()
                            : "Java Development";

                    if (loadedIds.contains(id)) {
                        System.out.println(
                                "Skipping duplicate ID " + id
                                        + " at line " + lineNumber);
                        continue;
                    }

                    Student student =
                            new Student(id, name, age, course);

                    students.add(student);
                    loadedIds.add(id);

                } catch (IllegalArgumentException e) {
                    System.out.println(
                            "Invalid record at line " + lineNumber
                                    + ": " + e.getMessage());
                }
            }

            System.out.println(
                    "Loaded " + students.size() + " student record(s).");

        } catch (IOException e) {
            System.out.println(
                    "Could not read student file: " + e.getMessage());
        }
    }

    // Check whether an ID already exists.
    public boolean hasStudentId(int id) {
        for (Student student : students) {
            if (student.getId() == id) {
                return true;
            }
        }
        return false;
    }

    public boolean isValidId(int id) {
        return id >= 1 && id <= 1000;
    }

    // Add a student and save the changes.
    public void addStudent(Student student) {
        if (hasStudentId(student.getId())) {
            System.out.println(
                    "ID " + student.getId() + " already exists!");
            return;
        }

        students.add(student);

        if (saveToFile()) {
            System.out.println("Student added successfully!");
        } else {
            students.remove(student);
            System.out.println(
                    "Student was not added because saving failed.");
        }
    }

    // View students sorted by ID.
    public void viewStudents() {
        if (students.isEmpty()) {
            System.out.println("No student records found.");
            return;
        }

        students.sort(Comparator.comparingInt(Student::getId));

        System.out.println("\n--- Student Records ---");
        System.out.printf("%-8s %-20s %-8s %-25s%n",
                "ID", "Name", "Age", "Course");
        System.out.println(
                "---------------------------------------------------------------");

        for (Student s : students) {
            System.out.printf("%-8d %-20s %-8d %-25s%n",
                    s.getId(), s.getName(),
                    s.getAge(), s.getCourse());
        }
    }

    // Search by ID.
    public void searchStudent(int id) {
        for (Student s : students) {
            if (s.getId() == id) {
                System.out.println("\nStudent found:");
                System.out.println("ID: " + s.getId());
                System.out.println("Name: " + s.getName());
                System.out.println("Age: " + s.getAge());
                System.out.println("Course: " + s.getCourse());
                return;
            }
        }

        System.out.println("Student not found.");
    }

    // Update a student and restore the old record if saving fails.
    public void updateStudent(
            int id, String name, int age, String course) {

        for (int i = 0; i < students.size(); i++) {
            Student oldStudent = students.get(i);

            if (oldStudent.getId() == id) {
                try {
                    Student updatedStudent =
                            new Student(id, name, age, course);

                    students.set(i, updatedStudent);

                    if (saveToFile()) {
                        System.out.println(
                                "Student updated successfully!");
                    } else {
                        students.set(i, oldStudent);
                        System.out.println(
                                "Update cancelled because saving failed.");
                    }

                } catch (IllegalArgumentException e) {
                    System.out.println(
                            "Invalid details: " + e.getMessage());
                }
                return;
            }
        }

        System.out.println("Student not found.");
    }

    // Delete a student and restore it if saving fails.
    public void deleteStudent(int id) {
        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);

            if (student.getId() == id) {
                students.remove(i);

                if (saveToFile()) {
                    System.out.println(
                            "Student deleted successfully!");
                } else {
                    students.add(i, student);
                    System.out.println(
                            "Deletion cancelled because saving failed.");
                }
                return;
            }
        }

        System.out.println("Student not found.");
    }

    // Save records to a temporary file before replacing the main file.
    private boolean saveToFile() {
        Path target = file.toPath();
        Path temp = target.resolveSibling(
                target.getFileName().toString() + ".tmp");

        try {
            try (BufferedWriter bw =
                         Files.newBufferedWriter(temp)) {

                for (Student s : students) {
                    bw.write(s.getId() + ","
                            + s.getName() + ","
                            + s.getAge() + ","
                            + s.getCourse());
                    bw.newLine();
                }
            }

            try {
                Files.move(temp, target,
                        StandardCopyOption.REPLACE_EXISTING,
                        StandardCopyOption.ATOMIC_MOVE);
            } catch (AtomicMoveNotSupportedException e) {
                Files.move(temp, target,
                        StandardCopyOption.REPLACE_EXISTING);
            }

            return true;

        } catch (IOException e) {
            System.out.println(
                    "Error saving student records: " + e.getMessage());
            return false;

        } finally {
            try {
                Files.deleteIfExists(temp);
            } catch (IOException e) {
                System.out.println(
                        "Warning: temporary file could not be deleted.");
            }
        }
    }
}
