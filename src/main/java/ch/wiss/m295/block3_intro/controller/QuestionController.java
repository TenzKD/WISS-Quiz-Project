package ch.wiss.m295.block3_intro.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.wiss.m295.block3_intro.model.Question;
import ch.wiss.m295.block3_intro.repositories.QuestionRepository;
import ch.wiss.wiss_quiz.exception.QuestionCouldNotBeSavedException;
import ch.wiss.wiss_quiz.exception.QuestionLoadException;

@RestController
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    QuestionRepository questionRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Question> getQuestion(@PathVariable long id) {
        Optional<Question> result = questionRepository.findById(id);
        if (result.isPresent()) {
            return ResponseEntity.ok().body(result.get());
        }
        throw new QuestionLoadException("Question with id " + id + " not found");
    }

    @GetMapping("/")
    public ResponseEntity<Iterable<Question>> getQuestions() {
        return ResponseEntity.ok().body(questionRepository.findAll());
    }

    @PostMapping("/")
    public ResponseEntity<Question> createQuestion(@RequestBody Question question) {
        try {
            question = questionRepository.save(question);
            return ResponseEntity.ok().body(question);
        } catch (Exception e) {
            throw new QuestionCouldNotBeSavedException("Question could not be saved: " + e.getMessage());
        }
    }

    @DeleteMapping("/")
    public ResponseEntity<String> deleteCategory(@PathVariable long id) {
        questionRepository.deleteById(id);
        return ResponseEntity.ok().body("Question " + id + " deleted");
    }
}
