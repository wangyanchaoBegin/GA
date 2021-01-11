package io.renren.modules.generator.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.generator.entity.GaTaskEntity;

import java.util.Map;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2021-01-04 22:55:43
 */
public interface GaTaskService extends IService<GaTaskEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

