package com.example.todo.service;

import com.example.todo.model.ToDo;
import com.example.todo.repository.ToDoRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * Service layer for ToDo business logic.
 * 
 * Best practices:
 * 1. Controllers call Services, NOT repositories directly.
 * 2. Services handle validation, transformation, and business rules.
 * 3. Services use repositories for data access (dependency injection).
 * 4. Keeps concerns separated and makes testing easier (mock the repository).
 */
@Service
public class ToDoService {
    
    private final ToDoRepository toDoRepository;
    
    /**
     * Constructor injection of the repository.
     * Spring will automatically inject the ToDoStore bean (implements ToDoRepository).
     */
    public ToDoService(ToDoRepository toDoRepository) {
        this.toDoRepository = toDoRepository;
    }
    
    /**
     * Create a new ToDo.
     * Validates input before saving.
     */
    public ToDo createToDo(ToDo todo) {
        // Validate input
        if (todo.getTitle() == null || todo.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("ToDo title cannot be empty");
        }
        
        // Delegate to repository (datasource layer)
        return toDoRepository.save(todo);
    }
    
    /**
     * Get a ToDo by id.
     * Throws exception if not found (or return Optional for caller to handle).
     */
    public ToDo getToDo(Long id) {
        return toDoRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("ToDo not found with id: " + id));
    }
    
    /**
     * Get all ToDos.
     */
    public List<ToDo> getAllToDos() {
        return toDoRepository.findAll();
    }
    
    /**
     * Update a ToDo.
     */
    public ToDo updateToDo(Long id, ToDo updatedToDo) {
        ToDo existing = getToDo(id); // Validates existence
        
        if (updatedToDo.getTitle() != null) {
            existing.setTitle(updatedToDo.getTitle());
        }
        if (updatedToDo.getDescription() != null) {
            existing.setDescription(updatedToDo.getDescription());
        }
        
        return toDoRepository.save(existing);
    }
    
    /**
     * Delete a ToDo.
     */
    public void deleteToDo(Long id) {
        if (!toDoRepository.existsById(id)) {
            throw new IllegalArgumentException("ToDo not found with id: " + id);
        }
        toDoRepository.deleteById(id);
    }
    
    /**
     * Mark a ToDo as complete.
     */
    public ToDo markAsComplete(Long id) {
        ToDo todo = getToDo(id);
        todo.setCompleted(true);
        return toDoRepository.save(todo);
    }
}
