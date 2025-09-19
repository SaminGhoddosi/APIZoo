package com.example.Zoo.Service;

import com.example.Zoo.DTO.AlimentacaoDto;
import com.example.Zoo.Models.Alimentacao;
import com.example.Zoo.Repositories.AlimentacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlimentacaoService {

    private final AlimentacaoRepository repository;
    @Autowired
    public AlimentacaoService(AlimentacaoRepository repository) {
        this.repository = repository;
    }

    public Alimentacao salvar(AlimentacaoDto dto) {
        Alimentacao alimentacao = new Alimentacao();
        alimentacao.setComida(dto.comida());
        alimentacao.setQtdDiaria(dto.qtdDiaria());
        alimentacao.setAnimal(dto.animal());
        return repository.save(alimentacao);
    }

    public List<Alimentacao> listar() {
        return repository.findAll();
    }

    public Optional<Alimentacao> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public Alimentacao atualizar(Long id, AlimentacaoDto dto) {
        return repository.findById(id).map(alimentacao -> {
            alimentacao.setComida(dto.comida());
            alimentacao.setQtdDiaria(dto.qtdDiaria()    );
            alimentacao.setAnimal(dto.animal());
            return repository.save(alimentacao);
        }).orElseThrow(() -> new RuntimeException("Alimentação não encontrada"));
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
