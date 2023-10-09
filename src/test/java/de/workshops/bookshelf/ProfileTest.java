package de.workshops.bookshelf;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.EnabledIf;

@SpringBootTest
public class ProfileTest {


//    @Value("${server.port}")
    private int port;

    @Test
    @EnabledIf(expression = "#{environment['spring.profiles.active'] == 'p'}", loadContext = true)
    void test() throws Exception{

    }
}
