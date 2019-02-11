package cn.bobo.fast.modules.job.task;

import cn.bobo.fast.modules.sys.entity.DriveMonitorEntity;
import cn.bobo.fast.modules.sys.service.DriveMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.awt.print.Book;
import java.io.IOException;

public class SAXParseHandler extends DefaultHandler
{


    private String content = null;   //存放当前节点值
    private DriveMonitorEntity dme=null;

    /**
     * 开始解析xml文档时调用此方法
     */
    @Override
    public void startDocument() throws SAXException {

        super.startDocument();
        System.out.println("开始解析xml文件");
        dme=new DriveMonitorEntity();
        System.out.println("dme init");
    }



    /**
     * 文档解析完成后调用此方法
     */
    @Override
    public void endDocument() throws SAXException {

        super.endDocument();
        System.out.println("xml文件解析完毕");
    }



    /**
     * 开始解析节点时调用此方法
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        super.startElement(uri, localName, qName, attributes);

        //当节点名为book时,获取book的属性id
        if(qName.equals("ZHUHAI_EP")){

            String id = attributes.getValue("id");//System.out.println("id值为"+id);

        }

    }


    /**
     *节点解析完毕时调用此方法
     *
     *@param qName 节点名
     */
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {

        super.endElement(uri, localName, qName);
        if(qName.equals("BILL_NO")){
            dme.setBillNo(content);
        }else if(qName.equals("STATUS")){
            dme.setStatus(content);
        }else if(qName.equals("IO_FLAG")){
            dme.setIoFlag(content);
        }else if(qName.equals("VE_NO")){
            dme.setVeNo(content);
        }else if(qName.equals("CUSTOMS")){
            dme.setCustoms(content);
        }else if(qName.equals("IO_FLOW")){
            dme.setIoFlow(content);
        }else if(qName.equals("GATE_TYPE")){
            dme.setGateType(content);
        }else if(qName.equals("TRADE_CO")){
            dme.setTradeCo(content);
        }else if(qName.equals("TRADE_NAME")){
            dme.setTradeName(content);
        }else if(qName.equals("NOTE")){
            dme.setNote(content);
        }

    }



    /**
     * 此方法用来获取节点的值
     */
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {

        super.characters(ch, start, length);

        content = new String(ch, start, length);
        //收集不为空白的节点值
//      if(!content.trim().equals("")){
//          System.out.println("节点值为："+content);
//      }

    }

    public DriveMonitorEntity getDme() {
        return dme;
    }



}
