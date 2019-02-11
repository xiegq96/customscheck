package cn.bobo.fast.modules.sys.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import cn.bobo.fast.common.utils.PageUtils;
import cn.bobo.fast.common.utils.Query;

import cn.bobo.fast.modules.sys.dao.DriveMonitorDao;
import cn.bobo.fast.modules.sys.entity.DriveMonitorEntity;
import cn.bobo.fast.modules.sys.service.DriveMonitorService;


@Service("driveMonitorService")
public class DriveMonitorServiceImpl extends ServiceImpl<DriveMonitorDao, DriveMonitorEntity> implements DriveMonitorService {
    @Autowired
    DriveMonitorDao driveMonitorDao;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        params.put("sidx", "entry_time");
        params.put("order", "DESC");
        Page<DriveMonitorEntity> page = this.selectPage(
                new Query<DriveMonitorEntity>(params).getPage(),
                new EntityWrapper<DriveMonitorEntity>()
        );
        return new PageUtils(page);
    }

    /**
     * 根据进出区标志生成对应的列表
     * @param params
     * @return
     */
   public  PageUtils queryIoFlagPage(Map<String, Object> params){
       String ioFlag = (String)params.get("ioFlag");
       params.put("sidx", "entry_time");
       params.put("order", "DESC");
       Page<DriveMonitorEntity> page = this.selectPage(
               new Query<DriveMonitorEntity>(params).getPage(),
               new EntityWrapper<DriveMonitorEntity>()
                       .like(StringUtils.isNotBlank(ioFlag),"io_flag", ioFlag)
       );
       return new PageUtils(page);
   }
}
