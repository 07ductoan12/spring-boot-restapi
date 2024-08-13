package com.toan.student_management_api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/** StudentApiController */
@RestController
@RequestMapping("/api/students")
public class StudentApiController {
    private static List<Student> listStudent = new ArrayList<>();
    private static Integer studentID = 0;

    static {
        listStudent.add(new Student(++studentID, "Nguyen Duc Toan"));
        listStudent.add(new Student(++studentID, "Nguyen Duc Toan 2"));
    }

    @GetMapping
    public ResponseEntity<?> list() {
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
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        Student student = new Student(id);
        if (listStudent.contains(student)) {
            listStudent.remove(student);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
