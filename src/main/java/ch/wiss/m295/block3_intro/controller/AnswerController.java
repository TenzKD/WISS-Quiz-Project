package ch.wiss.m295.block3_intro.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.wiss.m295.block3_intro.model.Answer;
import ch.wiss.m295.block3_intro.model.Category;
import ch.wiss.m295.block3_intro.model.Question;
import ch.wiss.m295.block3_intro.repositories.AnswerRepository;
import ch.wiss.m295.block3_intro.repositories.CategoryRepository;
import ch.wiss.m295.block3_intro.repositories.QuestionRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/answer")
public class AnswerController {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    // sendet ein HTTP POST-Request an URL "/"
    @PostMapping("/")
    public ResponseEntity<Question> createQuestionWithAnswers(@Valid @RequestBody Question questionBody) {
        System.out.println("Creating question" + questionBody.getQuestion());
        Optional<Category> category = categoryRepository.findById(questionBody.getCategory().getId());

        if (category.isPresent()) {
            Question question = new Question();
            question.setQuestion(questionBody.getQuestion());
            question.setCategory(category.get());
            question = questionRepository.save(question);
            for (Answer answer : questionBody.getAnswers()) {
                answer.setQuestion(question);
                answer = answerRepository.save(answer);
                question.getAnswers().add(answer);
            }
            return ResponseEntity.ok().body(questionRepository.save(question));
        }
        return ResponseEntity.notFound().build();
    }
}