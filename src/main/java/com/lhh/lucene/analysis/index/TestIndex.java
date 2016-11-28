package com.lhh.lucene.analysis.index;

import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;

import com.lhh.utils.JdbcNsanbanUtils;

public class TestIndex extends TestBaseIndex{

	public static void main(String[] args) {
		TestIndex t = new TestIndex();
		t.testNsanband();
	}
	
	private void testNsanband(){
		logger.info("start nsanban news_info table info lucene create index start...");
		JdbcNsanbanUtils utils = new JdbcNsanbanUtils();
		try {
			testNews(utils);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			utils.closeConnection();
		}
		logger.info("start nsanban news_info table info lucene succuess create index.");
	}
	
	private void testNews(JdbcNsanbanUtils utils) throws Exception{
		String s = "select n.idStr,n.WZTitle,n.LaiYuan,n.Author,n.NetAddress,n.KeyWord,n.WZAbstract,n.Content from news_info n ";
		int start = 0;
		int pageNumber = 20;
		boolean runing = true;
		int size = 50;
		Analyzer aly1 = new StandardAnalyzer();//将数字作为一个整体,每个单词都进行分隔
		Analyzer aly2 = new StopAnalyzer();//将数字停用 中文不起作用,只做空格分割
		Analyzer aly3 = new SimpleAnalyzer();//将数字停用 中文不起作用,只按照空格分割
		Analyzer aly4 = new WhitespaceAnalyzer();//按照空格分隔,中文不起作用
		
		while(runing){
			String sql = s+ "limit "+start*pageNumber+", "+pageNumber+";";
			logger.info("sql:"+sql);
			start++;
			List<Map<String, Object>> list = utils.findModeResult(sql, null);
			if( list == null || list.size() == 0 ) break;
			writerDirectoryIndex(aly1, list,StandardAnalyzer.class.getSimpleName());
			writerDirectoryIndex(aly2, list,StopAnalyzer.class.getSimpleName());
			writerDirectoryIndex(aly3, list,SimpleAnalyzer.class.getSimpleName());
			writerDirectoryIndex(aly4, list,WhitespaceAnalyzer.class.getSimpleName());
			if(start>size)break;
		}
	}
}
