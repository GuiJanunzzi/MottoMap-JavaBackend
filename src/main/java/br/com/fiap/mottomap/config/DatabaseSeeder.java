package br.com.fiap.mottomap.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import br.com.fiap.mottomap.model.Filial;
import br.com.fiap.mottomap.repository.FilialRepository;
import jakarta.annotation.PostConstruct;

@Configuration
public class DatabaseSeeder {
    
    @Autowired
    FilialRepository filialRepository;

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
    }
}
