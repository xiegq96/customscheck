package cn.bobo.fast.modules.sys.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import cn.bobo.fast.common.utils.FtpUtil;
import cn.bobo.fast.common.utils.PubTool;
import cn.bobo.fast.common.validator.ValidatorUtils;
import cn.bobo.fast.modules.sys.service.SysConfigService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import cn.bobo.fast.modules.sys.entity.DriveMonitorEntity;
import cn.bobo.fast.modules.sys.service.DriveMonitorService;
import cn.bobo.fast.common.utils.PageUtils;
import cn.bobo.fast.common.utils.R;

/**
 * 卡口监控表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-05-09 12:00:07
 */
@RestController
@RequestMapping("sys/drivemonitor")
public class DriveMonitorController {
    private static Log logger = LogFactory.getLog(DriveMonitorController.class);


    @Autowired
    private DriveMonitorService driveMonitorService;

    @Autowired
    private SysConfigService sysConfigService;

    private static String ftpHost;
    private static String ftpUserName;
    private static String ftpPassword;
    private static String ftpport;
    private static String ftpuppath;
    private static String localfile;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:drivemonitor:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = driveMonitorService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:drivemonitor:info")
    public R info(@PathVariable("id") Integer id) {
        DriveMonitorEntity driveMonitor = driveMonitorService.selectById(id);

        return R.ok().put("driveMonitor", driveMonitor);
    }

    @RequestMapping("/updatestate")
    @RequiresPermissions("sys:drivemonitor:updatestate")
    public R updatestate(@RequestBody Integer[] ids) {
        String msg = "";
        for (int i = 0; i < ids.length; i++) {
            DriveMonitorEntity driveMonitor = driveMonitorService.selectById(ids[i]);
            ftpHost = sysConfigService.getValue("ftphost");
            ftpUserName = sysConfigService.getValue("ftpusername");
            ftpPassword = sysConfigService.getValue("ftppasswd");
            ftpport = sysConfigService.getValue("ftpport");
            ftpuppath = sysConfigService.getValue("ftpuppath");
            localfile = sysConfigService.getValue("localfile");
            String filename = driveMonitor.getBillNo() + ".xml";
            if (!driveMonitor.getSendstatus().equals("1")) {
                msg += "核放单编号" + driveMonitor.getBillNo() + "未上传FTP;";
                continue;
            }
            //验证FTP是否成功被接收
            Boolean isExist = null;
            try {
                isExist = FtpUtil.isFTPFileExist(ftpuppath, filename, ftpUserName,
                        ftpPassword, ftpHost, Integer.valueOf(ftpport)
                );
                if (isExist) {
                    //如果文件没被收走。说明没成功接收
                    driveMonitor.setReceivestatus("2");
                } else {
                    driveMonitor.setReceivestatus("1");
                }

                driveMonitorService.updateById(driveMonitor);
            } catch (Exception e) {
                msg += "核放单编号" + driveMonitor.getBillNo() + e.getMessage() + ";";
                continue;
            }
        }

        if (msg.length() > 5) {
            return R.error(msg);
        }
        return R.ok();
    }


