package app.service;

import app.bean.Country;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author vasil
 */
@Slf4j
public class CountryServiceTest {

    @Autowired
    CountryService instance = new CountryService();

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getCountryFromUrl method, of class CountryService.
     */
    @Test
    public void testGetCountryFromUrl() {
        System.out.println("getCountryFromUrl");
        String url = "http://country.io/names.json";
        int expResult = 0;
        List<Country> result = instance.getCountryFromUrl(url);
        log.info("size = {}", result.size());
        assertNotEquals(expResult, result.size());

    }

}
