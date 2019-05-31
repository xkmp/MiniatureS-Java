package cn.xiaokai.mis.myshop;

import java.util.HashMap;
import java.util.Map;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.response.FormResponseSimple;
import cn.nukkit.inventory.PlayerInventory;
import cn.nukkit.item.Item;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;
import cn.xiaokai.mis.MiniatureS;
import cn.xiaokai.mis.form.FormStatic;
import cn.xiaokai.mis.form.MakeForm;
import cn.xiaokai.mis.myshop.seek.MySeek;
import cn.xiaokai.mis.tool.ItemIDSunName;
import cn.xiaokai.mis.tool.Tool;
import me.onebone.economyapi.EconomyAPI;
/**
 * @author Winfxk
 */
@SuppressWarnings("unchecked")
public class MyShopD {
	private Player player;
	private MiniatureS mis;

	/**
	 * 处理个人商店的数据
	 * 
	 * @param player 触发这些事件的玩家对象
	 */
	public MyShopD(Player player) {
		this.player = player;
		this.mis = MiniatureS.mis;
	}

	/**
	 * 处理查看玩家项目列表的时候发回的数据
	 * 
	 * @param data 发挥的数据
	 */
	public void MyShopItem(FormResponseSimple data) {
		if (!mis.config.getBoolean("个人商店")) {
			MakeForm.makeTip(player, TextFormat.RED + "个人商店已关闭！若需使用请联系管理员！");
			return;
		}
		TonsFx fx = mis.MyShopData.get(player.getName());
		HashMap<String, Object> map = fx.ShopItems.get(data.getClickedButtonId());
		int Count = Float.valueOf(String.valueOf(map.get("Count"))).intValue();
		if (Count < 1) {
			Config config = new Config(fx.file, Config.YAML);
			if (!(config.get("Items") instanceof Map)) {
				MakeForm.makeTip(player, TextFormat.RED + "打开失败！无法获取项目列表！");
				return;
			}
			HashMap<String, Object> items = (HashMap<String, Object>) config.get("Items");
			items.remove(String.valueOf(map.get("Key")));
			config.set("Items", items);
			config.save();
			MakeForm.makeTip(player, TextFormat.RED + "打开失败！该商品已售罄删除！");
			return;
		}
		(new MakeMyShopForm(player)).startPyItem(map, fx.file);
	}

	/**
	 * 处理搜索主页传回的数据
	 * 
	 * @param data 传回的数据
	 */
	public void SeekMain(FormResponseCustom data) {
		if (!mis.config.getBoolean("个人商店")) {
			MakeForm.makeTip(player, TextFormat.RED + "个人商店已关闭！若需使用请联系管理员！");
			return;
		}
		String ID = String.valueOf(data.getResponse(0));
		if (data.getResponse(0) == null || ID.isEmpty()) {
			MakeForm.makeTip(player, TextFormat.RED + "请输入有效的物品ID或物品名称！");
			return;
		}
		ID = ItemIDSunName.UnknownToID(ID);
		String ShopType = String.valueOf(data.getResponse(1)) == FormStatic.SeekMyShopType[0] ? "All"
				: (String.valueOf(data.getResponse(1)) == FormStatic.SeekMyShopType[1] ? "Sell" : "Shop");
		(new MySeek(player)).FormSeek(ID, ShopType);
	}

