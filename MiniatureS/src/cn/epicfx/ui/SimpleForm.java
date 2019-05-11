package cn.epicfx.ui;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class SimpleForm extends FormBase {
	private ArrayList<Map<String, Object>> buttons = new ArrayList<>();

	public SimpleForm(int id, String Title) {
		super(id, Title, "form");
		data.put("content", "");
		data.put("buttons", buttons);
	}

	public void setContent(String content) {
		data.put("content", content);
	}

	/**
	 * 添加一个按钮
	 * 
	 * @param text 按钮文字内容
	 */
	public void addButton(String text) {
		Map<String, Object> bt = new LinkedHashMap<String, Object>();
		bt.put("text", text);
		this.buttons.add(bt);
	}

	/**
	 * 降价一个按钮
	 * 
	 * @param text         按钮内容
	 * @param isLocalImage 是否为本地图片
	 * @param imagePath    图片路径或链接
	 */
	public void addButton(String text, boolean isLocalImage, String imagePath) {
		Map<String, Object> bt = new LinkedHashMap<String, Object>();
		bt.put("text", text);
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("data", imagePath);
		map.put("type", isLocalImage ? "path" : "url");
		bt.put("image", map);
		this.buttons.add(bt);
	}
}
