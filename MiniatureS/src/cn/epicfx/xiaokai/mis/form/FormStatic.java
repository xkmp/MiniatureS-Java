package cn.epicfx.xiaokai.mis.form;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.epicfx.xiaokai.mis.tool.Tool;
import cn.nukkit.form.element.ElementButton;

public class FormStatic {
	public static final String[] ButtonOpenTypeList = { "提示一个窗口", "打开一个新的界面", "执行命令", "控制台执行命令", "控制台带参数执行命令", "传送玩家",
			"打开商店" };
	private static final String[] ButtonImageType = { "无贴图", "自带贴图", "网络贴图" };
	private static final String[] buttonImageGetTypeStrings = { "贴图路径", "物品ID", "物品名称" };
	public static final String[] shopGetTypeStrings = { "所有玩家", "仅服务器管理员", "仅非服务器管理员" };
	public static final String[] ShopImageType = { "无贴图", "本地贴图", "网络贴图" };
	public static final String[] AddItemToShopType = { "出售物品", "购买物品", "出售经验", "购买经验", "以物换物" };

	public static List<ElementButton> getAddItemToShopType() {
		List<ElementButton> list = new ArrayList<ElementButton>();
		for (String Button : AddItemToShopType) {
			list.add(new ElementButton(Tool.getRandColor() + Button));
		}
		return list;
	}

	public static ArrayList<String> getButtonOpenTypeArrayList() {
		ArrayList<String> list = new ArrayList<String>();
		for (String Button : ButtonOpenTypeList) {
			list.add(Button);
		}
		return list;
	}

	public static List<ElementButton> getButtonOpenTypeList() {
		List<ElementButton> list = new ArrayList<ElementButton>();
		for (String Button : ButtonOpenTypeList) {
			list.add(new ElementButton(Tool.getRandColor() + Button));
		}
		return list;
	}

	public static List<String> getButtonImageType() {
		return Arrays.asList(ButtonImageType);
	}

	public static List<String> getbuttonImageGetTypeStrings() {
		return Arrays.asList(buttonImageGetTypeStrings);
	}
}
