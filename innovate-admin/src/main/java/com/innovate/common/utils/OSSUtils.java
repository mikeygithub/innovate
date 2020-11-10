package com.innovate.common.utils;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.util.UUID;

public class OSSUtils {

    private static String endpoint = "oss-cn-shenzhen.aliyuncs.com";

    private static String accessKeyId = "LTAI0X8ssJ8ezs4C";

    private static String accessKeySecret = "BMbWYHeCOaF8BMCXcgCY2c2TwyPHEs";

    private static String bucketName = "innovate-admin";


    /**
     * 上传文件
     * @param multipartFile
     * @param fileFullPath
     * @return
     */
    public static String upload2OSS(MultipartFile multipartFile, String fileFullPath){

        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {

            if (ossClient.doesBucketExist(bucketName)) {
                System.out.println("您已经创建Bucket：" + bucketName);
            } else {
                System.out.println("您的Bucket不存在，创建Bucket：" + bucketName);
                ossClient.createBucket(bucketName);
            }

            System.out.println(multipartFile.getInputStream());

           ossClient.putObject(bucketName, fileFullPath, multipartFile.getInputStream());

            System.out.println("Object：" + fileFullPath + "存入OSS成功。");

        } catch (OSSException oe) {
            oe.printStackTrace();
        } catch (ClientException ce) {
            ce.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ossClient.shutdown();
        }
        return fileFullPath;
    }

    /**
     * 上传文件到OSS
     * @param multipartFile
     * @return
     */
    public static String upload2OSS(MultipartFile multipartFile){

        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        String fileKey = null;

        try {

            if (ossClient.doesBucketExist(bucketName)) {
                System.out.println("您已经创建Bucket：" + bucketName + "。");
            } else {
                System.out.println("您的Bucket不存在，创建Bucket：" + bucketName + "。");
                ossClient.createBucket(bucketName);
            }

            String fileExt = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".")).toLowerCase();

            fileKey = UUID.randomUUID().toString()+ "/" + multipartFile.getOriginalFilename() + fileExt;

            ossClient.putObject(bucketName, fileKey, multipartFile.getInputStream());

            System.out.println("Object：" + fileKey + "存入OSS成功。");

        } catch (OSSException oe) {
            oe.printStackTrace();
        } catch (ClientException ce) {
            ce.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ossClient.shutdown();
        }
        return fileKey;
    }

    /**
     *
     * @param fileName
     * @param inputStream
     * @return
     */
    public static String upload2OSS(String fileName,InputStream inputStream){

        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        String fileKey = null;

        try {

            if (ossClient.doesBucketExist(bucketName)) {
                System.out.println("您已经创建Bucket：" + bucketName + "。");
            } else {
                System.out.println("您的Bucket不存在，创建Bucket：" + bucketName + "。");
                ossClient.createBucket(bucketName);
            }


            fileKey = fileName;

            ossClient.putObject(bucketName, fileKey, inputStream);

            System.out.println("Object：" + fileKey + "存入OSS成功。");

        } catch (OSSException oe) {
            oe.printStackTrace();
        } catch (ClientException ce) {
            ce.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ossClient.shutdown();
        }
        return fileKey;
    }

    /**
     * 从OBS下载文件
     *
     * @param objectName
     * @return
     */
    public static InputStream downloadFromOSS(String objectName) {

        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        OSSObject ossObject = null;

        try {

            if (ossClient.doesBucketExist(bucketName)) {
                System.out.println("您已经创建Bucket：" + bucketName );
            } else {
                System.out.println("您的Bucket不存在，创建Bucket：" + bucketName );
                ossClient.createBucket(bucketName);
            }

           ossObject  = ossClient.getObject(new GetObjectRequest(bucketName, objectName));

        } catch (OSSException oe) {
            oe.printStackTrace();
        } catch (ClientException ce) {
            ce.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ossClient.shutdown();
        }

        return ossObject.getObjectContent();
    }


    /**
     * 从OBS下载文件
     *
     * @param objectName
     * @return
     */
    public static File downloadFileFromOSS(String objectName) {

        File file = null;

        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            // 临时文件
            file = File.createTempFile(objectName,".tmp");
            // 下载OSS文件到本地文件。如果指定的本地文件存在会覆盖，不存在则新建。
            ossClient.getObject(new GetObjectRequest(bucketName, objectName), file);
            //
        } catch (OSSException oe) {
            oe.printStackTrace();
        } catch (ClientException ce) {
            ce.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ossClient.shutdown();
        }

        return file;
    }
}
