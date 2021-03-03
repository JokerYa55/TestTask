package app.repository;

import app.model.PhoneCode;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author vasil
 */
public interface PhoneCodeRepository extends CrudRepository<PhoneCode, String> {
    public List<PhoneCode> findByCountryLowerStartingWith(String country);
}
