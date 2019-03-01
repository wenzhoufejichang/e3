package com.hzn.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hzm.utils.FastDFSClient;
import com.hzm.utils.JsonUtils;

/**
 * 
 * 内容维护表现层
 * 
 * 上传图片
 * 
 */
@Controller
public class ImageUpload {

	@Value("${fastdfsip}")
	String ip;

	/**
	 * 图片上传模块
	 * 
	 * @param uploadFile
	 *                       富文本编辑器kingEditor中上传文件的名字
	 * 
	 */
	// text/plain;charset=utf-8
	// ,produces=MediaType.APPLICATION_JSON_UTF8_VALUE
	@ResponseBody
	@RequestMapping(value = "/pic/upload", produces = MediaType.TEXT_PLAIN_VALUE + ";charset=utf-8")

	public String upload(MultipartFile uploadFile) {

		String conf = "classpath:cfg.properties";
		Map<String, Object> map = new HashMap<>();
		try {
			FastDFSClient fdc = new FastDFSClient(conf);
			String string = uploadFile.getOriginalFilename()
					.substring(uploadFile.getOriginalFilename().lastIndexOf(".") + 1);
			String file = fdc.uploadFile(uploadFile.getBytes(), string);
			String ipchange = "http://" + ip + "//" + file;
			map.put("url", ipchange);
			map.put("error", 0);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			map.put("message", e.getMessage());
			map.put("error", 1);
		}

		return JsonUtils.objectToJson(map);
	}

}
