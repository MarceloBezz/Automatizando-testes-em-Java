package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.CadastroAbrigoDto;
import br.com.alura.adopet.api.service.AbrigoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class AbrigoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<CadastroAbrigoDto> jacksonTester;

    @Autowired
    private AbrigoService abrigoService;

    @Test
    void deveriaRetornarCodigo200AoCadastrarAbrigo() throws Exception{
        CadastroAbrigoDto dto = new CadastroAbrigoDto("Abrigo legal", "11999999999", "email@email.com");

        var response = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/abrigos")
                        .content(jacksonTester.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void deveriaRetornarCodigo400AoCadastrarAbrigoComDadosInvalidos() throws Exception{

        var response = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/abrigos")
                        .content("")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(400, response.getStatus());
    }
}