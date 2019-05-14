package cn.epicfx.xiaokai.mis.shop;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import cn.epicfx.xiaokai.mis.MiniatureS;
import cn.epicfx.xiaokai.mis.tool.ItemIDSunName;
import cn.epicfx.xiaokai.mis.tool.Tool;
import cn.nukkit.Player;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;

@SuppressWarnings("unchecked")
public class Shop {
	private MiniatureS mis;

	public Shop(MiniatureS mis) {
		this.mis = mis;
	}

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
		player.sendMessage(TextFormat.GREEN + "您成功的创建了一个可回收的物品！(" + TextFormat.WHITE + "回收每个" + TextFormat.YELLOW
				+ (ItemName != null ? ItemName : ID) + TextFormat.WHITE + "需要花费" + TextFormat.BLUE + Money
				+ (Astrict ? TextFormat.AQUA + ",空闲库存：" + TextFormat.DARK_GRAY + Item_Count
						: TextFormat.AQUA + ",不限制库存")
				+ TextFormat.WHITE + ",出售区间[" + TextFormat.RED + MinCount + TextFormat.WHITE + "-" + TextFormat.RED
				+ MaxCount + TextFormat.WHITE + "])");
	}

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

	public void AddShopShow(Player player, String ShopName, String ShopType, String[] Back_world, String[] Back_Player,
			Boolean imageType, String imagePath) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		String ConfigName = getShopConfigName();
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
		Con.put("Name", ShopName);
		Con.put("Player", player.getName());
		Con.put("Back_World", Back_world == null ? new String[] {} : Back_world);
		Con.put("Back_Player", Back_Player == null ? new String[] {} : Back_Player);
		Con.put("Buttons", new HashMap<String, Object>());
		Config Config = new Config(mis.getDataFolder() + MiniatureS.ShopConfigPath + ConfigName, 2);
		Config.setAll(Con);
		Config.save();
		String BtKey = ShopMakeForm.getShopName(0);
		list.put(BtKey, map);
		mis.ShopListConfig.set("Buttons", list);
		mis.ShopListConfig.save();
		player.sendMessage(TextFormat.GREEN + "创建成功!"
				+ (imageType == true ? (imagePath != null ? "\n设置贴图为：" + imagePath : "") : ""));
	}

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
