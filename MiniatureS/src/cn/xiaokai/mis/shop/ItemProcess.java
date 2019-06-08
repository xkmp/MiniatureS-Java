package cn.xiaokai.mis.shop;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.nukkit.Player;
import cn.nukkit.form.element.Element;
import cn.nukkit.form.element.ElementButtonImageData;
import cn.nukkit.form.element.ElementSlider;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.inventory.PlayerInventory;
import cn.nukkit.item.Item;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;
import cn.xiaokai.mis.MiniatureS;
import cn.xiaokai.mis.form.MakeForm;
import cn.xiaokai.mis.form.MakeID;
import cn.xiaokai.mis.tool.ItemIDSunName;
import cn.xiaokai.mis.tool.Tool;
import me.onebone.economyapi.EconomyAPI;

/**
 * @author Winfxk
 */
@SuppressWarnings("unchecked")
public class ItemProcess {
	private MiniatureS mis;

	public ItemProcess(MiniatureS mis) {
		this.mis = mis;
	}

	/**
	 * 判断玩家点击的是什么类型的项目
	 * 
	 * @param player 玩家对象
	 * @param Key    点击的项目键名
	 * @param Shop   点击的商店页键名
	 */
	public void Selection(Player player, String Key, String Shop) {
		Plugin Economy = mis.getServer().getPluginManager().getPlugin("EconomyAPI");
		if (Economy == null || !Economy.isEnabled()) {
			MakeForm.makeTip(player, mis.getMessage().getSon("Shop", "NotEconomyAPI"));
			return;
		}
		if (!(mis.ShopListConfig.get("Buttons") instanceof Map)) {
			MakeForm.makeTip(player, mis.getMessage().getSon("Shop", "SBDataError") + "\nError：无商店列表");
			return;
		}
		if (!(((HashMap<String, Object>) mis.ShopListConfig.get("Buttons")).get(Shop) instanceof Map)) {
			MakeForm.makeTip(player, mis.getMessage().getSon("Shop", "SBDataError") + "\nError：无法获取目标商店数据");
			return;
		}
		if (((HashMap<String, Object>) ((HashMap<String, Object>) mis.ShopListConfig.get("Buttons")).get(Shop))
				.get("File") == null) {
			MakeForm.makeTip(player,
					mis.getMessage().getSon("Shop", "SBDataError") + "\nError：无法获取商店数据，可能已被删除或配置错误，请检查！");
			return;
		}
		String ConfigFileName = String.valueOf(
				((HashMap<String, Object>) ((HashMap<String, Object>) mis.ShopListConfig.get("Buttons")).get(Shop))
						.get("File"));
		File file = new File(mis.getDataFolder() + MiniatureS.ShopConfigPath, ConfigFileName);
		if (!file.exists()) {
			MakeForm.makeTip(player,
					mis.getMessage().getSon("Shop", "SBDataError") + "\nError：目标商店配置数据不存在！可能已被更名活不存在！请检查！");
			return;
		}
		Config config = new Config(file, Config.YAML);
		if (!(config.get("Buttons") instanceof Map)) {
			MakeForm.makeTip(player, mis.getMessage().getSon("Shop", "SBDataError") + "\nError：无法获取目标商店项目列表！");
			return;
		}
		if (!((((HashMap<String, Object>) config.get("Buttons")).get(Key)) instanceof Map)) {
			MakeForm.makeTip(player, mis.getMessage().getSon("Shop", "SBDataError") + "\nError：无法获取目目标项目数据！");
			return;
		}
		HashMap<String, Object> dataHashMap = (HashMap<String, Object>) ((HashMap<String, Object>) config
				.get("Buttons")).get(Key);
		switch (String.valueOf(dataHashMap.get("Type")).toLowerCase()) {
		case "出售物品":
		case "sell_item":
			this.ClickSellItem(player, dataHashMap, Shop, Key);
			break;
		case "shop_item":
		case "回收物品":
			this.ClickShopItem(player, dataHashMap, Shop, Key);
			break;
		case "shop_exp":
		case "购买经验":
			this.ClickShopExp(player, dataHashMap, Shop, Key);
			break;
		case "sell_exp":
		case "回收经验":
			this.ClickSellExp(player, dataHashMap, Shop, Key);
			break;
		case "itemtoitem":
		case "以物换物":
			this.ClickItemToItem(player, dataHashMap, Shop, Key);
			break;
		default:
			MakeForm.makeTip(player, mis.getMessage().getSon("Shop", "SBDataError") + "\nError：未知项目类型！");
			break;
		}
	}

