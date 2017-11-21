package com.feng.test;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

public class FileUtil {

	/**
	 * 创建目录
	 * 
	 * @param destDirName 目标目录名
	 * @return 目录创建成功返回true，否则返回false
	 */
	public static boolean createDir(String destDirName) {
		boolean rst = false;
		if (destDirName != null && destDirName.isEmpty() == false) {
			if (destDirName.endsWith(File.separator) == false) {
				destDirName = destDirName + File.separator;
			}
			File dir = new File(destDirName);
			if (dir.exists()) {
				rst = true;
			} else {
				// 创建目录
				if (dir.mkdirs()) {
					rst = true;
				} else {
					rst = false;
				}
			}
		}
		return rst;
	}
	
	/**
	 * 拷贝一个文件
	 * @param from
	 * @param to
	 * @return
	 */
	public static boolean copyFile(String from, String to){
		boolean result = false;
		//
		try {
			File file = new File(from);
			if( file.exists() ){
				createFile(to);
				//
				InputStream in = new FileInputStream(from);
				FileOutputStream out = new FileOutputStream(to);
				int bytereade = 0;
				byte[] buffer = new byte[2012];
				while ( (bytereade=in.read(buffer)) != -1 ) {
					out.write(buffer, 0, bytereade);
				}
				out.flush();
				try { in.close(); in=null; } catch (Exception e) {}
				try { out.close(); out=null; } catch (Exception e) {}
				result = true;
			} else {
				System.out.println("文件不存在："+from);
			}
		} catch (Exception e) {
			result = false;
			e.printStackTrace();
		}
		//
		return result;
	}
	
	/**
	 * 创建文件，存在则不创建
	 * @param filePath  //文件完整路径
	 */
	public static boolean createFile(String filePath){
		boolean result = false;
		try{
			File file = new File(filePath);
			if( !file.exists() ){
				File parentDir = new File(file.getParent());//要创建的文件所在的文件夹
				if(!parentDir.exists()){ parentDir.mkdirs(); }//文件夹不存在则创建
				result = file.createNewFile();//创建文件
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 删除文件
	 * 
	 * @param filePathAndName
	 *            String 文件路径及名称 如c:/fqf.txt
	 * @param fileContent
	 *            String
	 * @return boolean
	 */
	public static void delFile(String filePathAndName) {
		try {
			String filePath = filePathAndName;
			filePath = filePath.toString();
			java.io.File myDelFile = new java.io.File(filePath);
			myDelFile.delete();

		} catch (Exception e) {
			System.out.println("删除文件操作出错");
			e.printStackTrace();
		}
	}
	
	/**
	 * 修改扩展名为
	 * @param fileName
	 * @param toExt
	 * @return
	 */
	public static String changeExtTo(String fileName, String toExt){
		String rst = fileName;
		int idx = fileName.lastIndexOf(".");
		fileName = fileName.substring(0, idx);
		rst = fileName +"."+ toExt;
		return rst;
	}
	
	/**
	 * 获取不带扩展名的文件名
	 * @param fileName
	 * @return
	 */
	public static String getFileNameNoExt(String fileName){
		String rst = fileName;
		int idx = fileName.lastIndexOf(".");
		if(idx > 0){
			rst = fileName.substring(0, idx);
		}
		return rst;
	}

	/**
	 * 读取到字节数组0
	 * 
	 * @param filePath //路径
	 * @throws IOException
	 */
	@SuppressWarnings("resource")
	public static byte[] getContent(String filePath) throws IOException {
		File file = new File(filePath);
		long fileSize = file.length();
		if (fileSize > Integer.MAX_VALUE) {
			System.out.println("file too big...");
			return null;
		}
		FileInputStream fi = new FileInputStream(file);
		byte[] buffer = new byte[(int) fileSize];
		int offset = 0;
		int numRead = 0;
		while (offset < buffer.length
				&& (numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0) {
			offset += numRead;
		}
		// 确保所有数据均被读取
		if (offset != buffer.length) {
			throw new IOException("Could not completely read file "
					+ file.getName());
		}
		fi.close();
		return buffer;
	}

	/**
	 * 读取到字节数组1
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public static byte[] toByteArray(String filePath) throws IOException {

		File f = new File(filePath);
		if (!f.exists()) {
			throw new FileNotFoundException(filePath);
		}
		ByteArrayOutputStream bos = new ByteArrayOutputStream((int) f.length());
		BufferedInputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(f));
			int buf_size = 1024;
			byte[] buffer = new byte[buf_size];
			int len = 0;
			while (-1 != (len = in.read(buffer, 0, buf_size))) {
				bos.write(buffer, 0, len);
			}
			return bos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			bos.close();
		}
	}

	/**
	 * 读取到字节数组2
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public static byte[] toByteArray2(String filePath) throws IOException {

		File f = new File(filePath);
		if (!f.exists()) {
			throw new FileNotFoundException(filePath);
		}

		FileChannel channel = null;
		FileInputStream fs = null;
		try {
			fs = new FileInputStream(f);
			channel = fs.getChannel();
			ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
			while ((channel.read(byteBuffer)) > 0) {
				// do nothing
				// System.out.println("reading");
			}
			return byteBuffer.array();
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				channel.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				fs.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Mapped File way MappedByteBuffer 可以在处理大文件时，提升性能
	 * 
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	public static byte[] toByteArray3(String filePath) throws IOException {

		FileChannel fc = null;
		RandomAccessFile rf = null;
		try {
			rf = new RandomAccessFile(filePath, "r");
			fc = rf.getChannel();
			MappedByteBuffer byteBuffer = fc.map(MapMode.READ_ONLY, 0,
					fc.size()).load();
			//System.out.println(byteBuffer.isLoaded());
			byte[] result = new byte[(int) fc.size()];
			if (byteBuffer.remaining() > 0) {
				// System.out.println("remain");
				byteBuffer.get(result, 0, byteBuffer.remaining());
			}
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				rf.close();
				fc.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}