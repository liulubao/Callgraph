package com.nju.callgraph.service.url;

import gr.gousiosg.javacg.stat.JCallGraph;
import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.junit.Test;

import java.io.*;
import java.net.URISyntaxException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Pattern;


public class JustTest {

	@Test
	public void test() throws URISyntaxException {
		String realName=JustTest.class.getClass().getResource("/").toURI().getPath().substring(1);
		realName=realName.replace("%", " ");
		realName=realName.replace("bin/", "");
		String endRealName=realName.replace("/", "\\\\");
		//��ȡ���л�����׼��ִ��cmd���ڳ���
		Runtime runtime=Runtime.getRuntime();
		System.out.println(realName);
        /*try {
            Process process=runtime.exec("cmd /k cd "+endRealName+"testFile\\JohnAme-code_dep-63d0799 && mvn install");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line=null;
            while((line=reader.readLine())!=null)
            {
            	System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }  */
		//JCallGraph.main(new String[]{endRealName+"testFile\\\\JohnAme-code_dep-63d0799\\\\target\\\\code_dep-1.0-SNAPSHOT.jar"});
		JCallGraph.main(new String[]{"testFile\\\\JohnAme-code_dep-63d0799\\\\target\\\\code_dep-1.0-SNAPSHOT.jar"});
	}

	/**
	 * ����class�ļ�
	 * @throws IOException
	 */
	@Test
	public void test2() throws IOException {
		String fileName="code_dep-1.0-SNAPSHOT.jar";
		File f = new File("testFile"+"/"+fileName);

		if (!f.exists()) {
			System.err.println("Jar file " + fileName + " does not exist");
		}

		try (JarFile jar = new JarFile(f)) {
			Enumeration<JarEntry> entrys = jar.entries();
			while(entrys.hasMoreElements())
			{
				JarEntry jarEntry=entrys.nextElement();
				String jarFileName=jarEntry.getName();
				if(jarFileName.endsWith(".class"))
				{
					System.out.println(jarFileName);
					ClassParser cp = new ClassParser(fileName, jarFileName);
					JavaClass clazz=cp.parse();
					Method[] methods=clazz.getMethods();
					for(int i=0;i<methods.length;i++)
					{
						Method method=methods[i];
						System.out.println(method.getName());
						System.out.println(method.getCode());
					}
				}
			}


		}
	}

	/**
	 * �������ļ�
	 * @throws IOException
	 */
	@Test
	public void testFindCode() throws IOException
	{
		String totalName="com.se.dao.DBSqlSessionFactory:getDBSqlSessionFactory()";
		//��maven��Ŀ���ļ�������
		String folder="testFile";
		//��Ŀ�ļ�������
		String taskName="JohnAme-code_dep-63d0799";
		String resultCode=findCode(totalName, folder, taskName);
		System.out.println(resultCode);
	}