	/**
	 * 处理在个人商店添加项目的数据
	 * 
	 * @param data 传回的数据
	 */
	public void newMyShopItem(FormResponseCustom data) {
		if (!mis.config.getBoolean("个人商店")) {
			MakeForm.makeTip(player, TextFormat.RED + "个人商店已关闭！若需使用请联系管理员！");
			return;
		}
		String ID = String.valueOf(data.getResponse(0));
		if (data.getResponse(0) == null || ID.isEmpty()) {
			MakeForm.makeTip(player, TextFormat.RED + "请输入有效的物品ID或物品名称！");
			return;
		}
		ID = ItemIDSunName.UnknownToID(ID);
		String countString = String.valueOf(data.getResponse(1));
		if (data.getResponse(1) == null || countString.isEmpty()) {
			MakeForm.makeTip(player, TextFormat.RED + "请输入有效的上架物品数量！");
			return;
		}
		if (!Tool.isInteger(countString)) {
			MakeForm.makeTip(player, TextFormat.RED + "请输入有效的上架物品数量(纯整数)！");
			return;
		}
		int Count = Float.valueOf(countString).intValue();
		if (Count <= 0) {
			MakeForm.makeTip(player, TextFormat.RED + "请输入有效的上架物品数量(大于零纯整数)！");
			return;
		}
		String MoneytString = String.valueOf(data.getResponse(2));
		if (data.getResponse(2) == null || MoneytString.isEmpty()) {
			MakeForm.makeTip(player, TextFormat.RED + "请输入有效的上架物品单价！");
			return;
		}
		if (!Tool.isInteger(MoneytString)) {
			MakeForm.makeTip(player, TextFormat.RED + "请输入有效的上架物品单价(纯整数)！");
			return;
		}
		int Money = Float.valueOf(MoneytString).intValue();
		if (Money <= 0) {
			MakeForm.makeTip(player, TextFormat.RED + "请输入有效的上架物品单价(大于零纯整数)！");
			return;
		}
		PlayerInventory inventory = player.getInventory();
		String ShopType = String.valueOf(data.getResponse(3)) == FormStatic.MyShopType[0] ? "Sell" : "Shop";
		if (ShopType == "Sell") {
			int ItemCount = 0;
			Map<Integer, Item> items = inventory.getContents();
			for (Integer i : items.keySet()) {
				Item item = items.get(i);
				if (Tool.isMateID(item.getId() + ":" + item.getDamage(), ID) && item.getEnchantments().length == 0)
					ItemCount += item.getCount();
			}
			if (ItemCount < Count) {
				MakeForm.makeTip(player,
						TextFormat.RED + "您的" + TextFormat.WHITE + ItemIDSunName.getIDByName(ID) + TextFormat.RED
								+ "不足！\n" + TextFormat.WHITE + "还需：" + (Count - ItemCount) + TextFormat.RED
								+ " PS：当前暂时无法上架附魔物品！");
				return;
			}
		} else {
			if (EconomyAPI.getInstance().myMoney(player) < (Count * Money)) {
				MakeForm.makeTip(player, TextFormat.RED + "您的余额不足以支付收购这些物品的费用！");
				return;
			}
		}
		int xxMoney = mis.config.getInt("上架耗资");
		String Sb_you = "";
		if (xxMoney > 1) {
			if (EconomyAPI.getInstance().myMoney(player) < xxMoney * Count) {
				MakeForm.makeTip(player,
						TextFormat.RED + "您的！" + mis.getMoneyName() + "不足以支付上架物品所需的费用！\n" + TextFormat.WHITE + "总共所需: "
								+ (xxMoney * Count) + "\n" + TextFormat.WHITE + "还差："
								+ ((xxMoney * Count) - EconomyAPI.getInstance().myMoney(player)));
				return;
			}
			EconomyAPI.getInstance().reduceMoney(player, xxMoney * Count);
			Sb_you = TextFormat.RED + "同时扣除上架所需费用：" + TextFormat.BLUE + (xxMoney * Count);
		}
		if (ShopType == "Sell") {
			if (!Tool.isInteger(ID.split(":")[1]) || !Tool.isInteger(ID.split(":")[0])) {
				MakeForm.makeTip(player, TextFormat.RED + "您的物品ID解析失败！");
				return;
			}
			String[] sb = ID.split(":");
			int IDD = Float.valueOf(sb[0]).intValue();
			int DD = Float.valueOf(sb[1]).intValue();
			inventory.remove(new Item(IDD, DD, Count));
		} else
			EconomyAPI.getInstance().reduceMoney(player, (Count * Money));
		(new MakeMyShop(player, ID, Count, Money, ShopType, Sb_you)).newItemSwitch();
	}

	/**
	 * 处理个人商店主页传回的数据
	 * 
	 * @param data 传回的数据
	 */
	public void Main(FormResponseSimple data) {
		int ID = data.getClickedButtonId();
		if (!mis.config.getBoolean("个人商店")) {
			MakeForm.makeTip(player, TextFormat.RED + "个人商店已关闭！若需使用请联系管理员！");
			return;
		}
		TonsFx fx = mis.MyShopData.get(player.getName());
		if (ID >= fx.files.size()) {
			if (ID == fx.files.size())
				(new MakeMyShopForm(player)).addItem();
			else
				(new MakeMyShopForm(player)).Seek();
			return;
		}
		(new MakeMyShopForm(player)).ShowPlayer(fx.files.get(ID));
	}
}
