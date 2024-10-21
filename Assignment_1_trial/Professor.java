package Assignment_1_trial;

import java.util.HashSet;
import java.util.Set;

public class Professor extends User {
    private Set<Course> taughtCourses;

    public Professor(String name, String email, String password) {
        super(name, email, password);
        this.taughtCourses = new HashSet<>();
    }

    @Override
    public void displayMenu() {
        System.out.println("\n1. Manage Courses");
        System.out.println("2. View Enrolled Students");
        System.out.println("3. Logout");
    }

    public void addCourse(Course course) {
        taughtCourses.add(course);
    }

    public void removeCourse(Course course) {
        taughtCourses.remove(course);
    }

    public Set<Course> getTaughtCourses() {
        return taughtCourses;
    }
}