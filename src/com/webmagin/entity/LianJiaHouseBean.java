package com.webmagin.entity;

public class LianJiaHouseBean {
	private int id;//id����
	
	private String houseState;//��Դ״̬
	
	private String houseName;//��Դ����
	
	private String price;//��Դ����
	
	private String updateDate;//��������
	
	private String tags;//��Դ��ǩ
	
	private String wuyeType;//��ҵ����
	
	private String address;//��Ŀ��ַ
	
	private String openOrderDate;//���¿�������
	
	private String contextPhone;//��ѯ�绰
	
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
