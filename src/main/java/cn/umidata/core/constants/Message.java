package cn.umidata.core.constants;

import com.alibaba.fastjson.annotation.JSONField;


public class Message {
	@JSONField(ordinal = 1)
	private String status = MsgStatus.SUCCESS;//0:失败，1:成功，失败返回errCode和errMsg
	@JSONField(ordinal = 2)
	private Object data;//成功返回的数据
	@JSONField(ordinal = 3)
	private String errCode="";//错误代码
	@JSONField(ordinal = 4)
	private String errMsg="";//错误内容
	
	public Message(){
		
	}
	
	public Message(Object data){
		setData(data);
	}
	
	public Message(String status,Object data){
		this.status = status;
		setData(data);
	}
	
	public Message(String status,String errCode,String errMsg){
		this.status = status;
		this.errCode = errCode;
		this.errMsg = errMsg;
	}
	
	public Message(String status,String errCode,String errMsg,Object data){
		this.status = status;
		this.errCode = errCode;
		this.errMsg = errMsg;
		setData(data);
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	
	@Override
	public String toString() {
		return "Message [status=" + status + ", data=" + data + ", errCode=" + errCode + ", errMsg=" + errMsg + "]";
	}
}
