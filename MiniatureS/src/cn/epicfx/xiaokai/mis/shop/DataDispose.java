package cn.epicfx.xiaokai.mis.shop;

import java.util.ArrayList;
import cn.epicfx.xiaokai.mis.MiniatureS;
import cn.epicfx.xiaokai.mis.form.FormStatic;
import cn.epicfx.xiaokai.mis.form.MakeForm;
import cn.epicfx.xiaokai.mis.tool.ItemIDSunName;
import cn.epicfx.xiaokai.mis.tool.Tool;
import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.response.FormResponseSimple;
import cn.nukkit.utils.TextFormat;

public class DataDispose {
	private MiniatureS mis;

	/**
	 * 处理商店功能创建的UI传回的数据
	 * 
	 * @param shop 插件主类对象
	 */
	public DataDispose(MiniatureS shop) {
		this.mis = shop;
	}

	/**
	 * 处理在创建项目时选择要创建项目类型页面传回的数据 { "出售物品", "购买物品", "出售经验", "购买经验", "以物换物" }
	 * 
	 * @param player 触发这个事件的玩家对象
	 * @param data   传递回来的数据
	 */
	public void SelectShopType(Player player, int data) {
		if (!player.isOp()) {
			MakeForm.makeTip(player, TextFormat.RED + "你没有权限设置该页！");
			return;
		}
		ShopMakeForm makeForm = new ShopMakeForm(mis);
		switch (data) {
		case 4:
			makeForm.AddItemToItem(player);
			break;
		case 3:
			makeForm.AddExpSell(player);
			break;
		case 2:
			makeForm.AddExpShop(player);
			break;
		case 1:
			makeForm.AddItemShop(player);
			break;
		case 0:
		default:
			makeForm.addItemSell(player);
			break;
		}
	}

	/**
	 * 处理UI传回的数据 以物换物
	 * 
	 * @param player 玩家对象
	 * @param data   UI传回的数据
	 */
	public void AddItemToItem(Player player, FormResponseCustom data) {
		if (!Tool.isNumeric(String.valueOf(data.getResponse(2))) || !Tool.isNumeric(String.valueOf(data.getResponse(3)))
				|| !Tool.isNumeric(String.valueOf(data.getResponse(4)))
				|| !Tool.isNumeric(String.valueOf(data.getResponse(5)))
				|| !Tool.isNumeric(String.valueOf(data.getResponse(6)))) {
			MakeForm.makeTip(player, TextFormat.RED + "部分参数仅支持纯数字！");
			return;
		}
		if (!Tool.isInteger(String.valueOf(data.getResponse(2))) || !Tool.isInteger(String.valueOf(data.getResponse(3)))
				|| !Tool.isInteger(String.valueOf(data.getResponse(4)))
				|| !Tool.isInteger(String.valueOf(data.getResponse(5)))
				|| !Tool.isInteger(String.valueOf(data.getResponse(6)))) {
			MakeForm.makeTip(player, TextFormat.RED + "部分参数不得超出范围（0-2147483647）！");
			return;
		}
		String BlockID = ItemIDSunName.UnknownToID(String.valueOf(data.getResponse(0)));
		String ToBlockID = ItemIDSunName.UnknownToID(String.valueOf(data.getResponse(1)));
		int Money = Float.valueOf(String.valueOf(data.getResponse(2))).intValue();
		int Min = Float.valueOf(String.valueOf(data.getResponse(3))).intValue();
		int Max = Float.valueOf(String.valueOf(data.getResponse(4))).intValue();
		int ItemCount = Float.valueOf(String.valueOf(data.getResponse(5))).intValue();
		int ItemMoeny = Float.valueOf(String.valueOf(data.getResponse(6))).intValue();
		(new Shop(mis)).AddItemToItem(mis.PlayerMenuBack.get(player.getName()), player, Money, BlockID, ToBlockID, Min,
				Max, ItemCount, ItemMoeny);
	}

