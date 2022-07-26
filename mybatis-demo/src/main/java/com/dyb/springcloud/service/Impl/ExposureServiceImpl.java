package com.dyb.springcloud.service.Impl;

import com.dyb.springcloud.mapper.ForexExposureMapper;
import com.dyb.springcloud.pojo.ForexExposure;
import com.dyb.springcloud.service.ExposureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ExposureServiceImpl implements ExposureService {
    @Resource
    ForexExposureMapper mapper;

    @Override
    public List<ForexExposure> findAll() {
        return mapper.findAll();
    }

    @Override
    public int insert(ForexExposure exposure) {
        return mapper.insert(exposure);
    }
}
