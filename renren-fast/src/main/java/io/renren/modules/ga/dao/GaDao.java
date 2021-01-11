package io.renren.modules.ga.dao;

import io.renren.modules.ga.entity.GaEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 
 * extends BaseMapper<GaTaskEntity>
 *
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2021-01-04 22:55:43
 */
@Mapper
public interface GaDao  {


    /**
     * ga任务分页查询
     *
     * @param params
     * @return
     */
    List<GaEntity> listForPage(Map<String,Object> params);
    int countTotal(Map<String,Object> params);


    /**
     * 1. 根据条件查询 GaEntity
     * 2. 应该在加上一个name检查的
     */
    GaEntity getGaEntity(String id);

    Integer save(GaEntity gaEntity);

    Integer batchSave(List<GaEntity> gaEntityList);

    Integer update(GaEntity gaEntity);


    /**
     * 批量删除 ga
     */
    Integer batchDelete(List<Long> ids);



//    /**
//     * 查询 ga
//     *
//     * @param params
//     * @return
//     */
//    GaTaskEntity getGaTaskEntity(Map<String,Object> params);
//
//    Integer save(GaTaskEntity gaTaskEntity);
//
//    Integer update(GaTaskEntity gaTaskEntity);
//
//



}