	public void AddExpSell(Player player, FormResponseCustom data) {
		if (!Tool.isNumeric(String.valueOf(data.getResponse(0))) || !Tool.isNumeric(String.valueOf(data.getResponse(1)))
				|| !Tool.isNumeric(String.valueOf(data.getResponse(2)))
				|| !Tool.isNumeric(String.valueOf(data.getResponse(3)))) {
			MakeForm.makeTip(player, TextFormat.RED + "所有参数仅支持纯数字！");
			return;
		}
		if (!Tool.isInteger(String.valueOf(data.getResponse(0))) || !Tool.isInteger(String.valueOf(data.getResponse(1)))
				|| !Tool.isInteger(String.valueOf(data.getResponse(2)))
				|| !Tool.isInteger(String.valueOf(data.getResponse(3)))) {
			MakeForm.makeTip(player, TextFormat.RED + "所有参数不得超出范围（0-2147483647）！");
			return;
		}
		int Exp = Float.valueOf(String.valueOf(data.getResponse(0))).intValue();
		int MinExp = Float.valueOf(String.valueOf(data.getResponse(1))).intValue();
		int MaxExp = Float.valueOf(String.valueOf(data.getResponse(2))).intValue();
		int ItemCount = Float.valueOf(String.valueOf(data.getResponse(3))).intValue();
		(new Shop(mis)).AddExpSell(mis.PlayerMenuBack.get(player.getName()), player, Exp, MinExp, MaxExp, ItemCount);
	}

	/**
	 * 创建一个出售经验给玩家的经验商店
	 * 
	 * @param player 玩家对象
	 * @param data   创建商店的UI传回的数据
	 */
	public void AddExpShop(Player player, FormResponseCustom data) {
		if (!Tool.isNumeric(String.valueOf(data.getResponse(0))) || !Tool.isNumeric(String.valueOf(data.getResponse(1)))
				|| !Tool.isNumeric(String.valueOf(data.getResponse(2)))
				|| !Tool.isNumeric(String.valueOf(data.getResponse(3)))) {
			MakeForm.makeTip(player, TextFormat.RED + "所有参数仅支持纯数字！");
			return;
		}
		if (!Tool.isInteger(String.valueOf(data.getResponse(0))) || !Tool.isInteger(String.valueOf(data.getResponse(1)))
				|| !Tool.isInteger(String.valueOf(data.getResponse(2)))
				|| !Tool.isInteger(String.valueOf(data.getResponse(3)))) {
			MakeForm.makeTip(player, TextFormat.RED + "所有参数不得超出范围（0-2147483647）！");
			return;
		}
		int Exp = Float.valueOf(String.valueOf(data.getResponse(0))).intValue();
		int MinExp = Float.valueOf(String.valueOf(data.getResponse(1))).intValue();
		int MaxExp = Float.valueOf(String.valueOf(data.getResponse(2))).intValue();
		int ItemCount = Float.valueOf(String.valueOf(data.getResponse(3))).intValue();
		(new Shop(mis)).AddExpShop(mis.PlayerMenuBack.get(player.getName()), player, Exp, MinExp, MaxExp, ItemCount);
	}

