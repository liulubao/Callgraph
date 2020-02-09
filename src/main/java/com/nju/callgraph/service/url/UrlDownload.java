package com.nju.callgraph.service.url;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class UrlDownload {
    /**
     * ????githubUrl???????zip???
     * @param url GitHub?????url
     * @param fileName ?????zip?????
     * @param savePath ???????��??
     * @throws IOException
     */

    public void downLoadFromGithubUrl(String url,String fileName,String savePath) throws IOException{
        //??��???????github api
        String[] name_repo = url.substring(19).split("/");
        String name = name_repo[0];
        String repo = name_repo[1].split("\\.")[0];
        String urlStr = "https://api.github.com/repos/" + name + "/" + repo + "/zipball";
        downLoadFromUrl(urlStr,fileName,savePath);
    }

    /**
     * ??????Url?????????
     * @param urlStr
     * @param fileName
     * @param savePath
     * @throws IOException
     */
    private void  downLoadFromUrl(String urlStr,String fileName,String savePath) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        //???��?????3??
        conn.setConnectTimeout(3*1000);
        //??????��???????????403????
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

        //?????????
        InputStream inputStream = conn.getInputStream();
        //??????????
        byte[] getData = readInputStream(inputStream);

        //???????��??
//        File saveDir = new File("file-service\\"+savePath);
        File saveDir = new File(savePath);
        if(!saveDir.exists()){
            saveDir.mkdir();
        }
        File file = new File(saveDir+File.separator+fileName);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(getData);
        if(fos!=null){
            fos.close();
        }
        if(inputStream!=null){
            inputStream.close();
        }
        conn.disconnect();

        System.out.println("info:"+url+" download success");

    }

    /**
     * ?????????��?????????
     * @param inputStream
     * @return
     * @throws IOException
     */
    private static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }

    /**
     * ???ZIP???
     * @param zipName
     * @param folderName
     * @return
     * @throws IOException
     */
    private static String unZip(String zipName,String folder) throws IOException, URISyntaxException {
        File zipFile=new File(folder+"/"+zipName);
        ZipFile zip = new ZipFile(zipFile,Charset.forName("GBK"));//????????????????
        File pathFile = new File(folder);
        if (!pathFile.exists()) {
            pathFile.mkdirs();
        }
        String folderName=null;
        boolean firstEnter=true;
        for (Enumeration<? extends ZipEntry> entries = zip.entries(); entries.hasMoreElements();) {

            ZipEntry entry = (ZipEntry) entries.nextElement();
            String zipEntryName = entry.getName();
            if(firstEnter)
            {
                folderName=zipEntryName.replace("/", "");
                firstEnter=!firstEnter;
            }
            InputStream in = zip.getInputStream(entry);
//            String outPath = (realName +"/"+ zipEntryName).replaceAll("\\*", "/");
            String outPath = (folder +"/"+ zipEntryName).replaceAll("\\*", "/");

            // ?��?��????????,????????????��??
            File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
            if (!file.exists()) {
                file.mkdirs();
            }
            // ?��?????��???????????,???????????????,????????
            if (new File(outPath).isDirectory()) {
                continue;
            }
            // ??????��?????
            System.out.println(outPath);

            FileOutputStream out = new FileOutputStream(outPath);
            byte[] buf1 = new byte[1024];
            int len;
            while ((len = in.read(buf1)) > 0) {
                out.write(buf1, 0, len);
            }
            in.close();
            out.close();
        }
        zip.close();
        System.out.println("******************??????********************");
        return folderName;
    }

    /**
     * mvn???jar??
     * @param filename
     * @throws URISyntaxException
     */
    public static String transferToJar(String folder, String folderName) throws URISyntaxException
    {
        //??????��???????????cmd???????
        Runtime runtime=Runtime.getRuntime();
        String realName= JustTest.class.getClass().getResource("/").toURI().getPath().substring(1);
        realName=realName.replace("%", " ");
        realName=realName.replace("target/classes/", "");
        realName=realName.replace("/", "\\\\");
        realName=realName+folder+"\\\\"+folderName;
        System.out.println("realName: "+realName+" folder: "+folder+" folderName: "+folderName);
        try {
            System.out.println("开始执行maven命令");
            Process process=runtime.exec("cmd.exe /k cd "+realName+" && mvn install -DskipTests");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line=null;
            while((line=reader.readLine())!=null)
            {
                System.out.println(line );
                System.out.println(line);
                System.out.println(line );
                if(line.contains("Finished at")) {
                    break;
                }
            }
            System.out.println("mvn install ??? 2");

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            return realName;
        }
    }

    public static String getJar(String gitUrl){
        UrlDownload a = new UrlDownload();
        System.out.println("gitUrl:"+gitUrl);
        String zipName="a.zip";
        String folder="testFile";
        String result = null;
        String folderName = null;
        try{
            a.downLoadFromGithubUrl(gitUrl,
                    zipName,folder);
            folderName=unZip(zipName,folder);
            System.out.println("folderName:"+folderName);
//            result = transferToJar(folder,folderName);
            System.out.println("result:"+result);
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            return folderName;
        }
    }

    public static void main(String[] args) {
        UrlDownload a = new UrlDownload();
        String zipName="a.zip";
        String folder="testFile1";
        try{
            a.downLoadFromGithubUrl("https://github.com/xiezhiping/advanced-java.git",
                    zipName,folder);
            String folderName=unZip(zipName,folder);
            System.out.println(folderName);
            transferToJar(folder,folderName);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
