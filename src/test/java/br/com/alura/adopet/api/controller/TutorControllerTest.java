package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.CadastroTutorDto;
import br.com.alura.adopet.api.service.TutorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class TutorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<CadastroTutorDto> jacksonTester;

    @MockBean
    private TutorService tutorService;

    @Test
    void deveriaDevolverCodigo200AoCadastrarTutor() throws Exception {
        CadastroTutorDto dto = new CadastroTutorDto("Novo tutor", "11999999999", "email@email.com");

        var response = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/tutores")
                        .content(jacksonTester.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void deveriaDevolverCodigo400AoCadastrarTutorComDadosIncorretos() throws Exception {
        var response = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/tutores")
                        .content("")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(400, response.getStatus());
    }
}