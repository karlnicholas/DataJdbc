package org.example.datajdbc.service;

import lombok.RequiredArgsConstructor;
import org.example.datajdbc.domain.Sort;
import org.example.datajdbc.repository.SortRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SortService {

    private final SortRepository sortRepository;

    // Retrieve all Sort entities
    public List<Sort> getAllSorts() {
        return sortRepository.findAll();
    }

    // Retrieve a Sort by its code
    public Optional<Sort> getSortByCode(Long id) {
        return sortRepository.findById(id);
    }

    // Save a new Sort or update an existing one
    public Sort saveSort(Sort sort) {
        return sortRepository.save(sort);
    }

    // Delete a Sort by its code
    public void deleteSortByCode(Long id) {
        sortRepository.deleteById(id);
    }
}
