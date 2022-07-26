package com.dyb.springcloud.mapper;

import com.dyb.springcloud.pojo.ForexExposure;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ForexExposureMapper {
    @Select("select * from fund_report_forex_exposure_row")
    List<ForexExposure> findAll();


    int insert(@Param("model") ForexExposure exposure);
}
