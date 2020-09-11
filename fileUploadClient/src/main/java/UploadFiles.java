import utils.FileDealUtil;
import utils.HttpClientUtil;
import utils.ZipCompress;

import java.io.File;

/**
 * @author ：xuanlong
 * @date ：Created in 2020/9/11 4:33 PM
 * @description 主方法
 * @modified By：
 * @version: 0.0.1
 */
public class UploadFiles {
    public static void main(String[] args) {
        //1.输入待上传文件的文件夹目录，
        //2.压缩文件
        //3.拆分文件
        //4.多线程方式上传文件 TODO
        //5.所有文件上传成功后执行文件合并 TODO
        String pathSrc = "/Users/xuanlong/Desktop/项目文档";
        String zipFileName = pathSrc + ".zip";
        String destFileSrc = pathSrc + "/result/";
        ZipCompress zipCompress = new ZipCompress(zipFileName, pathSrc);
        try {
            zipCompress.zip();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        FileDealUtil.splitZip(zipFileName, destFileSrc);

        File destFile = new File(destFileSrc);

        String serverDestFilePath = "/Users/xuanlong/Desktop/download/";
        for (File file : destFile.listFiles()) {
            HttpClientUtil.postUploadFile(serverDestFilePath, file);
        }
    }
}
