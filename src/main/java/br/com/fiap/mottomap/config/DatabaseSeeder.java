package br.com.fiap.mottomap.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import br.com.fiap.mottomap.model.Area;
import br.com.fiap.mottomap.model.CargoUsuario;
import br.com.fiap.mottomap.model.Filial;
import br.com.fiap.mottomap.model.PosicaoPatio;
import br.com.fiap.mottomap.model.Usuario;
import br.com.fiap.mottomap.repository.FilialRepository;
import br.com.fiap.mottomap.repository.PosicaoPatioRepository;
import br.com.fiap.mottomap.repository.UsuarioRepository;
import jakarta.annotation.PostConstruct;

@Configuration
public class DatabaseSeeder {
    
    @Autowired
    FilialRepository filialRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    PosicaoPatioRepository posicaoPatioRepository;

    @PostConstruct
    public void init(){
        var filiais = List.of(
            Filial.builder()
                .nome("Mottu São Paulo")
                .endereco("Av. Paulista, 1000")
                .cidade("São Paulo")
                .siglaEstado("SP")
                .numeroColuna(10)
                .numeroLinha(5)
                .capacidadeMaxima(150)
                .build(),

            Filial.builder()
                .nome("Mottu Rio de Janeiro")
                .endereco("Av. Atlântica, 500")
                .cidade("Rio de Janeiro")
                .siglaEstado("RJ")
                .numeroColuna(8)
                .numeroLinha(5)
                .capacidadeMaxima(120)
                .build(),

            Filial.builder()
                .nome("Mottu Belo Horizonte")
                .endereco("Av. Afonso Pena, 2000")
                .cidade("Belo Horizonte")
                .siglaEstado("MG")
                .numeroColuna(6)
                .numeroLinha(4)
                .capacidadeMaxima(80)
                .build(),

            Filial.builder()
                .nome("Mottu Porto Alegre")
                .endereco("Rua da Praia, 300")
                .cidade("Porto Alegre")
                .siglaEstado("RS")
                .numeroColuna(5)
                .numeroLinha(3)
                .capacidadeMaxima(60)
                .build(),

            Filial.builder()
                .nome("Mottu Brasília")
                .endereco("Setor Comercial Sul")
                .cidade("Brasília")
                .siglaEstado("DF")
                .numeroColuna(7)
                .numeroLinha(4)
                .capacidadeMaxima(100)
                .build()
        );

        filialRepository.saveAll(filiais);

        var usuarios = List.of(
            Usuario.builder()
                .nome("Cauã Aragão")
                .email("aragao@mottomap.com")
                .senha("caua12345")
                .cargoUsuario(CargoUsuario.COL_MECANICO)
                .filial(filiais.get(0))
                .build(),
            Usuario.builder()
                .nome("Gustavo Oliveira")
                .email("gustavo@mottomap.com")
                .senha("gustavo12345")
                .cargoUsuario(CargoUsuario.ADM_GERAL)
                .filial(filiais.get(1))
                .build(),
            Usuario.builder()
                .nome("Pedro Felix")
                .email("p.felix@mottomap.com")
                .senha("pedrito12345")
                .cargoUsuario(CargoUsuario.COL_PATIO)
                .filial(filiais.get(2))
                .build(),
            Usuario.builder()
                .nome("Pedro Barbosa")
                .email("p.barbosa@mottomap.com")
                .senha("barbosa12345")
                .cargoUsuario(CargoUsuario.ADM_LOCAL)
                .filial(filiais.get(3))
                .build(),
            Usuario.builder()
                .nome("Mateus Estevam")
                .email("mafeus@mottomap.com")
                .senha("mateus12345")
                .cargoUsuario(CargoUsuario.COL_MECANICO)
                .filial(filiais.get(4))
                .build()
        );

        usuarioRepository.saveAll(usuarios);

        var posicoes = List.of(
            PosicaoPatio.builder()
                .identificacao("A1")
                .numeroLinha(1)
                .numeroColuna(1)
                .area(Area.MINHA_MOTTU)
                .ocupado(true)
                .filial(filiais.get(0))
                .build(),
            PosicaoPatio.builder()
                .identificacao("B1")
                .numeroLinha(2)
                .numeroColuna(1)
                .area(Area.PRONTAS)
                .ocupado(false)
                .filial(filiais.get(0))
                .build(),
            PosicaoPatio.builder()
                .identificacao("C1")
                .numeroLinha(3)
                .numeroColuna(1)
                .area(Area.PROBLEMAS_SIMPLES)
                .ocupado(true)
                .filial(filiais.get(0))
                .build(),
            PosicaoPatio.builder()
                .identificacao("D2")
                .numeroLinha(4)
                .numeroColuna(2)
                .area(Area.PROBLEMAS_GRAVES)
                .ocupado(false)
                .filial(filiais.get(1))
                .build(),
            PosicaoPatio.builder()
                .identificacao("E3")
                .numeroLinha(5)
                .numeroColuna(3)
                .area(Area.IRRECUPERAVEIS)
                .ocupado(false)
                .filial(filiais.get(1))
                .build()
        );

        posicaoPatioRepository.saveAll(posicoes);
    }
}
