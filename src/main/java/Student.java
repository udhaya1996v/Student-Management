
public class Student {

    private int id;
    private String name;
    private int age;
    private String course;

    // Constructor
    public Student(int id, String name, int age, String course) {
        setId(id);
        setName(name);
        setAge(age);
        setCourse(course);
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getCourse() {
        return course;
    }

    // Validate ID
    public void setId(int id) {
        if (id < 1 || id > 1000) {
            throw new IllegalArgumentException(
                    "ID must be between 1 and 1000.");
        }
        this.id = id;
    }

    // Validate name
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()
                || name.contains(",")) {
            throw new IllegalArgumentException(
                    "Name cannot be empty or contain commas.");
        }

        if (!name.trim().matches("[\\p{L} .'-]+")) {
            throw new IllegalArgumentException(
                    "Name must contain letters only.");
        }

        this.name = name.trim();
    }

    // Validate age
    public void setAge(int age) {
        if (age < 18 || age > 50) {
            throw new IllegalArgumentException(
                    "Age must be between 18 and 50.");
        }
        this.age = age;
    }

    // Validate course
    public void setCourse(String course) {
        if (course == null || course.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Course cannot be empty.");
        }

        String[] courses = {
                "Full Stack Development",
                "Java Development",
                "Python Development",
                "Data Analytics",
                "Cloud Computing"
        };

        for (String c : courses) {
            if (c.equalsIgnoreCase(course.trim())) {
                this.course = c;
                return;
            }
        }

        throw new IllegalArgumentException(
                "Please select one of the five available courses.");
    }
}
