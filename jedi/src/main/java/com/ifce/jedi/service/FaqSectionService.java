package com.ifce.jedi.service;

import com.ifce.jedi.dto.FaqSection.*;
import com.ifce.jedi.model.SecoesSite.FaqSection.FaqItem;
import com.ifce.jedi.model.SecoesSite.FaqSection.FaqSection;
import com.ifce.jedi.repository.FaqSectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FaqSectionService {

    @Autowired
    private FaqSectionRepository repository;

    @Transactional
    public FaqSectionResponseDto get() {
        Optional<FaqSection> entity = repository.findFirstByOrderByIdAsc();
        return entity.map(this::toResponse).orElseGet(this::createDefaultSection);
    }

    private FaqSectionResponseDto createDefaultSection() {
        FaqSection section = new FaqSection();
        section.setTitle("AJUDA");
        section.setSubtitle("Dúvidas frequentes");

        List<FaqItem> items = List.of(
                createItem(section, "Lorem ipsum dolor sit amet, consectetur adipiscing elit?",
                        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Dois consequat laborello dui vitae laoreet.", 1),
                createItem(section, "Lorem ipsum dolor sit amet, consectetur adipiscing elit?",
                        "Lorem ipsum dolor sit amet, consectetur adipiscing elit.", 2),
                createItem(section, "Lorem ipsum dolor sit amet, consectetur adipiscing elit?",
                        "Lorem ipsum dolor sit amet, consectetur adipiscing elit.", 3),
                createItem(section, "Lorem ipsum dolor sit amet, consectetur adipiscing elit?",
                        "Lorem ipsum dolor sit amet, consectetur adipiscing elit.", 4)
        );

        section.setItems(items);
        FaqSection saved = repository.save(section);
        return toResponse(saved);
    }

    private FaqItem createItem(FaqSection section, String question, String answer, int position) {
        FaqItem item = new FaqItem();
        item.setQuestion(question);
        item.setAnswer(answer);
        item.setPosition(position);
        item.setFaqSection(section);
        return item;
    }

    @Transactional
    public FaqSectionResponseDto updateItem(Long itemId, FaqItemUpdateDto dto) {
        FaqSection section = repository.findFirstByOrderByIdAsc()
                .orElseThrow(() -> new RuntimeException("FAQ Section não encontrada"));

        FaqItem item = section.getItems().stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Item FAQ não encontrado"));

        if (dto.question() != null) item.setQuestion(dto.question());
        if (dto.answer() != null) item.setAnswer(dto.answer());

        repository.save(section);
        return toResponse(section);
    }

    @Transactional
    public FaqSectionResponseDto updateHeader(FaqSectionHeaderUpdateDto dto) {
        FaqSection section = repository.findFirstByOrderByIdAsc()
                .orElseThrow(() -> new RuntimeException("FAQ Section não encontrada"));

        // Atualiza APENAS title e subtitle (ignora os itens)
        if (dto.title() != null) {
            section.setTitle(dto.title());
        }
        if (dto.subtitle() != null) {
            section.setSubtitle(dto.subtitle());
        }

        FaqSection updated = repository.save(section);
        return toResponse(updated); // Usa o método existente para a resposta
    }

    // FaqSectionService.java
    @Transactional
    public FaqSectionResponseDto addItem(FaqItemCreateDto dto) {
        // Busca a seção existente ou cria uma padrão (usando get() que já existe)
        FaqSection section = repository.findFirstByOrderByIdAsc()
                .orElseGet(() -> {
                    FaqSectionResponseDto defaultSection = createDefaultSection();
                    return repository.findById(defaultSection.id())
                            .orElseThrow(() -> new RuntimeException("FAQ Section não encontrada após criação"));
                });

        // Cria o novo item
        FaqItem newItem = new FaqItem();
        newItem.setQuestion(dto.question());
        newItem.setAnswer(dto.answer());
        newItem.setPosition(section.getItems().size() + 1);
        newItem.setFaqSection(section);

        // Adiciona à seção
        section.getItems().add(newItem);

        // Salva e retorna a resposta usando o toResponse existente
        FaqSection updatedSection = repository.save(section);
        return toResponse(updatedSection);
    }

    private FaqSectionResponseDto toResponse(FaqSection entity) {
        List<FaqItemResponseDto> items = entity.getItems().stream()
                .sorted((a, b) -> a.getPosition().compareTo(b.getPosition()))
                .map(item -> new FaqItemResponseDto(
                        item.getId(),
                        item.getQuestion(),
                        item.getAnswer(),
                        item.getPosition()
                ))
                .collect(Collectors.toList());

        return new FaqSectionResponseDto(
                entity.getId(),
                entity.getTitle(),
                entity.getSubtitle(),
                items
        );
    }
}