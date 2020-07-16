package com.xuncai.parking.common.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReadExcel {
    // 总行数
    private int totalRows = 0;
    // 总条数
    private int totalCells = 0;
    // 错误信息接收器
    private String errorMsg;

    // 构造方法
    public ReadExcel() {}

    // 获取总行数
    public int getTotalRows() {
        return totalRows;
    }

    // 获取总列数
    public int getTotalCells() {
        return totalCells;
    }

    // 获取错误信息
    public String getErrorInfo() {
        return errorMsg;
    }

    public   List<Map<String, Object>> getExcelInfo(MultipartFile mFile) {
        String fileName = mFile.getOriginalFilename();// 获取文件名
        try {
            if (!validateExcel(fileName)) {// 验证文件名是否合格
                return null;
            }

            boolean isExcel2003 = true;// 根据文件名判断文件是2003版本还是2007版本
            if (isExcel2007(fileName)) {
                isExcel2003 = false;
            }

            return createExcel(mFile.getInputStream(), isExcel2003);
        }catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public List<Map<String, Object>> createExcel(InputStream is, boolean isExcel2003) {
        try {
            Workbook wb = null;
            if (isExcel2003) {// 当excel是2003时,创建excel2003
                wb = new HSSFWorkbook(is);
            }else{// 当excel是2007时,创建excel2007
                wb = new XSSFWorkbook(is);
            }
            return readExcelValue(wb);// 读取Excel里面客户的信息
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 读取Excel内容
     * @param wb
     * @return
     */
    private List<Map<String, Object>> readExcelValue(Workbook wb) {
        // 得到第一个shell
         Sheet sheet = wb.getSheetAt(0);
         // 得到Excel的行数
         this.totalRows = sheet.getPhysicalNumberOfRows();
         // 得到Excel的列数(前提是有行数)
         if (totalRows > 1 && sheet.getRow(0) != null) {
                 this.totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
         }

                List<Map<String, Object>> userList = new ArrayList<Map<String, Object>>();
                // 循环Excel行数
                Integer count=0;
                 for (int r = 1; r < totalRows; r++) {
                         Row row = sheet.getRow(r);

                         if (row == null) {
                                 continue;
                             }
                         // 循环Excel的列
                         Map<String, Object> map = new HashMap<String, Object>();

                         for (int c = 0; c < this.totalCells; c++) {
                                 Cell cell = row.getCell(c);
                                 if (null != cell&&!"".equals(cell.getStringCellValue())&&cell.getStringCellValue()!=null) {
                                         if (c == 0) {
                                                map.put("couponCode", cell.getStringCellValue().trim());//券码
                                                 // 添加到list
                                                 userList.add(map);
                                                 count++;
                                            }
                                     }
                             }
                     }


                this.totalRows=count;
                return userList;
    }



            public boolean validateExcel(String filePath) {
                if (filePath == null || !(isExcel2003(filePath) || isExcel2007(filePath))) {
                     errorMsg = "文件名不是excel格式";
                     return false;
                 }
                 return true;
             }

             // @描述：是否是2003的excel，返回true是2003
             public static boolean isExcel2003(String filePath) {
                 return filePath.matches("^.+\\.(?i)(xls)$");
             }

             // @描述：是否是2007的excel，返回true是2007
             public static boolean isExcel2007(String filePath) {
                 return filePath.matches("^.+\\.(?i)(xlsx)$");
             }
}
