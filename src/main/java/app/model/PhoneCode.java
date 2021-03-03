package app.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author vasil
 */
@Entity
@Data
@NoArgsConstructor
public class PhoneCode {

    @Id
    String name;
    String country;
    String code;
    String countryLower;

    public PhoneCode(String name, String country, String code) {
        this.name = name;
        this.country = country;
        this.code = code;
        this.countryLower = country.toLowerCase();
    }

}
