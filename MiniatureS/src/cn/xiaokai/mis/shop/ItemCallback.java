package cn.xiaokai.mis.shop;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.inventory.PlayerInventory;
import cn.nukkit.item.Item;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;
import cn.xiaokai.mis.MiniatureS;
import cn.xiaokai.mis.form.MakeForm;
import cn.xiaokai.mis.tool.ItemIDSunName;
import cn.xiaokai.mis.tool.Tool;
import me.onebone.economyapi.EconomyAPI;

@SuppressWarnings("unchecked")
public class ItemCallback {
	private MiniatureS mis;
	private Player player;
	private FormResponseCustom data;
	private HashMap<String, Object> map;

	/**
	 * 玩家上的点击了项目判断所需数量并处理
	 * 
	 * @param mis    插件主类对象
	 * @param player 触发这个事件的玩家
	 * @param data   返回的数据
	 */
	public ItemCallback(MiniatureS mis, Player player, FormResponseCustom data) {
		this.mis = mis;
		this.data = data;
		this.player = player;
	}

	public void start() {
		if (mis.PlayerShopInteract.get(player.getName()) == null
				|| !(mis.PlayerShopInteract.get(player.getName()) instanceof Map)) {
			MakeForm.makeTip(player, TextFormat.RED + "打开项目错误！Error：无法获取项目数据！");
			return;
		}
		map = mis.PlayerShopInteract.get(player.getName());
		switch (String.valueOf(map.get("Type")).toLowerCase()) {
		case "itemtoitem":
		case "以物换物":
			this.ItemToItem();
			break;
		case "sell_exp":
		case "回收经验":
			this.SellExp();
			break;
		case "shop_exp":
		case "购买经验":
			this.ShopExp();
			break;
		case "shop_item":
		case "回收物品":
			this.SellItem();
			break;
		case "出售物品":
		case "sell_item":
			this.ShopItem();
			break;
		default:
			MakeForm.makeTip(player, TextFormat.RED + "打开项目错误！Error：无法获取项目类型！");
			break;
		}
	}

