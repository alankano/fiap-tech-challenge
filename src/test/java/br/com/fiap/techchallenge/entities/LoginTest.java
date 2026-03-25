package br.com.fiap.techchallenge.entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginTest {

    @Test
    void gettersAndSetters() {
        Login login = new Login();
        login.setLogin("alan132");
        login.setSenha("123");

        assertEquals("alan132", login.getLogin());
        assertEquals("123", login.getSenha());

    }

}
