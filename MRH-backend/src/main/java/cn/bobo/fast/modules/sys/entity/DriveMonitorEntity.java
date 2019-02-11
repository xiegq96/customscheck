package cn.bobo.fast.modules.sys.entity;

import cn.bobo.fast.common.utils.PubTool;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 卡口监控表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-05-09 12:00:07
 */
@TableName("tb_drive_monitor")
public class DriveMonitorEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 核放单编号
	 */
	private String billNo;
	/**
	 * 车辆编号
	 */
	private String veNo;
	/**
	 * 进场时间
	 */
	private String entryTime;
	/**
	 * 发送状态
	 */
	private String sendstatus;
	/**
	 * 接收状态
	 */
	private String receivestatus;
	private String status;
	private String ioFlag;
	private String customs;
	private String ioFlow;
	private String gateType;
	private String tradeCo;
	private String tradeName;
	private String note;
	private Integer count;

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIoFlag() {
		return ioFlag;
	}

	public void setIoFlag(String ioFlag) {
		this.ioFlag = ioFlag;
	}

	public String getCustoms() {
		return customs;
	}

	public void setCustoms(String customs) {
		this.customs = customs;
	}

	public String getIoFlow() {
		return ioFlow;
	}

	public void setIoFlow(String ioFlow) {
		this.ioFlow = ioFlow;
	}

	public String getGateType() {
		return gateType;
	}

	public void setGateType(String gateType) {
		this.gateType = gateType;
	}

	public String getTradeCo() {
		return tradeCo;
	}

	public void setTradeCo(String tradeCo) {
		this.tradeCo = tradeCo;
	}

	public String getTradeName() {
		return tradeName;
	}

	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * 设置：
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：核放单编号
	 */
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	/**
	 * 获取：核放单编号
	 */
	public String getBillNo() {
		return billNo;
	}
	/**
	 * 设置：车辆编号
	 */
	public void setVeNo(String veNo) {
		this.veNo = veNo;
	}
	/**
	 * 获取：车辆编号
	 */
	public String getVeNo() {
		return veNo;
	}
	/**
	 * 设置：进场时间
	 */
	public void setEntryTime(String entryTime) {
		this.entryTime = entryTime;
	}
	/**
	 * 获取：进场时间
	 */
	public String getEntryTime() {
		return entryTime;
	}
	/**
	 * 设置：发送状态
	 */
	public void setSendstatus(String sendstatus) {
		this.sendstatus = sendstatus;
	}
	/**
	 * 获取：发送状态
	 */
	public String getSendstatus() {
		return sendstatus;
	}
	/**
	 * 设置：接收状态
	 */
	public void setReceivestatus(String receivestatus) {
		this.receivestatus = receivestatus;
	}
	/**
	 * 获取：接收状态
	 */
	public String getReceivestatus() {
		return receivestatus;
	}

	private static String ENTER = "\r\n";
	private static String TAB   = "  ";

	@Override
	public String toString() {

		String str = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" + ENTER;
		str += "<ZHUHAI_REP xmlns=\"http://www.cneport.com/cbo/dist/entity\">" + ENTER;
		str += TAB + "<BILL_NO>" + billNo +"</BILL_NO>" + ENTER;
		str += TAB + "<STATUS>0</STATUS>" + ENTER;
		str += TAB + "<IO_FLAG>0</IO_FLAG>" + ENTER;
		str += TAB + "<VE_NO>" + veNo +"</VE_NO>" + ENTER;
		str += TAB + "<ENTRY_TIME>" + entryTime +"</ENTRY_TIME>" + ENTER;
		str += TAB + "<GATE_TYPE>2</GATE_TYPE>" + ENTER;
		str += TAB + "<TRADE_CO>351286006E</TRADE_CO>" + ENTER;
		str += TAB + "<TRADE_NAME>福建跨境通电子商务有限公司</TRADE_NAME>" + ENTER;
		str += TAB + "<NOTE></NOTE>" + ENTER;
		str += "</ZHUHAI_REP>";
		return str;
	}

	/**
	 * 核放申请单
	 * @return
	 */
	public String toString20181010() {
		String str = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" + ENTER;
		str += "<ZHUHAI_EP xmlns=\"http://www.cneport.com/cbo/dist/entity\">" + ENTER;
		str += TAB + "<BILL_NO>" + billNo +"</BILL_NO>" + ENTER;
		str += TAB + "<STATUS>"+status+"</STATUS>" + ENTER;
		str += TAB + "<IO_FLAG>"+ioFlag+"</IO_FLAG>" + ENTER;
		str += TAB + "<VE_NO>" + veNo +"</VE_NO>" + ENTER;
		str += TAB + "<CUSTOMS>" + customs +"</CUSTOMS>" + ENTER;
		str += TAB + "<IO_FLOW>" + ioFlow +"</IO_FLOW>" + ENTER;
		str += TAB + "<GATE_TYPE>" + gateType +"</GATE_TYPE>" + ENTER;
		str += TAB + "<TRADE_CO>"+tradeCo+"</TRADE_CO>" + ENTER;
		str += TAB + "<TRADE_NAME>"+tradeName+"</TRADE_NAME>" + ENTER;
		str += TAB + "<NOTE>"+note+"</NOTE>" + ENTER;
		str += "</ZHUHAI_EP>"+ ENTER;
		return str;
	}

	/**
	 * 结案单
	 * @return
	 */
	public String toJAString() {
		String str = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" + ENTER;
		str += "<ZHUHAI_REP xmlns=\"http://www.cneport.com/cbo/dist/entity\">" + ENTER;
		str += TAB + "<BILL_NO>" + billNo +"</BILL_NO>" + ENTER;
		str += TAB + "<STATUS>0</STATUS>" + ENTER;
		str += TAB + "<IO_FLAG>O</IO_FLAG>" + ENTER;
		str += TAB + "<VE_NO>" + veNo +"</VE_NO>" + ENTER;
		str += TAB + "<ENTRY_TIME>" + PubTool.sdf14.format(new Date()) +"</ENTRY_TIME>" + ENTER;
		str += TAB + "<GATE_TYPE>2</GATE_TYPE>" + ENTER;
		str += TAB + "<TRADE_CO>351286006E</TRADE_CO>" + ENTER;
		str += TAB + "<TRADE_NAME>福建跨境通电子商务有限公司</TRADE_NAME>" + ENTER;
		str += TAB + "<NOTE/>";
		str += "</ZHUHAI_REP>";
		return str;
	}

}