	/**
	 * 处理一个创建回收物品项目UI传递回来的数据
	 * 
	 * @param player 触发这个事件的玩家对象
	 * @param data   传递回来的数据
	 */
	public void AddItemShop(Player player, FormResponseCustom data) {
		if (!player.isOp()) {
			player.sendMessage(TextFormat.RED + "你无权创建商店");
			return;
		}
		String ID = ItemIDSunName.UnknownToID(String.valueOf(data.getResponse(0)));
		if (!Tool.isNumeric(String.valueOf(data.getResponse(1))) || !Tool.isNumeric(String.valueOf(data.getResponse(2)))
				|| !Tool.isNumeric(String.valueOf(data.getResponse(3)))
				|| !Tool.isNumeric(String.valueOf(data.getResponse(4)))
				|| !Tool.isNumeric(String.valueOf(data.getResponse(5)))) {
			MakeForm.makeTip(player, TextFormat.RED + "所有参数仅支持纯数字！");
			return;
		}
		if (!Tool.isInteger(String.valueOf(data.getResponse(1))) || !Tool.isInteger(String.valueOf(data.getResponse(2)))
				|| !Tool.isInteger(String.valueOf(data.getResponse(3)))
				|| !Tool.isInteger(String.valueOf(data.getResponse(4)))
				|| !Tool.isInteger(String.valueOf(data.getResponse(5)))) {
			MakeForm.makeTip(player, TextFormat.RED + "所有参数不得超出范围（0-2147483647）！");
			return;
		}
		int Count = Float.valueOf(String.valueOf(data.getResponse(1))).intValue();
		int Money = Float.valueOf(String.valueOf(data.getResponse(2))).intValue();
		int Item_Count = Float.valueOf(String.valueOf(data.getResponse(3))).intValue();
		int MinCount = Float.valueOf(String.valueOf(data.getResponse(4))).intValue();
		int MaxCount = Float.valueOf(String.valueOf(data.getResponse(5))).intValue();
		if (((int) (Money / Count)) < 1) {
			MakeForm.makeTip(player, TextFormat.RED + "您输入的每个物品的价格过低！请重新设置一个吧~");
			return;
		}
		(new Shop(mis)).AddItemShop(mis.PlayerMenuBack.get(player.getName()), player, ID, Money / Count, Item_Count,
				MinCount, MaxCount);
	}

	/**
	 * 处理创建出售物品项目UI传递回来的数据
	 * 
	 * @param player 触发这个事件的玩家对象
	 * @param data   传递回来的数据
	 */
	public void AddItemSell(Player player, FormResponseCustom data) {
		if (!player.isOp()) {
			player.sendMessage(TextFormat.RED + "你无权创建商店");
			return;
		}
		if (!Tool.isNumeric(String.valueOf(data.getResponse(1))) || !Tool.isNumeric(String.valueOf(data.getResponse(2)))
				|| !Tool.isNumeric(String.valueOf(data.getResponse(3)))
				|| !Tool.isNumeric(String.valueOf(data.getResponse(4)))
				|| !Tool.isNumeric(String.valueOf(data.getResponse(5)))) {
			MakeForm.makeTip(player, TextFormat.RED + "部分参数仅支持纯数字！");
			return;
		}
		if (!Tool.isInteger(String.valueOf(data.getResponse(1))) || !Tool.isInteger(String.valueOf(data.getResponse(2)))
				|| !Tool.isInteger(String.valueOf(data.getResponse(3)))
				|| !Tool.isInteger(String.valueOf(data.getResponse(4)))
				|| !Tool.isInteger(String.valueOf(data.getResponse(5)))) {
			MakeForm.makeTip(player, TextFormat.RED + "部分参数不得超出范围（0-2147483647）！");
			return;
		}
		String ID = ItemIDSunName.UnknownToID(String.valueOf(data.getResponse(0)));
		int Count = Float.valueOf(String.valueOf(data.getResponse(1))).intValue();
		int Money = Float.valueOf(String.valueOf(data.getResponse(2))).intValue();
		int Item_Count = Float.valueOf(String.valueOf(data.getResponse(3))).intValue();
		int MinCount = Float.valueOf(String.valueOf(data.getResponse(4))).intValue();
		int MaxCount = Float.valueOf(String.valueOf(data.getResponse(5))).intValue();
		if (((int) (Money / Count)) < 1) {
			MakeForm.makeTip(player, TextFormat.RED + "您输入的每个物品的价格过低！请重新设置一个吧~");
			return;
		}
		(new Shop(mis)).AddItemSell(mis.PlayerMenuBack.get(player.getName()), player, ID, Money / Count, Item_Count,
				MinCount, MaxCount);
	}