	/**
	 * 给玩家兑换物品用
	 */
	private void ItemToItem() {
		if (Float.valueOf(String.valueOf(data.getResponse(0))).intValue() < 1) {
			MakeForm.makeTip(player, TextFormat.RED + "经验出售失败！Error：请输入一个大于零的回收数！");
			return;
		}
		int Count = Float.valueOf(String.valueOf(data.getResponse(0))).intValue();
		Count = Count > Float.valueOf(String.valueOf(map.get("Max"))).intValue()
				? Float.valueOf(String.valueOf(map.get("Max"))).intValue()
				: (Count < Float.valueOf(String.valueOf(map.get("Min"))).intValue()
						? Float.valueOf(String.valueOf(map.get("Min"))).intValue()
						: Count);
		int Money = Float.valueOf(String.valueOf(map.get("Money"))).intValue() * Count;
		if (Money > EconomyAPI.getInstance().myMoney(player)) {
			MakeForm.makeTip(player,
					TextFormat.RED + "物品兑换失败！Error：你没有足够的" + mis.config.getString("货币单位") + "来完成兑换！\n"
							+ TextFormat.YELLOW + "每个物品兑换需消耗" + TextFormat.WHITE
							+ Float.valueOf(String.valueOf(map.get("Money"))).intValue() + TextFormat.YELLOW
							+ mis.config.getString("货币单位") + "\n" + TextFormat.YELLOW + "本次兑换需要" + TextFormat.WHITE
							+ Money + TextFormat.YELLOW + mis.config.getString("货币单位") + "\n" + TextFormat.YELLOW
							+ "您当前有" + TextFormat.WHITE + EconomyAPI.getInstance().myMoney(player) + TextFormat.YELLOW
							+ mis.config.getString("货币单位") + "\n" + TextFormat.YELLOW + "还需" + TextFormat.WHITE
							+ (Money - EconomyAPI.getInstance().myMoney(player)) + TextFormat.YELLOW
							+ mis.config.getString("货币单位"));
			return;
		}
		PlayerInventory inventory = player.getInventory();
		Map<Integer, Item> items = inventory.getContents();
		int MyCount = 0;
		for (int ike : items.keySet()) {
			Item item = items.get(ike);
			MyCount += item.getCount();
		}
		if (MyCount < (Float.valueOf(String.valueOf(map.get("ItemMoeny"))).intValue() * Count)) {
			MakeForm.makeTip(player, TextFormat.RED + "物品兑换失败！Error：您的物品不足！\n" + TextFormat.YELLOW + "本次兑换您需要"
					+ TextFormat.AQUA + (Float.valueOf(String.valueOf(map.get("ItemMoeny"))).intValue() * Count)
					+ ItemIDSunName.getIDByName(String.valueOf(map.get("BlockID"))) + "\n" + TextFormat.YELLOW + "您当前"
					+ (MyCount > 0 ? ("有" + TextFormat.AQUA + MyCount + TextFormat.YELLOW + "个") : "还没有没有")
					+ TextFormat.WHITE + ItemIDSunName.getIDByName(String.valueOf(map.get("BlockID"))) + "\n"
					+ TextFormat.YELLOW + "还需要" + TextFormat.AQUA
					+ ((Float.valueOf(String.valueOf(map.get("ItemMoeny"))).intValue() * Count) - MyCount)
					+ TextFormat.YELLOW + "个" + TextFormat.WHITE
					+ ItemIDSunName.getIDByName(String.valueOf(map.get("BlockID"))));
			return;
		}
		if (Boolean.valueOf(String.valueOf(map.get("Astrict")))) {
			if (Float.valueOf(String.valueOf(map.get("ItemCount"))).intValue() < 1) {
				MakeForm.makeTip(player, TextFormat.RED + "物品回收失败！Error：仓库已空！无法购买，您可以联系管理员添加库存！");
				return;
			} else if (Float.valueOf(String.valueOf(map.get("ItemCount"))).intValue() < Count) {
				MakeForm.makeTip(player,
						TextFormat.RED + "物品回收失败！Error：仓库剩余库存不足！无法购买，您可以联系管理员添加库存！\n" + TextFormat.YELLOW + "剩余库存："
								+ TextFormat.WHITE + String.valueOf(map.get("ItemCount")) + "\n" + TextFormat.YELLOW
								+ "所需库存：" + TextFormat.WHITE + Count + "\n" + TextFormat.YELLOW + "欠缺库存："
								+ (Count - Float.valueOf(String.valueOf(map.get("ItemCount"))).intValue()));
				return;
			} else {
				map.put("Item_Count", Float.valueOf(String.valueOf(map.get("ItemCount"))).intValue() - Count);
				ShopData value = mis.PlayerShopItemData.get(player.getName());
				String ShopConfigName = String
						.valueOf(((HashMap<String, Object>) mis.ShopListConfig.get("Buttons")).get(value.ShopKey));
				Config config = new Config(new File(mis.getDataFolder() + MiniatureS.ShopConfigPath, ShopConfigName),
						Config.YAML);
				HashMap<String, Object> ppap = (HashMap<String, Object>) config.get("Buttons");
				ppap.put(value.itemKey, map);
				config.set("Buttons", ppap);
				config.save();
			}
		}
		EconomyAPI.getInstance().reduceMoney(player, Money);
		String IDi = ItemIDSunName.UnknownToID(String.valueOf(map.get("BlockID")));
		String[] IDs = IDi.split(":");
		inventory.removeItem(new Item(Integer.valueOf(IDs[0]), Integer.valueOf(IDs[1]),
				Float.valueOf(String.valueOf(map.get("ItemMoeny"))).intValue() * Count));
		IDi = ItemIDSunName.UnknownToID(String.valueOf(map.get("ToBlockID")));
		IDs = IDi.split(":");
		inventory.addItem(new Item(Integer.valueOf(IDs[0]), Integer.valueOf(IDs[1]), Count));
		player.sendMessage(TextFormat.GREEN + "您成功使用" + TextFormat.AQUA
				+ (Float.valueOf(String.valueOf(map.get("ItemMoeny"))).intValue() * Count) + TextFormat.WHITE
				+ ItemIDSunName.getIDByName(ItemIDSunName.UnknownToID(String.valueOf(map.get("BlockID")))) + "兑换了"
				+ TextFormat.AQUA + Count + TextFormat.GREEN + "个" + TextFormat.WHITE + Count
				+ ItemIDSunName.getIDByName(IDi)
				+ (Money > 0
						? (TextFormat.GREEN + ",同时消耗" + TextFormat.WHITE + Money + TextFormat.GREEN
								+ mis.config.getString("货币单位"))
						: ""));
		this.remove();
	}

