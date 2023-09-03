package com.example.kuesioner.service;

import com.example.kuesioner.dto.KuesionerDto;
import com.example.kuesioner.dto.ResponseData;

public interface KuesionerService {
  public ResponseData<?> insertNewResponden(KuesionerDto data);
  public ResponseData<?> getAllResponden();
  public ResponseData<?> getSummaryResponden();
}
