package com.toan.api.student;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;

import java.util.ArrayList;
import java.util.List;

/** StudentApiController */
@RestController
@RequestMapping("/api/students")
@Validated
public class StudentApiController {
    private static List<Student> listStudent = new ArrayList<>();
    private static Integer studentID = 0;

    static {
        listStudent.add(new Student(++studentID, "Nguyen Duc Toan 1"));
        listStudent.add(new Student(++studentID, "Nguyen Duc Toan 2"));
        listStudent.add(new Student(++studentID, "Nguyen Duc Toan 3"));
        listStudent.add(new Student(++studentID, "Nguyen Duc Toan 4"));
        listStudent.add(new Student(++studentID, "Nguyen Duc Toan 5"));
        listStudent.add(new Student(++studentID, "Nguyen Duc Toan 6"));
    }

    @GetMapping
    public ResponseEntity<?> list(
            @RequestParam("pageSize") @Min(value = 10,
                    message = "Page size minimum is 10") Integer pageSize,
            @Max(value = 50, message = "Page size maximum is 50") Integer page) {
        System.out.println("Page Size = " + pageSize);
        System.out.println("Page Num = " + page);
        if (listStudent.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return new ResponseEntity<>(listStudent, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Student> add(@RequestBody Student student) {
        student.setId(++studentID);
        listStudent.add(student);
        return new ResponseEntity<>(student, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Student student) {
        if (listStudent.contains(student)) {
            int index = listStudent.indexOf(student);
            listStudent.set(index, student);

            return new ResponseEntity<>(listStudent, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") @Positive(
            message = "Student ID must be greater than zero") Integer id) {
        Student student = new Student(id);

        if (listStudent.contains(student)) {
            listStudent.remove(student);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
