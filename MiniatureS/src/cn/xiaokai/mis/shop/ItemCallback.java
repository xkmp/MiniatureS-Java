package cn.xiaokai.mis.shop;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

/**
 * @author Winfxk
 */
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
			MakeForm.makeTip(player, mis.getMessage().getSon("Shop", "ItemItemCountSB"));
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
					mis.getMessage().getSon("Shop", "ItemToItemMoenySB",
							new String[] { "{reMoney}", "{Money}", "{MyMoney}", "{asMoney}" },
							new Object[] { Float.valueOf(String.valueOf(map.get("Money"))).intValue(), Money,
									EconomyAPI.getInstance().myMoney(player),
									(Money - EconomyAPI.getInstance().myMoney(player)) }));
			return;
		}
		PlayerInventory inventory = player.getInventory();
		Map<Integer, Item> items = inventory.getContents();
		Map<String, Integer> myIMap = new HashMap<>();
		String YaoDeWuPing = "";
		if (map.get("BlockID") instanceof Map) {
			Map<String, Object> xs = (Map<String, Object>) map.get("BlockID");
			for (String xx : xs.keySet()) {
				YaoDeWuPing += "§e" + Count * (Tool.isInteger(String.valueOf(xs.get(xx)))
						? Float.valueOf(String.valueOf(xs.get(xx))).intValue()
						: 1) + "§6个§f" + ItemIDSunName.getIDByName(xx) + "\n";
				myIMap.put(xx,
						Count * (Tool.isInteger(String.valueOf(xs.get(xx)))
								? Float.valueOf(String.valueOf(xs.get(xx))).intValue()
								: 1));
			}
		} else if (map.get("BlockID") instanceof List) {
			ArrayList<String> myList = (ArrayList<String>) map.get("BlockID");
			for (String myitString : myList) {
				YaoDeWuPing += "§e" + Count + "§6个§f" + ItemIDSunName.getIDByName(myitString) + "\n";
				myIMap.put(myitString, Count);
			}
		} else {
			myIMap.put(String.valueOf(map.get("BlockID")), Count);
			YaoDeWuPing += "§e" + Count + "§6个§f" + ItemIDSunName.getIDByName(String.valueOf(map.get("BlockID")))
					+ "\n";
		}
		for (String itname : myIMap.keySet()) {
			int MyCount = 0;
			for (int ike : items.keySet()) {
				Item item = items.get(ike);
				if (Tool.isMateID(item.getId() + ":" + item.getDamage(), itname))
					MyCount += item.getCount();
			}
			if (MyCount < (Float.valueOf(String.valueOf(map.get("ItemMoeny"))).intValue() * Count)) {
				MakeForm.makeTip(player, mis.getMessage().getSon("Shop", "ItemToItemItemSB",
						new String[] { "{asCount}", "{isOK}", "{ItemName}", "{getCount}" },
						new Object[] {
								(Float.valueOf(String.valueOf(map.get("ItemMoeny"))).intValue() * Count) + "§6个§f"
										+ ItemIDSunName.getIDByName(itname),
								(MyCount > 0 ? ("有§5" + MyCount + "§e个") : "还没有没有"), ItemIDSunName.getIDByName(itname),
								((Float.valueOf(String.valueOf(map.get("ItemMoeny"))).intValue() * Count)
										- MyCount) }));
				return;
			}
		}
		if (Boolean.valueOf(String.valueOf(map.get("Astrict")))) {
			if (Float.valueOf(String.valueOf(map.get("ItemCount"))).intValue() < 1) {
				MakeForm.makeTip(player, mis.getMessage().getSon("Shop", "ItemCount0"));
				return;
			} else if (Float.valueOf(String.valueOf(map.get("ItemCount"))).intValue() < Count) {
				MakeForm.makeTip(player, mis.getMessage().getSon("Shop", "ItemCountas0",
						new String[] { "{Count}", "{asCount}", "{getCount}" }, new Object[] { map.get("ItemCount"),
								Count, (Count - Float.valueOf(String.valueOf(map.get("ItemCount"))).intValue()) }));
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
		for (String ik : myIMap.keySet()) {
			String IDi = ItemIDSunName.UnknownToID(ik);
			String[] IDs = IDi.split(":");
			inventory.removeItem(new Item(Integer.valueOf(IDs[0]), Integer.valueOf(IDs[1]), myIMap.get(ik) * Count));
		}
		String DeDeWuPing = "";
		Map<String, Integer> Dedewuping = new HashMap<>();
		if (map.get("ToBlockID") instanceof List) {
			ArrayList<String> list = (ArrayList<String>) map.get("ToBlockID");
			for (String in : list) {
				Dedewuping.put(in, Count);
				DeDeWuPing += "§e" + Count + "§f个§4" + ItemIDSunName.getIDByName(in) + "\n";
			}
		} else if (map.get("ToBlockID") instanceof Map) {
			HashMap<String, Object> mHashMap = (HashMap<String, Object>) map.get("ToBlockID");
			for (String is : mHashMap.keySet()) {
				int XCOUNt = Count * (Tool.isInteger(String.valueOf(mHashMap.get(is)))
						? Float.valueOf(String.valueOf(mHashMap.get(is))).intValue()
						: 1);
				DeDeWuPing += "§e" + XCOUNt + "§f个§4" + ItemIDSunName.getIDByName(is) + "\n";
				Dedewuping.put(is, XCOUNt);
			}
		} else {
			Dedewuping.put(String.valueOf(map.get("ToBlockID")), Count);
			DeDeWuPing += "§e" + Count + "§f个§4" + ItemIDSunName.getIDByName(String.valueOf(map.get("ToBlockID")))
					+ "\n";
		}
		for (String dd : Dedewuping.keySet()) {
			String IDi = ItemIDSunName.UnknownToID(dd);
			String[] IDs = IDi.split(":");
			inventory.addItem(new Item(Integer.valueOf(IDs[0]), Integer.valueOf(IDs[1]), Dedewuping.get(dd) * Count));
		}
		String FFF_PY_FFFF = "------------------------------";
		String sssss = "§6您成功兑换了以下物品\n" + Tool.getColorFont(FFF_PY_FFFF + "\n") + DeDeWuPing
				+ Tool.getColorFont(FFF_PY_FFFF + "\n") + "§4同时失去以下物品\n" + Tool.getColorFont(FFF_PY_FFFF + "\n")
				+ YaoDeWuPing + Tool.getColorFont(FFF_PY_FFFF + "\n")
				+ (Money > 0
						? (TextFormat.GREEN + ",同时消耗" + TextFormat.WHITE + Money + TextFormat.GREEN
								+ mis.config.getString("货币单位"))
						: "");
		player.sendMessage(sssss);
		this.remove();
	}

	/**
	 * 给玩家回收经验用
	 */
	private void SellExp() {
		if (Float.valueOf(String.valueOf(data.getResponse(0))).intValue() < 1) {
			MakeForm.makeTip(player, mis.getMessage().getSon("Shop", "ItemItemCountSB"));
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
			MakeForm.makeTip(player, mis.getMessage().getSon("Shop", "NetExpSB",
					new String[] { "{Count}", "{Exp}", "{getExp}" },
					new Object[] { Count, player.getExperienceLevel(), (Count - player.getExperienceLevel()) }));
			return;
		}
		if (Boolean.valueOf(String.valueOf(map.get("Astrict")))) {
			if (Float.valueOf(String.valueOf(map.get("ExpCount"))).intValue() < 1) {
				MakeForm.makeTip(player, mis.getMessage().getSon("Shop", "ItemCount0"));
				return;
			} else if (Float.valueOf(String.valueOf(map.get("ExpCount"))).intValue() < Count) {
				MakeForm.makeTip(player, mis.getMessage().getSon("Shop", "ItemCount0"));
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
		player.sendMessage(mis.getMessage().getSon("Shop", "ExpPyOKNotSB", new String[] { "{Count}", "{Money}" },
				new Object[] { Count, Money }));
		this.remove();
	}

	/**
	 * 给玩家购买经验
	 */
	private void ShopExp() {
		if (Float.valueOf(String.valueOf(data.getResponse(0))).intValue() < 1) {
			MakeForm.makeTip(player, mis.getMessage().getSon("Shop", "ItemItemCountSB"));
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
			MakeForm.makeTip(player,
					mis.getMessage().getSon("Shop", "ItemToItemMoenySB",
							new String[] { "{MoneyName}", "{reMoney}", "{Money}", "{MyMoney}", "{asMoney}" },
							new Object[] { Money, Money, EconomyAPI.getInstance().myMoney(player),
									(Money - EconomyAPI.getInstance().myMoney(player)), }));
			return;
		}
		if (Boolean.valueOf(String.valueOf(map.get("Astrict")))) {
			if (Float.valueOf(String.valueOf(map.get("ExpCount"))).intValue() < 1) {
				MakeForm.makeTip(player, mis.getMessage().getSon("Shop", "ItemCount0"));
				return;
			} else if (Float.valueOf(String.valueOf(map.get("ExpCount"))).intValue() < Count) {
				MakeForm.makeTip(player, mis.getMessage().getSon("Shop", "ItemCount0"));
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
		player.sendMessage(mis.getMessage().getSon("Shop", "ExpPyOKNotSbShop", new String[] { "{Count}", "{Money}" },
				new Object[] { Count, Money }));
		this.remove();
	}

	/**
	 * 开始回收玩家的物品
	 */
	private void ShopItem() {
		if (Float.valueOf(String.valueOf(data.getResponse(0))).intValue() < 1) {
			MakeForm.makeTip(player, mis.getMessage().getSon("Shop", "ItemItemCountSB"));
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
			MakeForm.makeTip(player,
					mis.getMessage().getSon("Shop", "ItemToItemMoenySB",
							new String[] { "{MoneyName}", "{reMoney}", "{Money}", "{MyMoney}", "{asMoney}" },
							new Object[] { Money, Money, EconomyAPI.getInstance().myMoney(player),
									(Money - EconomyAPI.getInstance().myMoney(player)), }));
			return;
		}
		if (Boolean.valueOf(String.valueOf(map.get("Astrict")))) {
			if (Float.valueOf(String.valueOf(map.get("Item_Count"))).intValue() < 1) {
				MakeForm.makeTip(player, mis.getMessage().getSon("Shop", "ItemCount0"));
				return;
			} else if (Float.valueOf(String.valueOf(map.get("Item_Count"))).intValue() < Count) {
				MakeForm.makeTip(player, mis.getMessage().getSon("Shop", "ItemCount0"));
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
		player.sendMessage(mis.getMessage().getSon("Shop", "ShopItemOKNotSB", new String[] { "{ItemName}", "{Money}" },
				new Object[] { ItemIDSunName.getIDByName(IDi), Money }));
		this.remove();
	}

	/**
	 * 出售物品给玩家
	 */
	private void SellItem() {
		if (Float.valueOf(String.valueOf(data.getResponse(0))).intValue() < 1) {
			MakeForm.makeTip(player, mis.getMessage().getSon("Shop", "ItemItemCountSB"));
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
					mis.getMessage().getSon("Shop", "SBPlayerNotItem",
							new String[] { "{Count}", "{MyCount}", "{reMoney}" },
							new Object[] { Count, MyCount, (Count - MyCount) }));
			return;
		}
		if (Boolean.valueOf(String.valueOf(map.get("Astrict")))) {
			if (Float.valueOf(String.valueOf(map.get("Item_Count"))).intValue() < 1) {
				MakeForm.makeTip(player, mis.getMessage().getSon("Shop", "ItemCount0"));
				return;
			} else if (Float.valueOf(String.valueOf(map.get("Item_Count"))).intValue() < Count) {
				MakeForm.makeTip(player, mis.getMessage().getSon("Shop", "ItemCount0"));
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
		player.sendMessage(mis.getMessage().getSon("Shop", "PYItemOKNotSB",
				new String[] { "{Count}", "{ItemName}", "{Money}" }, new Object[] { Count,
						ItemIDSunName.getIDByName(IDi), (Count * Float.valueOf(String.valueOf(map.get("Money")))) }));
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
