package cn.bobo.fast.modules.job.task;

/**
 * Copyright 2018 人人开源 http://www.renren.io
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

import cn.bobo.fast.common.utils.PubTool;
import cn.bobo.fast.modules.job.entity.ScheduleJobEntity;
import cn.bobo.fast.modules.sys.entity.SysUserEntity;
import cn.bobo.fast.modules.sys.service.DriveMonitorService;
import cn.bobo.fast.modules.sys.service.SysConfigService;
import cn.bobo.fast.modules.sys.service.SysUserService;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.awt.print.Book;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;

/**
 * 测试定时任务(演示Demo，可删除)
 * <p>
 * testTask为spring bean的名称
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.2.0 2016-11-28
 */
@Component("testTask")
public class TestTask {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysConfigService sysConfigService;
    @Autowired
    private DriveMonitorService driveMonitorService;

    public void test(String params) {
        logger.info("我是带参数的test方法，正在被执行，参数为：" + params);

        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        SysUserEntity user = sysUserService.selectById(1L);
        System.out.println(ToStringBuilder.reflectionToString(user));

    }


    public void test2() {
        logger.info("我是不带参数的test2方法，正在被执行");
    }

    /**
     * 定时去扫描入区存放文件夹
     */
    public void entrylist() {
        logger.info("我是不带参数的entrylist方法，正在被执行");
        String entrypath = sysConfigService.getValue("entrypath");
        System.out.println("===== entrypath目录下的文件 =====");
        File file = new File(entrypath);
        File[] aa = file.listFiles();
        for (int i = 0; i < aa.length; i++) {
            if (aa[i].isFile()) {
                System.out.println("=====" + aa[i]);
                SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
                SAXParser parser = null;
                try {
                    parser = saxParserFactory.newSAXParser();
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                } catch (SAXException e) {
                    e.printStackTrace();
                }
                SAXParseHandler handler = new SAXParseHandler();
                try {
                    parser.parse(aa[i], handler);
                    if(handler.getDme().getIoFlag().equals("I")){
                        System.out.println("===== 车辆编号"+handler.getDme().getVeNo());
                    }
                } catch (SAXException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void parsingMsg() {
        String msgpath = sysConfigService.getValue("msgpath");        //报文路径
        String remarkpath = sysConfigService.getValue("localfile");    //备份路径
//		System.out.println("=========指定目录下的所有文件夹==========");
//		File fileDirectory = new File(msgpath);
//		for (File temp : fileDirectory.listFiles()) {
//			if (temp.isDirectory()) {
//				System.out.println(temp.toString());
//			}
//		}
        System.out.println("=========指定目录下的所有文件==========");
        File file = new File(msgpath);
        File[] aa = file.listFiles();
        for (int i = 0; i < aa.length; i++) {
            if (aa[i].isFile()) {
                System.out.println(aa[i]);
                SAXParserFactory sParserFactory = SAXParserFactory.newInstance();
                SAXParser parser = null;
                try {
                    parser = sParserFactory.newSAXParser();
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                } catch (SAXException e) {
                    e.printStackTrace();
                }

                SAXParseHandler handler = new SAXParseHandler();
                try {
                    parser.parse(aa[i], handler);
                    boolean binsert = driveMonitorService.insert(handler.getDme());
                    if (binsert) {
                        copyFile(remarkpath + File.separatorChar + PubTool.sdf8.format(new Date()) + File.separator + aa[i].getName(), aa[i].toString());
                        deleteFile(aa[i].toString());
                    }
                } catch (SAXException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }


    }

    /**
     * 1.解析报文并
     * 2.复制至 抬杆硬件解析目录  和 备份目录
     * 3.组织 结案报文 发送至结案目录
     */
    public void parsingMsgToJS() {
        String msgpath = sysConfigService.getValue("msgpath");        //报文路径
        String remarkpath = sysConfigService.getValue("localfile");    //备份路径
//		String copypath=sysConfigService.getValue("copypath"); 	//复制路径
        String japath = sysConfigService.getValue("japath");        //结案路径
//		System.out.println("=========指定目录下的所有文件夹==========");
//		File fileDirectory = new File(msgpath);
//		for (File temp : fileDirectory.listFiles()) {
//			if (temp.isDirectory()) {
//				System.out.println(temp.toString());
//			}
//		}
        System.out.println("=========指定目录下的所有文件==========");
        File file = new File(msgpath);
        File[] aa = file.listFiles();
        for (int i = 0; i < aa.length; i++) {
            if (aa[i].isFile()) {
                System.out.println(aa[i]);
                SAXParserFactory sParserFactory = SAXParserFactory.newInstance();
                SAXParser parser = null;
                try {
                    parser = sParserFactory.newSAXParser();
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                } catch (SAXException e) {
                    e.printStackTrace();
                }

                SAXParseHandler handler = new SAXParseHandler();
                try {
                    parser.parse(aa[i], handler);
                    String jaxml = handler.getDme().toJAString();
                    if (handler.getDme().getIoFlag().equals("O")) {
                        PubTool.writeFile(japath + File.separatorChar + handler.getDme().getBillNo() + ".xml", jaxml);//结案单生成
                    }

                    boolean binsert = driveMonitorService.insert(handler.getDme());
                    if (binsert) {
//						copyFile(copypath+File.separator+aa[i].getName(),aa[i].toString());
//						//创建备份日期目录
//						String datapath=remarkpath+File.separatorChar+ PubTool.sdf8.format(new Date())+File.separator;
//						File dataFile = new File(datapath);
//						if(!dataFile.exists()){
//							dataFile.createNewFile();
//						}
//						copyFile(remarkpath+File.separatorChar+ PubTool.sdf8.format(new Date())+File.separator+aa[i].getName(),aa[i].toString());
                        copyFile(remarkpath + File.separator + aa[i].getName(), aa[i].toString());
                        deleteFile(aa[i].toString());
                    }
                } catch (SAXException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }


    }


    public void copyFile(String tarPath, String sourcePath) throws IOException {


        File tarFile = new File(tarPath);

        if (!tarFile.exists()) {

            tarFile.createNewFile();
        }


        File sourceFile = new File(sourcePath);

        try {

            // 创建一个文件输入流
            FileInputStream fis = new FileInputStream(sourceFile);
            // 输入流缓冲区
            BufferedInputStream bis = new BufferedInputStream(fis);

            byte[] buffer = new byte[bis.available()];

            bis.read(buffer);
            bis.close();
            fis.close();

            // 创建一个文件输出流
            FileOutputStream fos = new FileOutputStream(tarFile);

            BufferedOutputStream bos = new BufferedOutputStream(fos);

            // 将数据写入输出流
            bos.write(buffer);
            // 此处需要刷新
            bos.flush();

            bos.close();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    //删除文件（也可以当做复制完文件以后 ,剪切功能）

    /**
     * @param sourcePath 要删除的文件路径
     */

    public void deleteFile(String sourcePath) {

        File tarFile = new File(sourcePath);

        if (tarFile.isFile())

            tarFile.delete();

    }
}