	/**
	 * 给玩家回收经验用
	 */
	private void SellExp() {
		if (Float.valueOf(String.valueOf(data.getResponse(0))).intValue() < 1) {
			MakeForm.makeTip(player, TextFormat.RED + "经验出售失败！Error：请输入一个大于零的回收数！");
			return;
		}
		int Count = Float.valueOf(String.valueOf(data.getResponse(0))).intValue();
		Count = Count > Float.valueOf(String.valueOf(map.get("MaxExp"))).intValue()
				? Float.valueOf(String.valueOf(map.get("MaxExp"))).intValue()
				: (Count < Float.valueOf(String.valueOf(map.get("MinExp"))).intValue()
						? Float.valueOf(String.valueOf(map.get("MinExp"))).intValue()
						: Count);
		int Money = Count * Float.valueOf(String.valueOf(map.get("Money"))).intValue();
		if (Count > player.getExperienceLevel()) {
			MakeForm.makeTip(player,
					TextFormat.RED + "经验出售失败！Error：你没有足够的经验来出售！\n" + TextFormat.YELLOW + "需要：" + TextFormat.WHITE
							+ Count + "\n" + TextFormat.YELLOW + "您有：" + TextFormat.WHITE + player.getExperienceLevel()
							+ "\n" + TextFormat.YELLOW + "还差：" + TextFormat.WHITE
							+ (Count - player.getExperienceLevel()));
			return;
		}
		if (Boolean.valueOf(String.valueOf(map.get("Astrict")))) {
			if (Float.valueOf(String.valueOf(map.get("ExpCount"))).intValue() < 1) {
				MakeForm.makeTip(player, TextFormat.RED + "物品回收失败！Error：仓库已空！无法购买，您可以联系管理员添加库存！");
				return;
			} else if (Float.valueOf(String.valueOf(map.get("ExpCount"))).intValue() < Count) {
				MakeForm.makeTip(player,
						TextFormat.RED + "物品回收失败！Error：仓库剩余库存不足！无法购买，您可以联系管理员添加库存！\n" + TextFormat.YELLOW + "剩余库存："
								+ TextFormat.WHITE + String.valueOf(map.get("ExpCount")) + "\n" + TextFormat.YELLOW
								+ "所需库存：" + TextFormat.WHITE + Count + "\n" + TextFormat.YELLOW + "欠缺库存："
								+ (Count - Float.valueOf(String.valueOf(map.get("ExpCount"))).intValue()));
				return;
			} else {
				map.put("Item_Count", Float.valueOf(String.valueOf(map.get("ExpCount"))).intValue() - Count);
				ShopData value = mis.PlayerShopItemData.get(player.getName());
				String ShopConfigName = String
						.valueOf(((HashMap<String, Object>) mis.ShopListConfig.get("Buttons")).get(value.ShopKey));
				Config config = new Config(new File(mis.getDataFolder() + MiniatureS.ShopConfigPath, ShopConfigName),
						Config.YAML);
				HashMap<String, Object> ppap = (HashMap<String, Object>) config.get("Buttons");
				ppap.put(value.itemKey, map);
				config.set("Buttons", ppap);
				config.save();
			}
		}
		EconomyAPI.getInstance().addMoney(player, Money);
		player.setExperience(player.getExperience(), (player.getExperienceLevel() - Count));
		player.sendMessage(TextFormat.GREEN + "你成功出售了" + TextFormat.WHITE + Count + TextFormat.GREEN + "级经验！获利："
				+ TextFormat.WHITE + Money + TextFormat.GREEN + mis.config.getString("货币单位"));
		this.remove();
	}

