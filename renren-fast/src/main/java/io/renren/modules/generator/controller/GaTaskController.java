package io.renren.modules.generator.controller;

import java.util.Arrays;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.generator.entity.GaTaskEntity;
import io.renren.modules.generator.service.GaTaskService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2021-01-04 22:55:43
 */
@RestController
@RequestMapping("generator/gatask")
public class GaTaskController {
    @Autowired
    private GaTaskService gaTaskService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("generator:gatask:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = gaTaskService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("generator:gatask:info")
    public R info(@PathVariable("id") Long id){
		GaTaskEntity gaTask = gaTaskService.getById(id);

        return R.ok().put("gaTask", gaTask);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("generator:gatask:save")
    public R save(@RequestBody GaTaskEntity gaTask){
		gaTaskService.save(gaTask);

        return R.ok();
    }


    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("generator:gatask:update")
    public R update(@RequestBody GaTaskEntity gaTask){
		gaTaskService.updateById(gaTask);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("generator:gatask:delete")
    public R delete(@RequestBody Long[] ids){
		gaTaskService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
