package com.example.todo.datasource;

import com.example.todo.model.ToDo;
import com.example.todo.repository.ToDoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * In-memory implementation of ToDoRepository using ConcurrentHashMap.
 * This is a demonstration datasource layer. In production, replace with:
 * - JpaRepository (Spring Data JPA with database)
 * - MongoRepository (MongoDB)
 * - Custom REST client for external APIs
 */
@Repository
public class ToDoStore implements ToDoRepository {
    
    private final ConcurrentHashMap<Long, ToDo> store = new ConcurrentHashMap<>();
    private long idSequence = 0;
    
    /**
     * Save a ToDo. If id is null, generates a new one.
     */
    @Override
    public ToDo save(ToDo todo) {
        if (todo.getId() == null) {
            todo.setId(++idSequence);
        }
        store.put(todo.getId(), todo);
        return todo;
    }
    
    /**
     * Find a ToDo by id.
     */
    @Override
    public Optional<ToDo> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }
    
    /**
     * Get all ToDos.
     */
    @Override
    public List<ToDo> findAll() {
        return store.values().stream().collect(Collectors.toList());
    }
    
    /**
     * Delete a ToDo by id.
     */
    @Override
    public void deleteById(Long id) {
        store.remove(id);
    }
    
    /**
     * Check if a ToDo exists.
     */
    @Override
    public boolean existsById(Long id) {
        return store.containsKey(id);
    }
}