	/**
	 * 当玩家点击的项目为出售玩家物品时创建UI
	 * 
	 * @param player 玩家对象
	 * @param data   玩家点击的项目数据
	 * @param Shop   玩家正在操作的商店Key
	 * @param Key    玩家点击的项目Key
	 */
	public void ClickSellItem(Player player, HashMap<String, Object> data, String Shop, String Key) {
		List<Element> list = new ArrayList<>();
		list.add(new ElementSlider(
				"§f您打算购买多少个§f" + ItemIDSunName.getIDByName(String.valueOf(data.get("ID"))) + TextFormat.WHITE + "?\n"
						+ "§f您当前共有§e" + EconomyAPI.getInstance().myMoney(player) + "§f" + mis.config.getString("货币单位")
						+ "\n购买§e",
				Float.valueOf(String.valueOf(data.get("Min_Count"))).intValue(),
				Float.valueOf(String.valueOf(data.get("Max_Count"))).intValue(), 1,
				Float.valueOf(String.valueOf(data.get("Min_Count"))).intValue()));
		player.showFormWindow(new FormWindowCustom("§f[§e" + Shop + "§f]§a-§f[§c"
				+ ItemIDSunName.getIDByName(String.valueOf(data.get("ID"))) + "§f]§9物品出售", list,
				ItemIDSunName.getIDByPath(String.valueOf(data.get("ID")))), MakeID.PlayerShopInteract.getID());
		mis.PlayerShopInteract.put(player.getName(), data);
		ShopData value = new ShopData();
		value.itemKey = Key;
		value.ShopKey = Shop;
		mis.PlayerShopItemData.put(player.getName(), value);
	}

	/**
	 * 当玩家点击的项目为回收玩家物品时创建UI
	 * 
	 * @param player 玩家对象
	 * @param data   玩家点击的项目数据
	 * @param Shop   玩家正在操作的商店Key
	 * @param Key    玩家点击的项目Key
	 */
	public void ClickShopItem(Player player, HashMap<String, Object> data, String Shop, String Key) {
		List<Element> list = new ArrayList<>();
		PlayerInventory inventory = player.getInventory();
		Map<Integer, Item> items = inventory.getContents();
		int Count = 0;
		for (int ike : items.keySet()) {
			Item item = items.get(ike);
			if (Tool.isMateID(item.getId() + ":" + item.getDamage(), String.valueOf(data.get("ID"))))
				Count += item.getCount();
		}
		list.add(
				new ElementSlider(
						"§f您想要出售多少个§e" + ItemIDSunName.getIDByName(String.valueOf(data.get("ID"))) + "§f?\n§f您当前"
								+ (Count > 0 ? ("共有§e" + Count + "§f个") : "还没有") + "§e"
								+ ItemIDSunName.getIDByName(String.valueOf(data
										.get("ID")))
								+ "§f"
								+ (Count > 0
										? ("\n全部出售约能获利§e"
												+ ((int) (Count * Float.valueOf(String.valueOf(data.get("Money")))))
												+ "§f" + mis.config.getString("货币单位"))
												+ (Count > Float.valueOf(String.valueOf(data.get("Max_Count")))
														.intValue()
																? ("\n§f但由于物品出售上限\n本次您最多越能获利§e"
																		+ ((int) Float
																				.valueOf(String
																						.valueOf(data.get("Max_Count")))
																				.intValue()
																				* Float.valueOf(String
																						.valueOf(data.get("Money"))))
																		+ "§f" + mis.config.getString("货币单位"))
																: "")
										: "")
								+ (Boolean.valueOf(String.valueOf(data.get("Astrict")))
										? ("\n§f剩余库存：" + String.valueOf(data.get("ExpCount")))
										: "")
								+ "\n出售" + TextFormat.AQUA,
						Float.valueOf(String.valueOf(data.get("Min_Count"))).intValue(),
						Float.valueOf(String.valueOf(data.get("Max_Count"))).intValue(), 1,
						Count > Float.valueOf(String.valueOf(data.get("Max_Count"))).intValue()
								? Float.valueOf(String.valueOf(data.get("Max_Count"))).intValue()
								: Count));
		player.showFormWindow(
				new FormWindowCustom(
						TextFormat.WHITE + "[" + TextFormat.YELLOW + Shop + TextFormat.WHITE + "]"
								+ TextFormat.DARK_AQUA + "-" + TextFormat.WHITE + "[" + TextFormat.AQUA
								+ ItemIDSunName.getIDByName(String.valueOf(data.get("ID"))) + TextFormat.WHITE + "]"
								+ TextFormat.BLUE + "物品回收",
						list, ItemIDSunName.getIDByPath(String.valueOf(data.get("ID")))),
				MakeID.PlayerShopInteract.getID());
		mis.PlayerShopInteract.put(player.getName(), data);
		ShopData value = new ShopData();
		value.itemKey = Key;
		value.ShopKey = Shop;
		mis.PlayerShopItemData.put(player.getName(), value);
	}

