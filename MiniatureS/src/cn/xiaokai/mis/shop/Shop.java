package cn.xiaokai.mis.shop;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import cn.nukkit.Player;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;
import cn.xiaokai.mis.MiniatureS;
import cn.xiaokai.mis.tool.ItemIDSunName;
import cn.xiaokai.mis.tool.Tool;
/**
 * @author Winfxk
 */
@SuppressWarnings("unchecked")
public class Shop {
	private MiniatureS mis;

	/**
	 * 有关于商店的各种创建 创建商店分页|添加可供使用的各种项目
	 * 
	 * @param mis 插件主类对象
	 */
	public Shop(MiniatureS mis) {
		this.mis = mis;
	}

	/**
	 * 创建一个以物换物的商店
	 * 
	 * @param Key       商店滴
	 * @param player    创建这个商店的玩家对象
	 * @param Money     每次兑换的时候要扣除的金币
	 * @param BlockID   要用来当做金币兑换的物品
	 * @param ToBlockID 准备被兑换的物品
	 * @param Min       每次最少能对少多少个
	 * @param Max       每次最多能兑换多少个
	 * @param ItemCount 库存
	 * @param ItemMoeny 没得可得物品需要玩家使用多少物品兑换
	 */
	public void AddItemToItem(String Key, Player player, int Money, String BlockID, String ToBlockID, int Min, int Max,
			int ItemCount, int ItemMoeny) {
		boolean Astrict = (ItemCount > 0);
		ItemCount = ItemCount >= 0 ? ItemCount : 0;
		HashMap<String, Object> map = new HashMap<>();
		map.put("Money", Money);
		map.put("BlockID", BlockID);
		map.put("Astrict", Astrict);
		map.put("ToBlockID", ToBlockID);
		map.put("Min", Min);
		map.put("Max", Max);
		map.put("Type", "ItemToItem");
		map.put("ItemCount", ItemCount);
		map.put("ItemMoeny", ItemMoeny);
		HashMap<String, Object> Bt = (HashMap<String, Object>) mis.ShopListConfig.get("Buttons");
		if (!Bt.containsKey(Key)) {
			player.sendMessage(TextFormat.RED + "非常抱歉！您正在创建出售物品的商店页不存在或已被移除");
			return;
		}
		String ConfigName = String.valueOf(((HashMap<String, Object>) Bt.get(Key)).get("File"));
		File file = new File(mis.getDataFolder() + MiniatureS.ShopConfigPath, ConfigName);
		if (!file.exists()) {
			player.sendMessage(TextFormat.RED + "非常抱歉！您正在创建出售物品的商店页不存在或已被移除");
			mis.ShopListConfig.remove(mis.PlayerMenuBack.get(player.getName()));
			mis.ShopListConfig.save();
			return;
		}
		Config config = new Config(file, 2);
		HashMap<String, Object> Buttons;
		if (config.get("Buttons") instanceof HashMap)
			Buttons = (HashMap<String, Object>) config.get("Buttons");
		else
			Buttons = new HashMap<String, Object>();
		Buttons.put(getShopItemKey(Buttons), map);
		config.set("Buttons", Buttons);
		config.save();
		player.sendMessage(TextFormat.GREEN + "成功创建一个物品兑换商店！使用" + TextFormat.YELLOW + ItemMoeny + TextFormat.GREEN + "个"
				+ TextFormat.WHITE + ItemIDSunName.getIDByName(BlockID) + TextFormat.GREEN + "可兑换一个" + TextFormat.WHITE
				+ ItemIDSunName.getIDByName(ToBlockID) + TextFormat.GREEN
				+ (ItemMoeny > 0
						? ("并扣除" + TextFormat.BLUE + ItemMoeny + TextFormat.GREEN + mis.config.getString("货币单位"))
						: "")
				+ (Astrict ? ("，限制库存" + TextFormat.BLUE + ItemCount) : "，不限制库存"));
	}

