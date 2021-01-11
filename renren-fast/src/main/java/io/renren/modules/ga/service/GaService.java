package io.renren.modules.ga.service;

import io.renren.modules.ga.entity.GaEntity;
import io.renren.modules.ga.entity.Page;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2021-01-04 22:55:43
 */
public interface GaService {

    Page<GaEntity> queryPage(Map<String, Object> params);
    GaEntity getGaEntity(String id);



    Boolean save(GaEntity gaEntity, StringBuilder chkMsg);


    Boolean batchSave(List<GaEntity> gaEntityList, StringBuilder chkMsg);

    Boolean update(GaEntity gaEntity, StringBuilder chkMsg);


    Boolean batchDelete(List<Long> ids, StringBuilder chkMsg);



}

