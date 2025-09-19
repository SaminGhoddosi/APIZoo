package com.example.Zoo.Service;

import com.example.Zoo.DTO.AnimalDTO;
import com.example.Zoo.Models.Animal;
import com.example.Zoo.Models.Cuidador;
import com.example.Zoo.Repositories.AnimalRepository;
import com.example.Zoo.Repositories.CuidadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnimalService {

    private final AnimalRepository animalRepository;

    @Autowired
    private CuidadorRepository cuidadorRepository;

    @Autowired
    private EmailService emailService;

    // Use constructor injection for the main repository
    public AnimalService(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    public Animal create(Animal animal) {
        // Salva o animal diretamente no banco de dados
        Animal savedAnimal = animalRepository.save(animal);

        // Encontra o cuidador responsável
        Cuidador cuidador = cuidadorRepository.findById(savedAnimal.getCuidador().getId())
                .orElseThrow(() -> new RuntimeException("Cuidador não encontrado"));

        // Envia a notificação por e-mail
        String to = cuidador.getEmail();
        String subject = "Novo animal sob sua responsabilidade!";
        String body = String.format("Olá, %s! Um novo animal (%s) foi adicionado sob sua responsabilidade.",
                cuidador.getNome(), savedAnimal.getNome());

        emailService.sendNotificationEmail(to, subject, body);

        // Retorna a entidade Animal que foi salva
        return savedAnimal;
    }

    public List<Animal> getAll(){
        return animalRepository.findAll();
    }

    public Animal getById(Long id){
        return animalRepository.findById(id).orElseThrow(() -> new RuntimeException("Animal não encontrado"));
    }

    public List<Animal> getByAgeRange(Integer idadeMin, Integer idadeMax){
        if (idadeMin != null && idadeMax != null) {
            return animalRepository.findByIdadeBetween(idadeMin, idadeMax);
        } else if (idadeMin != null) {
            return animalRepository.findByIdadeGreaterThanEqual(idadeMin);
        } else if (idadeMax != null) {
            return animalRepository.findByIdadeLessThanEqual(idadeMax);
        } else {
            return animalRepository.findAll();
        }
    }

    public List<Animal> getBySpecie(String especie){
        return animalRepository.findByEspecie(especie);
    }

    public void delete(Long id){
        Animal animal = getById(id);
        animalRepository.delete(animal);
    }

    public Animal update(Long id, AnimalDTO animalDTO){
        Animal animal = getById(id);
        animal.setNome(animalDTO.nome());
        animal.setCuidador(animalDTO.cuidador());
        animal.setEspecie(animalDTO.especie());
        animal.setHabitat(animalDTO.habitat());
        animal.setIdade(animalDTO.idade());
        return animalRepository.save(animal);
    }

    // Helper methods for DTO to Entity conversion and vice versa
    private Animal toEntity(AnimalDTO dto) {
        Animal animal = new Animal();
        animal.setNome(dto.nome());
        animal.setCuidador(dto.cuidador());
        animal.setEspecie(dto.especie());
        animal.setHabitat(dto.habitat());
        animal.setIdade(dto.idade());
        return animal;
    }


}