	/**
	 * 给玩家购买经验
	 */
	private void ShopExp() {
		if (Float.valueOf(String.valueOf(data.getResponse(0))).intValue() < 1) {
			MakeForm.makeTip(player, TextFormat.RED + "经验购买失败！Error：请输入一个大于零的购买数！");
			return;
		}
		int Count = Float.valueOf(String.valueOf(data.getResponse(0))).intValue();
		Count = Count > Float.valueOf(String.valueOf(map.get("MaxExp"))).intValue()
				? Float.valueOf(String.valueOf(map.get("MaxExp"))).intValue()
				: (Count < Float.valueOf(String.valueOf(map.get("MinExp"))).intValue()
						? Float.valueOf(String.valueOf(map.get("MinExp"))).intValue()
						: Count);
		int Money = Count * Float.valueOf(String.valueOf(map.get("Money"))).intValue();
		if (Money > EconomyAPI.getInstance().myMoney(player)) {
			MakeForm.makeTip(player, TextFormat.RED + "经验购买失败！Error：你没有足够的" + mis.config.getString("货币单位")
					+ "来购买这个项目！\n" + TextFormat.YELLOW + "需要：" + TextFormat.WHITE + Money + TextFormat.YELLOW
					+ mis.config.getString("货币单位") + "\n" + TextFormat.YELLOW + "您有：" + TextFormat.WHITE
					+ EconomyAPI.getInstance().myMoney(player) + TextFormat.YELLOW + mis.config.getString("货币单位") + "\n"
					+ TextFormat.YELLOW + "还差：" + TextFormat.WHITE + (Money - EconomyAPI.getInstance().myMoney(player))
					+ TextFormat.YELLOW + mis.config.getString("货币单位"));
			return;
		}
		if (Boolean.valueOf(String.valueOf(map.get("Astrict")))) {
			if (Float.valueOf(String.valueOf(map.get("ExpCount"))).intValue() < 1) {
				MakeForm.makeTip(player, TextFormat.RED + "物品回收失败！Error：仓库已空！无法购买，您可以联系管理员添加库存！");
				return;
			} else if (Float.valueOf(String.valueOf(map.get("ExpCount"))).intValue() < Count) {
				MakeForm.makeTip(player,
						TextFormat.RED + "物品回收失败！Error：仓库剩余库存不足！无法购买，您可以联系管理员添加库存！\n" + TextFormat.YELLOW + "剩余库存："
								+ TextFormat.WHITE + String.valueOf(map.get("ExpCount")) + "\n" + TextFormat.YELLOW
								+ "所需库存：" + TextFormat.WHITE + Count + "\n" + TextFormat.YELLOW + "欠缺库存："
								+ (Count - Float.valueOf(String.valueOf(map.get("ExpCount"))).intValue()));
				return;
			} else {
				map.put("Item_Count", Float.valueOf(String.valueOf(map.get("ExpCount"))).intValue() - Count);
				ShopData value = mis.PlayerShopItemData.get(player.getName());
				String ShopConfigName = String
						.valueOf(((HashMap<String, Object>) mis.ShopListConfig.get("Buttons")).get(value.ShopKey));
				Config config = new Config(new File(mis.getDataFolder() + MiniatureS.ShopConfigPath, ShopConfigName),
						Config.YAML);
				HashMap<String, Object> ppap = (HashMap<String, Object>) config.get("Buttons");
				ppap.put(value.itemKey, map);
				config.set("Buttons", ppap);
				config.save();
			}
		}
		EconomyAPI.getInstance().reduceMoney(player, Money);
		player.setExperience(player.getExperience(), (player.getExperienceLevel() + Count));
		player.sendMessage(TextFormat.GREEN + "成功购买" + TextFormat.WHITE + Count + TextFormat.GREEN + "级经验，花费:"
				+ TextFormat.WHITE + Money + TextFormat.GREEN + mis.config.getString("货币单位"));
		this.remove();
	}

