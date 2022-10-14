package io.github.vsverchkov.stockservice.model.dto;

import io.github.vsverchkov.stockservice.model.type.ReservedStatus;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ReservedDto {

    private final UUID catalogId;

    private final ReservedStatus status;

    private final Integer quantity;

}
