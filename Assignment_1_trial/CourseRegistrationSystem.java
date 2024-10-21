package Assignment_1_trial;

import java.util.HashSet;
import java.util.Set;
import java.util.Scanner;
import java.util.Iterator;
import java.util.regex.Pattern;
import java.io.Console;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Scanner;
import java.util.Iterator;
import java.util.regex.Pattern;
import java.io.Console;

class EarlyDropException extends Exception {
    private LocalDate eligibleDate;
    private long daysRemaining;

    public EarlyDropException(String message, LocalDate eligibleDate, long daysRemaining) {
        super(message);
        this.eligibleDate = eligibleDate;
        this.daysRemaining = daysRemaining;
    }

    public LocalDate getEligibleDate() {
        return eligibleDate;
    }

    public long getDaysRemaining() {
        return daysRemaining;
    }
}

public class CourseRegistrationSystem {
    private Set<User> users;
    private Set<Course> courses;
    private Set<Complaint> complaints;
    private Scanner scanner;

    public CourseRegistrationSystem() {
        users = new HashSet<>();
        courses = new HashSet<>();
        complaints = new HashSet<>();
        scanner = new Scanner(System.in);
        initializeData();
    }

    private void initializeData() {
        
        users.add(new Student("Smit Deoghare", "s@gmail.com", "Password@123"));
        users.add(new Student("Rudray Dave", "r@gmail.com", "Password@123"));
        users.add(new Student("Atharva Katke", "a@gmail.com", "Password@123"));
        users.add(new Student("Ishat Shivre", "i@gmail.com", "Password@123"));
        
        users.add(new Professor("Prof1", "prof1@gmail.com", "Professor@1"));
        users.add(new Professor("Prof2", "prof2@gmail.com", "Professor@2"));
        
        users.add(new Administrator("Admin", "admin@gmail.com", "Admin123#"));
        
        courses.add(new Course("AI201", "Computer Organisation", 4));
        courses.add(new Course("AI203", "Database Management System", 3));
        courses.add(new Course("AI205", "Design and Analysis of Algorithm", 4));
        courses.add(new Course("AI207", "Discrete Mathematics", 4));
        courses.add(new Course("AI231", "Object Oriented Programming", 3));
    }

