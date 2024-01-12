package com.giacomini.andrea.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

@Entity
@Table(name = "auto")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Auto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "4x4")
    private Boolean quattroPerQuattro;

    @Column(name = "fasciaDiPrezzo")
    @NotNull(message = "La Fascia di Prezzo non può avere un valore null")
    @NotBlank(message = "La Fascia di Prezzo non può essere vuota")
    @Pattern(regexp = "^0 a 5K$|^da 5K a 10K$|^oltre i 10K$",
             message = "La fascia di prezzo deve essere '0 a 5K', 'da 5K a 10K' oppure 'oltre i 10K'")
    private String fasciaDiPrezzo;

    @Column(name = "anzianita")
    @Max(value = 5, message = "L'anzianità deve avere un valore massimo di 5")
    @Min(value = 1, message = "L'anzianità deve avere un valore minimo di 1")
    @NotNull(message = "L'anzianità non può avere un valore null")
    private Integer anzianita;

    @Column(name = "cambioAutomatico")
    private Boolean cambioAutomatico;

    @Column(name = "alimentazione")
    @NotNull(message = "L'Alimentazione non può avere un valore null")
    @NotBlank(message = "L'Alimentazione non può essere vuota")
    @Pattern(regexp = "^benzina$|^gasolio$|^elettrica$",
            message = "L'Alimentazione può essere solo 'benzina', 'gasolio' oppure 'elettrica'")
    private String alimentazione;

    public Auto(boolean quattroPerQuattro, String fasciaDiPrezzo,
                Integer anzianita, boolean cambioAutomatico, String alimentazione) {
        this.quattroPerQuattro = quattroPerQuattro;
        this.fasciaDiPrezzo = fasciaDiPrezzo;
        this.anzianita = anzianita;
        this.cambioAutomatico = cambioAutomatico;
        this.alimentazione = alimentazione;
    }
}