	/**
	 * 处理商店分页里面点击项目的事件
	 * 
	 * @param player 触发这个事件的玩家对象
	 * @param data   传递回来的数据
	 */
	public void Shop(Player player, FormResponseSimple data) {
		if (!player.isOp()) {
			MakeForm.makeTip(player, TextFormat.RED + "你没有权限这样做！");
			return;
		}
		int list_int = mis.shopList.get(player.getName()).size();
		if (!player.isOp() && data != null && list_int < (data.getClickedButtonId() + 1)) {
			MakeForm.makeTip(player, TextFormat.RED + "数据解析错误：未知的数据Key或您无权限！");
			return;
		}
		/*
		 * 当玩家点击的项目ID超过系统记录的ID时默认为创建一个项目
		 */
		if (player.isOp() && (data == null || data.getClickedButtonId() >= list_int)) {
			mis.shopList.remove(player.getName());
			mis.shopMakeForm.SelectShopType(player);
			return;
		}
		(new ItemProcess(mis)).Selection(player, mis.shopList.get(player.getName()).get(data.getClickedButtonId()),
				mis.PlayerMenuBack.get(player.getName()));
	}

	/**
	 * 处理创建上点分页UI传递回来的数据
	 * 
	 * @param player 触发这个事件的玩家
	 * @param data   传递回来的数据
	 */
	public void addShopShow(Player player, FormResponseCustom data) {
		if (!player.isOp()) {
			MakeForm.makeTip(player, TextFormat.RED + "你没有权限这样做！");
			return;
		}
		String ShopName;
		if (data.getResponse(0) != null && data.getResponse(0) != "") {
			ShopName = String.valueOf(data.getResponse(0));
		} else {
			MakeForm.makeTip(player, TextFormat.RED + "请输入商店分页名称");
			return;
		}
		String ShopType;
		if (data.getResponse(1) != null)
			ShopType = (String.valueOf(data.getResponse(1)) == FormStatic.shopGetTypeStrings[0]) ? "All"
					: ((String.valueOf(data.getResponse(1)) == FormStatic.shopGetTypeStrings[1]) ? "Op" : "Player");
		else
			ShopType = "All";
		String[] Back_World = {};
		if (data.getResponse(2) != null && data.getResponse(2) != "")
			Back_World = String.valueOf(data.getResponse(2)).split(";");
		String[] Back_Player = {};
		if (data.getResponse(3) != null && data.getResponse(3) != "")
			Back_Player = String.valueOf(data.getResponse(3)).split(";");
		boolean ImageType = false;
		if (data.getResponse(4) != null)
			ImageType = String.valueOf(data.getResponse(4)) == FormStatic.ShopImageType[0] ? false
					: String.valueOf(data.getResponse(4)) == FormStatic.ShopImageType[1] ? true : false;
		String imagePath = null;
		if (data.getResponse(5) != null) {
			imagePath = String.valueOf(data.getResponse(5));
			if (ImageType == true)
				imagePath = ItemIDSunName.UnknownToPath(imagePath);
			else
				imagePath = null;
		}
		ArrayList<String> strings = new ArrayList<>();
		for (String s : Back_World) {
			if (!s.equals(""))
				strings.add(s);
		}
		ArrayList<String> strings1 = new ArrayList<>();
		for (String s : Back_Player) {
			if (!s.equals(""))
				strings.add(s);
		}
		(new Shop(mis)).AddShopShow(player, ShopName, ShopType, strings1, strings, ImageType, imagePath);
	}

	/**
	 * 处理UI主页传递回来的数据
	 * 
	 * @param player 触发数据传递事件的玩家对象
	 * @param data   传递过来的数据
	 */
	public void Main(Player player, FormResponseSimple data) {
		if (mis.shopList.get(player.getName()) == null) {
			MakeForm.makeTip(player, TextFormat.RED + "数据解析错误：无法获取玩家视图列表(Shop Main)！");
			return;
		}
		int list_int = mis.shopList.get(player.getName()).size();
		if (!player.isOp() && data != null && list_int < (data.getClickedButtonId() + 1)) {
			MakeForm.makeTip(player, TextFormat.RED + "数据解析错误：未知的数据Key或您无权限！");
			return;
		}
		if (player.isOp() && (data == null || data.getClickedButtonId() >= list_int)) {
			mis.shopList.remove(player.getName());
			mis.shopMakeForm.MakeAddShop(player);
			return;
		}
		(new ShopMakeForm(mis)).MakeShowShopShow(player,
				mis.shopList.get(player.getName()).get(data.getClickedButtonId()));
	}
}
