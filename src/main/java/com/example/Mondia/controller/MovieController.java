package com.example.Mondia.controller;

import com.example.Mondia.exception.ResourceNotFoundException;
import com.example.Mondia.model.Movie;
import com.example.Mondia.repository.MovieRepository;
import com.example.Mondia.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.lang.Long;

@CrossOrigin("http://localhost:4200")
@RestController
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/categories/{categoryId}/movies")
    public List<Movie> getMoviesByCategoryId(@PathVariable Long categoryId) {
        return movieRepository.findByCategoryId(categoryId);
    }

//   @GetMapping("/categories/{categoryId}/movies/{movieId}")
//     public Optional<Movie> getMoviesById(@PathVariable Long categoryId,
//                                @PathVariable Long movieId
//                               ) {
//         if(!categoryRepository.existsByyId(categoryId)) {
//             throw new ResourceNotFoundException("Category not found with id " + categoryId);
//         }

//         return movieRepository.findById(movieId);
//  }
                

  @GetMapping("/movies/{movieId}")
    public Optional<Movie> getMoviesById(@PathVariable Long movieId) {
        return movieRepository.findById(movieId);
    } 

    @PostMapping("/categories/{categoryId}/movies")
    public Movie addMovie(@PathVariable Long categoryId,
                            @Valid @RequestBody Movie movie) {
        return categoryRepository.findById(categoryId)
                .map(category -> {
                    movie.setCategory(category);
                    return movieRepository.save(movie);
                }).orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + categoryId));
    }

    @PutMapping("/categories/{categoryId}/movies/{movieId}")
    public Movie updateMovie(@PathVariable Long categoryId,
                               @PathVariable Long movieId,
                               @Valid @RequestBody Movie movieRequest) {
        if(!categoryRepository.existsById(categoryId)) {
            throw new ResourceNotFoundException("Category not found with id " + categoryId);
        }

        return movieRepository.findById(movieId)
                .map(movie -> {
                    movie.setTitle(movieRequest.getTitle());
                    return movieRepository.save(movie);
                }).orElseThrow(() -> new ResourceNotFoundException("Movie not found with id " + movieId));
    }

    @DeleteMapping("/categories/{categoryId}/movies/{movieId}")
    public ResponseEntity<?> deleteMovie(@PathVariable Long categoryId,
                                          @PathVariable Long movieId) {
        if(!categoryRepository.existsById(categoryId)) {
            throw new ResourceNotFoundException("Category not found with id " + categoryId);
        }

        return movieRepository.findById(movieId)
                .map(movie -> {
                    movieRepository.delete(movie);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Movie not found with id " + movieId));

    }
}
