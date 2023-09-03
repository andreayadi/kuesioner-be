package com.example.kuesioner.dto;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SummaryData {
  
  private int totalResponden;
  private List<Integer> respondenAges;
  private Map<String,Integer> respondenApps;
  private Map<String,PilihanData> pilihanResponden;
  private Map<String,Integer> totalSkor;
  private Map<String,Float> totalIndex;
  private float tingkatKepuasan;
}
