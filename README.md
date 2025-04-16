# Library Management System

A comprehensive library management system built using Java, implementing various design patterns and data structures.

## Features

- Book management (add, search, view)
- Reader management (register, view)
- Borrowing system (borrow, return, reserve)
- Real-time notifications using Java Sockets
- REST API using Spark Java

## Design Patterns Implemented

- Singleton Pattern: Implemented in `Librarian` class
- Factory Pattern: Used in `BookFactory` and `ReaderFactory`
- Observer Pattern: Implemented with `BookAvailabilitySubject`, `BookAvailabilityObserver`, and `NotificationService`

## Data Structures Used

- HashMap: Used in repositories for storing books and readers
- Queue: Used in `BorrowRepository` for managing waiting lists
- ArrayList: Used in the `Reader` class for tracking borrowed books

## Project Structure

```
src/
├── main/
│   └── java/
│       ├── com/
│           └── library/
│               ├── model/
│               │   ├── Book.java
│               │   ├── Reader.java
│               │   └── Librarian.java
│               ├── factory/
│               │   ├── BookFactory.java
│               │   └── ReaderFactory.java
│               ├── repository/
│               │   ├── BookRepository.java
│               │   ├── ReaderRepository.java
│               │   └── BorrowRepository.java
│               ├── service/
│               │   ├── BookService.java
│               │   ├── ReaderService.java 
│               │   └── BorrowService.java
│               ├── observer/
│               │   ├── BookAvailabilitySubject.java
│               │   ├── BookAvailabilityObserver.java
│               │   └── NotificationService.java
│               ├── controller/
│               │   ├── BookController.java
│               │   ├── ReaderController.java
│               │   └── BorrowController.java
│               ├── socket/
│               │   ├── NotificationServer.java
│               │   └── NotificationClient.java
│               └── LibraryApplication.java
└── test/
    └── java/
        └── com/
            └── library/
                ├── service/
                    └── BookServiceTest.java
```

## Prerequisites

- Java 11 or higher
- Maven 3.6 or higher

## Building the Project

```bash
mvn clean package
```

This will create a JAR file with all dependencies included in the `target` directory.

## Running the Application

```bash
java -jar target/library-management-system-1.0-SNAPSHOT-jar-with-dependencies.jar
```

The application will start a web server on port 8080 and a notification server on port 9090.

## API Endpoints

### Book Management
- GET `/api/books` - Get all books
- GET `/api/books/:isbn` - Get book by ISBN
- GET `/api/books/available` - Get available books
- GET `/api/books/search/title/:title` - Search books by title
- GET `/api/books/search/author/:author` - Search books by author
- POST `/api/books` - Add new book

### Reader Management
- GET `/api/readers` - Get all readers
- GET `/api/readers/:registrationNumber` - Get reader by registration number
- POST `/api/readers` - Register new reader

### Borrowing System
- POST `/api/borrow` - Borrow a book
- POST `/api/return` - Return a book
- POST `/api/reserve` - Reserve a book

## Testing with Postman

Example request body for adding a book:
```json
{
    "isbn": "9780451524935",
    "title": "1984",
    "author": "George Orwell"
}
```

Example request body for registering a reader:
```json
{
    "name": "John Doe",
    "registrationNumber": "A12345"
}
```

Example request body for borrowing a book:
```json
{
    "readerRegistrationNumber": "A12345",
    "isbn": "9780451524935"
}
``` 