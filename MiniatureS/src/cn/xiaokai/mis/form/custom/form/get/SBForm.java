package cn.xiaokai.mis.form.custom.form.get;

import java.util.HashMap;

import cn.nukkit.form.element.Element;

/**
 * @author Winfxk
 */
public class SBForm {
	/**
	 * 根据数据提供的控件类型返回对应的控件对象
	 * 
	 * @param map 控件的数据
	 * @return
	 */
	public static Element Switch(HashMap<String, Object> map) {
		MakeElement make = new MakeElement(map);
		switch (String.valueOf(map.get("Type")).toLowerCase()) {
		case "dropdown":
		case "d":
		case "下拉":
		case "下拉框":
			return make.getDropdown();
		case "stepslider":
		case "steps":
		case "ss":
		case "滑动选择":
			return make.getStepSlider();
		case "slider":
		case "sl":
		case "滑动条":
		case "滑动":
			return make.getSlider();
		case "toggle":
		case "开关":
		case "togg":
			return make.getToggle();
		case "input":
		case "i":
		case "输入框":
		case "编辑框":
		case "edittxt":
		case "ed":
			return make.getInput();
		case "标签":
		case "label":
		case "txt":
		case "text":
		case "t":
		case "l":
		case "文本":
		default:
			return make.getLabel();
		}
	}
}
