package com.care.sekki.S3;



import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class S3UploadService {

	private final AmazonS3 amazonS3;

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	// 생성자 추가
	public S3UploadService(AmazonS3 amazonS3) {
		this.amazonS3 = amazonS3;
	}

<<<<<<< HEAD
	public String saveFile(MultipartFile multipartFile) throws IOException {
		String originalFilename = multipartFile.getOriginalFilename();
=======
    public String saveFile(MultipartFile multipartFile, String memberId) throws IOException {
        String originalFilename = multipartFile.getOriginalFilename();
        
        String folderPath = memberId + "/";
        String objectKey = folderPath + originalFilename;
>>>>>>> refs/remotes/origin/gyutae

		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(multipartFile.getSize());
		metadata.setContentType(multipartFile.getContentType());

<<<<<<< HEAD
		amazonS3.putObject(bucket, originalFilename, multipartFile.getInputStream(), metadata);
		return amazonS3.getUrl(bucket, originalFilename).toString();
	}
=======
        amazonS3.putObject(bucket, objectKey, multipartFile.getInputStream(), metadata);
        return amazonS3.getUrl(bucket, objectKey).toString();
    }
>>>>>>> refs/remotes/origin/gyutae
}
