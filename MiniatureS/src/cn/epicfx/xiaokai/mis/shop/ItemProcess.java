package cn.epicfx.xiaokai.mis.shop;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.epicfx.xiaokai.mis.MiniatureS;
import cn.epicfx.xiaokai.mis.form.MakeForm;
import cn.epicfx.xiaokai.mis.form.MakeID;
import cn.epicfx.xiaokai.mis.tool.ItemIDSunName;
import cn.epicfx.xiaokai.mis.tool.Tool;

import cn.nukkit.Player;
import cn.nukkit.form.element.Element;
import cn.nukkit.form.element.ElementSlider;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.inventory.PlayerInventory;
import cn.nukkit.item.Item;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;
import me.onebone.economyapi.EconomyAPI;

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
			MakeForm.makeTip(player, TextFormat.RED + "未检测到经济插件(" + TextFormat.WHITE + "EconomyAPI" + TextFormat.RED
					+ ")！可能未安装或未启用！商店功能不可用！");
			return;
		}
		if (!(mis.ShopListConfig.get("Buttons") instanceof Map)) {
			MakeForm.makeTip(player, TextFormat.RED + "无法打开该项目！请联系服务器管理员或反馈Bug。Error：无商店列表");
			return;
		}
		if (!(((HashMap<String, Object>) mis.ShopListConfig.get("Buttons")).get(Shop) instanceof Map)) {
			MakeForm.makeTip(player, TextFormat.RED + "无法打开该项目！请联系服务器管理员或反馈Bug。Error：无法获取目标商店数据");
			return;
		}
		if (((HashMap<String, Object>) ((HashMap<String, Object>) mis.ShopListConfig.get("Buttons")).get(Shop))
				.get("File") == null) {
			MakeForm.makeTip(player, TextFormat.RED + "无法打开该项目！请联系服务器管理员或反馈Bug。Error：无法获取商店数据，可能已被删除或配置错误，请检查！");
			return;
		}
		String ConfigFileName = String.valueOf(
				((HashMap<String, Object>) ((HashMap<String, Object>) mis.ShopListConfig.get("Buttons")).get(Shop))
						.get("File"));
		File file = new File(mis.getDataFolder() + MiniatureS.ShopConfigPath, ConfigFileName);
		if (!file.exists()) {
			MakeForm.makeTip(player, TextFormat.RED + "无法打开该项目！请联系服务器管理员或反馈Bug。Error：目标商店配置数据不存在！可能已被更名活不存在！请检查！");
			return;
		}
		Config config = new Config(file, Config.YAML);
		if (!(config.get("Buttons") instanceof Map)) {
			MakeForm.makeTip(player, TextFormat.RED + "无法打开该项目！请联系服务器管理员或反馈Bug。Error：无法获取目标商店项目列表！");
			return;
		}
		if (!((((HashMap<String, Object>) config.get("Buttons")).get(Key)) instanceof Map)) {
			MakeForm.makeTip(player, TextFormat.RED + "无法打开该项目！请联系服务器管理员或反馈Bug。Error：无法获取目目标项目数据！");
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
			MakeForm.makeTip(player, TextFormat.RED + "无法打开该项目！请联系服务器管理员或反馈Bug。Error：未知项目类型！");
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
				TextFormat.WHITE + "您打算购买多少个" + TextFormat.YELLOW
						+ ItemIDSunName.getIDByName(String.valueOf(data.get("ID"))) + TextFormat.WHITE + "?\n"
						+ TextFormat.WHITE + "您当前共有" + TextFormat.YELLOW + EconomyAPI.getInstance().myMoney(player)
						+ TextFormat.WHITE + mis.config.getString("货币单位") + "\n购买" + TextFormat.AQUA,
				Float.valueOf(String.valueOf(data.get("Min_Count"))).intValue(),
				Float.valueOf(String.valueOf(data.get("Max_Count"))).intValue(), 1,
				Float.valueOf(String.valueOf(data.get("Min_Count"))).intValue()));
		player.showFormWindow(
				new FormWindowCustom(
						TextFormat.WHITE + "[" + TextFormat.YELLOW + Shop + TextFormat.WHITE + "]"
								+ TextFormat.DARK_AQUA + "-" + TextFormat.WHITE + "[" + TextFormat.AQUA + Key
								+ TextFormat.WHITE + "]" + TextFormat.BLUE + "物品出售",
						list, ItemIDSunName.getIDByPath(String.valueOf(data.get("ID")))),
				MakeID.PlayerShopInteract.getID());
		mis.PlayerShopInteract.put(player.getName(), data);
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
		list.add(new ElementSlider(
				TextFormat.WHITE + "您想要出售多少个" + TextFormat.YELLOW
						+ ItemIDSunName.getIDByName(String.valueOf(data.get("ID"))) + TextFormat.WHITE + "?\n"
						+ TextFormat.WHITE + "您当前"
						+ (Count > 0 ? ("共有" + TextFormat.YELLOW + Count + TextFormat.WHITE + "个") : "还没有")
						+ TextFormat.YELLOW + ItemIDSunName.getIDByName(String.valueOf(data.get("ID")))
						+ TextFormat.WHITE
						+ (Count > 0 ? (",全部出售约能获利" + TextFormat.YELLOW
								+ ((int) (Count * Float.valueOf(String.valueOf(data.get("Money"))))) + TextFormat.WHITE
								+ mis.config.getString("货币单位"))
								+ (Count > Float.valueOf(String.valueOf(data.get("Max_Count"))).intValue()
										? ("\n" + TextFormat.WHITE + ",但由于物品出售上限，本次您最多越能获利" + TextFormat.YELLOW
												+ ((int) Float.valueOf(String.valueOf(data.get("Max_Count"))).intValue()
														* Float.valueOf(String.valueOf(data.get("Money"))))
												+ TextFormat.WHITE + mis.config.getString("货币单位"))
										: "")
								: "")
						+ (Boolean.valueOf(String.valueOf(data.get("Astrict")))
								? ("\n" + TextFormat.WHITE + "剩余库存：" + String.valueOf(data.get("ExpCount")))
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
								+ TextFormat.DARK_AQUA + "-" + TextFormat.WHITE + "[" + TextFormat.AQUA + Key
								+ TextFormat.WHITE + "]" + TextFormat.BLUE + "物品回收",
						list, ItemIDSunName.getIDByPath(String.valueOf(data.get("ID")))),
				MakeID.PlayerShopInteract.getID());
		mis.PlayerShopInteract.put(player.getName(), data);
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
								+ TextFormat.DARK_AQUA + "-" + TextFormat.WHITE + "[" + TextFormat.AQUA + Key
								+ TextFormat.WHITE + "]" + TextFormat.BLUE + "经验购买",
						list, "textures/items/gold_nugget.png"),
				MakeID.PlayerShopInteract.getID());
		mis.PlayerShopInteract.put(player.getName(), data);
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
								+ TextFormat.DARK_AQUA + "-" + TextFormat.WHITE + "[" + TextFormat.AQUA + Key
								+ TextFormat.WHITE + "]" + TextFormat.BLUE + "经验出售",
						list, "textures/items/gold_nugget.png"),
				MakeID.PlayerShopInteract.getID());
		mis.PlayerShopInteract.put(player.getName(), data);
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
		PlayerInventory inventory = player.getInventory();
		Map<Integer, Item> items = inventory.getContents();
		int Count = 0;
		for (Integer ike : items.keySet()) {
			Item item = items.get(ike);
			if (Tool.isMateID(item.getId() + ":" + item.getDamage(), String.valueOf(data.get("BlockID"))))
				Count += item.getCount();
		}
		int couu = Count > 0 ? (Count / (Float
				.valueOf(String.valueOf(data.get("ItemMoeny") == null ? 1 : data.get("ItemMoeny"))).intValue() == 0 ? 1
						: Float.valueOf(String.valueOf(data.get("ItemMoeny") == null ? 1 : data.get("ItemMoeny")))
								.intValue()))
				: 0;
		list.add(
				new ElementSlider(
						TextFormat.WHITE + "您想兑换多少个 " + TextFormat.YELLOW + ItemIDSunName.getIDByName(String.valueOf(
								data.get("ToBlockID"))) + TextFormat.WHITE + "?\n" + TextFormat.WHITE + "您当前共有 "
								+ TextFormat.GOLD + Count + TextFormat.WHITE + " 个" + TextFormat.DARK_GREEN
								+ ItemIDSunName.getIDByName(String.valueOf(data.get("BlockID"))) + "\n"
								+ TextFormat.WHITE + "最多可兑换 " + TextFormat.DARK_PURPLE + couu + TextFormat.WHITE + " 个"
								+ TextFormat.YELLOW + ItemIDSunName.getIDByName(String.valueOf(data.get("ToBlockID")))
								+ (Boolean.valueOf(String.valueOf(data.get("Astrict")))
										? ("\n" + TextFormat.WHITE + "剩余库存：" + TextFormat.RED
												+ String.valueOf(data.get("ExpCount")))
										: "")
								+ (Float.valueOf(String.valueOf(data.get("ItemMoeny"))).intValue() > 0
										? ("\n" + TextFormat.WHITE + "兑换每个" + TextFormat.YELLOW
												+ ItemIDSunName.getIDByName(String.valueOf(data.get("ToBlockID")))
												+ TextFormat.WHITE + "需要消耗" + TextFormat.AQUA
												+ Float.valueOf(String.valueOf(data.get("ItemMoeny"))).intValue()
												+ TextFormat.WHITE + mis.config.getString("货币单位"))
										: "")
								+ "\n兑换" + TextFormat.RED,
						Float.valueOf(String.valueOf(data.get("Min"))).intValue(),
						Float.valueOf(String.valueOf(data.get("Max"))).intValue(), 1,
						couu < 1 ? Float.valueOf(String.valueOf(data.get("Min"))).intValue()
								: (couu > Float.valueOf(String.valueOf(data.get("Max"))).intValue()
										? Float.valueOf(String.valueOf(data.get("Max"))).intValue()
										: couu)));
		player.showFormWindow(
				new FormWindowCustom(
						TextFormat.WHITE + "[" + TextFormat.YELLOW + Shop + TextFormat.WHITE + "]"
								+ TextFormat.DARK_AQUA + "-" + TextFormat.WHITE + "[" + TextFormat.AQUA + Key
								+ TextFormat.WHITE + "]" + TextFormat.BLUE + "物品兑换",
						list, "textures/ui/cartography_table_glass.png"),
				MakeID.PlayerShopInteract.getID());
		mis.PlayerShopInteract.put(player.getName(), data);
	}
}
