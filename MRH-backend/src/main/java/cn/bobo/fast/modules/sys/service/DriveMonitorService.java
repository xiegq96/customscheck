package cn.bobo.fast.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import cn.bobo.fast.common.utils.PageUtils;
import cn.bobo.fast.modules.sys.entity.DriveMonitorEntity;

import java.util.List;
import java.util.Map;

/**
 * 卡口监控表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-05-09 12:00:07
 */
public interface DriveMonitorService extends IService<DriveMonitorEntity> {

    PageUtils queryPage(Map<String, Object> params);
    PageUtils queryIoFlagPage(Map<String, Object> params);
}

