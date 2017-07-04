package com.webmagin.entity;

public class LianJiaHouseBean {
	private int id;//id主键
	
	private String houseState;//房源状态
	
	private String houseName;//房源名称
	
	private String price;//房源均价
	
	private String updateDate;//更新日期
	
	private String tags;//房源标签
	
	private String wuyeType;//物业类型
	
	private String address;//项目地址
	
	private String openOrderDate;//最新开盘日期
	
	private String contextPhone;//咨询电话
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getHouseState() {
		return houseState;
	}
	public void setHouseState(String houseState) {
		this.houseState = houseState;
	}
	public String getHouseName() {
		return houseName;
	}
	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public String getWuyeType() {
		return wuyeType;
	}
	public void setWuyeType(String wuyeType) {
		this.wuyeType = wuyeType;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getOpenOrderDate() {
		return openOrderDate;
	}
	public void setOpenOrderDate(String openOrderDate) {
		this.openOrderDate = openOrderDate;
	}
	public String getContextPhone() {
		return contextPhone;
	}
	public void setContextPhone(String contextPhone) {
		this.contextPhone = contextPhone;
	}
	@Override
	public String toString() {
		return "LianJiaHouseBean [id=" + id + ", houseState=" + houseState
				+ ", houseName=" + houseName + ", price=" + price
				+ ", updateDate=" + updateDate + ", tags=" + tags
				+ ", wuyeType=" + wuyeType + ", address=" + address
				+ ", openOrderDate=" + openOrderDate + ", contextPhone="
				+ contextPhone + "]";
	}
	
}
