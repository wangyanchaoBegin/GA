package io.renren.modules.ga.util;


//import com.exp.model.User;
import io.renren.modules.ga.bean.util.ExcelLuluData;
import io.renren.modules.ga.bean.util.ExcelLuluTile;
import io.renren.modules.ga.bean.util.ExcelSheet;
import lombok.Data;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.StringUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;



import java.io.FileOutputStream;


public class POIUtil {

    // excel http 导出
    private static boolean exportExcel(OutputStream out) throws Exception {

        XSSFWorkbook wb = new XSSFWorkbook();


        try {
            wb.write(out);
        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            //此处需要关闭 wb 变量

            out.close();

        }

        return true;

    }




    public static void exportExcel2http(ExcelSheet excelSheet, String sheetName,String fileName, HttpServletResponse response) throws Exception{
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename="+fileName+".xls");

        OutputStream outputStream = response.getOutputStream();
        HSSFWorkbook workbook = exportExcel(excelSheet, sheetName);
        if(workbook != null){
            workbook.write(outputStream);
            outputStream.close();
        }


    }


    public static int excelNum = 0;

    public static void exportExcel2local(ExcelSheet excelSheet, String sheetName, String out) throws Exception{

        String name = StringUtils.isEmpty(out) ? "D:\\lulu4exportWYC"+ ++excelNum  +".xls" : out;
        File file = new File(name);
        OutputStream outputStream = new FileOutputStream(file);
        HSSFWorkbook workbook = exportExcel(excelSheet, sheetName);
        if(workbook != null){
            workbook.write(outputStream);
            outputStream.close();
        }


    }




    // 导出excel to 嗯嗯
    // for y export
    // 这是个导出
    public static HSSFWorkbook exportExcel(ExcelSheet excelSheet, String sheetName) throws Exception{

//        i++;
//
//        String name = "D:\\lulu4export"+ i  +".xls";
        //指定数据存放的位置
//        OutputStream outputStream = new FileOutputStream(out);
        //1.创建一个工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();



        //2.创建一个工作表sheet
        HSSFSheet sheet = workbook.createSheet(StringUtils.isEmpty(sheetName)?"sheet01":sheetName);

        ExcelLuluTile excelLuluTile = excelSheet.getTitle();
        List<String> titles = excelLuluTile.getTitles();

        HSSFRow titleRow = sheet.createRow(0);
        for(int i = 0;i<titles.size();i++){
            String title = titles.get(i);
            titleRow.createCell(i).setCellValue(title);
        }

        List<ExcelLuluData> dataList = excelSheet.getData();
        for(int j = 0;j< dataList.size();j++){
            ExcelLuluData excelLuluData = dataList.get(j);
            List<Double> rowDatas = excelLuluData.getParams();

            //行
            HSSFRow row = sheet.createRow(j + 1);
            for(int k = 0; k< rowDatas.size(); k++){
                if(rowDatas == null){
                    System.err.println("err -->  这一行都是 null");
                    continue;
                }
                Double d = rowDatas.get(k) == null ? -9999999999.0 : rowDatas.get(k);

                if(row == null || row.createCell(k) == null){
                    System.err.println("err  222 -->  这一行都是 null");
                }else{ ;
                    row.createCell(k).setCellValue(d);
                }

            }

        }


        return workbook;

//        workbook.write(outputStream);
//        outputStream.close();
    }





    //

    static int i1, i2, i3, i4, i5, i6, i7, i8, i9, i10, i11, i12, i13, i14, i15, i16, i17, i18, i19, i20, i21, i22, i23, i24, i25, i26, i27, i28, i29, i30, i31, iy = 0;


    // 神经网络
    public static ExcelSheet importExcel(InputStream in)  throws Exception {

        XSSFWorkbook workbook = new XSSFWorkbook(in);
        return importExcel(workbook);
    }

