package com.example.todo.repository;

import com.example.todo.model.ToDo;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for ToDo persistence operations.
 * Defines the contract for data access—implementation agnostic.
 * Allows easy switching between in-memory, JPA, REST, or other backends.
 */
public interface ToDoRepository {
    
    /**
     * Save a new ToDo or update an existing one.
     */
    ToDo save(ToDo todo);
    
    /**
     * Find a ToDo by id.
     */
    Optional<ToDo> findById(Long id);
    
    /**
     * Get all ToDos.
     */
    List<ToDo> findAll();
    
    /**
     * Delete a ToDo by id.
     */
    void deleteById(Long id);
    
    /**
     * Check if a ToDo exists by id.
     */
    boolean existsById(Long id);
}
