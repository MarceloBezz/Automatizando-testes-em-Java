package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
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

import br.com.alura.adopet.api.service.AdocaoService;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class AdocaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdocaoService adocaoService;

    @Autowired
    private JacksonTester<SolicitacaoAdocaoDto> jacksonTester;

    @Test
    void deveriaDevolverCodigo400ParaSolicitacaoDeAdocaoComErros() throws Exception{
        //ARRANGE
        String json = "{}";

        //ACT
        var response = mockMvc.perform(
                MockMvcRequestBuilders
                .post("/adocoes")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    void deveriaDevolverCodigo200ParaSolicitacaoDeAdocaoSemErros() throws Exception{
        //ARRANGE
        SolicitacaoAdocaoDto dto = new SolicitacaoAdocaoDto(1l, 1l, "Qualquer");

        //ACT
        var response = mockMvc.perform(
                MockMvcRequestBuilders
                .post("/adocoes")
                .content(jacksonTester.write(dto).getJson())
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //ASSERT
        Assertions.assertEquals(200, response.getStatus());
    }
}
