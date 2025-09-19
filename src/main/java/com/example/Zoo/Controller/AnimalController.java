package com.example.Zoo.Controller;

import com.example.Zoo.DTO.AnimalDTO;
import com.example.Zoo.Models.Animal;
import com.example.Zoo.Service.AnimalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/animais")
public class AnimalController {
    private AnimalService animalService;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @GetMapping
    public ResponseEntity<List<Animal>> getAll() {
        return ResponseEntity.ok(animalService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Animal> getById(@PathVariable Long id) {
        return ResponseEntity.ok(animalService.getById(id));
    }
    @GetMapping("/especie/{especie}")
    public ResponseEntity<List<Animal>> getBySpecie(@PathVariable String especie){
        return ResponseEntity.ok(animalService.getBySpecie(especie));
    }
    @GetMapping("/idade")
    public ResponseEntity<List<Animal>> getByAgeRange(
            @RequestParam(required = false) Integer idadeMin,
            @RequestParam(required = false) Integer idadeMax) {
        return ResponseEntity.ok(animalService.getByAgeRange(idadeMin, idadeMax));
    }
    @PostMapping
    public ResponseEntity<Animal> create(@RequestBody Animal animalDTO){
        return ResponseEntity.ok(animalService.create(animalDTO));
    }
    @PutMapping
    public ResponseEntity<Animal> put(@PathVariable Long id, @RequestBody AnimalDTO animalDto){
        return ResponseEntity.ok(animalService.update(id, animalDto));
    }
    @DeleteMapping
    public ResponseEntity<Void> delete(@PathVariable Long id){
        animalService.delete(id);
        return ResponseEntity.ok().build();
    }

}
