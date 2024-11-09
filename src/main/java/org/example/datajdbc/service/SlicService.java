package org.example.datajdbc.service;

import lombok.RequiredArgsConstructor;
import org.example.datajdbc.domain.Slic;
import org.example.datajdbc.repository.SlicRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SlicService {

    private final SlicRepository slicRepository;

    // Retrieve all Slic entities
    public List<Slic> getAllSlics() {
        return slicRepository.findAll();
    }

    // Retrieve a Slic by its code
    public Slic getSlicByCode(Long id) {
        return slicRepository.findById(id).orElse(null);
    }

    // Save a new Slic or update an existing one
    public Slic saveSlic(Slic slic) {
        return slicRepository.save(slic);
    }

    // Delete a Slic by its code
    public void deleteSlicByCode(Long id) {
        slicRepository.deleteById(id);
    }
}