    public void start() {
        while (true) {
            try {
                System.out.println("\n*****************************************************\n"
                		+ "Welcome to the University Course Registration System");
                System.out.println("1. Log In");
                System.out.println("2. Sign Up");
                System.out.println("3. Exit the Application");
                int choice = getIntInput("Enter your choice: ");

                switch (choice) {
                    case 1:
                        login();
                        break;
                    case 2:
                        signUp();
                        break;
                    case 3:
                        System.out.println("\nThank you for using the Course Registration System.\nGoodbye!");
                        return;
                    default:
                        throw new IllegalArgumentException("\nInvalid choice. Please enter a number between 1 and 3.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
        }
    }

    private void signUp() {
        try {
            System.out.println("\nSign Up");
            System.out.println("1. Student");
            System.out.println("2. Professor");
            int role = getIntInput("Enter your role (1 or 2): ");

            if (role != 1 && role != 2) {
                throw new IllegalArgumentException("Invalid role.\nPlease enter 1 for Student or 2 for Professor.");
            }

            System.out.print("Enter name: ");
            String name = scanner.nextLine();
            if (name.trim().isEmpty()) {
                throw new IllegalArgumentException("Name cannot be empty.");
            }

            System.out.print("Enter email: ");
            String email = scanner.nextLine();
            if (!isValidEmail(email)) {
                throw new IllegalArgumentException("Invalid email format.");
            }

            String password = getValidPassword();

            User newUser;
            if (role == 1) {
                newUser = new Student(name, email, password);
            } else {
                newUser = new Professor(name, email, password);
            }

            if (users.add(newUser)) {
                System.out.println("Sign up successful. You can now log in.");
            } else {
                throw new IllegalStateException("User with this email already exists. Sign up failed.");
            }
        } catch (IllegalArgumentException | InvalidPasswordException | IllegalStateException e) {
            System.out.println("\nSign up failed: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred during sign up: " + e.getMessage());
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    private String getValidPassword() throws InvalidPasswordException {
        while (true) {
            String password = readPassword("Enter password (min 8 chars, must include uppercase, lowercase, number, and special char): ");
            try {
                validatePassword(password);
                return password;
            } catch (InvalidPasswordException e) {
                System.out.println("Invalid password: " + e.getMessage());
            }
        }
    }

    private String readPassword(String prompt) {
        Console console = System.console();
        if (console == null) {
            System.out.print(prompt);
            return scanner.nextLine();
        } else {
            return new String(console.readPassword(prompt));
        }
    }

    private void validatePassword(String password) throws InvalidPasswordException {
        if (password.length() < 8) {
            throw new InvalidPasswordException("Password must be at least 8 characters long");
        }
        if (!Pattern.compile("[A-Z]").matcher(password).find()) {
            throw new InvalidPasswordException("Password must contain at least one uppercase letter");
        }
        if (!Pattern.compile("[a-z]").matcher(password).find()) {
            throw new InvalidPasswordException("Password must contain at least one lowercase letter");
        }
        if (!Pattern.compile("\\d").matcher(password).find()) {
            throw new InvalidPasswordException("Password must contain at least one number");
        }
        if (!Pattern.compile("[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]").matcher(password).find()) {
            throw new InvalidPasswordException("Password must contain at least one special character");
        }
    }

    private void login() {
        try {
            System.out.println("\nLogin as:");
            System.out.println("1. Student");
            System.out.println("2. Professor");
            System.out.println("3. Administrator");
            int role = getIntInput("Enter your role: ");

            if (role < 1 || role > 3) {
                throw new IllegalArgumentException("Invalid role. Please enter a number between 1 and 3.");
            }

            System.out.print("\nEnter email: ");
            String email = scanner.nextLine();
            if (!isValidEmail(email)) {
                throw new IllegalArgumentException("Invalid email format.");
            }

            String password = readPassword("Enter password: ");

            User user = authenticateUser(email, password, role);
            if (user != null) {
                handleUserMenu(user);
            } else {
                throw new SecurityException("Invalid credentials or user not found.");
            }
        } catch (IllegalArgumentException | SecurityException e) {
            System.out.println("Login failed: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred during login: " + e.getMessage());
        }
    }

    private User authenticateUser(String email, String password, int role) {
        for (User user : users) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                if ((role == 1 && user instanceof Student) ||
                    (role == 2 && user instanceof Professor) ||
                    (role == 3 && user instanceof Administrator)) {
                    return user;
                }
            }
        }
        return null;
    }

    private void handleUserMenu(User user) {
        while (true) {
            try {
                user.displayMenu();
                int choice = getIntInput("Enter your choice: ");

                boolean shouldExit = false;
                if (user instanceof Student) {
                    shouldExit = handleStudentMenu((Student) user, choice);
                } else if (user instanceof Professor) {
                    shouldExit = handleProfessorMenu((Professor) user, choice);
                } else if (user instanceof Administrator) {
                    shouldExit = handleAdminMenu((Administrator) user, choice);
                }

                if (shouldExit) {
                    break;
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid choice: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }

    private boolean handleStudentMenu(Student student, int choice) throws Exception {
        switch (choice) {
            case 1:
                viewAvailableCourses();
                break;
            case 2:
                registerForCourses(student);
                break;
            case 3:
                viewSchedule(student);
                break;
            case 4:
                trackAcademicProgress(student);
                break;
            case 5:
                dropCourse(student);
                break;
            case 6:
                submitComplaint(student);
                break;
            case 7:
                System.out.println("Logging out...");
                return true;
            default:
                throw new IllegalArgumentException("Please enter a number between 1 and 7.");
        }
        return false;
    }

    private boolean handleProfessorMenu(Professor professor, int choice) throws Exception {
        switch (choice) {
            case 1:
                manageCourses(professor);
                break;
            case 2:
                viewEnrolledStudents(professor);
                break;
            case 3:
                System.out.println("Logging out...");
                return true;
            default:
                throw new IllegalArgumentException("Please enter a number between 1 and 3.");
        }
        return false;
    }

    private boolean handleAdminMenu(Administrator admin, int choice) throws Exception {
        switch (choice) {
            case 1:
                manageCourses(admin);
                break;
            case 2:
                manageStudentRecords();
                break;
            case 3:
                assignProfessorsToCourses();
                break;
            case 4:
                handleComplaints();
                break;
            case 5:
                System.out.println("Logging out...");
                return true;
            default:
                throw new IllegalArgumentException("Please enter a number between 1 and 5.");
        }
        return false;
    }
    
    private void submitComplaint(Student student) {
        try {
            System.out.println("Submit a Complaint");
            System.out.print("Please describe your complaint: ");
            String complaintText = scanner.nextLine();
            
            if (complaintText.trim().isEmpty()) {
                throw new IllegalArgumentException("Complaint text cannot be empty.");
            }
            
            Complaint newComplaint = new Complaint(student, complaintText);
            complaints.add(newComplaint);
            
            System.out.println("Your complaint has been submitted successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Failed to submit complaint: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred while submitting the complaint: " + e.getMessage());
        }
    }
    
    private void handleComplaints() {
        try {
            if (complaints.isEmpty()) {
                System.out.println("There are no complaints to handle at this time.");
                return;
            }

            System.out.println("Handling Complaints");
            Iterator<Complaint> iterator = complaints.iterator();
            while (iterator.hasNext()) {
                Complaint complaint = iterator.next();
                System.out.println("\nComplaint from " + complaint.getStudent().getName() + ":");
                System.out.println(complaint.getText());
                
                String response = getValidInput("Mark this complaint as resolved? (y/n): ", "^[ynYN]$");
                
                if (response.equalsIgnoreCase("y")) {
                    iterator.remove();
                    System.out.println("Complaint marked as resolved and removed from the list.");
                } else {
                    System.out.println("Complaint left unresolved.");
                }
            }
            
            System.out.println("\nAll complaints have been reviewed.");
        } catch (Exception e) {
            System.out.println("An error occurred while handling complaints: " + e.getMessage());
        }
    }
    
    private String getValidInput(String prompt, String regex) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (input.matches(regex)) {
                return input;
            }
            System.out.println("Invalid input. Please try again.");
        }
    }

    private int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private void viewAvailableCourses() {
        try {
            System.out.println("Available Courses:");
            for (Course course : courses) {
                System.out.println(course.getCode() + " - " + course.getName() + " (" + course.getCredits() + " credits)");
            }
        } catch (Exception e) {
            System.out.println("An error occurred while viewing available courses: " + e.getMessage());
        }
    }

    private void registerForCourses(Student student) {
        try {
            viewAvailableCourses();
            System.out.print("Enter the course code you want to register for: ");
            String courseCode = scanner.nextLine();
            if (courseCode.trim().isEmpty()) {
                throw new IllegalArgumentException("Course code cannot be empty.");
            }
            Course selectedCourse = findCourseByCode(courseCode);
            if (selectedCourse != null) {
                // Get enrollment date
                LocalDate enrollmentDate = getEnrollmentDate();
                student.registerForCourse(selectedCourse, enrollmentDate);
                System.out.println("Successfully registered for " + selectedCourse.getName());
                // Calculate and show the earliest possible drop date
                LocalDate dropEligibleDate = enrollmentDate.plusMonths(2);
                System.out.println("Note: You will be eligible to drop this course after: " 
                    + dropEligibleDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            } else {
                throw new IllegalArgumentException("Course not found.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Registration failed: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred during course registration: " + e.getMessage());
        }
    }

    private LocalDate getEnrollmentDate() {
        LocalDate enrollmentDate = LocalDate.now(); // Default to current date
        try {
            System.out.println("Enter enrollment date (dd-MM-yyyy) or press Enter for today's date: ");
            String dateStr = scanner.nextLine().trim();
            
            if (!dateStr.isEmpty()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                enrollmentDate = LocalDate.parse(dateStr, formatter);
                
                // Validate that the date is not in the future
                if (enrollmentDate.isAfter(LocalDate.now())) {
                    throw new IllegalArgumentException("Enrollment date cannot be in the future");
                }
            }
        } catch (Exception e) {
            System.out.println("Invalid date format. Using current date.");
        }
        return enrollmentDate;
    }

    private void viewSchedule(Student student) {
        try {
            Set<Course> enrolledCourses = student.getEnrolledCourses();
            if (enrolledCourses.isEmpty()) {
                System.out.println("You are not enrolled in any courses.");
            } else {
                System.out.println("Your Schedule:");
                for (Course course : enrolledCourses) {
                    System.out.println(course.getCode() + " - " + course.getName());
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred while viewing your schedule: " + e.getMessage());
        }
    }

    private void trackAcademicProgress(Student student) {
        try {
            System.out.println("Academic Progress:");
            System.out.println("Current Semester: " + student.getCurrentSemester());
            System.out.println("CGPA: " + student.getCgpa());
            System.out.println("Completed Courses:");
            for (Course course : student.getCompletedCourses()) {
                System.out.println(course.getCode() + " - " + course.getName());
            }
        } catch (Exception e) {
            System.out.println("An error occurred while tracking academic progress: " + e.getMessage());
        }
    }

    private void dropCourse(Student student) {
        try {
            viewSchedule(student);
            System.out.print("Enter the course code you want to drop: ");
            String courseCode = scanner.nextLine();
            if (courseCode.trim().isEmpty()) {
                throw new IllegalArgumentException("Course code cannot be empty.");
            }
            Course selectedCourse = findCourseByCode(courseCode);
            
            if (selectedCourse != null && student.getEnrolledCourses().contains(selectedCourse)) {
                // Get enrollment date for this course
                LocalDate enrollmentDate = student.getEnrollmentDate(selectedCourse);
                if (enrollmentDate == null) {
                    throw new IllegalStateException("Enrollment date not found for this course.");
                }
                
                // Calculate time difference
                LocalDate currentDate = LocalDate.now();
                LocalDate dropEligibleDate = enrollmentDate.plusMonths(2);
                
                if (currentDate.isBefore(dropEligibleDate)) {
                    // Calculate remaining days
                    long daysRemaining = ChronoUnit.DAYS.between(currentDate, dropEligibleDate);
                    throw new EarlyDropException(
                        "ERROR: Course drop not allowed before completing 2 months of enrollment.",
                        dropEligibleDate,
                        daysRemaining
                    );
                }
                
                student.dropCourse(selectedCourse);
                System.out.println("Successfully dropped " + selectedCourse.getName());
            } else {
                throw new IllegalArgumentException("Course not found or not enrolled.");
            }
        } catch (EarlyDropException e) {
            System.out.println(e.getMessage());
            System.out.println("You will be eligible to drop this course after: " 
                + e.getEligibleDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            System.out.println("Days remaining until eligible for drop: " + e.getDaysRemaining());
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Failed to drop course: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred while dropping the course: " + e.getMessage());
        }
    }
    
    private void viewTaughtCourses(Professor professor) {
        try {
            Set<Course> taughtCourses = professor.getTaughtCourses();
            if (taughtCourses.isEmpty()) {
                System.out.println("You are not teaching any courses.");
            } else {
                System.out.println("Courses you are teaching:");
                for (Course course : taughtCourses) {
                    System.out.println(course.getCode() + " - " + course.getName());
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred while viewing taught courses: " + e.getMessage());
        }
    }

    private void addNewCourse() {
        try {
            System.out.print("Enter course code: ");
            String code = scanner.nextLine();
            if (code.trim().isEmpty()) {
                throw new IllegalArgumentException("Course code cannot be empty.");
            }
            System.out.print("Enter course name: ");
            String name = scanner.nextLine();
            if (name.trim().isEmpty()) {
                throw new IllegalArgumentException("Course name cannot be empty.");
            }
            int credits = getIntInput("Enter course credits: ");
            if (credits <= 0) {
                throw new IllegalArgumentException("Credits must be a positive number.");
            }

            Course newCourse = new Course(code, name, credits);
            courses.add(newCourse);
            System.out.println("Course added successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Failed to add course: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred while adding the course: " + e.getMessage());
        }
    }

    private void removeCourse() {
        try {
            viewAllCourses();
            System.out.print("Enter the code of the course to remove: ");
            String code = scanner.nextLine();
            if (code.trim().isEmpty()) {
                throw new IllegalArgumentException("Course code cannot be empty.");
            }
            Course courseToRemove = findCourseByCode(code);
            if (courseToRemove != null) {
                courses.remove(courseToRemove);
                System.out.println("Course removed successfully.");
            } else {
                throw new IllegalArgumentException("Course not found.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Failed to remove course: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred while removing the course: " + e.getMessage());
        }
    }

    private void manageCourses(Professor professor) {
        try {
            System.out.println("1. View taught courses");
            System.out.println("2. Add a new course");
            System.out.println("3. Remove a course");
            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    viewTaughtCourses(professor);
                    break;
                case 2:
                    addNewCourse();
                    break;
                case 3:
                    removeCourse();
                    break;
                default:
                    throw new IllegalArgumentException("Invalid choice. Please enter a number between 1 and 3.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    private void viewEnrolledStudents(Professor professor) {
        try {
            Set<Course> taughtCourses = professor.getTaughtCourses();
            if (taughtCourses.isEmpty()) {
                System.out.println("You are not teaching any courses.");
            } else {
                for (Course course : taughtCourses) {
                    System.out.println("Enrolled students for " + course.getName() + ":");
                    for (Student student : course.getEnrolledStudents()) {
                        System.out.println("- " + student.getName());
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred while viewing enrolled students: " + e.getMessage());
        }
    }

    private void manageCourses(Administrator admin) {
        try {
            System.out.println("1. Add a new course");
            System.out.println("2. Remove a course");
            System.out.println("3. View all courses");
            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    addNewCourse();
                    break;
                case 2:
                    removeCourse();
                    break;
                case 3:
                    viewAllCourses();
                    break;
                default:
                    throw new IllegalArgumentException("Invalid choice. Please enter a number between 1 and 3.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    private void manageStudentRecords() {
        try {
            System.out.println("1. View all students");
            System.out.println("2. Update student information");
            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    viewAllStudents();
                    break;
                case 2:
                    updateStudentInformation();
                    break;
                
                default:
                    throw new IllegalArgumentException("Invalid choice. Please enter a number between 1 and 2.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    private void assignProfessorsToCourses() {
        try {
            viewAllCourses();
            System.out.print("Enter the course code to assign a professor: ");
            String courseCode = scanner.nextLine();
            if (courseCode.trim().isEmpty()) {
                throw new IllegalArgumentException("Course code cannot be empty.");
            }
            Course selectedCourse = findCourseByCode(courseCode);
            if (selectedCourse == null) {
                throw new IllegalArgumentException("Course not found.");
            }
            
            viewAllProfessors();
            System.out.print("Enter the email of the professor to assign: ");
            String profEmail = scanner.nextLine();
            if (profEmail.trim().isEmpty()) {
                throw new IllegalArgumentException("Professor email cannot be empty.");
            }
            Professor selectedProf = findProfessorByEmail(profEmail);
            if (selectedProf == null) {
                throw new IllegalArgumentException("Professor not found.");
            }
            
            selectedCourse.assignProfessor(selectedProf);
            selectedProf.addCourse(selectedCourse);
            System.out.println("Professor successfully assigned to the course.");
        } catch (IllegalArgumentException e) {
            System.out.println("Failed to assign professor: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred while assigning the professor: " + e.getMessage());
        }
    }

    // Helper methods
    private Course findCourseByCode(String code) {
        for (Course course : courses) {
            if (course.getCode().equalsIgnoreCase(code)) {
                return course;
            }
        }
        return null;
    }

    private Professor findProfessorByEmail(String email) {
        for (User user : users) {
            if (user instanceof Professor && user.getEmail().equalsIgnoreCase(email)) {
                return (Professor) user;
            }
        }
        return null;
    }

    private void viewAllCourses() {
        try {
            System.out.println("All Courses:");
            for (Course course : courses) {
                System.out.println(course.getCode() + " - " + course.getName());
            }
        } catch (Exception e) {
            System.out.println("An error occurred while viewing all courses: " + e.getMessage());
        }
    }

    private void viewAllStudents() {
        try {
            System.out.println("All Students:");
            for (User user : users) {
                if (user instanceof Student) {
                    Student student = (Student) user;
                    System.out.println(student.getName() + " - " + student.getEmail());
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred while viewing all students: " + e.getMessage());
        }
    }

    private void viewAllProfessors() {
        try {
            System.out.println("All Professors:");
            for (User user : users) {
                if (user instanceof Professor) {
                    Professor professor = (Professor) user;
                    System.out.println(professor.getName() + " - " + professor.getEmail());
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred while viewing all professors: " + e.getMessage());
        }
    }
    
    private Student findStudentByEmail(String email) {
        for (User user : users) {
            if (user instanceof Student && user.getEmail().equalsIgnoreCase(email)) {
                return (Student) user;
            }
        }
        return null;
    }

    private void updateStudentInformation() {
        try {
            viewAllStudents();
            System.out.print("Enter the email of the student to update: ");
            String email = scanner.nextLine();
            if (email.trim().isEmpty()) {
                throw new IllegalArgumentException("Student email cannot be empty.");
            }
            Student student = findStudentByEmail(email);
            if (student == null) {
                throw new IllegalArgumentException("Student not found.");
            }
            
            System.out.print("Enter new name (or press enter to skip): ");
            String newName = scanner.nextLine();
            if (!newName.isEmpty()) {
                student.setName(newName);
            }
            System.out.print("Enter new email (or press enter to skip): ");
            String newEmail = scanner.nextLine();
            if (!newEmail.isEmpty()) {
                if (!isValidEmail(newEmail)) {
                    throw new IllegalArgumentException("Invalid email format.");
                }
                student.setEmail(newEmail);
            }
            System.out.println("Student information updated successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Failed to update student information: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred while updating student information: " + e.getMessage());
        }
    }
    
class InvalidPasswordException extends Exception {
    private static final long serialVersionUID = 1L;

    public InvalidPasswordException(String message) {
        super(message);
        }
    }

public static void main(String[] args) {
    CourseRegistrationSystem system = new CourseRegistrationSystem();
    system.start();
    }
}	
