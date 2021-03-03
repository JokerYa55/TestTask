package app.controler;

import app.exception.DataNotFound;
import app.model.PhoneCode;
import app.service.PhoneCodeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author vasil
 */
@org.springframework.web.bind.annotation.RestController
public class RestController {

    @Autowired
    PhoneCodeService phoneCodeService;

    @GetMapping(path = "/code/{country}")
    public List<PhoneCode> getByCountry(@PathVariable(name = "country") String countryName) {
        List<PhoneCode> result = phoneCodeService.getPhoneCodeByName(countryName);
        if (result.isEmpty()) {
            throw new DataNotFound("Данные не найдены");
        }
        return phoneCodeService.getPhoneCodeByName(countryName);
    }
}
