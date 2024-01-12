package com.giacomini.andrea.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
public class InfoMsg
{
    public LocalDate data;
    public String message;
}