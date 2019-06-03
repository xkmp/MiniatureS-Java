package cn.xiaokai.mis.form;

import java.util.HashMap;
import java.util.Map;

import cn.xiaokai.mis.tool.Tool;

/**
 * @author Winfxk
 */
public enum MakeID {
	/**
	 * 自定义界面为多样型时
	 */
	CustomTypeForm(781315351),
	/**
	 * 自定义界面类型为选择性按钮的时候的ID
	 */
	ModalTypeForm(89151534),
	/**
	 * 自定义表单类型为简单型时的ID
	 */
	SimpleTypeForm(8941564),
	/**
	 * 创建自定义界面的UI的ID
	 */
	MakeCustom(498616),
	/**
	 * 开始在个人商店项目购买东西确认窗口的ID
	 */
	startMyShopItem(9455498),
	/**
	 * 個人商店显示某人的商店项目列表的UI的ID
	 */
	MyShopItem(785641651),
	/**
	 * 玩家在个人商店搜索物品，显示搜索结果的UI ID
	 */
	MyShopFormSeek(86466861),
	/**
	 * 个人商店搜索主页ID
	 */
	MyShopSeekMain(74894156),
	/**
	 * 添加个人商店项目的UI的ID
	 */
	newMyShopItem(563210521),
	/**
	 * 个人商店主页UI的ID
	 */
	MyShopMain(84161654),
	/**
	 * 在非主业创建一个点击后弹出一个窗口的按钮的窗口的ID
	 */
	SonAddTipForm(7889446),
	/**
	 * 在非主业创建一个点击后可以自行命令的按钮的窗口滴
	 */
	SonAddCommadnForm(78744164),
	/**
	 * 在非主业创建衣蛾点击后传送费的按钮的窗口的ID
	 */
	SonAddTransferForm(984651),
	/**
	 * 在非主业创建一个点击后可以打开一个商店分页的按钮时的窗口ID
	 */
	SonAddOpenShow(78674864),
	/**
	 * 在非主业创建一个点击后可以打开一个界面的按钮时的窗口的ID
	 */
	SonAddOpenWindow(96456165),
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
	MainAddTipForm(124224341),
	/**
	 * 在主页添加点击后传送万家的按钮时创建的UI的ID
	 */
	MainAddTransferForm(24324635),
	/**
	 * 在主页添加一个点击后可以打开商店分页的按钮时创建的UI的ID
	 */
	MainAddOpenShow(97464315),
	/**
	 * 在主页添加命令类型的按钮时创建的UI的ID
	 */
	MainAddCommadnForm(234686164),
	/**
	 * 主页需要添加按钮的时候创建选择界面的UI
	 */
	MainAddButtonType(894165416),
	/**
	 * 在主页添加一个点击后可以打开新界面的界面的ID
	 */
	MainAddOpenWindow(46512356),
	/**
	 * 当玩家要删除按钮的时候，会创建一个UI，这个是ID
	 */
	MakeRemoveButton(856541),
	/**
	 * 玩家删除按钮前的确认提示窗口的ID
	 */
	MakeIsRemoveButton(867463114),
	/**
	 * 玩家执行参数命令的时候用，也就是提示玩家该输入参数了
	 */
	HeadIntoTheWater(54341561),
	/**
	 * 显示菜单列表的时候的UI iD
	 */
	ShowWindow(524861635),
	/**
	 * 在主页以外的界面创建按钮时让选择按钮类型的UI的ID
	 */
	SonAddButtonByType(7891231),
	/**
	 * 配置属性页ID
	 */
	SettingConfig(68461861);
	private int code;

	private static final Map<Integer, MakeID> MAP = new HashMap<>();

	static {
		for (MakeID item : MakeID.values()) {
			MAP.put(item.code, item);
		}
	}

	/**
	 * 获取枚举对象，依据是通过一个字符串
	 * 
	 * @param code 字符串内容
	 * @return 相应的枚举对象，若不存在，则返回null
	 */
	public static MakeID getByString(String code) {
		if (Tool.isInteger(code))
			return MAP.get(Float.valueOf(code).intValue());
		else
			return null;
	}

	/**
	 * 获取枚举对象，依据是一个整数数值
	 * 
	 * @param code 数值
	 * @return 相应的枚举对象，若不存在，则返回null
	 */
	public static MakeID getByID(int code) {
		return MAP.get(code);
	}

	/**
	 * 获取ID
	 * 
	 * @return
	 */
	public int getID() {
		return code;
	}

	private MakeID(int code) {
		this.code = code;
	}
}
