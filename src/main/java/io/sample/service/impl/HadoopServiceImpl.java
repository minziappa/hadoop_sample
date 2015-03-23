package io.sample.service.impl;

import io.sample.bean.CommonBean;
import io.sample.bean.DirectoryBean;
import io.sample.bean.RootDirectoryBean;
import io.sample.bean.model.HadoopGameModel;
import io.sample.bean.model.MasterSampleModel;
import io.sample.dao.SlaveDao;
import io.sample.service.HadoopService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.io.IOUtils;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FsShell;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.util.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HadoopServiceImpl implements HadoopService {

	final Logger logger = LoggerFactory.getLogger(HadoopServiceImpl.class);
	private static final String NEWLINE = "\r\n";

	@Autowired
	private CommonBean commonbean;
	@Autowired
    private Configuration configuration;
	@Autowired
	private SqlSession slaveDao;

	private org.apache.hadoop.conf.Configuration conf;
	private FileSystem dfs;
	private Path filenamePath;
	private FSDataInputStream fsIn;
	private InputStreamReader insr;
	private BufferedReader br;

	public HadoopServiceImpl() {

	}

	/**
     * Get the game list when the Tomcat start.
     * 
     * 
     * @throws  Exception
     *          If a error occur, ...
     *
     * @since  1.7
     */
	@PostConstruct
	public void init() throws SQLException {

		List<HadoopGameModel> masterSampleList = null;

		try {
			Map<String, Object> mapSelectList = new HashMap<String, Object>();

			masterSampleList = slaveDao.getMapper(SlaveDao.class).selectGameList(mapSelectList);
			if(masterSampleList != null) {

				for(HadoopGameModel masterSample : masterSampleList) {
					logger.info("key >>>> " + masterSample.getGameId());
				}

			}
			commonbean.setHadoopGameList(masterSampleList);

		} catch (Exception e) {
			logger.error("Exception error", e);
		}
	}

	public FileSystem openFileSystem() {

		try {

			logger.info("openFileSystem start");

			conf = new org.apache.hadoop.conf.Configuration();
			conf.set("fs.default.name", configuration.getString("hdfs.url"));
			dfs = FileSystem.get(conf);

		} catch (Exception e) {
			logger.error("Exception error >>> ", e);
		}

		return dfs;
	}

	public BufferedReader readFiles(String path) {

		try {

			filenamePath = new Path(path);
			fsIn = dfs.open(filenamePath);
			insr = new InputStreamReader(fsIn);
	        br = new BufferedReader(insr);
		} catch (Exception e) {
			logger.error("Exception error >>> ", e);
		}

		return br;
	}

	@Override
	public MasterSampleModel getMasterSample(String key) throws Exception {

		// I will use a algorithm.
		List<MasterSampleModel> sampleList = commonbean.getMasterSampleList();
		MasterSampleModel apnsGame = null;
		for(MasterSampleModel apnsGameModel : sampleList) {
			if (apnsGameModel.getMasterId().equals(key)) {

				apnsGame = apnsGameModel;
				break;
			}
		}

		return apnsGame;
	}

	public String readLogsOne(String searchWord) throws Exception {

		// org.apache.commons.io.IOUtils
		byte[] fileBytes = IOUtils.toByteArray(fsIn);
		fsIn.read(fileBytes);

		//create string from byte array
		String strFileContent = new String(fileBytes);

		return extractLogsTag(strFileContent, searchWord);
	}

	public String readLogsTwoSingle(String searchWord) throws Exception {

		String findStr = ".*" + searchWord + ".*";

        BufferedReader br=new BufferedReader(new InputStreamReader(fsIn));

        String s;
		StringBuffer sb = new StringBuffer();
        while ((s = br.readLine()) != null){
        	if (s.matches(findStr)) {
        		sb.append(s).append("<br/>");
        	}
        }

        return sb.toString();
	}

	public String readLogsTwoPlural(String path, String searchWord) {

		String[] multiWord = searchWord.split(" ");

        String s;
        String findStr;
		StringBuffer sb = new StringBuffer();

		this.openFileSystem();
		this.readFiles(path);

		try {
	        while ((s = br.readLine()) != null){
	    		for (int i=0 ; i < multiWord.length; i++) {
	        		logger.debug("multiWord[" + i + "] >>> " + multiWord[i]);
	    			findStr = ".*" + multiWord[i] + ".*";
	            	if (s.matches(findStr)) {
	            		if((multiWord.length-1) == i) {
	            			logger.debug("last i >>> " + i);
	            			sb.append(s);
	            		}
	            	} else {
	            		break;
	            	}
	    		}
	        }
		} catch (Exception e) {
			logger.error("Eeception error >> ", e);
		} finally {
	        this.destroy();
		}

        return sb.toString();
	}

	
	
	public String readLogsThreePlural(String path, String searchWord, String searchFlag) throws Exception {

		logger.info("blnSearch is " + searchFlag);

        String findStr;
        String findStr2;
		StringBuffer sb = new StringBuffer();

		FSDataInputStream fsIn =  null;
		FSDataInputStream fsIn2 =  null;
		BufferedReader br = null;
		BufferedReader br2 = null;

		String[] multiWord = searchWord.split(" ");

		try {

			this.openFileSystem();
			// You need to pass in your HDFS path
			FileStatus[] status = this.dfs.listStatus(new Path(path));  

	        for (int i=0;i<status.length;i++){

	        	if(!status[i].isDir()) {

	        		if(status[i].getBlockSize() > 128*1024*1024) {
	        			logger.warn("There is Big data in a diretory. file is " + status[i].getPath());
	        		}

		        	fsIn =  dfs.open(status[i].getPath());
		        	// Add the logic to check if there are files.
		        	br = new BufferedReader(new InputStreamReader(fsIn));

		            String line;
		            while ((line=br.readLine()) != null){
			    		for (int n=0 ; n < multiWord.length; n++) {
			        		logger.debug("multiWord[" + n + "] >>> " + multiWord[n]);
			    			findStr = ".*" + multiWord[n] + ".*";
			            	if (line.matches(findStr)) {
			            		if((multiWord.length-1) == n) {
			            			logger.debug("last n >>> " + n);
			            			sb.append(line).append(NEWLINE);
			            		}
			            		logger.info("Let me think about the Searched size is " + sb.length()/1024 + "kilobyte");

			            		if(sb.length() > 2*1024*1024 && !searchFlag.equals("true")) {
				            		logger.error("1 Data size limit exceeded, size=" + sb.length()/1024/1024 + " Megabyte");
				            		return null;
			            		}

			            	} else {
			            		break;
			            	}
			    		}
		            }

	        	} else {
	        		Path path2 = status[i].getPath();
	        		FileStatus[] status2 = this.dfs.listStatus(path2);

	        		for (int n=0;n<status2.length;n++){

	                	if(!status2[n].isDir()) {

	                		if(status[i].getBlockSize() > 128*1024*1024) {
	                			logger.warn("There is Big data in diretorys. file is " + status[i].getPath());
	                		}

	                    	fsIn2 =  dfs.open(status2[n].getPath());
	                        br2 = new BufferedReader(new InputStreamReader(fsIn2));

	                        String line2;
	        	            while ((line2=br2.readLine()) != null){
	        		    		for (int m=0 ; m < multiWord.length; m++) {
	        		        		logger.debug("multiWord[" + m + "] >>> " + multiWord[m]);
	        		    			findStr2 = ".*" + multiWord[m] + ".*";
	        		            	if (line2.matches(findStr2)) {
	        		            		if((multiWord.length-1) == n) {
	        		            			sb.append(line2).append(NEWLINE);
	        		            		}
	        		            	} else {
	        		            		break;
	        		            	}

				            		if(sb.length() > 2*1024*1024 && !searchFlag.equals("true")) {
					            		logger.error("2 Data size limit exceeded, size=" + sb.length()/1024/1024 + " Megabyte");
					            		return null;
				            		}

	        		    		}
	        	            }
	                	} else {
	                		sb.append("Please, go to the one more step.");
	                	}
	        		}
	        	}
	        }

		} catch (Exception e) {
			logger.error("Exception >> ", e);
		} finally {
			// Close Reader
			if(br != null) {
				br.close();				
			}
			if(fsIn != null) {
				fsIn.close();
			}
			// Close Stream
			if(fsIn2 != null) {
				fsIn2.close();
			}
			if(br2 != null) {
				br2.close();				
			}
		}
        return sb.toString();
	}

	public String readLogsThree(String[] args) {

		// (?i) <- Not to distinguish between capital letter and small letter "Search for String"
		// .*   <- To search things where the string line information is.
		String findStr = "(?i).*" + args[0] + ".*";
		int lineNumber = 1;

		StringBuffer sb = new StringBuffer();
		try {
			////////////////////////////////////////////////////////////////
			BufferedReader in = new BufferedReader(new FileReader(args[1]));
			String s;

			while ((s = in.readLine()) != null) {
				if (s.matches(findStr))
					System.out.format("%3d: %s%n", lineNumber, s);
					sb.append(s).append("<br/>");
					lineNumber++; // increase line number
			}
			in.close();
			////////////////////////////////////////////////////////////////
		} catch (IOException e) {
			System.err.println(e); // 
			System.exit(1);
		} catch (PatternSyntaxException e) { // 
			System.err.println(e);
			System.exit(1);
		}

		return sb.toString();
	}

	public String extractLogsTag(String contenfFile, String strSearch) {

	    Pattern p = Pattern.compile(".*" + strSearch + ".*", Pattern.MULTILINE);
	    Matcher m = p.matcher(contenfFile);

	    StringBuffer sb = new StringBuffer();
	    while(m.find()) {
		    // extractHashTag = sepcialCharacter_replace(m.group());
		    sb.append(m.group()).append("<br>");
	    }

	    return sb.toString();
	}

	public List<DirectoryBean> directoryList(String path) throws Exception {

		this.openFileSystem();

	    // You need to pass in your HDFS path
		FileStatus[] status = this.dfs.listStatus(new Path(path));
		List<DirectoryBean> directoryList = new ArrayList<DirectoryBean>();

        for (int i=0;i<status.length;i++){
        	DirectoryBean directory = new DirectoryBean();

    	    String parentPath = status[i].getPath().getParent().toString();
        	directory.setPath(getFullName(parentPath, status[i].getPath().getName()));

        	directory.setName(status[i].getPath().getName());
            if (!status[i].isDir()) {
            	directory.setType("file");
            	directory.setSize(StringUtils.byteDesc(status[i].getLen()));
            	directory.setReplication(Short.toString(status[i].getReplication()));
            	directory.setBlockSize(StringUtils.byteDesc(status[i].getBlockSize()));
            } else {
            	directory.setType("dir");
            	directory.setSize("");
            	directory.setReplication("");
            	directory.setBlockSize("");
            }
            directory.setModificationTime(FsShell.dateForm.format(new Date((status[i].getModificationTime()))));
            directory.setPermission(status[i].getPermission().toString());
            directory.setOwner(status[i].getOwner());
            directory.setGroup(status[i].getGroup());
            directoryList.add(directory);
        }

        this.destroy();

        return directoryList;
	}

	public List<RootDirectoryBean> rootDirectory(String path) throws Exception {

		logger.debug("path >> " + path);
		String[] eachPath = path.split("/");
		StringBuffer sb = new StringBuffer();
		List<RootDirectoryBean> rootDirList = new ArrayList<RootDirectoryBean>();
		for(int i=1; i<eachPath.length-1; i++) {
			RootDirectoryBean rootDir = new RootDirectoryBean();
			rootDir.setPath(sb.append("/").append(eachPath[i]).toString());
			logger.debug("each path >> " + rootDir.getPath());
			rootDir.setName(eachPath[i]);
			rootDirList.add(rootDir);
		}

		return rootDirList;
	}

	/**
	* Get the string representation of the full path name
	* @param parent the parent path
	* @return the full path in string
	*/
	public String getFullName(String parent, String localNamePath) {

		String[] fullNameSplit = parent.split(":9000");

	    StringBuilder fullName = new StringBuilder(fullNameSplit[1]);
	    if (!parent.endsWith(Path.SEPARATOR)) {
	    	fullName.append(Path.SEPARATOR);
	    }
	    fullName.append(localNamePath);
	    return fullName.toString();
	}

	public String sepcialCharacter_replace(String str) {
	    str = org.apache.commons.lang.StringUtils.replaceChars(str, "-_+=!@#$%^&*()[]{}|\\;:'\"<>,.?/~`） ","");

	    if(str.length() < 1) {
	    	return null;
	    }
	    return str;
	}

	@PreDestroy
	public void destroy() {

		try {
			if(br != null) {
				br.close();
			}
			if(insr != null) {
				insr.close();
			}
			if(fsIn != null) {
				fsIn.close();
			}
			if(dfs != null) {
				dfs.close();
			}
		} catch (IOException e) {
			logger.error("IOException error >> " + e);
		}
	}

}