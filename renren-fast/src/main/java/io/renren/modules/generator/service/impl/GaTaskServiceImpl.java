package io.renren.modules.generator.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.generator.dao.GaTaskDao;
import io.renren.modules.generator.entity.GaTaskEntity;
import io.renren.modules.generator.service.GaTaskService;


@Service("gaTaskService")
public class GaTaskServiceImpl extends ServiceImpl<GaTaskDao, GaTaskEntity> implements GaTaskService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<GaTaskEntity> page = this.page(
                new Query<GaTaskEntity>().getPage(params),
                new QueryWrapper<GaTaskEntity>()
        );

        return new PageUtils(page);
    }

}