	/**
	 *
	 * @param totalName	Jcallgraph���Ǹ�������ȫ��
	 * @param folder ��maven��Ŀ���ļ�������
	 * @param taskName	��Ŀ��
	 * @return \n�ָ�Ĵ����(�Ҳ����ͷ��ؿ��ַ���)
	 * @throws IOException
	 */
	public String findCode(String totalName,String folder,String taskName) throws IOException
	{
		String[] splitMName=totalName.split(":");
		//�ָ��������ȫ��
		String allName=splitMName[0];
		//�ָ�ĺ������β�
		String methodAndType=splitMName[1];
		//�ָ���β�
		String[] allType=methodAndType.substring(methodAndType.indexOf("(")+1,methodAndType.indexOf(")")).split(",");
		//��ʵ������
		String methodName=methodAndType.substring(0,methodAndType.indexOf("("));
		//��ʵ������ȥ��<init>��Ϊ���췽����
		methodName=methodName.replace("<init>", allName.substring(allName.lastIndexOf(".")+1));
		//ƴ�������ĺ����ַ���
		String totalMethod=methodName+"\\(";

		for(int i=0;i<allType.length;i++)
		{
			String type=allType[i].substring(allType[i].lastIndexOf(".")+1);
			//�ж��ǲ��ǿ�����
			if(type.equals(""))
			{
				totalMethod+="\\)*";
				break;
			}
			//�����ż���ת��
			type=type.replace("[", "\\[");
			type=type.replace("]", "\\]");
			totalMethod+=type+"*";
			if(i<allType.length-1)
				totalMethod+=",";
			else
//				totalMethod+="\\)^;*";
			    totalMethod+="\\)*";
		}
		totalMethod=totalMethod.replace("*", ".*");
		//��ע�ͽ�������
		totalMethod="[^/]*"+totalMethod;
		//�͵�������������
		totalMethod=totalMethod.replace("\\).*", "\\)[^;]*");
		//System.out.println(totalMethod);
		//java�ļ���·��
		String[] folderNames=allName.split("\\.");
		//taskName="javaSource";
		File rootDir=new File(folder+"/"+taskName);
		//�ж��Ƿ�ΪԴ�ļ������������ļ�
		String path=getSrcPath(rootDir,folderNames[0]).getPath().replace("\\", "/")+"/";
		for(int i=1;i<folderNames.length;i++)
		{
			String thisFolder=folderNames[i];
			path+=thisFolder;
			if(i<folderNames.length-1)
				path+="/";
			else
				path+=".java";
		}
		//System.out.println(path);
		//���������ַ���
		String resultCode="";
		//��¼���������ַ���
		String fatherClass="";
		//��¼�˴��Ƿ��ҵ�����
		boolean isFatherExist=true;
		//Ѱ�Һ�����
		while(isFatherExist&&resultCode.equals(""))
		{
			//ÿ�غ�����һ��
			isFatherExist=false;
			//�ж��ǲ��ǵ�һ�ν�ѭ��
			if(!fatherClass.equals(""))
			{
				path=getSrcPath(rootDir,fatherClass+".java").getPath().replace("\\", "/")+"/";
				path=path.replace(".java/", ".java");
			}
			//System.out.println(path);
			FileInputStream in=null;
			try{
				in=new FileInputStream(path);
			}
			catch (IOException ex)
			{
				return null;
			}
			BufferedReader br=new BufferedReader(new InputStreamReader(in));
			int braceCount=0;	//��¼������ƥ�����
			boolean braceFlag=false;	//��¼�Ƿ������������
			boolean flag=false;	//��¼�Ƿ��ҵ�������
			String line=null;
			while((line=br.readLine())!=null)
			{
				if(Pattern.matches(totalMethod, line))
				{
					flag=true;
				}
				//��ǰ�ļ�������
				String className=path.substring(path.lastIndexOf("/")+1,path.indexOf("."));
				if(Pattern.matches(".*class "+className+" extends.*", line))
				{
					//�ո��и��ַ���
					String[] lineArray=line.split("\\s");
					//��¼�ǲ��������
					boolean isThisClass=false;
					for(int i=0;i<lineArray.length;i++)
					{
						String thisWord=lineArray[i];
						if(thisWord.equals(className))
							isThisClass=true;
						if(thisWord.equals("extends")&isThisClass)
						{
							isFatherExist=true;
							fatherClass=lineArray[i+1];
							break;
						}
					}
				}
				if(flag)
				{
					resultCode+=line+"\n";
					for(char word:line.toCharArray())
					{
						if(word == '{')
						{
							braceCount++;
							braceFlag=true;
						}
						if(word == '}')
						{
							braceCount--;
							braceFlag=true;
						}
					}
				}
				if(braceFlag&&flag&&braceCount==0)
					break;
			}
		}
		return resultCode;
	}

	/**
	 * �ݹ����ļ�
	 * @param file �ļ���
	 * @param folderName �ļ���
	 * @return	���ļ���Ŀ¼�����·��
	 */
	public File getSrcPath(File file,String folderName)
	{
		if(file.getName().equals(folderName))
			return file;
		else if(!file.isDirectory())
			return null;
		else
		{
			File[] files=file.listFiles();
			File resultFile=null;
			for(int i=0;i<files.length;i++)
			{
				File thisFile=files[i];
				resultFile=getSrcPath(thisFile,folderName);
				if(resultFile!=null)
					return resultFile;
			}
			return resultFile;
		}
	}



	/**
	 * �����ǵ���ȡ���ļ��е�����
	 * @throws IOException
	 */
	@Test
	public void testGetPomJar() throws IOException
	{
		//��maven��Ŀ���ļ�������
		String folder="testFile";
		//��Ŀ�ļ�������
		String taskName="niloc132-tvguide-sample-parent-ceaf39a";
		Map<String,String> SonAndJarPath=getAllSonAndJarPath(folder, taskName);
		for (Map.Entry<String, String> entry : SonAndJarPath.entrySet()) {
			String resultCode=findCode("com.acme.gwt.data.TvEpisode:getEpisodeNumber()", folder, taskName+"/"+entry.getKey());
			if(resultCode!=null)
			{
				System.out.println(resultCode);
				break;
			}
			else
				continue;
			//JCallGraph.main(new String[]{folder+"/"+taskName+"/"+entry.getKey()+"/target/"+entry.getValue()});

		}


	}

	/**
	 * ȡ���ļ������Լ���Ӧ�Ĵ���õ�jar����
	 * @param folder
	 * @param taskName
	 * @return
	 */
	public Map<String,String> getAllSonAndJarPath(String folder,String taskName)
	{
		Map<String,String> SonAndJarPath=new HashMap<String, String>();
		File rootDir=new File(folder+"/"+taskName);
		File[] sonsFile=rootDir.listFiles();
		for(int i=0;i<sonsFile.length;i++)
		{
			File thisSon=sonsFile[i];
			//���ļ�������
			String sonName=thisSon.getName();
			//targetĿ¼·��
			String path=folder+"/"+taskName+"/"+sonName+"/target";
			File targetFile=new File(path);
			if(!targetFile.exists())
				continue;
			File[] targetFiles=targetFile.listFiles();
			String jarFileName="";
			for(int j=0;j<targetFiles.length;j++)
			{
				File thisTargetFile=targetFiles[j];
				if(thisTargetFile.getName().endsWith(".jar"))
				{
					jarFileName=thisTargetFile.getName();
					break;
				}
			}
			SonAndJarPath.put(sonName, jarFileName);
		}
		return SonAndJarPath;
	}

}
