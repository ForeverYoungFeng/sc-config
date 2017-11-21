package com.feng.util;
//iText-5.0.2.jar和iTextAsian.jar
import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;    
import java.io.IOException;    
import java.net.MalformedURLException;


import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.DocumentException;    
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;    
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;


public class Pdf {       
    public static void main(String[] args) {
    	createPDf();
    }
    public static void createPDf(){
    	//第一步：创建一个document对象。    
    	Document document = new Document(PageSize.A4.rotate());    
    	try {   
    		// 第二步：    
    		// 创建一个PdfWriter实例，    
    		// 将文件输出流指向一个文件。    
    		PdfWriter.getInstance(document,new FileOutputStream("E:\\HelloWorld.pdf"));    
    		BaseFont bfChinese = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", false);    
    		Font fontChinese =  new  Font(bfChinese,100,Font.NORMAL, Color.BLACK); 
    		/*Font fontChinese = new Font(bfChinese);   
     		document.add(new Paragraph("HelloWorld", FontFactory.getFont(
     		FontFactory.COURIER, 20, Font.BOLD, new Color(255, 0, 0))));*/
    		// 第三步：打开文档。    
    		document.open();    
    		// 第四步：在文档中增加一个段落。    
    		document.add(new Paragraph("Hello World中文"+","+"Hello iText"+","+"Hello xDuan",fontChinese)); 
    		PdfPTable t = new PdfPTable(3);

    		t.setSpacingBefore(25);
    		t.setSpacingAfter(25);
    		//int[] width = { 15, 15, 15, 15, 15, 25}; 
    		//t.setWidths(width);
    		PdfPCell c1 = new PdfPCell(new Phrase("Header1"));  
    		t.addCell(c1);
    		PdfPCell c2 = new PdfPCell(new Phrase("Header2"));
    		t.addCell(c2);
    		PdfPCell c3 = new PdfPCell(new Phrase("Header3"));
    		t.addCell(c3);
    		t.addCell(new Paragraph("Hello World中文"+","+"Hello iText"+","+"Hello xDuan",fontChinese));
    		t.addCell("1.2");
    		t.addCell("1.3"); 
    		document.add(t);
    	} catch (DocumentException de) {    
    		System.err.println(de.getMessage());    
    	} catch (IOException ioe) {    
    		System.err.println(ioe.getMessage());    
    	}    
    	// 第五步：关闭文档。    
    	document.close();    
    	// 检验程序是否正常运行到这里。    
    	System.out.println("快去看看吧");    
    }
    //itextpdf-5.5.6.jar
    public static void imgToPDf(){
    	Rectangle pageSize = new Rectangle(0,0,123,180);
    	Document pdfDoc = new Document(pageSize.rotate(),0,0,0,0);
    	// 将要生成的 pdf 文件的路径输出流
    	FileOutputStream pdfFile;
		try {
			pdfFile = new FileOutputStream(new File("D:/firstPdf.pdf"));
			// pdf 文件中的一个文字段落
	    	Paragraph paragraph = new Paragraph("My first pdf");
	    	Image image = Image.getInstance("D:/code128.png");
	    	// 用 Document 对象、File 对象获得 PdfWriter 输出流对象
	    	PdfWriter.getInstance(pdfDoc,pdfFile);
	    	pdfDoc.open(); // 打开 Document 文档
	    	image.setAlignment(Image.ALIGN_CENTER); 
	    	image.scaleAbsolute(130,80);
	    	// 添加一个文字段落、一张图片
	    	//pdfDoc.add(paragraph);
	    	pdfDoc.add(image);
	    	pdfDoc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (BadElementException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
    }
} 

