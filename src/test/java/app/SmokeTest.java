package app;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SmokeTest {

    @Test
    public void AppStart() {
        App.main(new String[]{});
        assertThat(true).isTrue();
    }
}