	/**
	 * 创建一个供玩家购买经验的窗口
	 * 
	 * @param player 要显示窗口的玩家名
	 * @param data   项目数据
	 * @param Shop   项目所在的商店
	 * @param Key    项目Key
	 */
	public void ClickShopExp(Player player, HashMap<String, Object> data, String Shop, String Key) {
		List<Element> list = new ArrayList<>();
		list.add(new ElementSlider(
				TextFormat.WHITE + "您想要购买多少经验？\n" + TextFormat.WHITE + "您当前共有" + TextFormat.AQUA
						+ EconomyAPI.getInstance().myMoney(player) + TextFormat.WHITE + mis.config.getString("货币单位")
						+ (Boolean.valueOf(String.valueOf(data.get("Astrict")))
								? ("\n" + TextFormat.WHITE + "剩余库存：" + String.valueOf(data.get("ExpCount")))
								: "")
						+ "\n购买" + TextFormat.AQUA,
				Float.valueOf(String.valueOf(data.get("MinExp"))).intValue(),
				Float.valueOf(String.valueOf(data.get("MaxExp"))).intValue(), 1,
				Float.valueOf(String.valueOf(data.get("MaxExp"))).intValue()));
		player.showFormWindow(
				new FormWindowCustom(
						TextFormat.WHITE + "[" + TextFormat.YELLOW + Shop + TextFormat.WHITE + "]"
								+ TextFormat.DARK_AQUA + "-" + TextFormat.WHITE + "[" + TextFormat.AQUA + "经验"
								+ TextFormat.WHITE + "]" + TextFormat.BLUE + "经验购买",
						list, "textures/items/gold_nugget.png"),
				MakeID.PlayerShopInteract.getID());
		mis.PlayerShopInteract.put(player.getName(), data);
		ShopData value = new ShopData();
		value.itemKey = Key;
		value.ShopKey = Shop;
		mis.PlayerShopItemData.put(player.getName(), value);
	}

	/**
	 * 创建一个供玩家出售经验的窗口
	 * 
	 * @param player 要显示窗口的玩家名
	 * @param data   项目数据
	 * @param Shop   项目所在的商店
	 * @param Key    项目Key
	 */
	public void ClickSellExp(Player player, HashMap<String, Object> data, String Shop, String Key) {
		List<Element> list = new ArrayList<>();
		int exp = player.getExperienceLevel();
		list.add(new ElementSlider(
				TextFormat.WHITE + "您想出售多少经验？\n" + TextFormat.WHITE + "您当前共有" + TextFormat.YELLOW + exp
						+ TextFormat.WHITE + "级\n" + TextFormat.WHITE + "最多可出售" + TextFormat.YELLOW + exp
						+ TextFormat.WHITE + "经验"
						+ (Boolean.valueOf(String.valueOf(data.get("Astrict")))
								? ("\n" + TextFormat.WHITE + "剩余库存：" + String.valueOf(data.get("ExpCount")))
								: "")
						+ "\n出售" + TextFormat.AQUA,
				Float.valueOf(String.valueOf(data.get("MinExp"))).intValue(),
				Float.valueOf(String.valueOf(data.get("MaxExp"))).intValue(), 1,
				exp < 1 ? Float.valueOf(String.valueOf(data.get("MinExp"))).intValue()
						: (exp > Float.valueOf(String.valueOf(data.get("MaxExp"))).intValue()
								? Float.valueOf(String.valueOf(data.get("MaxExp"))).intValue()
								: exp)));
		player.showFormWindow(
				new FormWindowCustom(
						TextFormat.WHITE + "[" + TextFormat.YELLOW + Shop + TextFormat.WHITE + "]"
								+ TextFormat.DARK_AQUA + "-" + TextFormat.WHITE + "[" + TextFormat.AQUA + "经验"
								+ TextFormat.WHITE + "]" + TextFormat.BLUE + "经验出售",
						list, "textures/items/gold_nugget.png"),
				MakeID.PlayerShopInteract.getID());
		mis.PlayerShopInteract.put(player.getName(), data);
		ShopData value = new ShopData();
		value.itemKey = Key;
		value.ShopKey = Shop;
		mis.PlayerShopItemData.put(player.getName(), value);
	}

