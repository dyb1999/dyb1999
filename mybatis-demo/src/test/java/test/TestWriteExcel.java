package test;


import com.alibaba.excel.EasyExcel;
import com.dyb.springcloud.pojo.DemoData;
import com.dyb.springcloud.pojo.ForexExposure;
import com.dyb.springcloud.service.ExposureService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

public class TestWriteExcel extends ApplicationTests {
    @Resource
    ExposureService service;

    @Test
    public void writeExcel(){
        List<ForexExposure> exposures = service.findAll();

        String fileName = "D:\\exposure.xlsx";
        EasyExcel.write(fileName, ForexExposure.class).sheet("敞口").doWrite(exposures);
    }

//    public static void main(String[] args) {
//        //实现excel写的操作
//        //1.设置写入文件夹的地址和excel文件名称
//        String fileName = "D:\\write.xlsx";
//
//        //2.调用easyexcel里面方法实现写操作
//        //传入：文件存放的路径+对应的实体类class
//        EasyExcel.write(fileName, DemoData.class).sheet("学生列表").doWrite(getData());
//    }
//
//    //创建方法返回list集合（测试数据）
//    private static List<DemoData> getData() {
//        List<DemoData> list = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            DemoData data = new DemoData();
//            data.setSno(i);
//            data.setSname("lucy" + i);
//            list.add(data);
//        }
//        return list;
//    }

}
