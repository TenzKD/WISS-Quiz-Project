package ch.wiss.m295.block3_intro.repositories;

import org.springframework.data.repository.CrudRepository;

import ch.wiss.m295.block3_intro.model.Answer;

public interface AnswerRepository extends CrudRepository<Answer, Long> {

}
