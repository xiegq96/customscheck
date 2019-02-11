package cn.bobo.fast.modules.sys.dao;

import cn.bobo.fast.modules.sys.entity.DriveMonitorEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * 卡口监控表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-05-09 12:00:07
 */
public interface DriveMonitorDao extends BaseMapper<DriveMonitorEntity> {
    List<DriveMonitorEntity> getEntryRecordList();
}
