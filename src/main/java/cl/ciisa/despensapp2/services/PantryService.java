package cl.ciisa.despensapp2.services;

import cl.ciisa.despensapp2.model.Pantry;
import cl.ciisa.despensapp2.repository.PantryRepository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PantryService {

    private final PantryRepository pantryRepository;

    public List<Pantry> findAll() {
        return pantryRepository.findAll();
    }

    public Optional<Pantry> findById(Long id) {
        return pantryRepository.findById(id);
    }

    public Pantry save(Pantry pantry) {
        return pantryRepository.save(pantry);
    }

    public void deleteById(Long id) {
        pantryRepository.deleteById(id);
    }

}