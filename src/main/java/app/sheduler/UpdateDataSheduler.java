package app.sheduler;

import app.bean.Country;
import app.bean.CountryPhoneCode;
import app.model.PhoneCode;
import app.repository.PhoneCodeRepository;
import app.service.CountryPhoneCodeService;
import app.service.CountryService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 *
 * @author vasil
 */
@Slf4j
@Service
public class UpdateDataSheduler {

    @Value("${app.country.url}")
    String countryUrl;

    @Value("${app.telephone.url}")
    String countryPhoneCodeUrl;

    @Autowired
    CountryService countryService;

    @Autowired
    CountryPhoneCodeService countryPhoneCodeService;

    @Autowired
    PhoneCodeRepository repository;

    @Scheduled(cron = "0 0 2 * * ?", zone = "Europe/Moscow")
    public void updateData() {
        log.info("Обновление данных");
        List<Country> countryList = countryService.getCountryFromUrl(countryUrl);
        List<CountryPhoneCode> countryPhoneCodeList = countryPhoneCodeService.getCountryPhoneCodeFromUrl(countryPhoneCodeUrl);
        if (!countryList.isEmpty() && !countryPhoneCodeList.isEmpty()) {
            Map<String, List<CountryPhoneCode>> countryPhoneMap = countryPhoneCodeList.stream().collect(Collectors.groupingBy(CountryPhoneCode::getCountryCode));
            List<PhoneCode> phoneCodeList = countryList.stream()
                    .map(t -> new PhoneCode(t.getCode(), t.getName(), countryPhoneMap.get(t.getCode()).get(0).getPhoneCode()))
                    .collect(Collectors.toList());
            if (!phoneCodeList.isEmpty()) {
                repository.saveAll(phoneCodeList);
            }
        }
    }

    @PostConstruct
    public void postConstruct() {
        try {
            updateData();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
