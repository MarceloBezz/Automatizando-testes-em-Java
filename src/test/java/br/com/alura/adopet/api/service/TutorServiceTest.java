package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.AtualizacaoTutorDto;
import br.com.alura.adopet.api.dto.CadastroTutorDto;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TutorServiceTest {

    @InjectMocks
    private TutorService service;

    @Mock
    private TutorRepository tutorRepository;

    @Mock
    private CadastroTutorDto dto;

    private Tutor tutor;

    @Mock
    private AtualizacaoTutorDto dtoAtualizacao;

    @Captor
    private ArgumentCaptor<Tutor> captor;

    @Test
    void deveriaCadastrarTutorNoBanco() {
        dto = new CadastroTutorDto("Novo tutor", "11999999999", "email@email.com");
        BDDMockito.given(tutorRepository.existsByTelefoneOrEmail(dto.telefone(), dto.email())).willReturn(false);

        service.cadastrar(dto);

        BDDMockito.then(tutorRepository).should().save(captor.capture());
        Tutor tutorSalvo = captor.getValue();

        Assertions.assertEquals(tutorSalvo.getNome(), dto.nome());
        Assertions.assertEquals(tutorSalvo.getTelefone(), dto.telefone());
        Assertions.assertEquals(tutorSalvo.getEmail(), dto.email());
    }

    @Test
    void deveriaAtualizarDadosDoTutor() {
        dtoAtualizacao = new AtualizacaoTutorDto(1L, "Novo nome", "11999999987", "emailnovo@email.com");
        dto = new CadastroTutorDto("Tutor", "11999999999", "email@email.com");
        tutor = new Tutor(dto);
        BDDMockito.given(tutorRepository.getReferenceById(dtoAtualizacao.id())).willReturn(tutor);

        service.atualizar(dtoAtualizacao);

        Assertions.assertEquals(tutor.getNome(), dtoAtualizacao.nome());
        Assertions.assertEquals(tutor.getTelefone(), dtoAtualizacao.telefone());
        Assertions.assertEquals(tutor.getEmail(), dtoAtualizacao.email());
    }
}