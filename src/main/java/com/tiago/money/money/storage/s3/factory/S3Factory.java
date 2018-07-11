package com.tiago.money.money.storage.s3.factory;

import com.amazonaws.services.s3.model.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;

public class S3Factory {

    public static ObjectMetadata getObjectMetadata(MultipartFile file) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());
        return objectMetadata;
    }

    public static AccessControlList getAcl() {
        AccessControlList acl = new AccessControlList();
        acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
        return acl;
    }

    public static PutObjectRequest getPutObjectRequest(
            String nameBucket, String filename, MultipartFile file ) throws IOException {

        PutObjectRequest putObjectRequest = new PutObjectRequest(
                nameBucket,
                filename,
                file.getInputStream(),
                S3Factory.getObjectMetadata(file)
        ).withAccessControlList(S3Factory.getAcl());

        return putObjectRequest;
    }

    public static ObjectTagging getObjectTag(String nameTag, String situationTag) {
        return new ObjectTagging(Arrays.asList(new Tag(nameTag, situationTag)));
    }
}