	/**
	 * 让玩家可以将多余的经验卖给服务器
	 * 
	 * @param Key      商店key
	 * @param player   玩家对象
	 * @param Exp      每级经验多少钱
	 * @param MinExp   玩家每次购买能买的最低等级
	 * @param MaxExp   玩家每次最多能买多少经验
	 * @param ExpCount 库存
	 */
	public void AddExpSell(String Key, Player player, int Exp, int MinExp, int MaxExp, int ExpCount) {
		boolean Astrict = (ExpCount > 0);
		Exp = Exp > 0 ? Exp : 1;
		ExpCount = ExpCount >= 0 ? ExpCount : 0;
		HashMap<String, Object> map = new HashMap<>();
		map.put("Money", Exp);
		map.put("ExpCount", ExpCount);
		map.put("Astrict", Astrict);
		map.put("MinExp", MinExp);
		map.put("Type", "Sell_Exp");
		map.put("MaxExp", MaxExp);
		HashMap<String, Object> Bt = (HashMap<String, Object>) mis.ShopListConfig.get("Buttons");
		if (!Bt.containsKey(Key)) {
			player.sendMessage(TextFormat.RED + "非常抱歉！您正在创建出售物品的商店页不存在或已被移除");
			return;
		}
		String ConfigName = String.valueOf(((HashMap<String, Object>) Bt.get(Key)).get("File"));
		File file = new File(mis.getDataFolder() + MiniatureS.ShopConfigPath, ConfigName);
		if (!file.exists()) {
			player.sendMessage(TextFormat.RED + "非常抱歉！您正在创建出售物品的商店页不存在或已被移除");
			mis.ShopListConfig.remove(mis.PlayerMenuBack.get(player.getName()));
			mis.ShopListConfig.save();
			return;
		}
		Config config = new Config(file, 2);
		HashMap<String, Object> Buttons;
		if (config.get("Buttons") instanceof HashMap)
			Buttons = (HashMap<String, Object>) config.get("Buttons");
		else
			Buttons = new HashMap<String, Object>();
		Buttons.put(getShopItemKey(Buttons), map);
		config.set("Buttons", Buttons);
		config.save();
		player.sendMessage(TextFormat.GREEN + "您成功添加了一个" + TextFormat.GOLD + "回收" + TextFormat.GREEN + "类型的经验商店！（"
				+ TextFormat.WHITE + "每级经验" + TextFormat.BLUE + Exp + TextFormat.WHITE + mis.config.getString("货币单位")
				+ (Astrict ? ("，限制库存：" + TextFormat.RED + ExpCount) : "，不限制库存"));
	}

	/**
	 * 卖经验给玩家
	 * 
	 * @param Key      商店key
	 * @param player   玩家对象
	 * @param Exp      每级经验多少钱
	 * @param MinExp   玩家每次购买能买的最低等级
	 * @param MaxExp   玩家每次最多能买多少经验
	 * @param ExpCount 库存
	 */
	public void AddExpShop(String Key, Player player, int Exp, int MinExp, int MaxExp, int ExpCount) {
		boolean Astrict = (ExpCount > 0);
		Exp = Exp > 0 ? Exp : 1;
		ExpCount = ExpCount >= 0 ? ExpCount : 0;
		HashMap<String, Object> map = new HashMap<>();
		map.put("Money", Exp);
		map.put("ExpCount", ExpCount);
		map.put("Astrict", Astrict);
		map.put("MinExp", MinExp);
		map.put("Type", "Shop_Exp");
		map.put("MaxExp", MaxExp);
		HashMap<String, Object> Bt = (HashMap<String, Object>) mis.ShopListConfig.get("Buttons");
		if (!Bt.containsKey(Key)) {
			player.sendMessage(TextFormat.RED + "非常抱歉！您正在创建出售物品的商店页不存在或已被移除");
			return;
		}
		String ConfigName = String.valueOf(((HashMap<String, Object>) Bt.get(Key)).get("File"));
		File file = new File(mis.getDataFolder() + MiniatureS.ShopConfigPath, ConfigName);
		if (!file.exists()) {
			player.sendMessage(TextFormat.RED + "非常抱歉！您正在创建出售物品的商店页不存在或已被移除");
			mis.ShopListConfig.remove(mis.PlayerMenuBack.get(player.getName()));
			mis.ShopListConfig.save();
			return;
		}
		Config config = new Config(file, 2);
		HashMap<String, Object> Buttons;
		if (config.get("Buttons") instanceof HashMap)
			Buttons = (HashMap<String, Object>) config.get("Buttons");
		else
			Buttons = new HashMap<String, Object>();
		Buttons.put(getShopItemKey(Buttons), map);
		config.set("Buttons", Buttons);
		config.save();
		player.sendMessage(TextFormat.GREEN + "您成功添加了一个" + TextFormat.GOLD + "出售" + TextFormat.GREEN + "类型的经验商店！（"
				+ TextFormat.WHITE + "每级经验" + TextFormat.BLUE + Exp + TextFormat.WHITE + mis.config.getString("货币单位")
				+ (Astrict ? ("，限制库存：" + TextFormat.RED + ExpCount) : "，不限制库存"));
	}