    // 神经网络excel数据导入。
    public static ExcelSheet importExcel(String in) throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(new File(in)));
        return importExcel(workbook);
    }

    // 神经网络excel数据导入。
    public static ExcelSheet importExcel(XSSFWorkbook workbook) throws Exception {
//        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(new File(in)));
        XSSFSheet sheet = null;
        ExcelSheet excelSheet = new ExcelSheet();

        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            //获取每个sheet
            sheet = workbook.getSheetAt(i);
            List<ExcelLuluData> list = new ArrayList<>();// 具体的每一个数据
            //getPhysicalNumberOfRows获取有记录的行数
            for (int j = 0; j < sheet.getPhysicalNumberOfRows(); j++) {
                Row row = sheet.getRow(j);
                if (null != row && j == 0) {// 第一行是标题
                    ExcelLuluTile title = new ExcelLuluTile();
                    for (int z = 0; z < row.getLastCellNum(); z++) {
                        Cell cell = row.getCell(z);
                        String t = cell.getStringCellValue();
                        title.add(t);
                    }

                    excelSheet.setTitle(title);
                }

                if (null != row && j > 0) {
                    //getLastCellNum获取最后一列
//                    User user = new User();
                    ExcelLuluData rowData = new ExcelLuluData();
                    for (int k = 0; k < row.getLastCellNum(); k++) {
                        Cell cell = row.getCell(k);
                        Double d = cell == null ? null : cell.getNumericCellValue();
                        rowData.add(d);
                    }

                    list.add(rowData);
                }
            }

            excelSheet.setData(list);
            System.out.println("读取sheet表：" + workbook.getSheetName(i) + "完成");
            return excelSheet;
        }

        return null;
    }


    // 神经网络的导出  txt 文件
    public static void exportParams2txt(ExcelSheet excelSheet, String fileName, HttpServletResponse response) throws Exception {


        // 导出 txt
        response.setCharacterEncoding("utf-8");
        //设置响应的内容类型
        response.setContentType("text/plain");
        //设置文件的名称和格式
        response.addHeader("Content-Disposition","attachment;filename=" + fileName + ".txt");


//        BufferedOutputStream buff = new BufferedOutputStream(outStr);

//            outStr = response.getOutputStream();
//            buff = new BufferedOutputStream(outStr);

//        buff.write(text.getBytes("UTF-8"));
//        buff.flush();
//        buff.close();

        try (ServletOutputStream outStr = response.getOutputStream();){
            exportParams(excelSheet, outStr);
        }

    }


    // 神经网络的导出
    public static void exportParams2local(ExcelSheet excelSheet, String out) throws Exception {

        try (FileOutputStream outSTr = new FileOutputStream(new File(out));){
            exportParams(excelSheet, outSTr);
        }

    }






    // 神经网络的导出
    public static void exportParams(ExcelSheet excelSheet, OutputStream outputStream) throws Exception {

//        String path = out;
//        String enter = "\r\n";
//        List<ExcelLuluData> dataList = excelSheet.getData();
//        StringBuffer write;
//        try (FileOutputStream outSTr = new FileOutputStream(new File(path));
//             BufferedOutputStream Buff = new BufferedOutputStream(outSTr);) {
//
//
//        }

//        String path = out;
        String enter = "\r\n";
        List<ExcelLuluData> dataList = excelSheet.getData();
        StringBuffer write;
        try (
             BufferedOutputStream Buff = new BufferedOutputStream(outputStream);) {
            String outX1 = "x1 =[";
            String outX2 = "x2 =[";
            String outX3 = "x3 =[";
            String outX4 = "x4 =[";
            String outX5 = "x5 =[";
            String outX6 = "x6 =[";
            String outX7 = "x7 =[";
            String outX8 = "x8 =[";
            String outX9 = "x9 =[";
            String outX10 = "x10 =[";
            String outX11 = "x11 =[";
            String outX12 = "x12 =[";
            String outX13 = "x13 =[";
            String outX14 = "x14 =[";
            String outX15 = "x15 =[";
            String outX16 = "x16 =[";
            String outX17 = "x17 =[";
            String outX18 = "x18 =[";
            String outX19 = "x19 =[";
            String outX20 = "x20 =[";
            String outX21 = "x21 =[";
            String outX22 = "x22 =[";
            String outX23 = "x23 =[";
            String outX24 = "x24 =[";
            String outX25 = "x25 =[";
            String outX26 = "x26 =[";
            String outX27 = "x27 =[";
            String outX28 = "x28 =[";
            String outX29 = "x29 =[";
            String outX30 = "x30 =[";
            String outX31 = "x31 =[";
            String y = "y =[";

            i1 = 0;
            i2 = 0;
            i3 = 0;
            i4 = 0;
            i5 = 0;
            i6 = 0;
            i7 = 0;
            i8 = 0;
            i9 = 0;
            i10 = 0;
            i11 = 0;
            i12 = 0;
            i13 = 0;
            i14 = 0;
            i15 = 0;
            i16 = 0;
            i17 = 0;
            i18 = 0;
            i19 = 0;
            i20 = 0;
            i21 = 0;
            i22 = 0;
            i23 = 0;
            i24 = 0;
            i25 = 0;
            i26 = 0;
            i27 = 0;
            i28 = 0;
            i29 = 0;
            i30 = 0;
            i31 = 0;
            iy = 0;


            for (int m = 0; m < dataList.size(); m++) {
                ExcelLuluData excelLuluData = dataList.get(m);
                List<Double> rowDatas = excelLuluData.getParams();

                int nSize = rowDatas.size();// 33 应该把第一列去掉
                if (nSize != 33) {
                    System.out.println("-------------------- nSize != 32       m : " + m);
                    continue;
                }

                for (int nn = 0; nn < nSize; nn++) {
                    Double one = rowDatas.get(nn);
                    int n = nn - 1;
                    if (n == 0) {
                        outX1 += one.toString();
                        outX1 += ",";
                        i1++;
                    } else if (n == 1) {
                        outX2 += one.toString();
                        outX2 += ",";
                        i2++;
                    } else if (n == 2) {
                        outX3 += one.toString();
                        outX3 += ",";
                        i3++;
                    } else if (n == 3) {
                        outX4 += one.toString();
                        outX4 += ",";
                        i4++;
                    } else if (n == 4) {
                        outX5 += one.toString();
                        outX5 += ",";
                        i5++;
                    } else if (n == 5) {
                        outX6 += one.toString();
                        outX6 += ",";
                        i6++;
                    } else if (n == 6) {
                        outX7 += one.toString();
                        outX7 += ",";
                        i7++;
                    } else if (n == 7) {
                        outX8 += one.toString();
                        outX8 += ",";
                        i8++;
                    } else if (n == 8) {
                        outX9 += one.toString();
                        outX9 += ",";
                        i9++;
                    } else if (n == 9) {
                        outX10 += one.toString();
                        outX10 += ",";
                        i10++;
                    } else if (n == 10) {
                        outX11 += one.toString();
                        outX11 += ",";
                        i11++;
                    } else if (n == 11) {
                        outX12 += one.toString();
                        outX12 += ",";
                        i12++;
                    } else if (n == 12) {
                        outX13 += one.toString();
                        outX13 += ",";
                        i13++;
                    } else if (n == 13) {
                        outX14 += one.toString();
                        outX14 += ",";
                        i14++;
                    } else if (n == 14) {
                        outX15 += one.toString();
                        outX15 += ",";
                        i15++;
                    } else if (n == 15) {
                        outX16 += one.toString();
                        outX16 += ",";
                        i16++;
                    } else if (n == 16) {
                        outX17 += one.toString();
                        outX17 += ",";
                        i17++;
                    } else if (n == 17) {
                        outX18 += one.toString();
                        outX18 += ",";
                        i18++;
                    } else if (n == 18) {
                        outX19 += one.toString();
                        outX19 += ",";
                        i19++;
                    } else if (n == 19) {
                        outX20 += one.toString();
                        outX20 += ",";
                        i20++;
                    } else if (n == 20) {
                        outX21 += one.toString();
                        outX21 += ",";
                        i21++;
                    } else if (n == 21) {
                        outX22 += one.toString();
                        outX22 += ",";
                        i22++;
                    } else if (n == 22) {
                        outX23 += one.toString();
                        outX23 += ",";
                        i23++;
                    } else if (n == 23) {
                        outX24 += one.toString();
                        outX24 += ",";
                        i24++;
                    } else if (n == 24) {
                        outX25 += one.toString();
                        outX25 += ",";
                        i25++;
                    } else if (n == 25) {
                        outX26 += one.toString();
                        outX26 += ",";
                        i26++;
                    } else if (n == 26) {
                        outX27 += one.toString();
                        outX27 += ",";
                        i27++;
                    } else if (n == 27) {
                        outX28 += one.toString();
                        outX28 += ",";
                        i28++;
                    } else if (n == 28) {
                        outX29 += one.toString();
                        outX29 += ",";
                        i29++;
                    } else if (n == 29) {
                        outX30 += one.toString();
                        outX30 += ",";
                        i30++;
                    } else if (n == 30) {
                        outX31 += one.toString();
                        outX31 += ",";
                        i31++;
                    } else if (n == 31) {
                        y += one.toString();
                        y += ",";
                        iy++;
                    }
                }
            }

            System.out.println(i1 + " " + i2 + " " + i3 + " " + i4 + " " + i5 + " " + i6 + " " + i7 + " " + i8 + " " + i9 + " " + i10);
            System.out.println(i11 + " " + i1 + " " + i1 + " " + i14 + " " + i15 + " " + i16 + " " + i17 + " " + i18 + " " + i19 + " " + i20);
            System.out.println(i21 + " " + i22 + " " + i23 + " " + i24 + " " + i25 + " " + i26 + " " + i27 + " " + i28 + " " + i2 + " " + i30 + " " + i31 + " " + iy);

            outX1 = outX1.substring(0, outX1.length() - 1);
            outX2 = outX2.substring(0, outX2.length() - 1);
            outX3 = outX3.substring(0, outX3.length() - 1);
            outX4 = outX4.substring(0, outX4.length() - 1);
            outX5 = outX5.substring(0, outX5.length() - 1);
            outX6 = outX6.substring(0, outX6.length() - 1);
            outX7 = outX7.substring(0, outX7.length() - 1);
            outX8 = outX8.substring(0, outX8.length() - 1);
            outX9 = outX9.substring(0, outX9.length() - 1);
            outX10 = outX10.substring(0, outX10.length() - 1);
            outX11 = outX11.substring(0, outX11.length() - 1);
            outX12 = outX12.substring(0, outX12.length() - 1);
            outX13 = outX13.substring(0, outX13.length() - 1);
            outX14 = outX14.substring(0, outX14.length() - 1);
            outX15 = outX15.substring(0, outX15.length() - 1);
            outX16 = outX16.substring(0, outX16.length() - 1);
            outX17 = outX17.substring(0, outX17.length() - 1);
            outX18 = outX18.substring(0, outX18.length() - 1);
            outX19 = outX19.substring(0, outX19.length() - 1);
            outX20 = outX20.substring(0, outX20.length() - 1);
            outX21 = outX21.substring(0, outX21.length() - 1);
            outX22 = outX22.substring(0, outX22.length() - 1);
            outX23 = outX23.substring(0, outX23.length() - 1);
            outX24 = outX24.substring(0, outX24.length() - 1);
            outX25 = outX25.substring(0, outX25.length() - 1);
            outX26 = outX26.substring(0, outX26.length() - 1);
            outX27 = outX27.substring(0, outX27.length() - 1);
            outX28 = outX28.substring(0, outX28.length() - 1);
            outX29 = outX29.substring(0, outX29.length() - 1);
            outX30 = outX30.substring(0, outX30.length() - 1);
            outX31 = outX31.substring(0, outX31.length() - 1);
            y = y.substring(0, y.length() - 1);

            outX1 += "];";
            outX2 += "];";
            outX3 += "];";
            outX4 += "];";
            outX5 += "];";
            outX6 += "];";
            outX7 += "];";
            outX8 += "];";
            outX9 += "];";
            outX10 += "];";
            outX11 += "];";
            outX12 += "];";
            outX13 += "];";
            outX14 += "];";
            outX15 += "];";
            outX16 += "];";
            outX17 += "];";
            outX18 += "];";
            outX19 += "];";
            outX20 += "];";
            outX21 += "];";
            outX22 += "];";
            outX23 += "];";
            outX24 += "];";
            outX25 += "];";
            outX26 += "];";
            outX27 += "];";
            outX28 += "];";
            outX29 += "];";
            outX30 += "];";
            outX31 += "];";
            y += "];";
            write = new StringBuffer();
            write.append(outX1 + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter);
            write.append(outX2 + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter);
            write.append(outX3 + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter);
            write.append(outX4 + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter);
            write.append(outX5 + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter);
            write.append(outX6 + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter);
            write.append(outX7 + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter);
            write.append(outX8 + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter);
            write.append(outX9 + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter);
            write.append(outX10 + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter);
            write.append(outX11 + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter);
            write.append(outX12 + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter);
            write.append(outX13 + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter);
            write.append(outX14 + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter);
            write.append(outX15 + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter);
            write.append(outX16 + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter);
            write.append(outX17 + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter);
            write.append(outX18 + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter);
            write.append(outX19 + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter);
            write.append(outX20 + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter);
            write.append(outX21 + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter);
            write.append(outX22 + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter);
            write.append(outX23 + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter);
            write.append(outX24 + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter);
            write.append(outX25 + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter);
            write.append(outX26 + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter);
            write.append(outX27 + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter);
            write.append(outX28 + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter);
            write.append(outX29 + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter);
            write.append(outX30 + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter);
            write.append(outX31 + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter);
            write.append(y + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter + enter);
            Buff.write(write.toString().getBytes("UTF-8"));

            Buff.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}





