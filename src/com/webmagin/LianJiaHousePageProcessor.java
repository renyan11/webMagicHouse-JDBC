package com.webmagin;

import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import com.webmagin.dao.LianJiaHouseDao;
import com.webmagin.entity.LianJiaHouseBean;

/**
 * 链家房源爬虫
 * @author Ryan
 * @describe 可以爬取指定城市的所有房源信息，并保存到数据库中。
 */
public class LianJiaHousePageProcessor implements PageProcessor {
	
	// 设置指定城市 比如南京:nj 北京:bj 深圳:sz
	private static String cityname = "nj";//以南京为例
	
	// 共抓取到的房源数量
	private static int size = 0;
	
	// 抓取网站的相关配置，包括：编码、抓取间隔、重试次数等
	private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);
	
	// 记录总条数
	private static String pageNum = null;
	@Override
	public Site getSite() {
		return site;
	}

	@Override
	// process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
	public void process(Page page) {
		if(page.getUrl().regex("http://"+cityname+"\\.fang\\.lianjia\\.com/loupan/pg1").match() && (pageNum == null)){
			pageNum = page.getHtml().xpath("//span[@id='findCount']/text()").get();
			System.out.println("pageNum================"+pageNum);
		}
		//房源列表页
		if(!page.getUrl().regex("http://"+cityname+"\\.fang\\.lianjia\\.com/loupan/p_\\w+").match()){
			// 添加所有房源页
			page.addTargetRequests(page.getHtml().xpath("//ul[@id='house-lst']").links()
				.regex("/loupan/p_\\w+")
				.replace("/loupan/", "http://"+cityname+"\\.fang\\.lianjia\\.com/loupan/")// 巧用替换给把相对url转换成绝对url
				.all());
			// 添加其他列表页 //*[@id="matchid"]/div/div/div
			page.addTargetRequests(page.getHtml().xpath("//div[@id='matchid']/div/div/div").links()
				.regex("/loupan/pg\\d+")
				.replace("/loupan/", "http://"+cityname+"\\.fang\\.lianjia\\.com/loupan/")// 巧用替换给把相对url转换成绝对url
				.all());
		}else{
			/*
				方法	           说明                                               示例
				get()	返回一条String类型的结果	String link= html.links().get()
				toString()	功能同get()，返回一条String类型的结果	String link= html.links().toString()
				all()	返回所有抽取结果	List links= html.links().all()
				match()	是否有匹配结果	if (html.links().match()){ xxx; }
			*/
			size++;// 房源数量加1 
			
			LianJiaHouseBean ljhb = new LianJiaHouseBean();
			// 设置房源状态
			ljhb.setHouseState(page.getHtml().xpath("//div[@class='state-div']/span[@class='state']/text()").get());
			// 设置房源名称
			ljhb.setHouseName(page.getHtml().xpath("//div[@class='name-box']/a/h1/text()").get());
			// 设置房源均价
			String price = page.getHtml().xpath("//p[@class='jiage']/span[@class='junjia']/text()").get()+page.getHtml().xpath("//p[@class='jiage']/span[@class='yuan']/text()").get();
			ljhb.setPrice((price == "nullnull" || "nullnull".equals(price))?" 价格待定":price);
			// 设置更新日期
			ljhb.setUpdateDate(page.getHtml().xpath("//p[@class='update']/span/text()").get());
			// 设置房源标签(多个)
			ljhb.setTags(listToString(page.getHtml().xpath("//div[@class='bottom-info']/p[@class='small-tags']/span/text()").all()));
			// 设置物业类型(这里的span[2]不能写成span:eq(1)跟css选择器还是略有区别)
			ljhb.setWuyeType(page.getHtml().xpath("//p[@class='wu-type']/span[2]/text()").get());
			// 设置项目地址
			ljhb.setAddress(page.getHtml().xpath("//p[@class='where']/span/text()").get());
			// 设置最新开盘日期 (这里的span[2]不能写成span:eq(1)跟css选择器还是略有区别)
			ljhb.setOpenOrderDate(page.getHtml().xpath("//p[@class='when']/span[2]/text()").get());
			// 设置咨询电话
			ljhb.setContextPhone(page.getHtml().xpath("//div[@class='btn_phone_ll']/span/text()").get());
			// 对象存入数据库 (感觉这样实现Cloneable接口可以避免频繁创建对象)
			LianJiaHouseDao.getOne().addHouseInfo(ljhb);
			// 把对象输出控制台
			System.out.println("***"+ljhb+"***");
		}
	}
	
	// 把list转换为string，用,分割
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
		System.out.println("【爬虫开始】请耐心等待一大波数据到你碗里来...");
		startTime = System.currentTimeMillis();
		Spider create = Spider.create(new LianJiaHousePageProcessor());
		//page1必须先执行,不然拿不到pageNum
		create.addUrl("http://" +cityname+ ".fang.lianjia.com/loupan/pg1").thread(15).run();
		int pageCount = (Integer.parseInt(pageNum)%10==0)?(Integer.parseInt(pageNum)/10):(Integer.parseInt(pageNum)/10)+1;
		for (int i = 2; i <= pageCount; i++) {
			// 从城市房源page2开始抓，开启15个线程，启动爬虫
			create.addUrl("http://" +cityname+ ".fang.lianjia.com/loupan/pg"+i).thread(15).run();
		}
		endTime = System.currentTimeMillis();
		System.out.println("【爬虫结束】共抓取" + size + "个房源，耗时约" + ((endTime - startTime) / 1000) + "秒，已保存到数据库，请查收！");
	}
}
