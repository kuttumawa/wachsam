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
import com.amazonaws.services.s3.model.CopyObjectRequest;
import com.amazonaws.services.s3.model.CopyObjectResult;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
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
	private String BUCKET_NAME="wachsam-articulos-repository";
	private  String S3_URL_CONTEXT ="https://s3.amazonaws.com/";
	private String acceskey;
	private String secretKey;
	public void s3serviceInit()  {
		ClientConfiguration clientConfig = new ClientConfiguration();
		clientConfig.setProtocol(Protocol.HTTPS);
		clientConfig.setProxyHost("proxybc.mjusticia.es");
		clientConfig.setProxyPort(8080);
		clientConfig.setProxyUsername("dlinares");
		clientConfig.setProxyPassword("Infocentro2032");
		
		credentials= new BasicAWSCredentials(acceskey,secretKey);
		s3client = new AmazonS3Client(credentials,clientConfig);
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
	
	public void renameObject(String bucketName,String oldKeyName,String newKeyName){
		CopyObjectRequest copyObjRequest = new CopyObjectRequest(bucketName, 
				oldKeyName, bucketName, newKeyName);
		CopyObjectResult result=s3client.copyObject(copyObjRequest);
		s3client.deleteObject(new DeleteObjectRequest(bucketName, oldKeyName));
	}

	public String getBUCKET_NAME() {
		return BUCKET_NAME;
	}

	public void setBUCKET_NAME(String bUCKET_NAME) {
		BUCKET_NAME = bUCKET_NAME;
	}

	public String getS3_URL_CONTEXT() {
		return S3_URL_CONTEXT;
	}

	public void setS3_URL_CONTEXT(String s3_URL_CONTEXT) {
		S3_URL_CONTEXT = s3_URL_CONTEXT;
	}

	public String getAcceskey() {
		return acceskey;
	}

	public void setAcceskey(String acceskey) {
		this.acceskey = acceskey;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
	
	
}