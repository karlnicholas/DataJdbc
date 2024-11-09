package org.example.datajdbc.service;

import lombok.RequiredArgsConstructor;
import org.example.datajdbc.domain.Country;
import org.example.datajdbc.repository.CountryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryService {

    private final CountryRepository countryRepository;

    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    public Country getCountryById(Long id) {
        return countryRepository.findById(id).orElse(null);
    }

    public Country saveCountry(Country country) {
        return countryRepository.save(country);
    }

    public void deleteCountryById(Long id) {
        countryRepository.deleteById(id);
    }
}