	/**
	 * 创建一个以物换物的窗口
	 * 
	 * @param player 要显示窗口的玩家对象
	 * @param data   以物换物的数据
	 * @param Shop   所在商店的Key
	 * @param Key    所点击的项目的Key
	 */
	public void ClickItemToItem(Player player, HashMap<String, Object> data, String Shop, String Key) {
		List<Element> list = new ArrayList<>();
		String FFFSB;
		String FFF_PY_FFFF = "------------------------------";
		String ItemsString = "";
		ArrayList<String> icon = new ArrayList<>();
		if (data.get("BlockID") instanceof List) {
			for (String ditm : ((ArrayList<String>) data.get("BlockID"))) {
				ItemsString += Tool.getRandColor() + ItemIDSunName.getIDByName(ditm) + " §e1" + Tool.getRandColor()
						+ "个\n";
				icon.add(ItemIDSunName.getIDByPath(ditm));
			}
		} else if (data.get("BlockID") instanceof Map) {
			Map<String, Object> SCFFF = (Map<String, Object>) data.get("BlockID");
			for (String ditm : SCFFF.keySet()) {
				ItemsString += Tool.getRandColor() + ItemIDSunName.getIDByName(ditm) + " §e" + SCFFF.get(ditm)
						+ Tool.getRandColor() + " 个\n";
				icon.add(ItemIDSunName.getIDByPath(ditm));
			}
		} else {
			ItemsString = Tool.getRandColor() + ItemIDSunName.getIDByName(String.valueOf(data.get("BlockID"))) + " §e1"
					+ Tool.getRandColor() + "个\n";
			icon.add(ItemIDSunName.getIDByPath(String.valueOf(data.get("BlockID"))));
		}
		String moneyItemString = "";
		if (data.get("ToBlockID") instanceof List) {
			ArrayList<String> zZFFFString = (ArrayList<String>) data.get("ToBlockID");
			for (String ditm : zZFFFString)
				moneyItemString += Tool.getRandColor() + ItemIDSunName.getIDByName(ditm) + " §e1" + Tool.getRandColor()
						+ "个\n";
		} else if (data.get("ToBlockID") instanceof Map) {
			Map<String, Object> sbF = ((Map<String, Object>) data.get("ToBlockID"));
			for (String ditm : sbF.keySet()) {
				moneyItemString += Tool.getRandColor() + ItemIDSunName.getIDByName(ditm) + " §e" + sbF.get(ditm)
						+ Tool.getRandColor() + "个\n";
			}
		} else
			moneyItemString = Tool.getRandColor() + ItemIDSunName.getIDByName(String.valueOf(data.get("ToBlockID")))
					+ " §e1" + Tool.getRandColor() + "个\n";
		FFFSB = "§f您将可以兑换以下物品\n" + Tool.getColorFont(FFF_PY_FFFF + "\n") + moneyItemString
				+ Tool.getColorFont(FFF_PY_FFFF) + "\n§4兑换这些物品您将会失去以下物品\n" + Tool.getColorFont(FFF_PY_FFFF + "\n")
				+ ItemsString + Tool.getColorFont(FFF_PY_FFFF + "\n")
				+ (Boolean.valueOf(String.valueOf(data.get("Astrict")))
						? ("\n§f剩余库存：§4" + String.valueOf(data.get("ItemCount")))
						: "")
				+ (Float.valueOf(String.valueOf(data.get("ItemMoeny"))).intValue() > 0
						? ("\n§f每次兑换需要消耗§5" + Float.valueOf(String.valueOf(data.get("ItemMoeny"))).intValue() + "§f"
								+ mis.config.getString("货币单位"))
						: "")
				+ Tool.getColorFont("\n兑换前请检查背包空间是否充足！！！！") + "\n兑换";
		list.add(new ElementSlider(FFFSB, Float.valueOf(String.valueOf(data.get("Min"))).intValue(),
				Float.valueOf(String.valueOf(data.get("Max"))).intValue(), 1,
				Float.valueOf(String.valueOf(data.get("Min"))).intValue()));
		player.showFormWindow(
				new FormWindowCustom("§f[§e" + Shop + "§f]§a-§9物品兑换", list, new ElementButtonImageData(
						ElementButtonImageData.IMAGE_DATA_TYPE_PATH, icon.get(Tool.getRand(0, icon.size() - 1)))),
				MakeID.PlayerShopInteract.getID());
		mis.PlayerShopInteract.put(player.getName(), data);
		ShopData value = new ShopData();
		value.itemKey = Key;
		value.ShopKey = Shop;
		mis.PlayerShopItemData.put(player.getName(), value);
	}
}
