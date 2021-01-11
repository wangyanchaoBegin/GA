package io.renren.modules.ga.bean.util;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ExcelLuluTile {

    List<String> titles = new ArrayList<>();
    String param1;
    String param2;
    String param3;
    String param4;
    String param5;
    String param6;
    String param7;
    String param8;
    String param9;
    String param10;
    String param11;
    String param12;

    public List<String> add(String title) {
        this.titles.add(title);
        return this.titles;
    }
}
