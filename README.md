# fileUploadServer
文件转成base64上传并保存

## 构建
  gradle clean build
## 启动
  java -jar build/libs/file-upload-example-1.0.0-SNAPSHOT.jar

# fileUploadClient
  ## 功能简介 
     指定本地文件夹，自动压缩成zip包，并进行拆分成多个小的文件后，通过HttpClient上传到服务端
  ## 执行
   导入到IDE，maven构建成功，在主方法类UploadFiles中
   ### 修改HttpClientUtils的post_url为服务端的地址
   ### 指定本地待上传服务端的文件夹目录pathSrc
   ### 指定上传到服务端的文件夹目录serverDestFilePath
