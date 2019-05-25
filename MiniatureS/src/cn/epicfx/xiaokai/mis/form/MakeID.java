package cn.epicfx.xiaokai.mis.form;

import java.util.HashMap;
import java.util.Map;

public enum MakeID {
	/**
	 * 菜单主页ID
	 */
	MainFormID(97005423),
	/**
	 * 设置属性页ID
	 */
	SetConfig(165412354),
	/**
	 * 选择要添加按钮类型页ID
	 */
	AddButtonType(1563165423),
	/**
	 * 商店主页ID
	 */
	ShopMain(755453486),
	/**
	 * 添加商店分页ID
	 */
	ShopAddShop(168854321),
	/**
	 * 商店分页ID
	 */
	Shop(45435453),
	/**
	 * 添加商店物品类型页ID
	 */
	AddShopType(231635415),
	/**
	 * 上架物品出售页ID
	 */
	AddItemSell(564357421),
	/**
	 * 上架物品回收页ID
	 */
	AddItemShop(456123156),
	/**
	 * 上架出售经验等级页ID
	 */
	AddExpShop(338713874),
	/**
	 * 上架经验回收页ID
	 */
	AddExpSell(45456456),
	/**
	 * 上架以物易物页ID
	 */
	AddItemToItem(234734554),
	/**
	 * 玩家打开商店项目时的界面ID
	 */
	PlayerShopInteract(5873431),
	/**
	 * 在主页添加提示类型的按钮时创建的UI的ID
	 */
	MainAddTipForm(124224341);
	private int code;

	private static final Map<Integer, MakeID> MAP = new HashMap<>();

	static {
		for (MakeID item : MakeID.values()) {
			MAP.put(item.code, item);
		}
	}

	public static MakeID getByString(String code) {
		return MAP.get(Integer.valueOf(code));
	}

	public static MakeID getByID(int code) {
		return MAP.get(code);
	}

	public int getID() {
		return code;
	}

	private MakeID(int code) {
		this.code = code;
	}
}
