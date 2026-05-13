package com.kairosmix.application.usecases;

import com.kairosmix.domain.entities.CustomMix;
import com.kairosmix.domain.ports.output.CustomMixRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateCustomMixUseCase {
    private final CustomMixRepositoryPort customMixRepository;

    public CustomMix execute(CustomMix customMix) {
        if (customMix == null) {
            throw new IllegalArgumentException("La mezcla no puede ser nula");
        }

        // Validar que el nombre sea único
        customMixRepository.findByName(customMix.getName()).ifPresent(m -> {
            throw new IllegalArgumentException("Ya existe una mezcla con el nombre: " + customMix.getName());
        });

        // Validar mezcla
        customMix.validateMix();

        // Calcular información nutricional
        customMix.calculateTotal();

        return customMixRepository.save(customMix);
    }
}
