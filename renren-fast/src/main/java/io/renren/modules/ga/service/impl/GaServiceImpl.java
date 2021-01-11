package io.renren.modules.ga.service.impl;

import io.renren.modules.ga.dao.GaDao;
import io.renren.modules.ga.entity.GaEntity;
import io.renren.modules.ga.entity.Page;
import io.renren.modules.ga.service.GaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;


//@Service("gaTaskService")
@Service
public class GaServiceImpl implements GaService {

//    @Mapper

//    @Autowired

    @Resource
    private GaDao gaDao;


    @Override
    public Page<GaEntity> queryPage(Map<String, Object> params) {
        params.put("allFlag",true);
        int count = gaDao.countTotal(params);
        List<GaEntity> list = count > 0 ? gaDao.listForPage(params) : null;
        Page<GaEntity> page = new Page<>(list , count);
        return page;
    }

    @Override
    public GaEntity getGaEntity(String id) {

        return gaDao.getGaEntity(id);
    }

    @Transactional
    @Override
    public Boolean save(GaEntity gaEntity, StringBuilder chkMsg) {
        Map<String,Object> params = new HashMap();
        params.put("sameName",gaEntity.getName());
        int count = gaDao.countTotal(params);
        if(count > 0){
            chkMsg.append("name repeat.");
            return false;
        }

        gaEntity.setCreateTime(new Date());
        Integer res = gaDao.save( gaEntity);
        return res > 0;
    }


    @Transactional
    @Override
    public Boolean batchSave(List<GaEntity> gaEntityList, StringBuilder chkMsg) {
        if(gaEntityList == null || gaEntityList.isEmpty()){
            // 批量插入里面的 list 不能有null， 不然会报错。
            return true;
        }

        int limit = 5;//分批处理，每次处理 5 个
        List<GaEntity> newList = new ArrayList<>();
        for(int i=0; i < gaEntityList.size(); i++){
            newList.add(gaEntityList.get(i));
            if(limit == newList.size() || i == gaEntityList.size()-1){
                // 处理业务newList
                Integer res = gaDao.batchSave( newList);
                System.out.println("out res : " + res);
                //清空newList，处理下一批
                newList.clear();
            }
        }

        return true;
    }

    @Override
    public Boolean update(GaEntity gaEntity, StringBuilder chkMsg) {
        Map<String,Object> params = new HashMap();
        params.put("sameName",gaEntity.getName());
        int count = gaDao.countTotal(params);
        if(count > 0){
            chkMsg.append("name repeat.");
            return false;
        }

        gaEntity.setUpdateTime(new Date());
        Integer res = gaDao.update( gaEntity);
        return res > 0;
    }

    @Override
    public Boolean batchDelete(List<Long> ids, StringBuilder chkMsg) {

        int res = gaDao.batchDelete(ids);

        return true;
    }


//    @Override
//    public PageUtils queryPage(Map<String, Object> params) {
//        IPage<GaTaskEntity> page = this.page(
//                new Query<GaTaskEntity>().getPage(params),
//                new QueryWrapper<GaTaskEntity>()
//        );
//
//        return new PageUtils(page);
//    }

    // TODO excel 的实现在这里




}