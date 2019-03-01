package com.hzm.tttt;

import java.io.IOException;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;

public class FastDfsTest {

	//@Test
	public void upImage() {

		try {
			ClientGlobal.init("E:\\android\\workspace1\\WEB\\e3_manager_web\\src\\main\\resources\\cfg.properties");
			TrackerClient tc = new TrackerClient();
			TrackerServer server = tc.getConnection();
			StorageClient sc = new StorageClient(server, null);
			String[] strings = sc.upload_appender_file("E:\\图片\\0065oQSqly1frqscr5o00j30k80qzafc.jpg", "jpg", null);
			for (String s : strings) {

				System.out.println(s);

			}
		} catch (IOException | MyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
