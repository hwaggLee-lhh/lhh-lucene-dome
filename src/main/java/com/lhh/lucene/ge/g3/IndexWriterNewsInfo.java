package com.lhh.lucene.ge.g3;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;

import com.lhh.lucene.analysis.index.TestBaseIndex;
import com.lhh.utils.JdbcNsanbanUtils;

/**
 * 新闻索引生成
 * @author hwaggLee
 * @createDate 2016年11月1日
 * @addresss：http://www.cnblogs.com/yinpeng186/archive/2011/09/16/2178259.html
 */
public class IndexWriterNewsInfo {
	private static final String path = TestBaseIndex.path;

	public static void createIndexWriter(){
		JdbcNsanbanUtils utisl = new JdbcNsanbanUtils();
		try {
			SimpleAnalyzer analyzer = new SimpleAnalyzer();
			List<Map<String,Object>> list = null;
			create(analyzer, list);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			utisl.closeConnection();
		}
	}
	
	/**
	 * 创建索引
	 * @param analyzer
	 * @param list
	 * @throws Exception
	 */
	private static void create(Analyzer analyzer,List<Map<String,Object>> list) throws Exception{
		if( analyzer == null )return;
		if( list == null || list.isEmpty() ) return;
		IndexWriterConfig config = new IndexWriterConfig(analyzer);
		Directory dict = new SimpleFSDirectory(new File(path).toPath());//http://blog.itpub.net/28624388/viewspace-763724/
		IndexWriter indexWriter = new IndexWriter(dict, config);
		
		for (Map<String,Object>	map: list) {
			if( map == null || map.isEmpty() )continue;
            Document doc = new Document();
            for (String key : map.keySet()) {
				Object object = map.get(key);
				if(StringUtils.isBlank(key) || object == null ) continue;
				doc.add(new Field(key,object.toString(), new FieldType()));//其中ID、Name、Add都是数据库中的字段名，这个应该可以看明白的吧 
			}
            indexWriter.addDocument(doc);
		}
		indexWriter.commit();//添加完所有document，我们对索引进行优化，优化主要是将多个索引文件合并到一个，有利于提高索引速度。 
		indexWriter.close();//随后将writer关闭，这点很重要。
	}
}
