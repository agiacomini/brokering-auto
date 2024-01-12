package com.giacomini.andrea.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AutoDto {
    private Long id;
    private Boolean quattroPerQuattro;
    private String fasciaDiPrezzo;
    private Integer anzianita;
    private Boolean cambioAutomatico;
    private String alimentazione;
}