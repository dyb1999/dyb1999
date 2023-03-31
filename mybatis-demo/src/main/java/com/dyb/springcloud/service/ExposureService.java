package com.dyb.springcloud.service;

import com.dyb.springcloud.pojo.ForexExposure;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ExposureService {

    List<ForexExposure> findAll();

    int insert(ForexExposure exposure);
}
