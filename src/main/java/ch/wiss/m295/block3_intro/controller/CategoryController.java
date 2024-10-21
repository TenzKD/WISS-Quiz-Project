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
        return ResponseEntity.ok().body(categoryRepository.findAll());
    }

    /**
     * this adds a new category
     */
    @PostMapping("/") // Map ONLY POST Requests
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        category = categoryRepository.save(category);
        return ResponseEntity.ok().body(category);
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
