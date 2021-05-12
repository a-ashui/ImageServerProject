package org.example.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Image {
    private Integer imageId;
    private String imageName;
    private Long size;//图片大小
    private String uploadTime;//上传时间
    private String md5;//文件校验唯一性
    private String contentType;//文件格式
    private String path;//路径
}