    @RequestMapping("/resend")
    @RequiresPermissions("sys:drivemonitor:resend")
    public R resend(@RequestBody Integer[] ids) {
        String msg = "";
        for (int i = 0; i < ids.length; i++) {
            DriveMonitorEntity driveMonitor = driveMonitorService.selectById(ids[i]);
            ftpHost = sysConfigService.getValue("ftphost");
            ftpUserName = sysConfigService.getValue("ftpusername");
            ftpPassword = sysConfigService.getValue("ftppasswd");
            ftpport = sysConfigService.getValue("ftpport");
            ftpuppath = sysConfigService.getValue("ftpuppath");
            localfile = sysConfigService.getValue("localfile");
            String filename = driveMonitor.getBillNo() + ".xml";
            String xml = driveMonitor.toString20181010();
            System.out.println("*****************" + ftpHost);
            System.out.println("*****************" + ftpUserName);
            System.out.println("*****************" + ftpPassword);
            System.out.println("*****************" + ftpport);
            PubTool.writeFile(localfile + File.separatorChar + PubTool.sdf8.format(new Date()) + File.separatorChar + filename, xml);
            //logger.info(xml);
            //FTP发送
            try {
                InputStream xmlInputStream = new ByteArrayInputStream(xml.getBytes("UTF-8"));

                Boolean isUp = FtpUtil.uploadFile(ftpHost, ftpUserName,
                        ftpPassword, Integer.valueOf(ftpport), ftpuppath,
                        filename, xmlInputStream);

                if (isUp) {
                    driveMonitor.setSendstatus("1");
                    driveMonitor.setReceivestatus("9");
                } else {
                    driveMonitor.setSendstatus("2");
                    driveMonitorService.updateById(driveMonitor);
                    msg += "核放单编号" + driveMonitor.getBillNo() + "发送FTP失败;";
                    continue;
                }

            } catch (Exception e) {
                e.printStackTrace();
                return R.error(e.getMessage());
            }
            //验证FTP是否成功被接收
            Boolean isExist = null;
            try {
                isExist = FtpUtil.isFTPFileExist(ftpuppath, filename, ftpUserName,
                        ftpPassword, ftpHost, Integer.valueOf(ftpport)
                );
                if (isExist) {
                    //如果文件没被收走。说明没成功接收
                    driveMonitor.setReceivestatus("2");
                } else {
                    driveMonitor.setReceivestatus("1");
                }
            } catch (Exception e) {
                e.printStackTrace();
                msg += "核放单编号" + driveMonitor.getBillNo() + e.getMessage() + ";";
                continue;
            }
            driveMonitorService.updateById(driveMonitor);
        }

        if (msg.length() > 5) {
            return R.error(msg);
        }
        return R.ok();
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:drivemonitor:save")
    public R save(@RequestBody DriveMonitorEntity driveMonitor) {
        driveMonitor.setBillNo(
                PubTool.getRandomCharAndNumr(1)
                        + PubTool.sdf8.format(new Date())
                        + PubTool.getRandomCharAndNumr(2)
                        + PubTool.sdfHmsS.format(new Date())
        );
        driveMonitor.setReceivestatus("9");
        driveMonitor.setSendstatus("9");
        driveMonitor.setEntryTime(PubTool.sdf14.format(new Date()));
        driveMonitor.setNote(PubTool.sdf8.format(new Date()));
        driveMonitorService.insert(driveMonitor);
        localfile = sysConfigService.getValue("localfile");
        //组文件
        String xml = driveMonitor.toString20181010();
        String filename = driveMonitor.getBillNo() + ".xml";
        PubTool.writeFile(localfile + File.separatorChar + PubTool.sdf8.format(new Date()) + File.separatorChar + filename, xml);
        //logger.info(xml);
        //FTP发送
        try {
            InputStream xmlInputStream = new ByteArrayInputStream(xml.getBytes("UTF-8"));
            ftpHost = sysConfigService.getValue("ftphost");
            ftpUserName = sysConfigService.getValue("ftpusername");
            ftpPassword = sysConfigService.getValue("ftppasswd");
            ftpport = sysConfigService.getValue("ftpport");
            ftpuppath = sysConfigService.getValue("ftpuppath");
            Boolean isUp = FtpUtil.uploadFile(ftpHost, ftpUserName,
                    ftpPassword, Integer.valueOf(ftpport), ftpuppath,
                    filename, xmlInputStream);

            if (isUp) {
                driveMonitor.setSendstatus("1");
            } else {
                driveMonitor.setSendstatus("2");
                driveMonitorService.updateById(driveMonitor);
                return R.ok("发送FTP失败");
            }

            //验证FTP是否成功被接收
            Boolean isExist = FtpUtil.isFTPFileExist(ftpuppath, filename, ftpUserName,
                    ftpPassword, ftpHost, Integer.valueOf(ftpport)
            );
            if (isExist) {
                //如果文件没被收走。说明没成功接收
                driveMonitor.setReceivestatus("2");
            } else {
                driveMonitor.setReceivestatus("1");
            }

            driveMonitorService.updateById(driveMonitor);

        } catch (Exception e) {

            e.printStackTrace();
            return R.ok(e.getMessage());
        }

        return R.ok("发送成功");
    }


    /**
     * 保存
     */
    @RequestMapping("/savelocal")
    @RequiresPermissions("sys:drivemonitor:savelocal")
    public R savelocal(@RequestBody DriveMonitorEntity driveMonitor) {

       /* driveMonitor.setBillNo(PubTool.getRandomCharAndNumr(1)+PubTool.sdf8.format(new Date())+PubTool.getRandomCharAndNumr(2)+PubTool.sdfHmsS.format(new
                Date()));*/
        driveMonitor.setBillNo(
                PubTool.getRandomCharAndNumr(1)
                        + PubTool.sdf8.format(new Date())
                        + PubTool.getRandomCharAndNumr(2)
                        + PubTool.sdfHmsS.format(new Date())
        );
        driveMonitor.setReceivestatus("9");
        driveMonitor.setSendstatus("9");
        driveMonitor.setEntryTime(PubTool.sdf14.format(new Date()));
        driveMonitorService.insert(driveMonitor);
        localfile = sysConfigService.getValue("entrypath");
        //组文件
        String xml = driveMonitor.toString20181010();
        String filename = driveMonitor.getBillNo() + ".xml";
        //    PubTool.writeFile(localfile+ File.separatorChar+PubTool.sdf8.format(new Date())+File.separatorChar+filename, xml);
        PubTool.writeFile(localfile + File.separatorChar + filename, xml);
        //logger.info(xml);
        return R.ok("保存成功");
    }
    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:drivemonitor:delete")
    public R delete(@RequestBody Integer[] ids) {
        driveMonitorService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }
    /**
     * 入区列表
     *
     * @param params
     * @return
     */
    @RequestMapping("/entryList")
  /* @RequiresPermissions("sys:drivemonitor:entryList")*/
    public R entrylist(@RequestParam Map<String, Object> params) {
        params.put("ioFlag", "I");
        PageUtils page = driveMonitorService.queryIoFlagPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 出区列表
     *
     * @param params
     * @return
     */
    @RequestMapping("/outList")
    public R outlist(@RequestParam Map<String, Object> params) {
        params.put("ioFlag", "O");
        PageUtils page = driveMonitorService.queryIoFlagPage(params);
        return R.ok().put("page", page);

    }

    /**
     * @param ids
     * @return
     */
    @RequestMapping("/generate")
    public R generateList(@RequestBody Integer[] ids) {
        int count = 0;
        for (int i = 0; i < ids.length; i++) {
            DriveMonitorEntity driveMonitorEntity = driveMonitorService.selectById(ids[i]);
            String xml = driveMonitorEntity.toJAString();
            String fileName = PubTool.sdf14.format(new Date()) +i+ "结案单" + ".xml";
            localfile = sysConfigService.getValue("japath");
            boolean flag = PubTool.writeFile(localfile + File.separatorChar + fileName, xml);
            int sendNum = driveMonitorEntity.getCount();
            logger.info("***********************" + fileName);
            logger.info("***********************" + flag);
            logger.info("***********************" + sendNum);
            if (flag) {
                sendNum++;
                count++;
                driveMonitorEntity.setCount(sendNum);
                driveMonitorService.updateById(driveMonitorEntity);
                logger.info("***********************" + driveMonitorEntity.getCount());
            }
        }
        //如果生成的文件数等于传入的记录数，返回成功，否则返回失败
        if(count == ids.length){
            return R.ok("结案单生成成功");
        }else{
            return R.error("结案单生成失败");
        }
    }
}