	/**
	 * 开始回收玩家的物品
	 */
	private void ShopItem() {
		if (Float.valueOf(String.valueOf(data.getResponse(0))).intValue() < 1) {
			MakeForm.makeTip(player, TextFormat.RED + "物品购买失败！Error：请输入一个大于零的回收数！");
			return;
		}
		int Count = Float.valueOf(String.valueOf(data.getResponse(0))).intValue();
		Count = Count > Float.valueOf(String.valueOf(map.get("Max_Count"))).intValue()
				? Float.valueOf(String.valueOf(map.get("Max_Count"))).intValue()
				: (Count < Float.valueOf(String.valueOf(map.get("Min_Count"))).intValue()
						? Float.valueOf(String.valueOf(map.get("Min_Count"))).intValue()
						: Count);
		int Money = Count * Float.valueOf(String.valueOf(map.get("Money"))).intValue();
		if (Money > EconomyAPI.getInstance().myMoney(player)) {
			MakeForm.makeTip(player, TextFormat.RED + "物品购买失败！Error：你没有足够的" + mis.config.getString("货币单位")
					+ "来购买这个物品！\n" + TextFormat.YELLOW + "需要：" + TextFormat.WHITE + Money + TextFormat.YELLOW
					+ mis.config.getString("货币单位") + "\n" + TextFormat.YELLOW + "您有：" + TextFormat.WHITE
					+ EconomyAPI.getInstance().myMoney(player) + TextFormat.YELLOW + mis.config.getString("货币单位") + "\n"
					+ TextFormat.YELLOW + "还差：" + TextFormat.WHITE + (Money - EconomyAPI.getInstance().myMoney(player))
					+ TextFormat.YELLOW + mis.config.getString("货币单位"));
			return;
		}
		if (Boolean.valueOf(String.valueOf(map.get("Astrict")))) {
			if (Float.valueOf(String.valueOf(map.get("Item_Count"))).intValue() < 1) {
				MakeForm.makeTip(player, TextFormat.RED + "物品回收失败！Error：仓库已空！无法购买，您可以联系管理员添加库存！");
				return;
			} else if (Float.valueOf(String.valueOf(map.get("Item_Count"))).intValue() < Count) {
				MakeForm.makeTip(player,
						TextFormat.RED + "物品回收失败！Error：仓库剩余库存不足！无法购买，您可以联系管理员添加库存！\n" + TextFormat.YELLOW + "剩余库存："
								+ TextFormat.WHITE + String.valueOf(map.get("Item_Count")) + "\n" + TextFormat.YELLOW
								+ "所需库存：" + TextFormat.WHITE + Count + "\n" + TextFormat.YELLOW + "欠缺库存："
								+ (Count - Float.valueOf(String.valueOf(map.get("Item_Count"))).intValue()));
				return;
			} else {
				map.put("Item_Count", Float.valueOf(String.valueOf(map.get("Item_Count"))).intValue() - Count);
				ShopData value = mis.PlayerShopItemData.get(player.getName());
				String ShopConfigName = String
						.valueOf(((HashMap<String, Object>) mis.ShopListConfig.get("Buttons")).get(value.ShopKey));
				Config config = new Config(new File(mis.getDataFolder() + MiniatureS.ShopConfigPath, ShopConfigName),
						Config.YAML);
				HashMap<String, Object> ppap = (HashMap<String, Object>) config.get("Buttons");
				ppap.put(value.itemKey, map);
				config.set("Buttons", ppap);
				config.save();
			}
		}
		PlayerInventory inventory = player.getInventory();
		String IDi = ItemIDSunName.UnknownToID(String.valueOf(map.get("ID")));
		String[] IDs = IDi.split(":");
		inventory.addItem(new Item(Integer.valueOf(IDs[0]), Integer.valueOf(IDs[1]), Count));
		EconomyAPI.getInstance().reduceMoney(player, Money);
		player.sendMessage(
				TextFormat.GREEN + "您成功购买了" + TextFormat.WHITE + ItemIDSunName.getIDByName(IDi) + TextFormat.GREEN
						+ "，花费：" + TextFormat.WHITE + Money + TextFormat.GREEN + " " + mis.config.getString("货币单位"));
		this.remove();
	}

