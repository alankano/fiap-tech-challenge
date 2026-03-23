package br.com.fiap.techchallenge.dto;

import br.com.fiap.techchallenge.entities.Restaurante;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UpdateRestauranteRecordTest {

    @Test
    void constructFromEntityAndJson() throws Exception {
        Restaurante r = new Restaurante();
        r.setId(1L);
        r.setNome("R");
        r.setEndereco("E");
        r.setTipoCozinha("T");
        r.setDiasFuncionamento(List.of("Seg"));
        r.setHorarioAbertura("08:00");
        r.setHorarioFechamento("22:00");
        r.setIdUsuario("1");

        UpdateRestauranteRecord rec = new UpdateRestauranteRecord(r);
        assertEquals("R", rec.getNome());

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(rec);
        UpdateRestauranteRecord read = mapper.readValue(json, UpdateRestauranteRecord.class);
        assertEquals(rec.getEndereco(), read.getEndereco());
    }
}

