package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.example.dao.ImageDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 1. @WebServlet
 * 2. 继承HttpServlet
 * 3. 重写doXXX方法
 */
@WebServlet("/image")
@MultipartConfig
public class ImageServlet extends HttpServlet {

    private static final String IMAGE_DIR ="F://image";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");

        try {
            //1. 解析请求数据
            Part p = req.getPart("uploadImage");
//        p.write("E://TMP");//保存文件到服务器本地某个路径
            long size = p.getSize();//获取上传的文件大小
            String contentType = p.getContentType();//获取每个part的数据格式
            String name = p.getSubmittedFileName();//获取上传的文件名


            //图片上传时间，数据库是保存的字符串，所以用日期格式化类来转换
            Date date = new Date();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String uploadTime = df.format(date);
            //获取part（上传图片文件的二进制输入流）
            InputStream is = p.getInputStream();//获取上传文件的输入流（数据）
            //根据输入流转md5校验码
            String md5 = DigestUtils.md5Hex(is);

            //如果已上传该图片（相同的md5值），就不能插入数据和保存本地
            int num = ImageDAO.queryCount(md5);
            if(num >= 1) {
                throw new RuntimeException("上传图片重复");
            }
            //2. 根据请求数据完成业务处理
            //TODO
            //2.1:保存上传图片为本地
            //上传的图片名可能相同但是md5是唯一的
            p.write(IMAGE_DIR+"/"+md5);
            //2.2:图片信息保存在数据库---> 后续查询列表接口要用
        }catch (Exception e){
            e.getStackTrace();
            resp.setStatus(500);
        }
        //3. 返回响应数据
//        ObjectMapper m = new ObjectMapper();
//        Map<String, Object> data = new HashMap<>();
//        data.put("size", size/1024);
//        data.put("contentType", contentType);
//        data.put("name", name);
//        String json = m.writeValueAsString(data);
//        resp.getWriter().println(json);
    }
}
