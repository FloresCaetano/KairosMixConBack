package com.kairosmix.infrastructure.mapper;

import com.kairosmix.domain.entities.CustomMix;
import com.kairosmix.domain.entities.MixComponent;
import com.kairosmix.infrastructure.rest.dto.CustomMixDTO;
import com.kairosmix.infrastructure.rest.dto.MixComponentDTO;
import com.kairosmix.infrastructure.rest.dto.MixNutritionalInfoDTO;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class CustomMixMapper {
    public CustomMixDTO entityToDto(CustomMix entity) {
        if (entity == null) {
            return null;
        }
        return CustomMixDTO.builder()
            .id(entity.getId())
            .name(entity.getName())
            .clientId(entity.getClient() != null ? entity.getClient().getId() : null)
            .description(entity.getDescription())
            .components(mapComponentsToDto(entity.getComponents()))
            .totalPrice(entity.getTotalPrice() != null ? entity.getTotalPrice().doubleValue() : null)
            .nutritionalInfo(mapNutritionalInfoToDto(entity.calculateNutritionalInfo()))
            .build();
    }

    public CustomMix dtoToEntity(CustomMixDTO dto) {
        if (dto == null) {
            return null;
        }
        return CustomMix.builder()
            .id(dto.getId())
            .name(dto.getName())
            .description(dto.getDescription())
            .totalPrice(dto.getTotalPrice() != null ? java.math.BigDecimal.valueOf(dto.getTotalPrice()) : null)
            .build();
    }

    private List<MixComponentDTO> mapComponentsToDto(List<MixComponent> components) {
        if (components == null) {
            return Collections.emptyList();
        }
        return components.stream()
            .map(component -> MixComponentDTO.builder()
                .id(component.getId())
                .productId(component.getProduct().getId())
                .productCode(component.getProduct().getCode())
                .productName(component.getProduct().getName())
                .quantity(component.getQuantity())
                .unitPrice(component.getUnitPrice())
                .subtotal(component.getSubtotal())
                .build())
            .toList();
    }

    private MixNutritionalInfoDTO mapNutritionalInfoToDto(com.kairosmix.domain.entities.MixNutritionalInfo info) {
        if (info == null) {
            return null;
        }
        return MixNutritionalInfoDTO.builder()
            .calories(info.getCalories())
            .protein(info.getProtein())
            .fat(info.getFat())
            .carbs(info.getCarbs())
            .fiber(info.getFiber())
            .build();
    }
}
