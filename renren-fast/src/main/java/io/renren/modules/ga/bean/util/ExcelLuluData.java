package io.renren.modules.ga.bean.util;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ExcelLuluData {

    List<Double> params = new ArrayList<>();

    public List<Double> add(Double param) {
        this.params.add(param);
        return this.params;
    }

    Double param1;
    Double param2;
    Double param3;
    Double param4;
    Double param5;
    Double param6;
    Double param7;
    Double param8;
    Double param9;
    Double param10;
    Double param11;
    Double param12;
    // b变为 31 个
    Double shang;

    public List<Double> getDatas() {
        return this.params;
    }
}
