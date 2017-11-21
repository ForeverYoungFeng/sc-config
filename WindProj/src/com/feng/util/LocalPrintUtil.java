package com.feng.util;
//jbarcode-0.2.8.jar
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.Size2DSyntax;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.OrientationRequested;
import javax.print.attribute.standard.PrintQuality;
import javax.print.attribute.standard.PrinterName;

import org.jbarcode.JBarcode;
import org.jbarcode.encode.Code128Encoder;
import org.jbarcode.paint.BaseLineTextPainter;
import org.jbarcode.paint.WidthCodedPainter;

public class LocalPrintUtil {
	//生成条码
	public static void print(String dzCode,String name,String xh,String imgPath){
        try {
            JBarcode localJBarcode = new JBarcode(Code128Encoder.getInstance(),WidthCodedPainter.getInstance(),BaseLineTextPainter.getInstance());
            localJBarcode.setShowCheckDigit(false);
            BufferedImage localBufferedImage = localJBarcode.createBarcode(dzCode);

            BufferedImage img = new BufferedImage(450, 250, BufferedImage.TYPE_INT_RGB);
            Graphics g = img.getGraphics();
            g.setColor(Color.white);
            g.fillRect(0,0,500,300);
            g.setColor(Color.BLACK);
            g.setFont(new Font("黑体",Font.PLAIN,20));
            g.drawString("装备名称："+name,35,40);
            g.drawString("装备型号："+xh, 35,70);
            g.drawImage(localBufferedImage,25,130,400,120,Color.white, null);

            String fileName=imgPath+"/"+dzCode+".png";
            File file=new File(fileName);

            OutputStream os = new BufferedOutputStream(new FileOutputStream(file));
            ImageIO.write(img,"PNG",os);

            //开始打印标签
            DocFlavor dof = null;
            if (fileName.endsWith(".gif")) {
                dof = DocFlavor.INPUT_STREAM.GIF;
            } else if (fileName.endsWith(".jpg")) {
                dof = DocFlavor.INPUT_STREAM.JPEG;
            } else if (fileName.endsWith(".png")) {
                dof = DocFlavor.INPUT_STREAM.PNG;
            }
            PrintService[] services = PrintServiceLookup.lookupPrintServices(dof,null);
            PrintService ps = null;
            if (services != null && services.length > 0) {
                String printerName = "GK888t";
                for (PrintService service : services){
                    String pname=service.getAttribute(PrinterName.class).getValue();
                    if (pname.indexOf(printerName)>=0) {
                        ps = service;
                        break;
                    }
                }
            }
            PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
            //设置横向还是纵向打印
            pras.add(OrientationRequested.PORTRAIT);
            //打印份数
            pras.add(new Copies(1));
            //打印的质量
            pras.add(PrintQuality.HIGH);
            DocAttributeSet das = new HashDocAttributeSet();
            //设置打印区域大小
            MediaPrintableArea mpa=new MediaPrintableArea(30,3,53,35,Size2DSyntax.MM);
            das.add(mpa);
            FileInputStream fin = new FileInputStream(fileName);
            Doc doc = new SimpleDoc(fin,dof,das);
            DocPrintJob job = ps.createPrintJob();
            job.print(doc,pras);
            fin.close();
            if(file.exists()){
                file.delete();
            }
        }catch (Exception localException) {
            localException.printStackTrace();
        }
    }
}
