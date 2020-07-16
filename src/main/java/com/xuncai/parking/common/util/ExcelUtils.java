package com.xuncai.parking.common.util;

import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.xssf.usermodel.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ExcelUtils {

   // private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     *tempPath 模板文件路径
     *path 文件路径
     *fileName :文件名称
     *sheetName sheet名称
     *list 集合数据
     */
    public void exportExcel(String tempPath, String path,String fileName,String sheetName, HttpServletResponse response, List<LinkedHashMap<String,Object>> list) {
        File newFile = createNewFile(tempPath, path);
        InputStream is = null;
        XSSFWorkbook workbook = null;
        XSSFSheet sheet = null;
        try {
            is = new FileInputStream(newFile);// 将excel文件转为输入流
            workbook = new XSSFWorkbook(is);// 创建个workbook，
            // 获取第一个sheet
            sheet = workbook.getSheetAt(0);
            workbook.setSheetName(0, sheetName);
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        if (sheet != null) {
            try {
                // 写数据
                FileOutputStream fos = new FileOutputStream(newFile);
                XSSFRow row = sheet.getRow(0);
                if (row == null) {
                    row = sheet.createRow(0);
                }
                XSSFCell cell = row.getCell(0);
//                if (cell == null) {
//                    cell = row.createCell(0);
//                }
//                cell.setCellValue("我是标题");

                for (int i = 0; i < list.size(); i++) {
                	Map<String,Object> vo = list.get(i);
                    row = sheet.createRow(i+1); //从第三行开始

                    //这里就可以使用sysUserMapper，做相应的操作
                    //User user = excelUtils.sysUserMapper.selectByPrimaryKey(vo.getId());                  

                    //根据excel模板格式写入数据....
                    int j=0;
                    for (Map.Entry<String,Object> entry : vo.entrySet()) { 
                    	 System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue()); 	
                    	 createRowAndCell(entry.getValue(), row, cell, j);
                    	 j++;
                    }
//                    createRowAndCell(vo.getRealName(), row, cell, 0);
//                    createRowAndCell(vo.getOrderInfo(), row, cell, 1);
//                    createRowAndCell(vo.getLy(), row, cell, 2);
//                    createRowAndCell(format.format(DateFormat.getDateInstance().parse(vo.getCreateTime())), row, cell, 3);
//                    createRowAndCell(vo.getTotal(), row, cell, 4);
//                    createRowAndCell(getOrderSource(vo.getSourceId()), row, cell, 5);
                    //.....
                }
                workbook.write(fos);
                fos.flush();
                fos.close();

                // 下载
                InputStream fis = new BufferedInputStream(new FileInputStream(
                        newFile));
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                fis.close();
                response.reset();
                response.setContentType("text/html;charset=UTF-8");
                OutputStream toClient = new BufferedOutputStream(
                        response.getOutputStream());
                response.setContentType("application/x-msdownload");
                String newName = URLEncoder.encode(
                		fileName,
                        "UTF-8");
                response.addHeader("Content-Disposition",
                        "attachment;filename=\"" + newName + "\"");
                response.addHeader("Content-Length", "" + newFile.length());
                toClient.write(buffer);
                toClient.flush();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (null != is) {
                        is.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        // 删除创建的新文件
        this.deleteFile(newFile);
    }

    /**
	 * @Title: exportExcel
	 * @Description: 导出Excel的方法
	 * @author: evan @ 2014-01-09 
	 * @param workbook 
	 * @param sheetNum (sheet的位置，0表示第一个表格中的第一个sheet)
	 * @param sheetTitle  （sheet的名称）
	 * @param headers    （表格的标题）
	 * @param result   （表格的数据）
	 * @param out  （输出流）
	 * @throws Exception
	 */
	public void exportExcel(XSSFWorkbook workbook, int sheetNum,
			String sheetName, String[] headers, List<LinkedHashMap<String,Object>> list,
			OutputStream out) throws Exception {
		// 生成一个表格
		XSSFSheet sheet = workbook.createSheet();
		workbook.setSheetName(sheetNum, sheetName);
		// 设置表格默认列宽度为20个字节
		sheet.setDefaultColumnWidth((short) 20);
		// 生成一个样式
		XSSFCellStyle style = workbook.createCellStyle();
		// 设置这些样式
//		style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
//		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
//		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
//		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 生成一个字体
//		HSSFFont font = workbook.createFont();
//		font.setColor(HSSFColor.BLACK.index);
//		font.setFontHeightInPoints((short) 12);
//		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		//style.setFont(font);
 
		// 指定当单元格内容显示不下时自动换行
		//style.setWrapText(true);
 
		// 产生表格标题行
		XSSFRow row = sheet.createRow(0);
		for (int count = 0; count < headers.length; count++) {
			XSSFCell cell = row.createCell((short) count);
		
			cell.setCellStyle(style);
			HSSFRichTextString text = new HSSFRichTextString(headers[count]);
			cell.setCellValue(text.toString());
		}
		// 遍历集合数据，产生数据行
//		if (result != null) {
//			int index = 1;
//			for (List<String> m : result) {
//				row = sheet.createRow(index);
//				int cellIndex = 0;
//				for (String str : m) {
//					HSSFCell cell = row.createCell((short) cellIndex);
//					cell.setCellValue(str.toString());
//					cellIndex++;
//				}
//				index++;
//			}
//		}
		
		for(int i = 0; i < list.size(); i++) {
        	Map<String,Object> vo = list.get(i);
            row = sheet.createRow(i+1); //从第三行开始
            //根据excel模板格式写入数据....
            int cellIndex=0;
            for (Map.Entry<String,Object> entry : vo.entrySet()) { 
            	 System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue()); 	
            	 XSSFCell cell = row.createCell((short) cellIndex);
            	 if(entry.getValue()!=null){
            		 cell.setCellValue(entry.getValue().toString());
            	 }else{
            		 cell.setCellValue("");
            	 }
				 cellIndex++;
            }
        }
	}
    
    /**
     *根据当前row行，来创建index标记的列数,并赋值数据
     */
    private void createRowAndCell(Object obj, XSSFRow row, XSSFCell cell, int index) {
        cell = row.getCell(index);
        if (cell == null) {
            cell = row.createCell(index);
        }

        if (obj != null)
            cell.setCellValue(obj.toString());
        else 
            cell.setCellValue("");
    }

    /**
     * 复制文件
     * 
     * @param s
     *            源文件
     * @param t
     *            复制到的新文件
     */

    public void fileChannelCopy(File s, File t) {
        try {
            InputStream in = null;
            OutputStream out = null;
            try {
                in = new BufferedInputStream(new FileInputStream(s), 1024);
                out = new BufferedOutputStream(new FileOutputStream(t), 1024);
                byte[] buffer = new byte[1024];
                int len;
                while ((len = in.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                }
            } finally {
                if (null != in) {
                    in.close();
                }
                if (null != out) {
                    out.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 读取excel模板，并复制到新文件中供写入和下载
     * 
     * @return
     */
    public File createNewFile(String tempPath, String rPath) {
        // 读取模板，并赋值到新文件************************************************************
        // 文件模板路径
        String path = (tempPath);
        File file = new File(path);
        // 保存文件的路径
        String realPath = rPath;
        // 新的文件名
        String newFileName = System.currentTimeMillis() + ".xlsx";
        // 判断路径是否存在
        File dir = new File(realPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        // 写入到新的excel
        File newFile = new File(realPath, newFileName);
        try {
            newFile.createNewFile();
            // 复制模板到新文件
            fileChannelCopy(file, newFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newFile;
    }

    /**
     * 下载成功后删除
     * 
     * @param files
     */
    private void deleteFile(File... files) {
        for (File file : files) {
            if (file.exists()) {
                file.delete();
            }
        }
    }



}