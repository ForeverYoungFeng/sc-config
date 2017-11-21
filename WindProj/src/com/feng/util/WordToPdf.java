package com.feng.util;
//依赖jar包：jodconverter-core-3.0-beta-4.jar
//Demo:/zxflow/src/com/ztesoft/zxflow/app/knowladge/scheduler/DayTemperComputJob.java
import java.io.File;

import org.artofsolving.jodconverter.OfficeDocumentConverter;
import org.artofsolving.jodconverter.office.DefaultOfficeManagerConfiguration;
import org.artofsolving.jodconverter.office.OfficeManager;
/*在线预览技术实现：
（1）openoffice+flexpaper+swftools
（2）openoffice+pdf.jsJodConverter转换txt的时候会出现乱码情况：
DocumentFormat txt = new DocumentFormat("Plain Text", "txt", "text/plain");
txt.setInputFamily(DocumentFamily.TEXT);
Map<String,Object> txtLoadAndStoreProperties = new LinkedHashMap<String,Object>();
txtLoadAndStoreProperties.put("FilterName", "Text (encoded)");
txtLoadAndStoreProperties.put("FilterOptions", "utf8");//关键的就在这了
txt.setLoadProperties(txtLoadAndStoreProperties);
txt.setStoreProperties(DocumentFamily.TEXT, txtLoadAndStoreProperties);
addFormat(txt);*/
public class WordToPdf {
	public void doTrans2pdf(){
		OfficeManager officeManager = null;
		try {
			DefaultOfficeManagerConfiguration configuration = new DefaultOfficeManagerConfiguration();
			// 设置OpenOffice.org安装目录 
			configuration.setOfficeHome("D:\\mySoft\\openOffice");            
			// 设置转换端口，默认为8100
			configuration.setPortNumbers(8100);
			// 设置任务执行超时时间
			configuration.setTaskExecutionTimeout(1000 * 60 * 1L); 
			// 设置任务队列超时时间 1000 * 60 * 60 * 24L(24h) 
			configuration.setTaskQueueTimeout(1000 * 60 * 60L);
			officeManager = configuration.buildOfficeManager();
			// 启动服务
			officeManager.start();
			OfficeDocumentConverter converter = new OfficeDocumentConverter(officeManager);
			//converter.convert(new File(""),new File(""));
			converter.convert(new File("E:\\1.doc"),new File("E:\\1.pdf"));
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			if (officeManager != null){
				//关闭服务
	            officeManager.stop();
	        }
		}
	}
}