	/**
	 * 创建一个出售类型的物品商店
	 * 
	 * @param Key        要添加项目的商店分页Key
	 * @param player     添加项目的玩家对象
	 * @param ID         要添加回收的物品ID
	 * @param Money      每个物品回收值多少钱
	 * @param Item_Count 商店空余库存股，为零时不限制
	 * @param MinCount   每次最低玩家必须回收多少
	 * @param MaxCount   每次最高玩家只能回收多少
	 */
	public void AddItemShop(String Key, Player player, String ID, int Money, int Item_Count, int MinCount,
			int MaxCount) {
		String ItemName = ItemIDSunName.getIDByName(ID);
		boolean Astrict = (Item_Count > 0);
		Money = Money > 0 ? Money : 1000;
		Item_Count = Item_Count <= 0 ? 0 : Item_Count;
		HashMap<String, Object> map = new HashMap<>();
		map.put("ID", ID);
		map.put("Money", Money);
		map.put("Item_Count", Item_Count);
		map.put("Astrict", Astrict);
		map.put("Min_Count", MinCount);
		map.put("Type", "Shop_Item");
		map.put("Max_Count", MaxCount);
		HashMap<String, Object> Bt = (HashMap<String, Object>) mis.ShopListConfig.get("Buttons");
		if (!Bt.containsKey(Key)) {
			player.sendMessage(TextFormat.RED + "非常抱歉！您正在创建出售物品的商店页不存在或已被移除");
			return;
		}
		String ConfigName = String.valueOf(((HashMap<String, Object>) Bt.get(Key)).get("File"));
		File file = new File(mis.getDataFolder() + MiniatureS.ShopConfigPath, ConfigName);
		if (!file.exists()) {
			player.sendMessage(TextFormat.RED + "非常抱歉！您正在创建出售物品的商店页不存在或已被移除");
			mis.ShopListConfig.remove(mis.PlayerMenuBack.get(player.getName()));
			mis.ShopListConfig.save();
			return;
		}
		Config config = new Config(file, 2);
		HashMap<String, Object> Buttons;
		if (config.get("Buttons") instanceof HashMap)
			Buttons = (HashMap<String, Object>) config.get("Buttons");
		else
			Buttons = new HashMap<String, Object>();
		Buttons.put(getShopItemKey(Buttons), map);
		config.set("Buttons", Buttons);
		config.save();
		player.sendMessage(TextFormat.GREEN + "您成功的创建了一个可回收的物品！(" + TextFormat.WHITE + "回收每个" + TextFormat.YELLOW
				+ (ItemName != null ? ItemName : ID) + TextFormat.WHITE + "需要花费" + TextFormat.BLUE + Money
				+ (Astrict ? TextFormat.AQUA + ",空闲库存：" + TextFormat.DARK_GRAY + Item_Count
						: TextFormat.AQUA + ",不限制库存")
				+ TextFormat.WHITE + ",出售区间[" + TextFormat.RED + MinCount + TextFormat.WHITE + "-" + TextFormat.RED
				+ MaxCount + TextFormat.WHITE + "])");
	}

	/**
	 * 创建一个出售商店给玩家买东西
	 * 
	 * @param Key        要添加项目商店分页Key
	 * @param player     创建项目的玩家对象
	 * @param ID         要添加的物品ID
	 * @param Money      每个物品的价格
	 * @param Item_Count 物品库存，为零时不限制
	 * @param MinCount   最低玩家得买多少
	 * @param MaxCount   最高只能买多少
	 */
	public void AddItemSell(String Key, Player player, String ID, int Money, int Item_Count, int MinCount,
			int MaxCount) {
		String ItemName = ItemIDSunName.getIDByName(ID);
		boolean Astrict = (Item_Count > 0);
		Money = Money > 0 ? Money : 1000;
		Item_Count = Item_Count <= 0 ? 0 : Item_Count;
		HashMap<String, Object> map = new HashMap<>();
		map.put("ID", ID);
		map.put("Money", Money);
		map.put("Item_Count", Item_Count);
		map.put("Astrict", Astrict);
		map.put("Min_Count", MinCount);
		map.put("Type", "Sell_Item");
		map.put("Max_Count", MaxCount);
		System.out.println(Key);
		HashMap<String, Object> Bt = (HashMap<String, Object>) mis.ShopListConfig.get("Buttons");
		if (!Bt.containsKey(Key)) {
			player.sendMessage(TextFormat.RED + "非常抱歉！您正在创建出售物品的商店页不存在或已被移除");
			return;
		}
		String ConfigName = String.valueOf(((HashMap<String, Object>) Bt.get(Key)).get("File"));
		File file = new File(mis.getDataFolder() + MiniatureS.ShopConfigPath, ConfigName);
		if (!file.exists()) {
			player.sendMessage(TextFormat.RED + "非常抱歉！您正在创建出售物品的商店页不存在或已被移除");
			mis.ShopListConfig.remove(mis.PlayerMenuBack.get(player.getName()));
			mis.ShopListConfig.save();
			return;
		}
		Config config = new Config(file, 2);
		HashMap<String, Object> Buttons;
		if (!config.getString("Buttons").equals("[]"))
			Buttons = (HashMap<String, Object>) config.get("Buttons");
		else
			Buttons = new HashMap<String, Object>();
		Buttons.put(getShopItemKey(Buttons), map);
		config.set("Buttons", Buttons);
		config.save();
		player.sendMessage(TextFormat.GREEN + "您成功的创建了一个可出售的物品！(" + TextFormat.WHITE + "出售每个" + TextFormat.YELLOW
				+ (ItemName != null ? ItemName : ID) + TextFormat.WHITE + "需要花费" + TextFormat.BLUE + Money
				+ (Astrict ? TextFormat.AQUA + ",限制库存：" + TextFormat.DARK_GRAY + Item_Count
						: TextFormat.AQUA + ",不限制库存")
				+ TextFormat.WHITE + ",购买区间[" + TextFormat.RED + MinCount + TextFormat.WHITE + "-" + TextFormat.RED
				+ MaxCount + TextFormat.WHITE + "])");
	}

