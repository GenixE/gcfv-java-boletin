# Boletín App

Boletín App is a Java application designed to manage student grades for a specific subject. The application allows users to import student data from a file, display the list of students, open individual student records, and save the grades to a file.

## Features

- Import student data from a text file.
- Display a list of students.
- Open and edit individual student records.
- Save student grades to a text file.

## Requirements

- Java Development Kit (JDK) 8 or higher.
- IntelliJ IDEA 2024.3 or any other Java IDE.

## Installation

1. Clone the repository:
    ```sh
    git clone https://github.com/GenixE/boletin-app.git
    ```
2. Open the project in IntelliJ IDEA or your preferred Java IDE.

## Usage

1. Run the `Main` class to start the application.
2. Use the "Importar alumnos" button to import student data from a file.
3. Double-click on a student in the list to open their record and edit their grade.
4. Use the "Guardar" button to save the grades to a file.

## File Format

### Import File (`ficheros/alumnos.txt`)

The import file should have the following format:
```
Course Name
<empty line>
Student Name 1
Student Name 2
...
```

### Save File (`ficheros/boletin.txt`)

The save file will have the following format:
```
asignatura=<subject>
fecha=<date>

nombre=<student name>
nota=<grade>
...
```

## License

This project is licensed under the MIT License. See the `LICENSE` file for more details.