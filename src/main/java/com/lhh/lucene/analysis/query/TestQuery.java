package com.lhh.lucene.analysis.query;

import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;

import com.lhh.utils.JdbcNsanbanUtils;

public class TestQuery extends TestBaseQuery{

	public static void main(String[] args) {
		TestQuery t = new TestQuery();
		t.testNsanband();
	}
	
	private void testNsanband(){
		logger.info("start nsanban news_info table info lucene query key and value start...");
		JdbcNsanbanUtils utils = new JdbcNsanbanUtils();
		String key = "WZTitle";
		String value = "新三板违规账户清理开始";
		try {
			testNews(utils,key,value);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			utils.closeConnection();
		}
		logger.info("start nsanban news_info table info lucene succuess query index.");
	}
	
	private void testNews(JdbcNsanbanUtils utils,String key,String keyword) throws Exception{
		Analyzer aly1 = new StandardAnalyzer();//将数字作为一个整体,每个单词都进行分隔
		Analyzer aly2 = new StopAnalyzer();//将数字停用 中文不起作用,只做空格分割
		Analyzer aly3 = new SimpleAnalyzer();//将数字停用 中文不起作用,只按照空格分割
		Analyzer aly4 = new WhitespaceAnalyzer();//按照空格分隔,中文不起作用
		
		Query query1 = new QueryParser(key, aly1).parse(keyword);
		Query query2 = new QueryParser(key, aly2).parse(keyword);
		Query query3 = new QueryParser(key, aly3).parse(keyword);
		Query query4 = new QueryParser(key, aly4).parse(keyword);
		
		List<Map<String,Object>> list1 = queryDirectory(query1, 10, StandardAnalyzer.class.getSimpleName());
		List<Map<String,Object>> list2 = queryDirectory(query2, 10, StopAnalyzer.class.getSimpleName());
		List<Map<String,Object>> list3 = queryDirectory(query3, 10, SimpleAnalyzer.class.getSimpleName());
		List<Map<String,Object>> list4 = queryDirectory(query4, 10, WhitespaceAnalyzer.class.getSimpleName());
		for (Map<String, Object> map : list1) {
			System.out.println(map.get("WZTitle"));
		}
		System.out.println("------------------------>");
		for (Map<String, Object> map : list2) {
			System.out.println(map.get("WZTitle"));
		}
		System.out.println("------------------------>");
		for (Map<String, Object> map : list3) {
			System.out.println(map.get("WZTitle"));
		}
		System.out.println("------------------------>");
		for (Map<String, Object> map : list4) {
			System.out.println(map.get("WZTitle"));
		}
		System.out.println("------------------------>");
	}
}
