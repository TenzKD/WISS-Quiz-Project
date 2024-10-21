package ch.wiss.m295.block3_intro.repositories;

import org.springframework.data.repository.CrudRepository;

import ch.wiss.m295.block3_intro.model.Question;

public interface QuestionRepository extends CrudRepository<Question, Long> {

}
