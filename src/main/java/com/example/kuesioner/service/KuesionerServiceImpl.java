package com.example.kuesioner.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.kuesioner.dto.KuesionerDto;
import com.example.kuesioner.dto.PilihanData;
import com.example.kuesioner.dto.ResponseData;
import com.example.kuesioner.dto.SummaryData;
import com.example.kuesioner.repository.Kuesioner;
import com.example.kuesioner.repository.KuesionerRepository;

@Service
public class KuesionerServiceImpl implements KuesionerService {

  @Autowired
  KuesionerRepository repository;

  Kuesioner kuesioner;

  List<Kuesioner> kuesioners;

  SummaryData summaryData;

  PilihanData pilihanData;

  @Override
  public ResponseData<?> insertNewResponden(KuesionerDto data) {
    try {

      kuesioner = new Kuesioner();

      kuesioner.setBirthDate(data.getBirthDate());
      kuesioner.setApps(data.getApps());
      kuesioner.setValue(data.getValue());
      
      repository.save(kuesioner);
      
      return successResponse(kuesioner);
    } catch (Exception e) {

      return failedResponse("Failed to insert new Data");
    }
  }
  
  @Override
  public ResponseData<?> getAllResponden() {
    try {

      kuesioners = repository.findAll();

      return successResponse(kuesioners);
    } catch (Exception e) {

      return failedResponse("Failed to retrieve all Records");
    }
  }

  @Override
  public ResponseData<?> getSummaryResponden() {
    List<Integer> respondenAges = new ArrayList<>();
    Map<String,Integer> respondenApps = new HashMap<>();
    Map<String,PilihanData> pilihanResponden = new HashMap<>();
    Map<String,Integer> totalSkor = new HashMap<>();
    Map<String,Float> totalIndex = new HashMap<>();
    
    try {
      
      summaryData = new SummaryData();
      kuesioners = repository.findAll();
      
      kuesioners.forEach(k ->{
        
        respondenAges.add(getAge(k.getBirthDate()));
        processAppsSummary(respondenApps, k.getApps());
        processPilihanResponden(pilihanResponden, k.getValue());        
      });

      processTotalSkor(totalSkor, pilihanResponden);
      processTotalIndex(totalIndex, totalSkor, kuesioners.size());

      summaryData.setTotalResponden(kuesioners.size());
      summaryData.setRespondenAges(respondenAges);
      summaryData.setRespondenApps(respondenApps);
      summaryData.setPilihanResponden(pilihanResponden);
      summaryData.setTotalSkor(totalSkor);
      summaryData.setTotalIndex(totalIndex);
      summaryData.setTingkatKepuasan(processTingkatKepuasan(totalIndex));
      
      return successResponse(summaryData);
    } catch (Exception e) {

      // return failedResponse("Failed to calculate summary Data");
      return failedResponse(e.getMessage());
    }
  }

  private Integer getAge(String birthDate){
    int year = Integer.parseInt(birthDate.substring(0, 4));
    return 2023 - year;
  }

  private void processAppsSummary(Map<String,Integer> summaryApps, List<String> respondenApp){

    respondenApp.forEach(a -> {
      if( summaryApps.containsKey(a) ){
        summaryApps.put(a, summaryApps.get(a) + 1);        
      } else{

        summaryApps.put(a, 1);
      }
    });
  }

  private void processPilihanResponden(Map<String,PilihanData> summaryPilihan, List<Integer> pilihan){

    for (int i = 0; i < pilihan.size(); i++) {
      String indKey = "X" + ( i + 1 );

      if(summaryPilihan.containsKey(indKey)){

        incrementPilihanData(summaryPilihan.get(indKey), pilihan.get(i));
      }else {
        
        pilihanData = new PilihanData();
        summaryPilihan.put(indKey, pilihanData);
        incrementPilihanData(summaryPilihan.get(indKey), pilihan.get(i));
      }

    }
  }

  private void incrementPilihanData(PilihanData objPilihan, int value){

    switch (value) {
      case 1:
        objPilihan.setSts(objPilihan.getSts() + 1);
        break;
      case 2:
        objPilihan.setTs(objPilihan.getTs() + 1);
        break;
      case 3:
        objPilihan.setR(objPilihan.getR() + 1);
        break;
      case 4:
        objPilihan.setS(objPilihan.getS() + 1);
        break;
      case 5:
        objPilihan.setSs(objPilihan.getSs() + 1);
        break;
    
      default:
        break;
    }
  }

  private void processTotalSkor(Map<String,Integer> total, Map<String,PilihanData> pilihan){
  
    pilihan.forEach((k,v) -> {
      int finalSkor = 0;
      
      if(v.getSts() != 0){
        finalSkor = finalSkor + (v.getSts() * 1);
      }
      
      if(v.getTs() != 0){
        finalSkor = finalSkor + (v.getTs() * 2);
      }
      
      if(v.getR() != 0){
        finalSkor = finalSkor + (v.getR() * 3);
      }
      
      if(v.getS() != 0){
        finalSkor = finalSkor + (v.getS() * 4);
      }
      
      if(v.getSs() != 0){
        finalSkor = finalSkor + (v.getSs() * 5);
      }

      total.put(k, finalSkor);
    });
  }

  private void processTotalIndex(Map<String,Float> finalIndex, Map<String,Integer> finalSkor, int totalResponden){

    finalSkor.forEach((k,v) ->{
      float hasilAkhir = 0;     

      hasilAkhir = (v * 100) / (5 * totalResponden);
      
      finalIndex.put(k, hasilAkhir);
    });
  }
  
  private float processTingkatKepuasan(Map<String,Float> nilaiIndex){
    float tingkatKepuasan = 0;

    for (float value : nilaiIndex.values())
    tingkatKepuasan = tingkatKepuasan + value;

    return tingkatKepuasan / nilaiIndex.size();
  }
  
  private ResponseData<?> successResponse(Object data){
    return new ResponseData<>(HttpStatus.OK.value(), data);
  }
  
  private ResponseData<?> failedResponse(Object data){
    return new ResponseData<>(HttpStatus.BAD_REQUEST.value(), data);
  }


  
}
