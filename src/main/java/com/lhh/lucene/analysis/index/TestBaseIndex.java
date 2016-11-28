package com.lhh.lucene.analysis.index;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;

public abstract class TestBaseIndex {
	protected final Logger logger = Logger.getLogger(getClass());
	protected final String logname = this.getClass().getSimpleName();
	public static final String path = "D:\\data\\luceneindex\\newsinfo";//索引文件存放地址

	/**
	 * 创建索引
	 * @param analyzer
	 * @param list
	 * @throws Exception
	 */
	public void writerDirectoryIndex(Analyzer analyzer,List<Map<String,Object>> list,String filename) throws Exception{
		if( analyzer == null )return;
		if( list == null || list.isEmpty() ) return;
		IndexWriterConfig config = new IndexWriterConfig(analyzer);
		String path = TestBaseIndex.path;
		if (filename != null && filename.trim().length() > 0 ) {
			path += "\\"+filename;
		}
		logger.info("create dirctory index ： filepath="+path);
		Directory dict = new SimpleFSDirectory(new File(path).toPath());//http://blog.itpub.net/28624388/viewspace-763724/
		IndexWriter indexWriter = new IndexWriter(dict, config);
		
		for (Map<String,Object>	map: list) {
			if( map == null || map.isEmpty() )continue;
            Document doc = new Document();
            for (String key : map.keySet()) {
				Object object = map.get(key);
				if(StringUtils.isBlank(key) || object == null ) continue;
				FieldType fieldType = new FieldType();
				fieldType.setStored(true);
				fieldType.setIndexOptions(IndexOptions.DOCS );
				doc.add(new Field(key,object.toString(),fieldType));//其中ID、Name、Add都是数据库中的字段名
				//doc.add(new TextField(key,object.toString(), Store.YES));//其中ID、Name、Add都是数据库中的字段名
			}
            indexWriter.addDocument(doc);
		}
		indexWriter.commit();//添加完所有document，我们对索引进行优化，优化主要是将多个索引文件合并到一个，有利于提高索引速度。 
		indexWriter.close();//随后将writer关闭，这点很重要。
	}
}
