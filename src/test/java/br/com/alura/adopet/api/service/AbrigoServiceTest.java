package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.AbrigoDto;
import br.com.alura.adopet.api.dto.CadastroAbrigoDto;
import br.com.alura.adopet.api.dto.PetDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class AbrigoServiceTest {

    @InjectMocks
    AbrigoService service;

    private  CadastroAbrigoDto cadastroAbrigoDto;

    @Mock
    private AbrigoRepository abrigoRepository;

    @Mock
    private Abrigo abrigo;

    @Captor
    private ArgumentCaptor<Abrigo> abrigoCaptor;

    @Spy
    private List<Pet> petsDoAbrigo;

    @Test
    void deveriaSalvarAbrigoNoBanco() {
        cadastroAbrigoDto = new CadastroAbrigoDto("Abrigo legal", "11999999999", "email@email.com");
        BDDMockito.given(abrigoRepository.existsByNomeOrTelefoneOrEmail(cadastroAbrigoDto.nome(), cadastroAbrigoDto.telefone(), cadastroAbrigoDto.email()))
                .willReturn(false);

        service.cadatrar(cadastroAbrigoDto);

        BDDMockito.then(abrigoRepository).should().save(abrigoCaptor.capture());
        Abrigo abrigoSalvo = abrigoCaptor.getValue();

        Assertions.assertEquals(abrigoSalvo.getNome(), cadastroAbrigoDto.nome());
        Assertions.assertEquals(abrigoSalvo.getTelefone(), cadastroAbrigoDto.telefone());
        Assertions.assertEquals(abrigoSalvo.getEmail(), cadastroAbrigoDto.email());
    }

    @Test
    void naoDeveriaCadastrarAbrigoJaCadastrado() {
        cadastroAbrigoDto = new CadastroAbrigoDto("Abrigo legal", "11999999999", "email@email.com");
        BDDMockito.given(abrigoRepository.existsByNomeOrTelefoneOrEmail(cadastroAbrigoDto.nome(), cadastroAbrigoDto.telefone(), cadastroAbrigoDto.email()))
                .willReturn(true);

        Assertions.assertThrows(ValidacaoException.class, () -> service.cadatrar(cadastroAbrigoDto));
    }

    @Test
    void deveriaCarregarUmAbrigoPeloId() {
        cadastroAbrigoDto = new CadastroAbrigoDto("Abrigo legal", "11999999999", "email@email.com");
        abrigo = new Abrigo(cadastroAbrigoDto);
        BDDMockito.given(abrigoRepository.findById(1L)).willReturn(Optional.of(abrigo));

        var abrigoEncontrado = service.carregarAbrigo("1");

        Assertions.assertEquals(abrigoEncontrado, abrigo);
        Assertions.assertEquals(abrigoEncontrado.getNome(), abrigo.getNome());
        Assertions.assertEquals(abrigoEncontrado.getEmail(), abrigo.getEmail());
        Assertions.assertEquals(abrigoEncontrado.getTelefone(), abrigo.getTelefone());
    }

    @Test
    void deveriaCarregarUmAbrigoPeloNome () {
        cadastroAbrigoDto = new CadastroAbrigoDto("Abrigo legal", "11999999999", "email@email.com");
        abrigo = new Abrigo(cadastroAbrigoDto);
        BDDMockito.given(abrigoRepository.findByNome("Abrigo legal")).willReturn(Optional.of(abrigo));

        var abrigoEncontrado = service.carregarAbrigo("Abrigo legal");

        Assertions.assertEquals(abrigoEncontrado, abrigo);
        Assertions.assertEquals(abrigoEncontrado.getNome(), abrigo.getNome());
        Assertions.assertEquals(abrigoEncontrado.getEmail(), abrigo.getEmail());
        Assertions.assertEquals(abrigoEncontrado.getTelefone(), abrigo.getTelefone());
    }
}