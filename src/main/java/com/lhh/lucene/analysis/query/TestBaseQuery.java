package com.lhh.lucene.analysis.query;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;

public abstract class TestBaseQuery {
	protected final Logger logger = Logger.getLogger(getClass());
	protected final String logname = this.getClass().getSimpleName();
	public static final String path = "D:\\data\\luceneindex\\newsinfo";//索引文件存放地址

	public List<Map<String,Object>> queryDirectory(Query query,int n ,String filename) throws Exception{
		String path = TestBaseQuery.path;
		if (filename != null && filename.trim().length() > 0 ) {
			path += "\\"+filename;
		}
		logger.info("query dirctory index ： filepath="+path);
		Directory dict = new SimpleFSDirectory(new File(path).toPath());//http://blog.itpub.net/28624388/viewspace-763724/
		IndexReader indexReader = DirectoryReader.open(dict);
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);
		TopDocs td = indexSearcher.search(query, n);
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        ScoreDoc[] sds = td.scoreDocs;  
        for (ScoreDoc sd : sds) {   
            Document d = indexSearcher.doc(sd.doc);   
            if( d == null )continue;
            Map<String,Object> map = new LinkedHashMap<String, Object>();
            for (IndexableField field : d.getFields()) {
            	if( field == null )continue;
            	map.put(field.name(), field.stringValue());
			}
            list.add(map);
        }
        indexReader.close();
        dict.close();
        return list;
	}
}
