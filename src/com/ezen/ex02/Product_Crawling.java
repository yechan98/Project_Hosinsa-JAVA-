package com.ezen.ex02;

import java.io.IOException;
import java.util.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class Product_Crawling {
	static ArrayList<Product_Info> pilist = new ArrayList<Product_Info>();
		
	public static void main(String[] args) {
		Product_Crawling pcraw = new Product_Crawling();
		pcraw.product_Crawling();
	}
	
	public static ArrayList<Product_Info> product_Crawling() {
		String pNumber;
		String categorys;
		String product;
		String brands;
		int prices;
		int stocks;		
		
		try {
			for(int i=1; i<=3; i++) {//100
				Document doc = Jsoup.connect("https://www.musinsa.com/ranking/best?period=now&age=ALL&mainCategory=&subCategory=&leafCategory=&price=&golf=false&kids=false&newProduct=false&exclusive=false&discount=false&soldOut=false&page="+i).get();
				Elements ranking = doc.select("div.box li.li_box p.txt_num_rank");
				Elements img = doc.select("div.list_img img.lazy");
				Elements link = doc.select("div.list_img a");
				Elements productNo = doc.select("div.box li.li_box");
				Elements brand = doc.select("div.box li.li_box p.item_title a");
				Elements productName = doc.select("div.box li.li_box p.list_info a");
				
				try {
					for(int j=0; j<img.size(); j++) {
						Document doc2 = Jsoup.connect(link.get(j).attr("href")).get();
						Element category = doc2.select("div.product_info p.item_categories a:eq(0)").get(0);
						Element price = doc2.select("#normal_price").get(0);
				        int amount = (int)(Math.random()*100+1);
				        
				       
				        String pName = productName.get(j).text();
				        String pName2 = null;
				        if(productName.get(j).text().length()>30) {
				        	pName2 = pName.substring(0,30);
				        }
				        else if(productName.get(j).text().length()<=30) {
				        	pName2 = String.format("%-30s",pName);
				        }

				        
						System.out.println("랭킹 : "+ranking.get(j).text().split("위")[0]);
						System.out.println("제품번호 : "+productNo.get(j).attr("data-goods-no"));
						System.out.println("카테고리 : "+category.text());
						System.out.println("브랜드 : "+brand.get(j).text());
						System.out.println("제품명 : "+pName2);
						System.out.println("가격 : "+price.text());
						System.out.println("재고 : "+amount+"개");
						System.out.println("=========================================");
						
						pNumber = productNo.get(j).attr("data-goods-no");
						categorys = category.text();
						brands = brand.get(j).text();
						product = pName2;
						prices = Integer.parseInt(price.text());
						stocks = amount;	
												
						Product_Info pinfo = new Product_Info();
						pinfo.setpNumber(pNumber);
						pinfo.setCategorys(categorys);
						pinfo.setBrands(brands);
						pinfo.setProduct(product);
						pinfo.setPrices(prices);
						pinfo.setStocks(stocks);
						
						pilist.add(pinfo);
						
					}
				} catch(Exception ex) {}
			} 
		} catch(Exception ex) {}
		return pilist;
	}
}

