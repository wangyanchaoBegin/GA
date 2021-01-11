package io.renren.modules.ga.controller;

import io.renren.modules.ga.bean.util.ExcelSheet;
import io.renren.modules.ga.entity.GaEntity;
import io.renren.modules.ga.entity.Page;
import io.renren.modules.ga.entity.RestResult;
import io.renren.modules.ga.service.GaService;
import io.renren.modules.ga.util.POIUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;


/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2021-01-04 22:55:43
 */
@RestController
@RequestMapping("lulu/ga")
public class GaController {

    @Autowired
    private GaService gaService;

    /**
     * 列表   http://localhost:8080/renren-fast/lulu/ga/page
     *
     * page: 1
     * limit: 20
     *
     */
    @RequestMapping("/page")
    public RestResult<Page<GaEntity>> page(@RequestParam Map<String, Object> params){
//        PageUtils page = gaTaskService.queryPage(params);

        RestResult<Page<GaEntity>> res = new RestResult<>();
        Page<GaEntity> page = gaService.queryPage(params);
        res.setData(page);
        return res;
    }

    /**
     *    http://localhost:8080/renren-fast/lulu/ga/getGaEntity?id=17
     */
    @RequestMapping("/getGaEntity")
    public RestResult<GaEntity> getGaEntity(@RequestParam(name="id",required = true) String id){
//        PageUtils page = gaTaskService.queryPage(params); getGaEntity

        RestResult<GaEntity> res = new RestResult<>();
        GaEntity gaEntity = gaService.getGaEntity(id);
        res.setData(gaEntity);
        return res;
    }


    /**
     * 保存
     *
     * http://localhost:8080/renren-fast/lulu/ga/save
     *  {
     *     "name": "task100",
     *     "params": "1",
     *     "state": null,
     *     "info": "77",
     *     "createUser": "xiaolu"
     * }
     */
    @RequestMapping("/save")
    public RestResult save(@RequestBody GaEntity gaEntity){

        RestResult<Page<GaEntity>> res = new RestResult<>();

        StringBuilder chkMsg = new StringBuilder();
        if(!gaService.save(gaEntity, chkMsg)){
            res.setSuccess(false);
            res.setMsg(chkMsg.toString());
        }

        return res;
    }

    // TODO 修改


    /**
     *  POST
     *  http://localhost:8080/renren-fast/lulu/ga/batchSave
     *  [
     *  {
     *     "name": "task200",
     *     "params": "1",
     *     "state": null,
     *     "info": "77",
     *     "createUser": "xiaolu"
     * }, {
     *     "name": "task200",
     *     "params": "1",
     *     "state": null,
     *     "info": "77",
     *     "createUser": "xiaolu"
     * }]
     *
     *
     * @param gaEntityList
     * @return
     */
    @RequestMapping("/batchSave")
    public RestResult batchSave(@RequestBody List<GaEntity> gaEntityList){

        RestResult<Page<GaEntity>> res = new RestResult<>();

        StringBuilder chkMsg = new StringBuilder();
        if(!gaService.batchSave(gaEntityList, chkMsg)){
            res.setSuccess(false);
            res.setMsg(chkMsg.toString());
        }

        return res;
    }


    /**
     * http://localhost:8080/renren-fast/lulu/ga/update
     *
     *  {
     *     "id": 48,
     *     "name": "task100 update",
     *     "params": "1 update",
     *     "state": "state update",
     *     "info": "77 update",
     *     "createUser": "xiaolu"
     * }
     *
     * @param gaEntity
     * @return
     */
    @RequestMapping("/update")
    public RestResult update(@RequestBody GaEntity gaEntity){

        RestResult<Page<GaEntity>> res = new RestResult<>();

        StringBuilder chkMsg = new StringBuilder();
        if(!gaService.update(gaEntity, chkMsg)){
            res.setSuccess(false);
            res.setMsg(chkMsg.toString());
        }

        return res;
    }


//    Boolean batchDelete(List<Long> ids, StringBuilder chkMsg) {

    /**
     * POST
     * http://localhost:8080/renren-fast/lulu/ga/batchDelete
     * Content-Type  application/json
     * RAW   [49,50,51,52,53,54,55,56,57,58]
     *
     * @param ids
     * @return
     */
    @RequestMapping("/batchDelete")
    public RestResult batchDelete(@RequestBody List<Long> ids){

        RestResult<Page<GaEntity>> res = new RestResult<>();

        StringBuilder chkMsg = new StringBuilder();
        if(!gaService.batchDelete(ids, chkMsg)){
            res.setSuccess(false);
            res.setMsg(chkMsg.toString());
        }

        return res;
    }


