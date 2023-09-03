package com.example.kuesioner.repository;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "responden")
public class Kuesioner {
 
  @Id
  private String id;

  private String birthDate;
  
  private List<String> apps;
  
  private List<Integer> value;
}
