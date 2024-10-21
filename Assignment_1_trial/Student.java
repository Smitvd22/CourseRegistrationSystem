package Assignment_1_trial;

import java.util.HashSet;
import java.util.Set;

public class Student extends User {
    private int currentSemester;
    private Set<Course> enrolledCourses;
    private Set<Course> completedCourses;
    private double cgpa;

    public Student(String name, String email, String password) {
        super(name, email, password);
        this.currentSemester = 1;
        this.enrolledCourses = new HashSet<>();
        this.completedCourses = new HashSet<>();
        this.cgpa = 0.0;
    }

    @Override
    public void displayMenu() {
        System.out.println("\n1. View Available Courses");
        System.out.println("2. Register for Courses");
        System.out.println("3. View Schedule");
        System.out.println("4. Track Academic Progress");
        System.out.println("5. Drop Course");
        System.out.println("6. Submit Complaint");
        System.out.println("7. Logout");
    }

    public void registerForCourse(Course course) {
        enrolledCourses.add(course);
    }

    public void dropCourse(Course course) {
        enrolledCourses.remove(course);
    }

    public Set<Course> getEnrolledCourses() {
        return enrolledCourses;
    }

    public Set<Course> getCompletedCourses() {
        return completedCourses;
    }

    public double getCgpa() {
        return cgpa;
    }

    public void setCgpa(double cgpa) {
        this.cgpa = cgpa;
    }

    public int getCurrentSemester() {
        return currentSemester;
    }

    public void setCurrentSemester(int currentSemester) {
        this.currentSemester = currentSemester;
    }
}