    static int COUNT_Y = 0;

    /**
     * 计算 y
     * 1. 读入
     * 2. 计算y
     * 3. 导出  "D:\\export4y"+ i  +".xls";
     *
     * 之后咋 matlab中运行
     * >> Hello6
     * hello 6
     *
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping("/y4lulu")
    public Object y4lulu(@RequestParam(required = false, name = "in") String in,
                         @RequestParam(required = false, name = "out") String out) throws Exception {
        ++COUNT_Y;

//        xlsx
        String inTmp = StringUtils.isEmpty(in) ? "D:\\net-y\\in4y.xls": in;
        String outTmp = StringUtils.isEmpty(out) ? "D:\\net-y\\out4y" + COUNT_Y +".xls": out;
        String outTxt = StringUtils.isEmpty(out) ? "D:\\net-y\\out4y" + COUNT_Y +".txt": out;

        ExcelSheet res = POIUtil.importExcel(inTmp);
        res = res.handler4netY();
        POIUtil.exportExcel2local(res, "sheetY",outTmp);// exel  先不输出excel了
//        outTxt
        POIUtil.exportParams2local(res, outTxt);// txt 输出
        return "in 4 鲁鲁 success   love        yyyyyy" ;
    }


    /**
     * postman 测试方式：
     * 1. http://localhost:8080/renren-fast/lulu/ga/import
     * 2. post
     * 3. body --》 form-data --> file 参数后面选择 file（默认是 text） 在value里面选择具体文件。
     *
     * @param file
     * @return
     */
    @RequestMapping(value = "/import")
    public void dataImportAndOut(@RequestParam("file") MultipartFile file,
                             @RequestParam("fileName") String fileName,
                             HttpServletResponse response) {
        try {
            InputStream in = file.getInputStream();
            ExcelSheet res = POIUtil.importExcel(in);
            res = res.handler4netY();
            // POIUtil.exportExcel(res, outTmp, "sheetY");// exel  先不输出excel了
            String outTxt =  "D:\\net-y\\out4y" + COUNT_Y +".txt";
//            POIUtil.exportParams(res, outTxt);// txt 输出


            POIUtil.exportParams2txt(res , fileName, response);

//            // 导出 txt
//            response.setCharacterEncoding("utf-8");
//            //设置响应的内容类型
//            response.setContentType("text/plain");
//            //设置文件的名称和格式
//            response.addHeader("Content-Disposition","attachment;filename=" + fileName + ".txt");
//
//            ServletOutputStream outStr = response.getOutputStream();
//            BufferedOutputStream buff = new BufferedOutputStream(outStr);
//
////            outStr = response.getOutputStream();
////            buff = new BufferedOutputStream(outStr);
//
//            buff.write(text.getBytes("UTF-8"));
//            buff.flush();
//            buff.close();

//            return "ok 5555";
        } catch (Exception e) {
//            return "err";
        }
    }





    @RequestMapping(value = "/importAndOut")
    public void dataImportAndOutExcel(@RequestParam("file") MultipartFile file,
                                 @RequestParam("fileName") String fileName,
                                 HttpServletResponse response) {
        try {
            InputStream in = file.getInputStream();
            ExcelSheet res = POIUtil.importExcel(in);
            res = res.handler4netY();

            POIUtil.exportExcel2http(res, "sheet_name_1",  fileName,  response);

//            // 本地
//            String outTxt =  "D:\\net-y\\out4y" + COUNT_Y +".txt";
//            POIUtil.exportParams2txt(res , fileName, response);


        } catch (Exception e) {

        }
    }


    @RequestMapping(value = "/importAndOut2local")
    public void importAndOut2local(@RequestParam("file") MultipartFile file,
                                      @RequestParam("fileName") String fileName,
                                      HttpServletResponse response) {
        try {
            InputStream in = file.getInputStream();
            ExcelSheet res = POIUtil.importExcel(in);
            res = res.handler4netY();

            POIUtil.exportExcel2http(res, "sheet_name_1",  fileName,  response);

//            // 本地
//            String outTxt =  "D:\\net-y\\out4y" + COUNT_Y +".txt";
//            POIUtil.exportParams2txt(res , fileName, response);


        } catch (Exception e) {

        }
    }



}
