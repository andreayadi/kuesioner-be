package com.example.kuesioner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.kuesioner.dto.KuesionerDto;
import com.example.kuesioner.dto.ResponseData;
import com.example.kuesioner.service.KuesionerService;

@CrossOrigin("*")
@RestController
@RequestMapping("kuesioner")
public class KuesionerController {
  
  @Autowired
  KuesionerService service;

  ResponseData<?> responseData;

  @PostMapping
  public ResponseEntity<?> insertNewData(@RequestBody KuesionerDto data){

    responseData = service.insertNewResponden(data);
    return ResponseEntity.status(responseData.getStatus()).body(responseData.getData());
  }

  @GetMapping
  public ResponseEntity<?> getAllData(){

    responseData = service.getAllResponden();
    return ResponseEntity.status(responseData.getStatus()).body(responseData.getData());
  }

  @GetMapping("/summary")
  public ResponseEntity<?> getSummaryData(){

    responseData = service.getSummaryResponden();
    return ResponseEntity.status(responseData.getStatus()).body(responseData.getData());
  }
}
