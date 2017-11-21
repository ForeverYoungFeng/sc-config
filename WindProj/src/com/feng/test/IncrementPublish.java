package com.feng.test;

import java.io.File;


public class IncrementPublish {
	
	public static void main(String[] args) {
		/**
		 * 1. eclipse同步项目；
		 * 2. 通过 TortoiseSVN 的“show log”功能查看版本库；
		 * 3. 选择要做增量的版本号，然后右键选择“Compare revisions”会显示“Changed Files”列表；
		 * 注意：更新的开始版本要是上一次更新的结束版本到需要的版本
		 * 4. 全选或部分选择，然后右键选择“Export selection to...”，然后导出到指定的文件夹;
		 * 
		 * 至此，java版本（src）及资源文件（WebContent）的增量成功导出到指定文件夹；
		 * 
		 * 5. 通过 createIncrement 方法把增量java文件对应的class文件及资源文件拷贝到新的增量中
		 */
		String classesPath = "D:/apache-tomcat-run/apache-tomcat-zxflow/webapps/TTS2/WEB-INF/classes/";
		String javaIncrement = "E:/2018/";
		createIncrement(classesPath, javaIncrement);
		System.out.println("finish.");
	}
	
	/**
	 * 生成增量 classes 到 WebContent/WEB-INF/classes 中
	 * @param classesPath  -- 项目的 classes 路径
	 * @param javaIncrement  -- SVN导出的增量文件路径，文件一般包括src和WebContent两个文件夹
	 */
	public static void createIncrement(String classesPath, String javaIncrement){
		String svnClasses = javaIncrement +"WebContent/WEB-INF/classes/";
		FileUtil.createDir(svnClasses);//创建 SVN classes 目录
		String srcPath = javaIncrement +"src/";
		createPackageAndFiles(classesPath, srcPath, svnClasses, "");
	}
	/** 创建 package 及其文件 **/
	private static void createPackageAndFiles(String classesPath, String srcPath,
			String svnClasses, String packages){
		String packagePath = srcPath + packages;
		String fromPath = classesPath + packages;
		String toPath = svnClasses + packages;
		//
		File packageFile = new File(packagePath);
		File[] children = packageFile.listFiles();
		String newPackages = null;
		String fileName = null;
		for(File child : children){
			fileName = child.getName();
			if( child.isDirectory() ){
				//文件夹（包）
				newPackages = packages + fileName + File.separator;
				createPackageAndFiles(classesPath, srcPath, svnClasses, newPackages);
			} else {
				//文件
				if(fileName.endsWith(".java")){
					//转换扩展名为java的文件，其他文件原样拷贝
					fileName = FileUtil.changeExtTo(fileName, "class");
				}
				FileUtil.copyFile(fromPath+fileName, toPath+fileName);
				copyInnerClass(fromPath, toPath, fileName);
			}
		}
	}
	/** 拷贝文件对应的内部类 **/
	private static void copyInnerClass(String fromPath, String toPath, String fileName){
		File fromPathDirectory = new File(fromPath);
		File[] children = fromPathDirectory.listFiles();
		String tempName = null;
		String fileNameNoExt = FileUtil.getFileNameNoExt(fileName)+"$";
		for(File child : children){
			tempName = child.getName();
			if(tempName.startsWith(fileNameNoExt)){
				tempName = FileUtil.changeExtTo(tempName, "class");
				FileUtil.copyFile(fromPath+tempName, toPath+tempName);
			}
		}
	}
}
