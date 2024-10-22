# 🎓 University Course Registration System

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![OOP](https://img.shields.io/badge/OOP-Principles-blue?style=for-the-badge)
![Console](https://img.shields.io/badge/Console-Application-green?style=for-the-badge)

A robust console-based University Course Registration System implemented in Java, demonstrating core Object-Oriented Programming principles and best practices.

## 📚 Table of Contents
- [Features](#-features)
- [OOP Concepts Implementation](#-oop-concepts-implementation)
- [System Architecture](#-system-architecture)
- [Getting Started](#-getting-started)
- [Class Structure](#-class-structure)
- [Exception Handling](#-exception-handling)

## 🚀 Features

### Student Functions
- Course registration and withdrawal
- View available courses
- Track academic progress
- View personal schedule
- Submit complaints
- Drop courses (with time constraints)

### Professor Functions
- Manage taught courses
- View enrolled students
- Course management

### Administrator Functions
- Manage course catalog
- Handle student records
- Assign professors to courses
- Process complaints
- Update student information

## 💡 OOP Concepts Implementation

### 1. Encapsulation
- Private data members with public getter/setter methods
- Information hiding within classes
- Example:
java
private Set<User> users;
private Set<Course> courses;
private Set<Complaint> complaints;


### 2. Inheritance
- Class hierarchy implementing the following structure:

User (Base Class)
├── Student
├── Professor
└── Administrator

- Common attributes and methods inherited from the User class

### 3. Polymorphism
- Method overriding for different user types
- Runtime polymorphism through user authentication
- Example:
java
private void handleUserMenu(User user) {
    if (user instanceof Student) {
        handleStudentMenu((Student) user, choice);
    } else if (user instanceof Professor) {
        handleProfessorMenu((Professor) user, choice);
    }
}


### 4. Abstraction
- Clear separation of concerns
- Well-defined interfaces for different user roles
- Complex operations hidden behind simple method calls

### 5. Collection Framework Usage
- HashSet for storing users, courses, and complaints
- Iterator pattern for collection traversal
- Example:
java
private Set<Course> enrolledCourses;
private Set<Course> completedCourses;


## 🏗 System Architecture

### Core Components
1. **User Management**
   - User authentication
   - Role-based access control
   - Password validation

2. **Course Management**
   - Course registration
   - Course withdrawal
   - Professor assignment

3. **Academic Progress Tracking**
   - Grade management
   - CGPA calculation
   - Course completion tracking

4. **Complaint System**
   - Complaint submission
   - Complaint resolution
   - Administrative review

## 🚦 Getting Started

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- Any Java IDE (Eclipse, IntelliJ IDEA, etc.)

### Installation
1. Clone the repository
bash
git clone https://github.com/yourusername/course-registration-system.git


2. Navigate to the project directory
bash
cd course-registration-system


3. Compile the project
bash
javac CourseRegistrationSystem.java


4. Run the application
bash
java CourseRegistrationSystem


## 📊 Class Structure

### Main Classes
- `CourseRegistrationSystem`: Core system controller
- `User`: Base class for all users
- `Student`: Handles student-specific operations
- `Professor`: Manages professor functionalities
- `Administrator`: Controls administrative tasks
- `Course`: Represents course information
- `Complaint`: Manages student complaints

### Custom Exceptions
- `EarlyDropException`: Handles course drop timing constraints
- `InvalidPasswordException`: Manages password validation

## ⚠️ Exception Handling

The system implements robust exception handling using:
- Custom exception classes
- Try-catch blocks
- Input validation
- Error messages
- Recovery mechanisms

Example:
java
try {
    validatePassword(password);
} catch (InvalidPasswordException e) {
    System.out.println("Invalid password: " + e.getMessage());
}


## 🔐 Security Features

1. **Password Protection**
   - Minimum length requirement
   - Special character requirement
   - Case sensitivity
   - Number requirement

2. **Input Validation**
   - Email format verification
   - Empty input checking
   - Numeric input validation

## 📝 Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## ⭐ Best Practices Implemented

- Clear code organization
- Comprehensive error handling
- Input validation
- Modular design
- Clean code principles
- Documentation
- Consistent naming conventions
