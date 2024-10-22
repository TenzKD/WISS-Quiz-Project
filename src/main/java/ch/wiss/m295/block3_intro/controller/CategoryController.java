package ch.wiss.m295.block3_intro.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.wiss.m295.block3_intro.model.Category;
import ch.wiss.m295.block3_intro.repositories.CategoryRepository;
import ch.wiss.wiss_quiz.exception.CategoryCouldNotBeSavedException;
import ch.wiss.wiss_quiz.exception.CategoryLoadException;

@RestController // This means that this class is a Controller
@RequestMapping("/category") // This means URL's start with /category (after Application path)
public class CategoryController {
    @Autowired
    private CategoryRepository categoryRepository;

    /*
     * This get the specific data from the id
     */
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable long id) {
        Optional<Category> result = categoryRepository.findById(id);
        if (result.isPresent()) {
            return ResponseEntity.ok().body(result.get());
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * this reads all category items
     */
    @GetMapping("/")
    public ResponseEntity<Iterable<Category>> getCategories() {
        try {
            return ResponseEntity.ok().body(categoryRepository.findAll());
        } catch (Exception e) {
            throw new CategoryLoadException("Could not load categories: " + e.getMessage());
        }
    }

    /**
     * this adds a new category
     */
    @PostMapping("/") // Map ONLY POST Requests
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        try {
            category = categoryRepository.save(category);
            return ResponseEntity.ok().body(category);
        } catch (Exception e) {
            throw new CategoryCouldNotBeSavedException("Category could not be saved");
        }
    }

    /*
     * this deletes a specific category with the same id
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable long id) {
        categoryRepository.deleteById(id);
        return ResponseEntity.ok().body("Category " + id + " deleted.");
    }
}
