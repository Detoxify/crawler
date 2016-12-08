package com.pana.crawler.crawler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Crawler {
	
	/**
	 * 传入url，将要访问的页面转换成String字符串
	 * 
	 * @param url
	 * @return htmlString
	 */
	public String getHtmlString(String url){
		
		String htmlString = "";
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		try {
			HttpResponse response = client.execute(httpGet);
			if(response != null && 
					response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				HttpEntity entity = response.getEntity();
				htmlString = EntityUtils.toString(entity);
//				htmlToFile(htmlString);
				return htmlString;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	/**
	 * 将访问的html页面保存为文件
	 * 
	 * @param htmlString
	 * @throws ParseException、IOException
	 * @throws 
	 */
	public void htmlToFile(String htmlString) throws ParseException, IOException{
		String dirPath = "E:\\douban-html\\";
		File dirFile = new File(dirPath);
		if(dirFile == null || !dirFile.exists()){
			dirFile.mkdir();
		}
		String filePath = dirPath+"douban.html";
		File htmlFile = new File(filePath);
		if(htmlFile == null || !htmlFile.exists()){
			try {
				htmlFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		FileOutputStream out = new FileOutputStream(htmlFile);
		out.write(htmlString.getBytes("UTF-8"));
		if (out != null) {
            out.close();
        }
	}

	/**
	 * 处理response，Jsoup解析页面，获取所需数据
	 * 
	 * @param url
	 */
	public void getMovieList(String url){
		List<String> movieType = null;
		String html = getHtmlString(url);
		//将String类型的html转换为Document
		Document doc = Jsoup.parse(html);
		//获取所有class为pl2的元素，即包含所有<a>的div
		Elements nodes = doc.getElementsByClass("pl2");	
		//循环进入每个<a>，访问影片的详细信息
		for(Element node : nodes){
			Movie movie = new Movie();
			movieType = new ArrayList<String>();
			Element link = node.select("a").first();
			String href = link.attr("href");
			String detail = getHtmlString(href);
			detail = detail.replace("property","class");
			Document movieDetail = Jsoup.parse(detail);
			Element name = movieDetail.getElementsByTag("h1").first();
			movie.setName(name.text());
			Elements types = movieDetail.getElementsByClass("v:genre");
			for(Element type : types){
				movieType.add(type.text());
			}
			movie.setTypes(movieType);
			Element grade = movieDetail.getElementsByTag("strong").first();
			Float movieGrade = Float.parseFloat(grade.html());
			movie.setGrade(movieGrade);
			System.out.println(movie);
		}
		
	}
	
	/**
	 * 测试用main方法
	 * 
	 * @param args
	 */
	public static void main(String[] args){
		
		Crawler crawler = new Crawler();
		crawler.getMovieList("https://movie.douban.com/chart");
	}
}
