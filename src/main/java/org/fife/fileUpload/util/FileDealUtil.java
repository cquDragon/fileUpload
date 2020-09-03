package org.fife.fileUpload.util;


import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @author ：xuanlong
 * @date ：Created in 2020/7/22 10:47 AM
 * @description：TODO
 * @modifiedby ：xuanlong
 * @version: 0.0.1
 */
public class FileDealUtil {


    public static final int SPLIT_ONE_FILE_SIZE = 1024 * 1024 * 2;


    public static synchronized int splitZip(String fileSrc, String destSrc) {
        File file = new File(fileSrc);
        if (!file.exists()) {
            System.out.println("文件不存在！");
            return 0;
        }
        //需要拆分文件大小
        long countFileSize = file.length();
        //拆分后一个文件大小
        //统计zip文件被分割的个数
        int partNum;
        if (countFileSize % SPLIT_ONE_FILE_SIZE == 0) {
            partNum = (int) (countFileSize / SPLIT_ONE_FILE_SIZE);
        } else {
            partNum = (int) (countFileSize / SPLIT_ONE_FILE_SIZE) + 1;
        }
        System.out.println("分割文件个数：" + partNum);

        InputStream in;
        try {
            in = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(in);
            BufferedOutputStream bos = null;

            byte[] bytes = new byte[SPLIT_ONE_FILE_SIZE];

            for (int i = 0; i < partNum; i++) {
                String newFileSrc = destSrc + "part-" + String.format("%03d", i) + ".zip";
                File newFile = new File(newFileSrc);
                if (!newFile.getParentFile().exists()) {
                    System.out.println("创建文件分割目录！");
                    newFile.getParentFile().mkdirs();
                }

                bos = new BufferedOutputStream(new FileOutputStream(newFile));
                int readSize;
                int count = 0;
                while ((readSize = bis.read(bytes)) != -1) {
                    bos.write(bytes, 0, readSize);
                    bos.flush();
                    count += readSize;
                    if (count >= SPLIT_ONE_FILE_SIZE) {
                        break;
                    }
                }
            }
            bis.close();
            in.close();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return partNum;
    }


    public static ArrayList<String> getSortedFiles(String fileDrectoryPath) {
        File directoryFile = new File(fileDrectoryPath);
        if (!directoryFile.exists()) {
            return null;
        }
        ArrayList<String> filesStr = new ArrayList<>();
        for (File file : directoryFile.listFiles()) {
            if (!file.getName().endsWith("zip")||file.isDirectory()) {
                continue;
            }
            filesStr.add(file.getAbsolutePath());
        }
        Collections.sort(filesStr);
        return filesStr;
    }




    /***
     *
     * @param destMergeFilePath 合并后保存的文件夹目录
     * @param spiltedFilesPath  拆分目标的文件加目录
     */
    public static void mergeFile(String spiltedFilesPath,String destMergeFilePath) {
        ArrayList<String> files = getSortedFiles(spiltedFilesPath);
        File file = new File(destMergeFilePath);
        if (!file.exists()) {
            System.out.println("创建目录"+destMergeFilePath);
            file.mkdirs();
        }
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(destMergeFilePath + "result"+DateUtil.dateTimeToStr(LocalDateTime.now(),DateUtil.DATE_TIME)+".zip")));
            BufferedInputStream bis = null;
            byte[] bytes = new byte[1024 * 1024 * 3];
            int readSize = 0;
            for (int i = 0; i < files.size(); i++) {
                bis = new BufferedInputStream(new FileInputStream(files.get(i)));
                while ((readSize = bis.read(bytes)) != -1) {
                    bos.write(bytes, 0, readSize);
                }
                bos.flush();
            }

            bos.close();
            bis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

 public static void main(String[] args) {
       //* FileDealUtil.splitZip("/Users/xuanlong/Desktop/ppt模版.zip", "/Users/xuanlong/Desktop/test/");*//
     FileDealUtil.splitZip("/Users/xuanlong/Desktop/file-upload-example-1.0.0-SNAPSHOT.jar.zip","/Users/xuanlong/Desktop/test2/");
       // System.out.println(FileDealUtil.getSortedFiles("/Users/xuanlong/Desktop/test/"));
        //FileDealUtil.mergeFile("/Users/xuanlong/Desktop/test/","/Users/xuanlong/Desktop/");
    }
}
