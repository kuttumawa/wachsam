package es.io.wachsam.services;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;

public class S3service{
	AWSCredentials credentials;
	AmazonS3 s3client;
	private static final Logger LOG = Logger.getLogger(S3service.class);
	public S3service()  {
			}
	
	public void createBucket(String bucketName) {		
		s3client.createBucket(bucketName);
	}
	private String makeMessage(String bucketName,String folderName){
		return "Datos[bucketName: " + bucketName + ",folderName: " + folderName;
	}
	public void createFolder(String bucketName,String folderName,Map<String,String> userMetadata) {
		LOG.info(makeMessage(bucketName,folderName));
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(0);
		metadata.setUserMetadata(userMetadata);
		InputStream emptyContent = new ByteArrayInputStream(new byte[0]);
		PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName,
					folderName, emptyContent, metadata);
		PutObjectResult result=s3client.putObject(putObjectRequest);
		LOG.info("Creada carpeta:  MD5" + result.getContentMd5());
	
	}
	public List<String> listBuckets(){
		List<String> buckets=new ArrayList<String>();
		for (Bucket bucket : s3client.listBuckets()) {
			buckets.add(bucket.getName());
			
		}
		return buckets;
	}
	public void uploadFile(String bucketName,String key,InputStream in,Map<String,String> userMetadata,String contentType){
		LOG.info(makeMessage(bucketName,key));
		ObjectMetadata metadata=new ObjectMetadata();
		metadata.setUserMetadata(userMetadata);
		metadata.setContentType(contentType);
		metadata.setContentDisposition("attachment; filename="+key+";");
		PutObjectResult result=null;
		result= s3client.putObject(new PutObjectRequest(bucketName, key, 
				in,metadata));
		LOG.info(result);
	}
	public void  changeObjectACLPublic(String bucketName,String key){
		s3client.setObjectAcl(bucketName, key, CannedAccessControlList.PublicRead);
	}
	
	public void  changeObjectACLPrivate(String bucketName,String key){
		s3client.setObjectAcl(bucketName, key, CannedAccessControlList.Private);
	}
	
	public void  downloadObjectToFile(String bucketName,String fileName,File out){
	    s3client.getObject(
		        new GetObjectRequest(bucketName, fileName),
		        out
		);
	}
    public InputStream  downloadObjectToInput(String bucketName,String key,File out){
    	S3Object object = s3client.getObject(
                new GetObjectRequest(bucketName, key));
        InputStream objectData = object.getObjectContent();
		try {
			objectData.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return objectData;
	}
	
	
	public void deleteObject(String bucketName,String key){
		s3client.deleteObject(bucketName,key);
		
	}
	
	public  void deleteFolderContentAndFolder(String bucketName,String folderKey) {
		List<S3ObjectSummary> fileList = s3client.listObjects(bucketName, folderKey).getObjectSummaries();
		for (S3ObjectSummary file : fileList) {
			s3client.deleteObject(bucketName, file.getKey());
		}
		s3client.deleteObject(bucketName, folderKey);
	}
	
	public URL generateObjectUrl(String bucketName,String key){
		GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName, key);
		request.getExpiration();
		return s3client.generatePresignedUrl(request);
		
	}
	
	
}