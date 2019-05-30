package cn.xiaokai.mis.form;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.nukkit.form.element.ElementButton;
import cn.xiaokai.mis.tool.Tool;

public class FormStatic {
	/**
	 * 按钮有的功能
	 */
	public static final String[] ButtonOpenTypeList = { "提示一个窗口", "打开一个新的界面", "执行命令", "传送玩家", "打开商店" };
	/**
	 * 创建UI时贴图的类型
	 */
	public static final String[] ButtonImageType = { "无贴图", "自带贴图", "网络贴图" };
	/**
	 * 创建UI时贴图路径的类型
	 */
	private static final String[] buttonImageGetTypeStrings = { "贴图路径", "物品ID", "物品名称" };
	/**
	 * 可用玩家分类
	 */
	public static final String[] shopGetTypeStrings = { "所有玩家", "仅服务器管理员", "仅非服务器管理员" };
	/**
	 * 商店分页的贴图类型
	 */
	public static final String[] ShopImageType = { "无贴图", "本地贴图", "网络贴图" };
	/**
	 * 已经有啦的商店类型
	 */
	public static final String[] AddItemToShopType = { "出售物品", "购买物品", "出售经验", "购买经验", "以物换物" };
	/**
	 * 当玩家点击一个可以自行命令的按钮后，执行这个命令的对象
	 */
	public static final String[] AddCommandPlayer = { "点击玩家", "控制台" };
	/**
	 * 个人商店的商店类型
	 */
	public static final String[] MyShopType = { "出售", "收购" };
	/**
	 * 个人商店搜索的商店类型
	 */
	public static final String[] SeekMyShopType = { "不限制", "出售", "收购" };

	/**
	 * 将已经有了的商店类型转换为按钮列表
	 * 
	 * @return
	 */
	public static List<ElementButton> getAddItemToShopType() {
		List<ElementButton> list = new ArrayList<ElementButton>();
		for (String Button : AddItemToShopType) {
			list.add(new ElementButton(Tool.getRandColor() + Button));
		}
		return list;
	}

	/**
	 * 将已经有啦的按钮类型转换为列表返回
	 * 
	 * @return
	 */
	public static ArrayList<String> getButtonOpenTypeArrayList() {
		ArrayList<String> list = new ArrayList<String>();
		for (String Button : ButtonOpenTypeList) {
			list.add(Button);
		}
		return list;
	}

	/**
	 * 将已经有了的按钮类型转换为按钮列表返回
	 * 
	 * @return
	 */
	public static List<ElementButton> getButtonOpenTypeList() {
		List<ElementButton> list = new ArrayList<ElementButton>();
		for (String Button : ButtonOpenTypeList) {
			list.add(new ElementButton(Tool.getRandColor() + Button));
		}
		return list;
	}

	/**
	 * 将已经有了的按钮贴图类型转换为列表返回
	 * 
	 * @return
	 */
	public static List<String> getButtonImageType() {
		return Arrays.asList(ButtonImageType);
	}

	/**
	 * 将已经有了的贴图路径类型转换为列表返回
	 * 
	 * @return
	 */
	public static List<String> getbuttonImageGetTypeStrings() {
		return Arrays.asList(buttonImageGetTypeStrings);
	}
}
