package br.com.primary.main.controller;

import br.com.primary.main.model.User;
import br.com.primary.main.model.UserRecord;
import br.com.primary.main.service.IUserService;
import br.com.primary.main.service.exceptions.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public record UserController(IUserService service) {
    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{email}")
    public ResponseEntity<User> findById(@PathVariable String email) {
        try {
            return ResponseEntity.ok(service.findById(email));
        } catch (BusinessException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping
    public ResponseEntity<User> save(@RequestBody User user) {
        try {
            User savedUser = service.create(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
        } catch (BusinessException e) {
            return ResponseEntity.unprocessableEntity().build();
        }
    }
    @DeleteMapping("/{email}")
    public ResponseEntity<Void> delete(@PathVariable String email) {
        try {
            service.delete(email);
            return ResponseEntity.noContent().build();
        } catch (BusinessException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