	/**
	 * 出售物品给玩家
	 */
	private void SellItem() {
		if (Float.valueOf(String.valueOf(data.getResponse(0))).intValue() < 1) {
			MakeForm.makeTip(player, TextFormat.RED + "物品回收失败！Error：请输入一个大于零的回收数！");
			return;
		}
		int Count = Float.valueOf(String.valueOf(data.getResponse(0))).intValue();
		Count = Count > Float.valueOf(String.valueOf(map.get("Max_Count"))).intValue()
				? Float.valueOf(String.valueOf(map.get("Max_Count"))).intValue()
				: (Count < Float.valueOf(String.valueOf(map.get("Min_Count"))).intValue()
						? Float.valueOf(String.valueOf(map.get("Min_Count"))).intValue()
						: Count);
		PlayerInventory inventory = player.getInventory();
		Map<Integer, Item> items = inventory.getContents();
		int MyCount = 0;
		for (int ike : items.keySet()) {
			Item item = items.get(ike);
			if (Tool.isMateID(item.getId() + ":" + item.getDamage(), String.valueOf(map.get("ID"))))
				MyCount += item.getCount();
		}
		if (MyCount < Count) {
			MakeForm.makeTip(player,
					TextFormat.RED + "物品回收失败！Error：你没有足够的物品！\n" + TextFormat.YELLOW + "需要：" + TextFormat.WHITE + Count
							+ "\n" + TextFormat.YELLOW + "您有：" + TextFormat.WHITE + MyCount + "\n" + TextFormat.YELLOW
							+ "还差：" + TextFormat.WHITE + (Count - MyCount));
			return;
		}
		if (Boolean.valueOf(String.valueOf(map.get("Astrict")))) {
			if (Float.valueOf(String.valueOf(map.get("Item_Count"))).intValue() < 1) {
				MakeForm.makeTip(player, TextFormat.RED + "物品回收失败！Error：仓库已空！无法购买，您可以联系管理员添加库存！");
				return;
			} else if (Float.valueOf(String.valueOf(map.get("Item_Count"))).intValue() < Count) {
				MakeForm.makeTip(player,
						TextFormat.RED + "物品回收失败！Error：仓库剩余库存不足！无法购买，您可以联系管理员添加库存！\n" + TextFormat.YELLOW + "剩余库存："
								+ TextFormat.WHITE + String.valueOf(map.get("Item_Count")) + "\n" + TextFormat.YELLOW
								+ "所需库存：" + TextFormat.WHITE + Count + "\n" + TextFormat.YELLOW + "欠缺库存："
								+ (Count - Float.valueOf(String.valueOf(map.get("Item_Count"))).intValue()));
				return;
			} else {
				map.put("Item_Count", Float.valueOf(String.valueOf(map.get("Item_Count"))).intValue() - Count);
				ShopData value = mis.PlayerShopItemData.get(player.getName());
				String ShopConfigName = String
						.valueOf(((HashMap<String, Object>) mis.ShopListConfig.get("Buttons")).get(value.ShopKey));
				Config config = new Config(new File(mis.getDataFolder() + MiniatureS.ShopConfigPath, ShopConfigName),
						Config.YAML);
				HashMap<String, Object> ppap = (HashMap<String, Object>) config.get("Buttons");
				ppap.put(value.itemKey, map);
				config.set("Buttons", ppap);
				config.save();
			}
		}
		String IDi = ItemIDSunName.UnknownToID(String.valueOf(map.get("ID")));
		String[] IDs = IDi.split(":");
		inventory.removeItem(new Item(Integer.valueOf(IDs[0]), Integer.valueOf(IDs[1]), Count));
		EconomyAPI.getInstance().addMoney(player, Count * Float.valueOf(String.valueOf(map.get("Money"))));
		player.sendMessage(TextFormat.GREEN + "成功出售 " + TextFormat.WHITE + Count + TextFormat.GREEN + " 个"
				+ TextFormat.AQUA + ItemIDSunName.getIDByName(IDi) + TextFormat.GREEN + "，获得" + TextFormat.WHITE
				+ (Count * Float.valueOf(String.valueOf(map.get("Money")))) + TextFormat.GREEN
				+ mis.config.getString("货币单位"));
		this.remove();
	}
	private void remove() {
		if (mis.PlayerShopInteract.get(player.getName()) != null)
			mis.PlayerShopInteract.remove(player.getName());
		if (mis.PlayerMenuBack.get(player.getName()) != null)
			mis.PlayerMenuBack.remove(player.getName());
		if (mis.shopList.get(player.getName()) != null)
			mis.shopList.remove(player.getName());
		if (mis.PlayerMenu.get(player.getName()) != null)
			mis.PlayerMenu.remove(player.getName());
		if (mis.PlayerShopItemData.get(player.getName()) != null)
			mis.PlayerShopItemData.remove(player.getName());
	}
}
