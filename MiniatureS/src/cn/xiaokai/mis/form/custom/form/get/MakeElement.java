package cn.xiaokai.mis.form.custom.form.get;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.nukkit.form.element.ElementDropdown;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.element.ElementLabel;
import cn.nukkit.form.element.ElementSlider;
import cn.nukkit.form.element.ElementStepSlider;
import cn.nukkit.form.element.ElementToggle;
import cn.nukkit.utils.TextFormat;
import cn.xiaokai.mis.MiniatureS;
import cn.xiaokai.mis.tool.Tool;

/**
 * @author Winfxk
 */
@SuppressWarnings("unchecked")
public class MakeElement {
	private MiniatureS mis;
	private HashMap<String, Object> map;

	public MakeElement(HashMap<String, Object> map) {
		this.map = map;
		mis = MiniatureS.mis;
	}

	/**
	 * 假如是下拉框
	 * 
	 * @return
	 */
	public ElementDropdown getDropdown() {
		List<String> options = new ArrayList<>();
		int defaultOption = Tool.isInteger(String.valueOf(map.get("默认值")))
				? Float.valueOf(map.get("默认值").toString()).intValue()
				: 0;
		if (map.get("Texts") instanceof List) {
			options = (List<String>) map.get("Texts");
		} else if (map.get("Texts") instanceof Map) {
			HashMap<String, String> map = (HashMap<String, String>) this.map.get("Texts");
			for (String ike : map.keySet())
				if (map.get(ike) == null && (map.get(ike).toString().isEmpty() && ike != null && !ike.isEmpty()))
					options.add(ike);
				else
					options.add(map.get(ike));
		}
		return new ElementDropdown(getString("Text"), options, defaultOption);
	}

	/**
	 * 如果是滑动选择条
	 * 
	 * @return
	 */
	public ElementStepSlider getStepSlider() {
		List<String> steps = new ArrayList<String>();
		Object object = map.get("Texts");
		if (object instanceof List)
			steps = (List<String>) object;
		else if (object instanceof Map) {
			HashMap<String, String> map = (HashMap<String, String>) object;
			for (String ike : map.keySet())
				if (map.get(ike) == null && (map.get(ike).toString().isEmpty() && ike != null && !ike.isEmpty()))
					steps.add(ike);
				else
					steps.add(map.get(ike));
		}
		int d = Tool.isInteger(map.get("默认").toString()) ? Float.valueOf(map.get("默认").toString()).intValue() : 0;
		return new ElementStepSlider(getString("Text"), steps, d);
	}

	/**
	 * 如果是滑动条
	 * 
	 * @return
	 */
	public ElementSlider getSlider() {
		int Min = Tool.isInteger(map.get("最小值").toString()) ? Float.valueOf(map.get("最小值").toString()).intValue() : 0;
		int Max = Tool.isInteger(map.get("最大值").toString()) ? Float.valueOf(map.get("最大值").toString()).intValue() : 100;
		int d = Tool.isInteger(map.get("默认值").toString()) ? Float.valueOf(map.get("默认值").toString()).intValue() : Min;
		int step = Tool.isInteger(map.get("步长").toString()) ? Float.valueOf(map.get("步长").toString()).intValue() : 1;
		return new ElementSlider(getString("Text"), Min, Max, step, d);
	}

	/**
	 * 如果是开关
	 * 
	 * @return
	 */
	public ElementToggle getToggle() {
		boolean d = false;
		if (map.get("状态") != null)
			try {
				d = Boolean.valueOf(map.get("状态").toString());
			} catch (Exception e) {
				mis.getLogger()
						.info(TextFormat.RED + "数据类型错误：此处只能为布尔值！\nText：" + getString("Text") + "\n" + e.getMessage());
				d = false;
			}
		return new ElementToggle(getString("Text"), d);
	}

	/**
	 * 假如是输入框
	 * 
	 * @return
	 */
	public ElementInput getInput() {
		return new ElementInput(getString("Text"), getString("显示"), getString("默认"));
	}

	/**
	 * 假如是标签</br>
	 * <b>PS:</b>默认为该项
	 * 
	 * @return
	 */
	public ElementLabel getLabel() {
		return new ElementLabel(getString("Text"));
	}

	/**
	 * 快速获取文本
	 * 
	 * @param Key 文本得到Key
	 * @return
	 */
	public String getString(String Key) {
		return mis.getMessage()
				.getText((map.get(Key) == null || map.get(Key).toString().isEmpty()) ? "" : map.get(Key).toString());
	}
}
