package ru.spellsofenglish.gameservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.spellsofenglish.gameservice.models.Task;

@Repository
public interface TaskRepository extends MongoRepository<Task,String> {
    Task findByIndex(int index);
}
