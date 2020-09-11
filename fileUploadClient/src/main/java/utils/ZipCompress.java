package utils;

/**
 * @author ：xuanlong
 * @date ：Created in 2020/9/11 3:22 PM
 * @description：TODO
 * @modified By：
 * @version: 0.0.1
 */

import java.io.*;
import java.util.zip.*;

public class ZipCompress {
    /**
     * 目的地Zip文件
     */
    private String zipFileName;
    /**
     * 源文件（带压缩的文件或文件夹)
     */

    private String sourceFileName;

    public ZipCompress(String zipFileName, String sourceFileName) {
        this.zipFileName = zipFileName;
        this.sourceFileName = sourceFileName;
    }

    public void zip() throws Exception {
        //File zipFile = new File(zipFileName);
        System.out.println("压缩中...");

        //创建zip输出流
        ZipOutputStream out = null ;

        //创建缓冲输出流
        BufferedOutputStream bos = null;

        File sourceFile;

        //调用函数
        try {
            out = new ZipOutputStream(new FileOutputStream(zipFileName));
            bos = new BufferedOutputStream(out);
            sourceFile = new File(sourceFileName);
            compress(out, bos, sourceFile, sourceFile.getName());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(bos!=null){
                bos.close();
            }
            if(out!=null){
                out.close();
            }
        }

        System.out.println("压缩完成");

    }

    public void compress(ZipOutputStream out, BufferedOutputStream bos, File sourceFile, String base)throws Exception {
        //如果路径为目录（文件夹）
        if (sourceFile.isDirectory()) {

            //取出文件夹中的文件（或子文件夹）
            File[] flist = sourceFile.listFiles();

            if (flist.length == 0) {
                System.out.println(base + "/");
                out.putNextEntry(new ZipEntry(base + "/"));
            } else {
                for (int i = 0; i < flist.length; i++) {
                    compress(out, bos, flist[i], base + "/" + flist[i].getName());
                }
            }
        } else//如果不是目录（文件夹），即为文件，则先写入目录进入点，之后将文件写入zip文件中
        {
            FileInputStream fos = null;
            BufferedInputStream bis = null;
            out.putNextEntry(new ZipEntry(base));


            int tag;
            System.out.println(base);

            try {
                fos = new FileInputStream(sourceFile);
                bis = new BufferedInputStream(fos);
                //将源文件写入到zip文件中
                while ((tag = bis.read()) != -1) {
                    bos.write(tag);
                }
            } catch (IOException e) {
                System.out.println("请检查文件目录是否输入正确：" + sourceFile);
                System.exit(0);
            } finally {
                if (bis != null) {
                    bis.close();
                }
                if (fos != null) {
                    fos.close();
                }
            }
        }
    }

    public static void main(String[] args) {
        ZipCompress zipCompress = new ZipCompress("/Users/xuanlong/Desktop/result.zip", "/Users/xuanlong/Desktop/项目文档");
        try {
            zipCompress.zip();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }
}
