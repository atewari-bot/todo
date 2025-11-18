package com.example.todo.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.todo.model.ToDo;
import com.example.todo.service.ToDoService;
import java.util.List;

/**
 * REST Controller for ToDo endpoints.
 * 
 * Best practices:
 * 1. Controller injects Service via constructor (dependency injection).
 * 2. Controller handles HTTP concerns only (routing, status codes, headers).
 * 3. ALL business logic is delegated to the service layer.
 * 4. Service layer handles database calls via repository.
 */
@RestController
@RequestMapping("/v1")
public class ToDoController{
    
    private final ToDoService toDoService;
    
    /**
     * Constructor injection of ToDoService.
     * Spring automatically injects the service bean.
     */
    public ToDoController(ToDoService toDoService) {
        this.toDoService = toDoService;
    }
    
    /**
     * Create a new ToDo.
     * POST /v1/todos
     * Request body: {"title": "...", "description": "..."}
     */
    @PostMapping("/todos")
    public ResponseEntity<ToDo> createToDo(@RequestBody ToDo todo){
        ToDo created = toDoService.createToDo(todo);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }  

    /**
     * Get a specific ToDo by id.
     * GET /v1/todos/{id}
     */
    @GetMapping("/todos/{id}")
    public ResponseEntity<ToDo> getToDo(@PathVariable Long id){
        ToDo todo = toDoService.getToDo(id);
        return ResponseEntity.ok(todo);
    }

    /**
     * Get all ToDos.
     * GET /v1/todos
     */
    @GetMapping("/todos")
    public ResponseEntity<List<ToDo>> getAllToDos(){
        List<ToDo> todos = toDoService.getAllToDos();
        return ResponseEntity.ok(todos);
    }

    /**
     * Update an existing ToDo.
     * PUT /v1/todos/{id}
     * Request body: {"title": "...", "description": "..."}
     */
    @PutMapping("/todos/{id}")
    public ResponseEntity<ToDo> updateToDo(@PathVariable Long id, @RequestBody ToDo updatedToDo){
        ToDo todo = toDoService.updateToDo(id, updatedToDo);
        return ResponseEntity.ok(todo);
    }

    /**
     * Delete a ToDo.
     * DELETE /v1/todos/{id}
     */
    @DeleteMapping("/todos/{id}")
    public ResponseEntity<Void> deleteToDo(@PathVariable Long id){
        toDoService.deleteToDo(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Mark a ToDo as complete.
     * PATCH /v1/todos/{id}/complete
     */
    @PatchMapping("/todos/{id}/complete")
    public ResponseEntity<ToDo> markToDoAsComplete(@PathVariable Long id){
        ToDo todo = toDoService.markAsComplete(id);
        return ResponseEntity.ok(todo);
    }
}