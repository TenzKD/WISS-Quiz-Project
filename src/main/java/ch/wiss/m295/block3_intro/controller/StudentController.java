package ch.wiss.m295.block3_intro.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.wiss.m295.block3_intro.model.Student;

@RestController
@RequestMapping("/student")
public class StudentController {

  List<Student> students = new ArrayList<>();

  @GetMapping("/{id}")
  public ResponseEntity<Student> getStudent(@PathVariable int id) {
    return ResponseEntity.ok().body(students.get(id));
  }

  @GetMapping("/")
  public ResponseEntity<Iterable<Student>> getStudents() {
    return ResponseEntity.ok().body(students);
  }

  @PostMapping("/")
  public ResponseEntity<Student> createStudent(@RequestBody Student student) {
    System.out.println("Creating student: " + student);
    if (students.size()==0){
      student.setId(1);
    } else { /* funny functional hack to get unique ids */
        students.stream().mapToInt(s -> (int)s.getId()).max()
          .ifPresentOrElse(id -> student.setId(id+1), () -> student.setId(1));
    }
    students.add(student);

    return ResponseEntity.ok().body(student);
  }

}
