package app.service;

import app.bean.CountryPhoneCode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author vasil
 */
@Service
@Slf4j
public class CountryPhoneCodeService {

    public List<CountryPhoneCode> getCountryPhoneCodeFromUrl(String url) {
        List<CountryPhoneCode> result = new ArrayList<>();
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> restRes = template.getForEntity(url, String.class);
        JSONParser parser = new JSONParser();
        JSONObject json;
        try {
            json = (JSONObject) parser.parse(restRes.getBody());
            result = (List<CountryPhoneCode>) json.entrySet()
                    .stream()
                    .map(t -> new CountryPhoneCode(((HashMap.Entry<String, String>) t).getKey(), ((HashMap.Entry<String, String>) t).getValue()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Ошибка : " + e.getMessage());
        }
        return result;
    }

}
