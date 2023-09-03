package com.example.kuesioner.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KuesionerDto {
  
  private String birthDate;
  private List<String> apps;
  private List<Integer> value;
}
