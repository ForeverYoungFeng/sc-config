package com.feng.util;
//依赖jar包：hadoop-auth-2.6.0.jar，hadoop-common-2.6.0.jar，hadoop-hdfs-2.6.0.jar
/*Demo:
	HadoopUtil.java
	/zxflow/src/com/ztesoft/zxflow/app/knowladge/scheduler/DayTemperComputJob.java，
	/zxflow/src/com/ztesoft/zxflow/app/knowladge/servlet/FileServlet.java
Hadoop中配置文件：
core-default.xml, core-site.xml
 
tracert datanode1.zs-hdp.com
nslookup datanode1.zs-hdp.com
core-site.xml内容：
<configuration>
 <property>
  <name>fs.defaultFS</name>
  <value>hdfs://namenode:9000</value>
 </property>
 <property>
  <name>io.file.buffer.size</name>
  <value>131072</value>
 </property>
<property>
 <name>hadoop.tmp.dir</name>
 <value>/home/hadoop/tmp</value>
</property>
</configuration>*/
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
 
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
 
 
public class HadoopUtil{
    /*
     *上传本地文件
     */
    public static void uploadLocalFile2HDFS(String defaultFS,String localPath, String hdfsPath) throws IOException{
        //解决 Could not locate executable null\bin\winutils.exe in the Hadoop binaries
        String wpath = new File(".").getCanonicalPath();
        System.getProperties().put("hadoop.home.dir", wpath);
        new File("./bin").mkdirs();
        new File("./bin/winutils.exe").createNewFile();
 
        /*conf.set("fs.defaultFS", "hdfs://hadoop2cluster");
        conf.set("dfs.nameservices", "hadoop2cluster");
        conf.set("dfs.ha.namenodes.hadoop2cluster", "nn1,nn2");
        conf.set("dfs.namenode.rpc-address.hadoop2cluster.nn1", "10.0.1.165:8020");
        conf.set("dfs.namenode.rpc-address.hadoop2cluster.nn2", "10.0.1.166:8020");
        conf.set("dfs.client.failover.proxy.provider.hadoop2cluster",
		"org.apache.hadoop.hdfs.server.namenode.ha.ConfiguredFailoverProxyProvider");
        */
        Configuration config = new Configuration(true);
        config.set("fs.defaultFS",defaultFS);
        FileSystem hdfs = FileSystem.get(config);
        Path src = new Path(localPath);
        Path dst = new Path(hdfsPath);
        hdfs.copyFromLocalFile(src, dst);
        hdfs.close();
    }
    /*
     * 创建文件
     */
    public static void createNewHDFSFile(String defaultFS,String toCreateFilePath, String content) throws IOException{
        //解决 Could not locate executable null\bin\winutils.exe in the Hadoop binaries
        String wpath = new File(".").getCanonicalPath();
        System.getProperties().put("hadoop.home.dir", wpath);
        new File("./bin").mkdirs();
        new File("./bin/winutils.exe").createNewFile();
 
        Configuration config = new Configuration(true);
        config.set("fs.defaultFS",defaultFS);
        FileSystem hdfs = FileSystem.get(config);
        FSDataOutputStream os = hdfs.create(new Path(toCreateFilePath));
        os.write(content.getBytes("UTF-8"));
        os.close();
        hdfs.close();
    }
    /*
     *删除文件
     */
    public static boolean deleteHDFSFile(String defaultFS,String dst) throws IOException{
        //解决 Could not locate executable null\bin\winutils.exe in the Hadoop binaries
        String wpath = new File(".").getCanonicalPath();
        System.getProperties().put("hadoop.home.dir", wpath);
        new File("./bin").mkdirs();
        new File("./bin/winutils.exe").createNewFile();
 
        Configuration config = new Configuration(true);
        config.set("fs.defaultFS",defaultFS);
        FileSystem hdfs = FileSystem.get(config);
        Path path = new Path(dst);
        boolean isDeleted = hdfs.delete(path);
        hdfs.close();
        return isDeleted;
    }
    //下载文件
    public static boolean GetFile(String defaultFS,File srcFile, String dstFile){
        //解决 Could not locate executable null\bin\winutils.exe in the Hadoop binaries
        String wpath;
        try {
            wpath = new File(".").getCanonicalPath();
            System.getProperties().put("hadoop.home.dir", wpath);
            new File("./bin").mkdirs();
            new File("./bin/winutils.exe").createNewFile();
 
            Configuration config = new Configuration(true);
            config.set("fs.defaultFS",defaultFS);
            FileSystem hdfs = FileSystem.get(config);
            InputStream is=hdfs.open(new Path(dstFile));//读取文件
            IOUtils.copyBytes(is,new FileOutputStream(srcFile),2048, true);
            is.close();
            hdfs.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    /*
     * 创建文件夹
     * the dir may like '/tmp/testdir'
     */
    public static boolean mkdir(String defaultFS,String dir) throws IOException{
        //解决 Could not locate executable null\bin\winutils.exe in the Hadoop binaries
        String wpath = new File(".").getCanonicalPath();
        System.getProperties().put("hadoop.home.dir", wpath);
        new File("./bin").mkdirs();
        new File("./bin/winutils.exe").createNewFile();
        //fs.exists(path))
        Configuration config = new Configuration(true);
        config.set("fs.defaultFS",defaultFS);
        FileSystem fs = FileSystem.get(config);
        boolean result=true;
        if(fs.exists(new Path(dir))){
 
        }else{
            result= fs.mkdirs(new Path(dir));
        }
        fs.close();
 
        return result;
    }
    /*
     *删除文件夹
     * dir may like '/tmp/testdir'
     */
    public static void deleteDir(String defaultFS,String dir) throws IOException{
        String wpath = new File(".").getCanonicalPath();
        System.getProperties().put("hadoop.home.dir", wpath);
        new File("./bin").mkdirs();
        new File("./bin/winutils.exe").createNewFile();
        //fs.deleteOnExit(path);
        Configuration config = new Configuration(true);
        config.set("fs.defaultFS",defaultFS);
        FileSystem fs = FileSystem.get(config);
        fs.delete(new Path(dir));
        fs.close();
    }
    //拷贝hdfs本地文件到另一个文件夹
    public static boolean copyFile(String defaultFS,String filePath,String newFilePath,String pdfPath,String newPdfPath){
 
        String wpath;
        try {
            wpath = new File(".").getCanonicalPath();
            System.getProperties().put("hadoop.home.dir", wpath);
            new File("./bin").mkdirs();
            new File("./bin/winutils.exe").createNewFile();
 
            Configuration config = new Configuration(true);
            config.set("fs.defaultFS",defaultFS);
            FileSystem hdfs = FileSystem.get(config);
            FSDataInputStream is=hdfs.open(new Path(filePath));
            OutputStream out = hdfs.create(new Path(newFilePath));
            IOUtils.copyBytes(is,out,2048, true);
            is.close();
            hdfs.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static void main(String[] args) {
    	String wpath = null;
		try {
			wpath = new File(".").getCanonicalPath();
			System.getProperties().put("hadoop.home.dir", wpath);
	    	new File("./bin").mkdirs();
	    	new File("./bin/winutils.exe").createNewFile();
	    	Configuration config = new Configuration(true);
	    	config.set("fs.defaultFS","hdfs://192.168.2.21:9000/");
	    	FileSystem hdfs = FileSystem.get(config);
	    	String p="hdfs://192.168.2.21:9000/deleteFile/14418672172734679561377993647497.doc";
	    	String dstFile="hdfs://192.168.2.21:9000/sourceFile/1/14418672172734679561377993647497.doc";
	    	FSDataInputStream is=hdfs.open(new Path(dstFile));//读取文件
	    	Path pp=new Path(p);
	    	OutputStream out = hdfs.create(pp);
	    	IOUtils.copyBytes(is,out,2048, true);
	    	is.close();
	    	hdfs.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
