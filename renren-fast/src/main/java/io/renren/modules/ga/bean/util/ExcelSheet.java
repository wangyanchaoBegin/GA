package io.renren.modules.ga.bean.util;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ExcelSheet {
    ExcelLuluTile title;
    List<ExcelLuluData> data;

    public ExcelSheet() {
//        title = new ExcelLuluTile();
//        data = new ExcelLuluData();
    }

    public void log() {
        if (title == null) {
            System.out.println("title is null.");
        }
        if (data == null) {
            System.out.println("data is null.");
        }

        List<String> titles = title.getTitles();
        System.out.println("title :    size-->" + titles.size());
        String titleTmp = "";
        for (String title : titles) {
            titleTmp += title;
            titleTmp += " ";
        }
        System.out.println(titleTmp);
        System.out.println("data :              size-->" + data.size());
        for (ExcelLuluData d : data) {
            String dataTmp = "";
            List<Double> rowDatas = d.getDatas();
            for (Double rowData : rowDatas) {
                dataTmp += rowData == null ? "null" : rowData.toString();
                dataTmp += " ";

            }

            System.out.println(dataTmp);
        }
    }


    // 把这个excel对象 处理成的   计算完成的   熵
    public ExcelSheet handler() {
        if (title == null) {
            System.out.println("title is null.");
            return null;
        }
        if (data == null) {
            System.out.println("data is null.");
            return null;
        }


        ExcelSheet excelSheetSave = new ExcelSheet();

        ExcelLuluTile excelLuluTileSave = new ExcelLuluTile();
        excelLuluTileSave.setTitles(title.getTitles());
        excelSheetSave.setTitle(excelLuluTileSave);

        List<ExcelLuluData> dataSaveList = new ArrayList<>();
        for (ExcelLuluData d : data) {
            List<Double> rowDatas = d.getDatas();
            List<Double> rowDatasSave = new ArrayList<>();
            double shang = 0;
            double total = 0;
            for (int m = 0; m < rowDatas.size(); m++) {
                if (1 <= m && m <= 11) {
                    if (rowDatas.get(m) != null) {
                        total += rowDatas.get(m);
                    }
                }
            }

            for (int i = 0; i < rowDatas.size(); i++) {
                if (total != 0) {
                    if (1 <= i && i <= 11) {
                        if (rowDatas.get(i) != null) {
                            if (rowDatas.get(i) > 0) {
                                double p = rowDatas.get(i) / total;
                                double s = p * Math.log(p);
                                shang -= s;
                            }
                        }
                    }
                }

                if (i == 14) {
                    if (total == 0) {
                        rowDatasSave.add(null);
                    } else {
                        rowDatasSave.add(shang);
                    }

                    shang = 0;
                    total = 0;
                } else {
                    rowDatasSave.add(rowDatas.get(i));
                }
            }

            ExcelLuluData excelLuluDataSave = new ExcelLuluData();
            excelLuluDataSave.setParams(rowDatasSave);
            dataSaveList.add(excelLuluDataSave);
        }

        excelSheetSave.setData(dataSaveList);
        return excelSheetSave;
    }


    //------------------------------------------
    // 把这个excel对象 处理成的  计算神经网络的 y
    public ExcelSheet handler4netY() {
        if (title == null) {
            System.out.println("title is null.");
            return null;
        }
        if (data == null) {
            System.out.println("data is null.");
            return null;
        }

        ExcelSheet excelSheetSave = new ExcelSheet();
        ExcelLuluTile excelLuluTileSave = new ExcelLuluTile();
        excelLuluTileSave.setTitles(title.getTitles());
        excelSheetSave.setTitle(excelLuluTileSave);
        List<ExcelLuluData> dataSaveList = new ArrayList<>();
        for (ExcelLuluData d : data) {
            // 原始数据
            List<Double> rowDatas = d.getDatas();
            // 用来保存的
//            List<Double> rowDatasSave = new ArrayList<>();
            double y = countNetY(rowDatas);
            // TODO 加上 y 的计算
            rowDatas.add(y);
            ExcelLuluData excelLuluDataSave = new ExcelLuluData();
            excelLuluDataSave.setParams(rowDatas);
            dataSaveList.add(excelLuluDataSave);
        }

        excelSheetSave.setData(dataSaveList);
        return excelSheetSave;
    }

    private int countNetY(List<Double> rowDatas) {
        // 31 个参数
        double[] w = new double[]{0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0,
                1.1, 1.2, 1.3, 1.4, 1.5, 1.6, 1.7, 1.8, 1.9, 2.0,
                2.1, 2.2, 2.3, 2.4, 2.5, 2.6, 2.7, 2.8, 2.9, 3.0,
                3.1};

        int res = 0;
        for (int i = 0; i < rowDatas.size(); i++) {
            // 这个列数 应该是 33
            if (rowDatas.size() != 33) {// TODO 目前这里报了错，是 32
//                System.out.println("--------------- rowDatas.size() err : " + rowDatas.size());
            }

            if (1 <= i && i <= 31) {// 这个是取值范围的
                if (rowDatas.get(i) != null) {
                    res += rowDatas.get(i) * w[i - 1];
                }
            }
        }

        return res;
    }
}
