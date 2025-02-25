package br.com.primary.main.controller;

import br.com.primary.main.model.entity.Product;
import br.com.primary.main.service.ProductService;
import java.util.List;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {
	private final ProductService service;
	
	public ProductController(ProductService service) {
		this.service = service;
	}
	@GetMapping
	public ResponseEntity<List<Product>> findAllProducts() {
		return ResponseEntity.ok(service.findAll());
	}
	@GetMapping("/{id}")
	public ResponseEntity<Product> findById(@PathVariable Long id) {
		return ResponseEntity.ok(service.findById(id));
	}
	@PostMapping
	public ResponseEntity<Product> save(@Valid @RequestBody Product p) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(p));
	}
	@PutMapping("/{id}")
	public ResponseEntity<Product> update(@PathVariable Long id, @Valid @RequestBody Product p){
		return ResponseEntity.ok(service.update(id, p));
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<Product> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
