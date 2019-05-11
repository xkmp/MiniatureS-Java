package cn.epicfx.ui;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import cn.nukkit.Player;

public class CustomForm extends FormBase {
	private ArrayList<Map<String, Object>> content = new ArrayList<Map<String, Object>>();

	public CustomForm(int id, String Title) {
		super(id, Title, "custom_form");
	}

	public CustomForm(int id) {
		super(id, "", "custom_form");
	}

	/**
	 * 添加滑步滑块
	 * 
	 * @param text  标题
	 * @param options 选项内容
	 */
	public void addStepSlider(String text, String[] options) {
		Map<String, Object> content = new LinkedHashMap<String, Object>();
		content.put("type", "step_slider");
		content.put("text", text);
		content.put("steps", options);
		addContent(content);
	}

	/**
	 * 带选择性的滑块
	 * 
	 * @param text         控件标题
	 * @param options        选项内容
	 * @param defaultIndex 默认显示第一几个
	 */
	public void addStepSlider(String text, String[] options, int defaultIndex) {
		Map<String, Object> content = new LinkedHashMap<String, Object>();
		content.put("type", "step_slider");
		content.put("text", text);
		content.put("steps", options);
		content.put("default", defaultIndex);
		addContent(content);
	}

	/**
	 * 添加滑块控件
	 * 
	 * @param text 控件标题
	 * @param min  控件最小值
	 * @param max  控件最大值
	 */
	public void addSlider(String text, int min, int max) {
		Map<String, Object> content = new LinkedHashMap<String, Object>();
		content.put("type", "slider");
		content.put("text", text);
		content.put("min", min);
		content.put("max", max);
		addContent(content);
	}

	/**
	 * 添加滑块控件
	 * 
	 * @param text    控件标题
	 * @param min     滑块最小值
	 * @param max     滑块最大值
	 * @param step    步长
	 * @param Default 默认值
	 */
	public void addSlider(String text, int min, int max, int step, int Default) {
		Map<String, Object> content = new LinkedHashMap<String, Object>();
		content.put("type", "slider");
		content.put("text", text);
		content.put("min", min);
		content.put("max", max);
		content.put("step", step);
		content.put("default", Default);
		addContent(content);
	}

	/**
	 * 添加下拉菜单
	 * 
	 * @param text    菜单标题
	 * @param options 菜单选项内容
	 */
	public void addDropdown(String text, String[] options) {
		addDropdown(text, options, 0);
	}

	/**
	 * 添加下拉菜单
	 * 
	 * @param text    菜单标题
	 * @param options 菜单选项内容
	 * @param Default 默认显示第几个
	 */
	public void addDropdown(String text, String[] options, int Default) {
		Map<String, Object> content = new LinkedHashMap<String, Object>();
		content.put("type", "dropdown");
		content.put("text", text);
		content.put("options", options);
		content.put("default", Default);
		addContent(content);
	}

	/**
	 * 添加一个标签
	 * 
	 * @param text 标签内容
	 */
	public void addLabel(String text) {
		Map<String, Object> content = new LinkedHashMap<String, Object>();
		content.put("type", "label");
		content.put("text", text);
		addContent(content);
	}

	/**
	 * 添加一个开关控件
	 * 
	 * @param text 控件标题
	 */
	public void addToggle(String text) {
		addToggle(text, false);
	}

	/**
	 * 添加一个开关控件
	 * 
	 * @param text    控件标题
	 * @param Default 控件默认状态
	 */
	public void addToggle(String text, boolean Default) {
		Map<String, Object> content = new LinkedHashMap<String, Object>();
		content.put("type", "toggle");
		content.put("text", text);
		content.put("default", Default);
		addContent(content);
	}

	/**
	 * 添加一个编辑框
	 * 
	 * @param text 编辑框标题
	 */
	public void addInput(String text) {
		addInput(text, null, "");
	}

	/**
	 * 添加一个编辑框
	 * 
	 * @param text    编辑框标题
	 * @param Default 编辑框默认内容
	 */
	public void addInput(String text, String Default) {
		addInput(text, Default, "");
	}

	/**
	 * 添加一个编辑框
	 * 
	 * @param text        编辑框的标题
	 * @param Default     默认内容
	 * @param placeholder
	 */
	public void addInput(String text, String Default, String placeholder) {
		Map<String, Object> content = new LinkedHashMap<String, Object>();
		content.put("type", "input");
		content.put("text", text);
		content.put("placeholder", placeholder);
		content.put("default", Default);
		addContent(content);
	}

	private void addContent(Map<String, Object> content) {
		this.content.add(content);
	}

	@Override
	public void sendPlayer(Player player) {
		data.put("content", this.content);
		super.sendPlayer(player);
	}
}
