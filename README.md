# Movie Recommender System - Testing Project

A comprehensive testing project demonstrating various software testing methodologies on a Java-based movie recommendation system. This project showcases Black Box, White Box, and Integration testing techniques using JUnit 5 and Mockito.

## 📋 Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Project Structure](#project-structure)
- [Testing Methodologies](#testing-methodologies)
- [Getting Started](#getting-started)
- [Running Tests](#running-tests)
- [Test Coverage](#test-coverage)
- [Contributing](#contributing)

## 🎯 Overview

This project implements a movie recommendation system that:
- Parses movie and user data from text files
- Generates personalized movie recommendations based on user preferences and movie genres
- Writes recommendations to output files
- Demonstrates comprehensive testing strategies for real-world applications

## ✨ Features

### Core Functionality
- **File Parsing**: Parse movies and users from structured text files
- **Recommendation Engine**: Generate recommendations based on genre matching
- **File Output**: Write recommendations to formatted output files
- **Input Validation**: Comprehensive validation for movie and user data

### Testing Features
- **Decision Table Testing**: Complete scenario coverage for file parsing
- **Statement/Branch/Condition Coverage**: Thorough White Box testing
- **Mock Objects**: Custom mocks for isolated testing
- **Integration Testing**: End-to-end workflow validation
- **Data Flow Testing**: Path-based testing scenarios

## 🛠 Technologies Used

- **Java 17**: Core programming language
- **JUnit 5**: Testing framework
- **Mockito**: Mocking framework for unit tests
- **Maven**: Build and dependency management
- **File I/O**: Text file processing for data input/output

## 📁 Project Structure

```
movierecommender/
├── src/
│   ├── main/java/movierecommender/
│   │   ├── Controllers/          # Application controllers
│   │   ├── Services/             # Business logic services
│   │   ├── Entities/             # Domain models (Movie, User, Recommendation)
│   │   ├── Contracts/            # Interface definitions
│   │   ├── Mock/                 # Custom mock implementations
│   │   ├── Helpers/              # Validation utilities
│   │   └── Errors/               # Custom exception classes
│   └── test/java/
│       ├── BlackBox/             # Black Box testing (Decision Tables, Equivalence Classes)
│       ├── Services/             # White Box testing (Statement, Branch, Condition Coverage)
│       ├── Controllers/          # Integration testing
│       └── Helpers/              # Utility testing
├── target/
│   └── surefire-reports/        # Test execution reports
└── pom.xml                       # Maven configuration
```

## 🧪 Testing Methodologies

### Black Box Testing
- **Equivalence Class Partitioning**: Testing valid/invalid input groups
- **State Transition Testing**: User workflow validation
- **Decision Table Testing**: 5 comprehensive test scenarios for [`MovieFileParserService`](movierecommender/src/main/java/movierecommender/Services/MovieFileParserService.java)

### White Box Testing
- **Statement Coverage**: Every line of code executed
- **Branch Coverage**: All decision paths tested
- **Condition Coverage**: Boolean expressions thoroughly tested
- **Path Coverage**: Complete execution paths validated
- **Data Flow Testing**: Variable lifecycle testing

### Integration Testing
- **End-to-End Testing**: Complete workflow from file input to recommendation output
- **Service Integration**: Testing service layer interactions
- **Mock Integration**: Testing with dependency injection

## 🚀 Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- IDE (VS Code, IntelliJ IDEA, Eclipse)

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/Movie-recommender-Testing-Project.git
   cd Movie-recommender-Testing-Project/movierecommender
   ```

2. **Install dependencies**
   ```bash
   mvn clean install
   ```

3. **Compile the project**
   ```bash
   mvn compile
   ```

### Input File Format

**Movies File (`movies.txt`)**:
```
The Shawshank Redemption, TSR001
Drama
The Godfather, TG002
Crime, Drama
```

**Users File (`users.txt`)**:
```
John Doe, 123456789
TSR001, TG002
Jane Smith, 987654321
TSR001
```

## 🧪 Running Tests

### Run All Tests
```bash
mvn test
```

### Run Specific Test Classes
```bash
# Black Box Tests
mvn test -Dtest=BlackBox.MovieFileParsereDecisionTableTesting

# White Box Tests
mvn test -Dtest=Services.MovieFileParserServiceTest

# Integration Tests
mvn test -Dtest=Controllers.RecommenderControllerIntegrationTest
```

### Generate Test Reports
```bash
mvn surefire-report:report
```

Test reports will be generated in `target/surefire-reports/`

## 📊 Test Coverage

### Current Test Metrics
- **Total Test Classes**: 8+
- **Total Test Methods**: 50+
- **Black Box Tests**: Decision table testing with 5 scenarios
- **White Box Tests**: Statement, Branch, Condition, Path, and Data Flow coverage
- **Integration Tests**: End-to-end workflow validation
- **Mock Tests**: Custom mock objects for isolated testing

### Key Test Classes
- [`MovieFileParserServiceTest`](movierecommender/src/test/java/Services/MovieFileParserServiceTest.java): Comprehensive White Box testing
- [`MovieFileParsereDecisionTableTesting`](movierecommender/src/test/java/BlackBox/MovieFileParsereDecisionTableTesting.java): Black Box decision table testing
- [`RecommenderControllerIntegrationTest`](movierecommender/src/test/java/Controllers/RecommenderControllerIntegrationTest.java): Integration testing
- [`UserFileParserServiceTest`](movierecommender/src/test/java/Services/UserFileParserServiceTest.java): Service layer testing

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/new-test-scenario`)
3. Commit your changes (`git commit -am 'Add new test scenario'`)
4. Push to the branch (`git push origin feature/new-test-scenario`)
5. Create a Pull Request

## 🎓 Educational Purpose

This project serves as a comprehensive example of:
- Software Testing Methodologies
- Test-Driven Development (TDD)
- Mock Object Design Patterns
- Java Testing Best Practices
- Maven Project Structure
- CI/CD Testing Integration

---

**Note**: This is an educational project focused on demonstrating testing methodologies rather than production movie recommendation algorithms.
