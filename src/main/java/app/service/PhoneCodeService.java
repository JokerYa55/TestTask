package app.service;

import app.model.PhoneCode;
import app.repository.PhoneCodeRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 *
 * @author vasil
 */
@Service
public class PhoneCodeService {

    @Autowired
    PhoneCodeRepository repository;

    @Cacheable(value = "PhoneCode", key = "#name")
    public List<PhoneCode> getPhoneCodeByName(String name) {
        return repository.findByCountryLowerStartingWith(name);
    }
}
