package com.hzn.web;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hzm.daomain.TbContent;
import com.hzm.pojo.EasyUiNote;
import com.hzm.pojo.EasyUiPager;
import com.hzm.pojo.Message;
import com.hzm.service.ContentManagementInterface;
import com.hzm.service.Contentinterface;

/**
 * 
 * 
 * 内容类维护表现层
 */
@Controller
@RequestMapping("/content")
public class Content {

	@Resource(name = "contentServiceImpl")
	private Contentinterface cf;
	@Resource(name = "contentManagementInterfaceImpl")
	private ContentManagementInterface cmi;

	/**
	 * 
	 * 获取内容分类树桩结构
	 * 
	 * @param id
	 *               父类节点
	 */
	@RequestMapping("/category/list")
	@ResponseBody
	public List<EasyUiNote> getByid(@RequestParam(defaultValue = "0") Long id) {
		List<EasyUiNote> list = cf.getByparentId(id);
		return list;

	}

	/**
	 * 
	 * 新增节点
	 * 
	 * @param parentId
	 *                     js中将本节点id赋值给父节点
	 * 
	 * @param name
	 *                     子节点的名字
	 * 
	 * 
	 */
	@RequestMapping(value = "/category/create", method = RequestMethod.POST)
	@ResponseBody
	public Message addTreeNote(Long parentId, String name) {

		Message treeNote = cf.addTreeNote(parentId, name);

		return treeNote;

	}

	/**
	 * 
	 * 更新本节点
	 * 
	 * @param id
	 *                 本节点id
	 * 
	 * @param name
	 *                 本节点的名字
	 * 
	 */
	@RequestMapping("/category/update")
	@ResponseBody
	public Message update(Long id, String name) {

		Message m = new Message();
		try {
			cf.updateByid(id, name);
			m.setStatus(200);
		} catch (Exception e) {
			// TODO: handle exception
			e.getStackTrace();
			m.setStatus(400);
		}
		return m;
	}

	/**
	 * 
	 * 删除本节点
	 * 
	 * @param id
	 *               本节点id
	 */
	@RequestMapping("/category/delete")
	@ResponseBody
	public Message deleteByid(Long id) {
		Message message = cf.deleteByid(id);

		return message;
	}

	/**
	 * 
	 * 添加内容展示项
	 * 
	 * 
	 * @param tc
	 *               jsp表单序列化 对TbContent自动映射
	 * 
	 */

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public Message save(TbContent tc) {
		Message m = new Message();
		try {
			String url = tc.getUrl();
			if (!url.contains("http://") || !url.contains("https://")) {//如果没有填写url地址的开头部分,自动补全
				url = "http://" + url;
				tc.setUrl(url);
			}
			tc.setCreated(new Date());
			tc.setUpdated(new Date());
			cmi.save(tc);
			m.setStatus(200);
		} catch (Exception e) {
			m.setStatus(400);
			// TODO: handle exception
		}
		return m;

	}

	/**
	 * 
	 * 根据内容类id查询查询内容列表
	 * 
	 * @param categoryId
	 *                       内容类categoryId
	 * 
	 * @param page
	 *                       第几页
	 * 
	 * @param rows
	 *                       每页显示个数
	 * 
	 */
	@RequestMapping("/query/list")
	@ResponseBody
	public EasyUiPager<TbContent> getByCategoryId(Long categoryId, Integer page, Integer rows) {

		EasyUiPager<TbContent> easyUiPager = cmi.getByCategoryId(categoryId, page, rows);

		return easyUiPager;

	}
}
