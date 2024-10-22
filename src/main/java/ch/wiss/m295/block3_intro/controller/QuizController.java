package ch.wiss.m295.block3_intro.controller;

import java.util.Optional;
import java.util.List;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ch.wiss.m295.block3_intro.model.Question;

import ch.wiss.m295.block3_intro.model.Category;
import ch.wiss.m295.block3_intro.repositories.CategoryRepository;
import ch.wiss.m295.block3_intro.repositories.QuestionRepository;
import ch.wiss.wiss_quiz.exception.AnswerCouldNotBeSavedException;
import ch.wiss.wiss_quiz.exception.CategoryNotFoundException;
import ch.wiss.wiss_quiz.exception.QuestionCouldNotBeSavedException;
import ch.wiss.wiss_quiz.exception.QuestionLoadException;

@RestController
@RequestMapping("/quiz")
public class QuizController {
    private static final int MAX_QUESTIONS = 2;
    @Autowired
    CategoryRepository catRepo;

    @Autowired
    QuestionRepository questionRepository;

    @GetMapping(path = "/{category_id}")
    public List<Question> getQuizQuestions(@PathVariable Long category_id) {
        Optional<Category> cat = catRepo.findById(category_id);

        // Falls es die Kategorie nicht findet
        if (cat.isEmpty()) {
            throw new CategoryNotFoundException("Category with id " + category_id + " not found");
        }

        List<Question> questions = questionRepository.findByCategory(cat.get());

        // Falls keine Fragen geladen werden
        if (questions.isEmpty()) {
            throw new QuestionLoadException("No questions found for category " + category_id);
        }

        if (questions.size() > MAX_QUESTIONS) {
            Collections.shuffle(questions);
            return questions.subList(0, MAX_QUESTIONS);
        }
        return questions;
    }

    @PostMapping("/save-question")
    public Question saveQuestion(@RequestBody Question question) {
        try {
            return questionRepository.save(question);
        } catch (Exception e) {
            // Falls ein Fehler beim Speichern der Frage auftritt
            throw new QuestionCouldNotBeSavedException("Question could not be saved: " + e.getMessage());
        }
    }

    @PostMapping("/save-answer")
    public void saveAnswer(@RequestBody Question question) {
        try {
            questionRepository.save(question);
        } catch (Exception e) {
            // Falls ein Fehler beim Speichern der Antwort auftritt
            throw new AnswerCouldNotBeSavedException("Answer could not be saved: " + e.getMessage());
        }
    }
}