	/**
	 * 添加一个商店分页
	 * 
	 * @param player      创建这个商店分页的玩家对象
	 * @param ShopName    上了分页名称
	 * @param ShopType    那些玩家可以打开这个商店分页OP|所有玩家|非OP玩家
	 * @param Back_world  黑名单世界列表
	 * @param Back_Player 黑名单玩家列表
	 * @param imageType   贴图类型Path|URL
	 * @param imagePath   按钮贴图路径
	 */
	public void AddShopShow(Player player, String ShopName, String ShopType, ArrayList<String> Back_world,
			ArrayList<String> Back_Player, Boolean imageType, String imagePath) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		String ConfigName = getShopConfigName();
		String BtKey = ShopMakeForm.getShopName(0);
		map.put("Text", ShopName);
		map.put("Image", imagePath);
		map.put("ImageType", true);
		map.put("Player", player.getName());
		map.put("File", ConfigName);
		map.put("Type", ShopType);
		map.put("Back_World", Back_world == null ? new String[] {} : Back_world);
		map.put("Back_Player", Back_Player == null ? new String[] {} : Back_Player);
		Map<String, Object> list;
		if (!mis.ShopListConfig.getString("Buttons").equals("[]"))
			list = (Map<String, Object>) mis.ShopListConfig.get("Buttons");
		else
			list = new HashMap<>();
		LinkedHashMap<String, Object> Con = new LinkedHashMap<>();
		Con.put("Content", null);
		Con.put("Name", BtKey);
		Con.put("Type", ShopType);
		Con.put("Player", player.getName());
		Con.put("Back_World", Back_world == null ? new String[] {} : Back_world);
		Con.put("Back_Player", Back_Player == null ? new String[] {} : Back_Player);
		Con.put("Buttons", new HashMap<String, Object>());
		Config Config = new Config(mis.getDataFolder() + MiniatureS.ShopConfigPath + ConfigName, 2);
		Config.setAll(Con);
		Config.save();
		list.put(BtKey, map);
		mis.ShopListConfig.set("Buttons", list);
		mis.ShopListConfig.save();
		player.sendMessage(TextFormat.GREEN + "创建成功!"
				+ (imageType == true ? (imagePath != null ? "\n设置贴图为：" + imagePath : "") : ""));
	}

	/**
	 * 随机生成一个不重复的商店配置文件名称
	 * 
	 * @return 文件名
	 */
	private String getShopConfigName() {
		String nameString = "";
		int length = Tool.getRand(5, 20);
		for (int i = 0; i < length; i++)
			nameString += Tool.getRandString();
		nameString = nameString + ".yml";
		File file = new File(mis.getDataFolder() + MiniatureS.ShopConfigPath, nameString);
		if (file.exists())
			return getShopConfigName();
		return nameString;
	}

	/**
	 * 随机生成一个商店Key，不重复
	 * 
	 * @param buttons 商店的项目列表，用于检测是否重复
	 * @return 商店kye
	 */
	private String getShopItemKey(HashMap<String, Object> buttons) {
		String nameString = "";
		int length = Tool.getRand(5, 20);
		for (int i = 0; i < length; i++)
			nameString += Tool.getRandString();
		if (buttons.containsKey(nameString))
			return getShopItemKey(buttons);
		return nameString;
	}
}
