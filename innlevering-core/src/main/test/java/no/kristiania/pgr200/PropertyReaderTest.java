package no.kristiania.pgr200;

import no.kristiania.pgr200.core.PropertyReader;
import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class PropertyReaderTest {

    @Test
    public void shouldReturnUrl() throws IOException {
        PropertyReader reader = new PropertyReader();

        assertThat(reader.getUrl()).isEqualTo("jdbc:postgresql://localhost/conferencedb");
    }

}
