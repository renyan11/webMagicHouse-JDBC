package com.webmagin;

import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import com.webmagin.dao.LianJiaHouseDao;
import com.webmagin.entity.LianJiaHouseBean;

/**
 * ���ҷ�Դ����
 * @author Ryan
 * @describe ������ȡָ�����е����з�Դ��Ϣ�������浽���ݿ��С�
 */
public class LianJiaHousePageProcessor implements PageProcessor {
	
	// ����ָ������ �����Ͼ�:nj ����:bj ����:sz
	private static String cityname = "nj";//���Ͼ�Ϊ��
	
	// ��ץȡ���ķ�Դ����
	private static int size = 0;
	
	// ץȡ��վ��������ã����������롢ץȡ��������Դ�����
	private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);
	
	// ��¼������
	private static String pageNum = null;
	@Override
	public Site getSite() {
		return site;
	}

	@Override
	// process�Ƕ��������߼��ĺ��Ľӿڣ��������д��ȡ�߼�
	public void process(Page page) {
		if(page.getUrl().regex("http://"+cityname+"\\.fang\\.lianjia\\.com/loupan/pg1").match() && (pageNum == null)){
			pageNum = page.getHtml().xpath("//span[@id='findCount']/text()").get();
			System.out.println("pageNum================"+pageNum);
		}
		//��Դ�б�ҳ
		if(!page.getUrl().regex("http://"+cityname+"\\.fang\\.lianjia\\.com/loupan/p_\\w+").match()){
			// ������з�Դҳ
			page.addTargetRequests(page.getHtml().xpath("//ul[@id='house-lst']").links()
				.regex("/loupan/p_\\w+")
				.replace("/loupan/", "http://"+cityname+"\\.fang\\.lianjia\\.com/loupan/")// �����滻�������urlת���ɾ���url
				.all());
			// ��������б�ҳ //*[@id="matchid"]/div/div/div
			page.addTargetRequests(page.getHtml().xpath("//div[@id='matchid']/div/div/div").links()
				.regex("/loupan/pg\\d+")
				.replace("/loupan/", "http://"+cityname+"\\.fang\\.lianjia\\.com/loupan/")// �����滻�������urlת���ɾ���url
				.all());
		}else{
			/*
				����	           ˵��                                               ʾ��
				get()	����һ��String���͵Ľ��	String link= html.links().get()
				toString()	����ͬget()������һ��String���͵Ľ��	String link= html.links().toString()
				all()	�������г�ȡ���	List links= html.links().all()
				match()	�Ƿ���ƥ����	if (html.links().match()){ xxx; }
			*/
			size++;// ��Դ������1 
			
			LianJiaHouseBean ljhb = new LianJiaHouseBean();
			// ���÷�Դ״̬
			ljhb.setHouseState(page.getHtml().xpath("//div[@class='state-div']/span[@class='state']/text()").get());
			// ���÷�Դ����
			ljhb.setHouseName(page.getHtml().xpath("//div[@class='name-box']/a/h1/text()").get());
			// ���÷�Դ����
			String price = page.getHtml().xpath("//p[@class='jiage']/span[@class='junjia']/text()").get()+page.getHtml().xpath("//p[@class='jiage']/span[@class='yuan']/text()").get();
			ljhb.setPrice((price == "nullnull" || "nullnull".equals(price))?" �۸����":price);
			// ���ø�������
			ljhb.setUpdateDate(page.getHtml().xpath("//p[@class='update']/span/text()").get());
			// ���÷�Դ��ǩ(���)
			ljhb.setTags(listToString(page.getHtml().xpath("//div[@class='bottom-info']/p[@class='small-tags']/span/text()").all()));
			// ������ҵ����(�����span[2]����д��span:eq(1)��cssѡ����������������)
			ljhb.setWuyeType(page.getHtml().xpath("//p[@class='wu-type']/span[2]/text()").get());
			// ������Ŀ��ַ
			ljhb.setAddress(page.getHtml().xpath("//p[@class='where']/span/text()").get());
			// �������¿������� (�����span[2]����д��span:eq(1)��cssѡ����������������)
			ljhb.setOpenOrderDate(page.getHtml().xpath("//p[@class='when']/span[2]/text()").get());
			// ������ѯ�绰
			ljhb.setContextPhone(page.getHtml().xpath("//div[@class='btn_phone_ll']/span/text()").get());
			// ����������ݿ� (�о�����ʵ��Cloneable�ӿڿ��Ա���Ƶ����������)
			LianJiaHouseDao.getOne().addHouseInfo(ljhb);
			// �Ѷ����������̨
			System.out.println("***"+ljhb+"***");
		}
	}
	
	// ��listת��Ϊstring����,�ָ�
	public static String listToString(List<String> stringList) {
		if (stringList == null) {
			return null;
		}
		StringBuilder result = new StringBuilder();
		boolean flag = false;
		for (String string : stringList) {
			if (flag) {
				result.append(",");
			} else {
				flag = true;
			}
			result.append(string);
		}
		return result.toString();
	}
	
	public static void main(String[] args) {
		long startTime, endTime;
		System.out.println("�����濪ʼ�������ĵȴ�һ�����ݵ���������...");
		startTime = System.currentTimeMillis();
		Spider create = Spider.create(new LianJiaHousePageProcessor());
		//page1������ִ��,��Ȼ�ò���pageNum
		create.addUrl("http://" +cityname+ ".fang.lianjia.com/loupan/pg1").thread(15).run();
		int pageCount = (Integer.parseInt(pageNum)%10==0)?(Integer.parseInt(pageNum)/10):(Integer.parseInt(pageNum)/10)+1;
		for (int i = 2; i <= pageCount; i++) {
			// �ӳ��з�Դpage2��ʼץ������15���̣߳���������
			create.addUrl("http://" +cityname+ ".fang.lianjia.com/loupan/pg"+i).thread(15).run();
		}
		endTime = System.currentTimeMillis();
		System.out.println("�������������ץȡ" + size + "����Դ����ʱԼ" + ((endTime - startTime) / 1000) + "�룬�ѱ��浽���ݿ⣬����գ�");
	}
}
