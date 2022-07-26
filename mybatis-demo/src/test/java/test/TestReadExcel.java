package test;

import com.alibaba.excel.EasyExcel;
import com.dyb.springcloud.listener.ExcelListener;
import com.dyb.springcloud.pojo.DemoData;

public class TestReadExcel {
    public static void main(String[] args) {
        String fileName = "D:\\write.xlsx";
        EasyExcel.read(fileName, DemoData.class, new ExcelListener()).sheet().doRead();
    }
}
