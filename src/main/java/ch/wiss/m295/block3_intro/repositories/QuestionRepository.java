package ch.wiss.m295.block3_intro.repositories;

import org.springframework.data.repository.CrudRepository;
import java.util.List;
import ch.wiss.m295.block3_intro.model.Category;
import ch.wiss.m295.block3_intro.model.Question;

public interface QuestionRepository extends CrudRepository<Question, Long> {
    List<Question> findByCategory(Category cat);
}
