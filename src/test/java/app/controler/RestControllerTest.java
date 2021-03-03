package app.controler;

import app.model.PhoneCode;
import app.repository.PhoneCodeRepository;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

/**
 *
 * @author vasil
 */
@SpringBootTest
@AutoConfigureMockMvc
@Profile("test")
@Slf4j
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@NoArgsConstructor
public class RestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PhoneCodeRepository phoneCodeRepository;

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        // Формируем тестовые данные для загрузки в БД
        List<PhoneCode> dataList = Stream.of(new PhoneCode("BE", "Belgium", "32"),
                new PhoneCode("BM", "Bermuda", "+1-441"),
                new PhoneCode("JE", "Jersey", "+44-1534"),
                new PhoneCode("JM", "Jamaica", "+1-876"),
                new PhoneCode("AD", "Andorra", "376"),
                new PhoneCode("AF", "Afghanistan", "93")
        )
                .collect(Collectors.toList());
        phoneCodeRepository.saveAll(dataList);
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Тест успеного выполнения запроса httpStatus = 200
     */
    @Test
    public void testGetByCountryOk() throws Exception {
        System.out.println("getByCountry");
        String result = mockMvc.perform(
                get("http://localhost:8080/code/be")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        
        log.info("result = {}", result);
    }

    /**
     * Тест не успеного выполнения запроса httpStatus = 404
     */
    @Test
    public void testGetByCountryNotFound() throws Exception {
        System.out.println("getByCountry");
        String result = mockMvc.perform(
                get("http://localhost:8080/code/tttt")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse()
                .getContentAsString();
        
        log.info("result = {}", result);
    }
    
}
