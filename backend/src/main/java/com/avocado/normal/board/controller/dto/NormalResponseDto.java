package com.avocado.normal.board.controller.dto;

import com.avocado.normal.board.domain.entity.Item;
import lombok.*;

import java.util.List;

@Getter
//@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class NormalResponseDto {

    private List<NormalResponseEntryDto> entries;

//    private NormalResponseEntryDto normalResponseEntryDto;
//
    public NormalResponseDto(List<NormalResponseEntryDto> entries){
        this.entries = entries;
    }
}