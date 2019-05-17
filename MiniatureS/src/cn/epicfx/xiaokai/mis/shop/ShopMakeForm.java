package cn.epicfx.xiaokai.mis.shop;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.epicfx.xiaokai.mis.MiniatureS;
import cn.epicfx.xiaokai.mis.form.FormStatic;
import cn.epicfx.xiaokai.mis.form.MakeForm;
import cn.epicfx.xiaokai.mis.form.MakeID;
import cn.epicfx.xiaokai.mis.tool.ItemIDSunName;
import cn.epicfx.xiaokai.mis.tool.Tool;

import cn.nukkit.Player;
import cn.nukkit.form.element.Element;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementButtonImageData;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.element.ElementSlider;
import cn.nukkit.form.element.ElementStepSlider;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;

@SuppressWarnings("unchecked")
public class ShopMakeForm {
	private MiniatureS mis;

	public ShopMakeForm(MiniatureS miniatureS) {
		this.mis = miniatureS;
	}

	/**
	 * 以物换物
	 * 
	 * @param player
	 */
	public void AddItemToItem(Player player) {
		if (!player.isOp()) {
			MakeForm.makeTip(player, TextFormat.RED + "你没有权限设置该页！");
			return;
		}
		List<Element> list = new ArrayList<>();
		list.add(new ElementInput(TextFormat.GREEN + "请输入商店可以换的物品ID/物品名称  " + TextFormat.WHITE + "多个使用;分割", "玩家将会换得什么",
				"钻石"));
		list.add(new ElementInput(TextFormat.GREEN + "请输入玩家换物所需的物品ID/物品名称" + TextFormat.WHITE + "多个使用;分割", "玩家需要用什么来还",
				player.getInventory().getItemInHand().getId() != 0
						? (player.getInventory().getItemInHand().getId() + ":"
								+ player.getInventory().getItemInHand().getDamage())
						: ""));
		list.add(new ElementInput(TextFormat.GREEN + "同时扣除玩家多少钱", "玩家在兑换物品时会扣除的金币", "1"));
		list.add(new ElementInput(TextFormat.GREEN + "玩家每次能换的最少数", "", "1"));
		list.add(new ElementInput(TextFormat.GREEN + "玩家每次能换的最大数", "", "64"));
		list.add(new ElementInput(TextFormat.GREEN + "请输入想要出售的经验库存", "留空或小于等于零时不限制库存", "0"));
		list.add(new ElementInput(TextFormat.GREEN + "需要多少个(金币)物品才能兑换目标物品 ", "", "1"));
		player.showFormWindow(
				new FormWindowCustom(Tool.getRandColor() + mis.PlayerMenuBack.get(player.getName()), list),
				MakeID.AddItemToItem.getID());
	}

	/**
	 * 让玩家回收经验给系统
	 * 
	 * @param player
	 */
	public void AddExpSell(Player player) {
		if (!player.isOp()) {
			MakeForm.makeTip(player, TextFormat.RED + "你没有权限设置该页！");
			return;
		}
		List<Element> list = new ArrayList<>();
		list.add(new ElementInput(TextFormat.RED + "请输入每级等级售价多少", "请输入玩家在购买等级时的每级售价",
				String.valueOf(100000 / ((player.getExperienceLevel() < 1) ? 1 : player.getExperienceLevel()))));
		list.add(new ElementInput(TextFormat.GREEN + "玩家每次购买等级的最少数", "", "1"));
		list.add(new ElementInput(TextFormat.GREEN + "玩家每次购买等级的最大数", "", "50"));
		list.add(new ElementInput(TextFormat.GREEN + "请输入想要出售的经验库存", "留空或小于等于零时不限制库存", "0"));
		player.showFormWindow(
				new FormWindowCustom(Tool.getRandColor() + mis.PlayerMenuBack.get(player.getName()), list),
				MakeID.AddExpSell.getID());
	}

	/**
	 * 卖经验等级给玩家
	 * 
	 * @param player
	 */
	public void AddExpShop(Player player) {
		if (!player.isOp()) {
			MakeForm.makeTip(player, TextFormat.RED + "你没有权限设置该页！");
			return;
		}
		List<Element> list = new ArrayList<>();
		list.add(new ElementInput(TextFormat.RED + "请输入每级等级售价多少", "请输入玩家在购买等级时的每级售价",
				String.valueOf(100000 / ((player.getExperienceLevel() < 1) ? 1 : player.getExperienceLevel()))));
		list.add(new ElementInput(TextFormat.GREEN + "玩家每次购买等级的最少数", "", "1"));
		list.add(new ElementInput(TextFormat.GREEN + "玩家每次购买等级的最大数", "", "50"));
		list.add(new ElementInput(TextFormat.GREEN + "请输入想要出售的经验库存", "留空或小于等于零时不限制库存", "0"));
		player.showFormWindow(
				new FormWindowCustom(Tool.getRandColor() + mis.PlayerMenuBack.get(player.getName()), list),
				MakeID.AddExpShop.getID());
	}

	/**
	 * 给玩家回收物品用
	 * 
	 * @param player
	 */
	public void AddItemShop(Player player) {
		if (!player.isOp()) {
			MakeForm.makeTip(player, TextFormat.RED + "你没有权限设置该页！");
			return;
		}
		List<Element> list = new ArrayList<>();
		list.add(new ElementInput(TextFormat.RED + "请输入您想要回收的物品名称/ID", "支持输入物品ID/物品名称",
				player.getInventory().getItemInHand().getId() != 0
						? (player.getInventory().getItemInHand().getId() + ":"
								+ player.getInventory().getItemInHand().getDamage())
						: ""));
		list.add(new ElementSlider(TextFormat.DARK_AQUA + "物品数量" + TextFormat.WHITE, 1, 64, 1, 1));
		list.add(new ElementInput(TextFormat.GOLD + "回收价格", "回收这些物品需要花费的东西 将会自动计算单个物品价格",
				String.valueOf(Tool.getRand(Tool.getRand(0, 499), Tool.getRand(500, 2000)))));
		list.add(new ElementInput(TextFormat.GREEN + "请输入想要出售的物品库存", "留空或小于等于零时不限制物品库存", "0"));
		list.add(new ElementInput(TextFormat.GREEN + "玩家每次回收物品的最少数", "", "1"));
		list.add(new ElementInput(TextFormat.GREEN + "玩家每次回收物品的最大数", "", "64"));
		player.showFormWindow(
				new FormWindowCustom(Tool.getRandColor() + mis.PlayerMenuBack.get(player.getName()), list),
				MakeID.AddItemShop.getID());
	}

	/**
	 * 添加一个出售类型的物品到商店
	 * 
	 * @param player
	 */
	public void addItemSell(Player player) {
		if (!player.isOp()) {
			MakeForm.makeTip(player, TextFormat.RED + "你没有权限设置该页！");
			return;
		}
		List<Element> list = new ArrayList<>();
		list.add(new ElementInput(TextFormat.GREEN + "请输入想要添加出售的物品ID", "您可以输入物品ID或物品名称",
				player.getInventory().getItemInHand().getId() != 0
						? (player.getInventory().getItemInHand().getId() + ":"
								+ player.getInventory().getItemInHand().getDamage())
						: ""));
		list.add(new ElementSlider(TextFormat.DARK_AQUA + "物品数量" + TextFormat.WHITE, 1, 64, 1, 1));
		list.add(new ElementInput(TextFormat.GOLD + "出售价格", "购买这些物品需要花费的东西 将会自动计算单个物品价格",
				String.valueOf(Tool.getRand(Tool.getRand(0, 499), Tool.getRand(500, 2000)))));
		list.add(new ElementInput(TextFormat.GREEN + "请输入想要出售的物品库存", "留空或小于等于零时不限制物品库存", "0"));
		list.add(new ElementInput(TextFormat.GREEN + "玩家每次购买的最少数", "", "1"));
		list.add(new ElementInput(TextFormat.GREEN + "玩家每次购买的最大数", "", "64"));
		player.showFormWindow(
				new FormWindowCustom(Tool.getRandColor() + mis.PlayerMenuBack.get(player.getName()), list),
				MakeID.AddItemSell.getID());
	}

	/**
	 * 选择要添加的物品商店类型
	 * 
	 * @param player
	 */
	public void SelectShopType(Player player) {
		if (!player.isOp()) {
			MakeForm.makeTip(player, TextFormat.RED + "你没有权限设置该页！");
			return;
		}
		player.showFormWindow(
				new FormWindowSimple(Tool.getRandColor() + mis.PlayerMenuBack.get(player.getName()),
						Tool.getRandColor() + "请选择您需要创建的商店类型", FormStatic.getAddItemToShopType()),
				MakeID.AddShopType.getID());
	}

	/**
	 * 添加一个商店分页
	 * 
	 * @param player
	 */
	public void MakeAddShop(Player player) {
		if (!player.isOp()) {
			MakeForm.makeTip(player, TextFormat.RED + "你没有权限设置该页！");
			return;
		}
		List<Element> list = new ArrayList<>();
		list.add(
				new ElementInput(TextFormat.GREEN + "请输入商店分页名称", getShopName(0), Tool.getRandColor() + getShopName(0)));
		list.add(new ElementStepSlider(TextFormat.GREEN + "请选择商店使用范围" + TextFormat.WHITE,
				Arrays.asList(FormStatic.shopGetTypeStrings)));
		list.add(new ElementInput(TextFormat.GREEN + "黑名单世界", "为空时不使用黑名单、多个世界用;分割"));
		list.add(new ElementInput(TextFormat.GREEN + "黑名单玩家", "为空时不使用、多个玩家使用;分割"));
		list.add(new ElementStepSlider(TextFormat.GREEN + "按钮贴图" + TextFormat.WHITE,
				Arrays.asList(FormStatic.ShopImageType)));
		list.add(new ElementInput(TextFormat.GREEN + "贴图路径/贴图物品ID/贴图物品名称/贴图链接 ", "当选择无贴图时请忽略本项，网络贴图请输入完整连接",
				ItemIDSunName.getIDByPath(
						player.getInventory().getItemInHand().getId() != 0
								? (player.getInventory().getItemInHand().getId() + ":"
										+ player.getInventory().getItemInHand().getDamage())
								: "")));
		player.showFormWindow(
				new FormWindowCustom(Tool.getRandColor() + mis.getName() + TextFormat.GREEN + "添加商店分页", list),
				MakeID.ShopAddShop.getID());
	}

	/**
	 * 显示商店分页
	 * 
	 * @param player
	 * @param ShopKey
	 */
	public void MakeShowShopShow(Player player, String ShopKey) {
		Map<String, Object> Button = (HashMap<String, Object>) ((Map<String, Object>) mis.ShopListConfig.get("Buttons"))
				.get(ShopKey);
		String ConfigName = String.valueOf(Button.get("File"));
		File file = new File(mis.getDataFolder() + MiniatureS.ShopConfigPath, ConfigName);
		if (!file.exists()) {
			player.sendMessage(TextFormat.RED + "该商店不存在！");
			return;
		}
		switch (String.valueOf(Button.get("Type") == null ? "All" : Button.get("Type")).toLowerCase()) {
		case "player":
		case "玩家":
		case "普通玩家":
			if (!player.isOp())
				break;
			else {
				player.sendMessage(TextFormat.RED + "您无法使用该商店！该商店仅供服务器普通玩家使用使用！！");
				return;
			}
		case "op":
		case "管理员":
			if (player.isOp())
				break;
			else {
				player.sendMessage(TextFormat.RED + "您无法使用该商店！该商店仅供服务器管理员使用！！");
				return;
			}
		case "all":
		case "全部":
		default:
			break;
		}
		if (Button.get("Back_Player") != null && (Button.get("Back_Player") instanceof List)
				&& ((ArrayList<String>) Button.get("Back_Player")).contains(player.getName())) {
			player.sendMessage(TextFormat.RED + "您无法使用该商店！您已被该商店列为黑名单！");
			return;
		}
		if (Button.get("Back_World") != null && (Button.get("Back_World") instanceof List)
				&& ((ArrayList<String>) Button.get("Back_World")).contains(player.getLevel().getFolderName())) {
			player.sendMessage(TextFormat.RED + "您无法在该世界使用该商店！您所在的世界已被该商店列为黑名单！");
			return;
		}
		Config config = new Config(file, 2);
		List<ElementButton> List = new ArrayList<ElementButton>();
		ArrayList<String> strings = new ArrayList<>();
		if (config.get("Buttons") != null && config.get("Buttons") instanceof Map) {
			Map<String, Object> bs = (Map<String, Object>) config.get("Buttons");
			for (String Key : bs.keySet()) {
				strings.add(Key);
				Map<String, Object> map = (Map<String, Object>) bs.get(Key);
				switch (String.valueOf(map.get("Type")).toLowerCase()) {
				case "itemtoitem":
				case "以物换物":
					List.add(new ElementButton(
							TextFormat.YELLOW
									+ String.valueOf((map.get("ItemMoeny") == null ? "1" : map.get("ItemMoeny")))
									+ TextFormat.BLACK + "个" + TextFormat.YELLOW
									+ ItemIDSunName.getIDByName(String.valueOf(map.get("BlockID"))) + TextFormat.BLACK
									+ "可兑换一个" + TextFormat.YELLOW + ItemIDSunName.getIDByName(String.valueOf(map.get(
											"ToBlockID")))
									+ TextFormat.BLACK
									+ (Float.valueOf(String.valueOf(map.get("Money"))).intValue() > 0
											? ("并扣除" + TextFormat.WHITE + String.valueOf(map.get("Money"))
													+ TextFormat.BLACK + mis.config.getString("货币单位"))
											: "")
									+ (Boolean.valueOf(String.valueOf(map.get("Astrict")))
											? ("，空余库存：" + TextFormat.WHITE + String.valueOf(map.get("ExpCount")))
											: "，不限制库存"),
							new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH,
									"textures/ui/cartography_table_glass.png")));
					break;
				case "sell_exp":
				case "回收经验":
					List.add(
							new ElementButton(
									TextFormat.BLACK
											+ "出售每级经验可得" + TextFormat.YELLOW + String.valueOf(map.get("Money"))
											+ TextFormat.BLACK + mis.config.getString("货币单位")
											+ (Boolean.valueOf(String.valueOf(map.get("Astrict")))
													? ("，空余库存：" + TextFormat.WHITE
															+ String.valueOf(map.get("ExpCount")))
													: "，不限制库存"),
									new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH,
											"textures/items/gold_nugget.png")));
					break;
				case "shop_exp":
				case "出售经验":
					List.add(
							new ElementButton(
									TextFormat.BLACK
											+ "购买每级经验需要" + TextFormat.YELLOW + String.valueOf(map.get("Money"))
											+ TextFormat.BLACK + mis.config.getString("货币单位")
											+ (Boolean.valueOf(String.valueOf(map.get("Astrict")))
													? ("，限制库存：" + TextFormat.WHITE
															+ String.valueOf(map.get("ExpCount")))
													: "，不限制库存"),
									new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH,
											"textures/items/gold_nugget.png")));
					break;
				case "shop_item":
				case "回收物品":
					List.add(
							new ElementButton(
									TextFormat.BLACK + "出售每个" + TextFormat.YELLOW
											+ ItemIDSunName.getIDByName(String.valueOf(map.get("ID")))
											+ TextFormat.BLACK + "可得" + TextFormat.BLUE
											+ String.valueOf(map.get("Money")) + TextFormat.BLACK
											+ mis.config.getString("货币单位")
											+ (Boolean.valueOf(String.valueOf(map.get("Astrict")))
													? (TextFormat.WHITE + " 库存："
															+ String.valueOf(map.get("Item_Count")))
													: ""),
									new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH,
											ItemIDSunName.UnknownToPath(String.valueOf(map.get("ID"))))));
					break;
				case "出售物品":
				case "sell_item":
				default:
					List.add(
							new ElementButton(
									TextFormat.BLACK + "每个" + TextFormat.YELLOW
											+ ItemIDSunName.getIDByName(String.valueOf(map.get("ID")))
											+ TextFormat.BLACK + "购买需花费" + TextFormat.BLUE
											+ String.valueOf(map.get("Money")) + TextFormat.BLACK
											+ mis.config.getString("货币单位")
											+ (Boolean.valueOf(String.valueOf(map.get("Astrict")))
													? (TextFormat.WHITE + " 库存："
															+ String.valueOf(map.get("Item_Count")))
													: ""),
									new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH,
											ItemIDSunName.UnknownToPath(String.valueOf(map.get("ID"))))));
					break;
				}
			}
		}
		if (player.isOp()) {
			List.add(new ElementButton(Tool.getRandColor() + "添加物品"));
		}
		mis.shopList.put(player.getName(), strings);
		player.showFormWindow(new FormWindowSimple(Tool.getRandColor() + String.valueOf(Button.get("Text")),
				config.exists("Content") ? config.get("Content") == null ? "" : config.getString("Content") : "", List),
				MakeID.Shop.getID());
		mis.PlayerMenuBack.put(player.getName(), ShopKey);
	}

	/**
	 * 显示商店UI主页
	 * 
	 * @param player
	 */
	public void MakeMain(Player player) {
		List<ElementButton> buttons = new ArrayList<ElementButton>();
		Map<String, Object> Config = mis.ShopListConfig.getAll();
		Map<String, Object> Button;
		Map<String, Object> ButtonList = new HashMap<>();
		ArrayList<String> list = new ArrayList<>();
		if (Config.get("Buttons") != null && Config.get("Buttons") instanceof Map) {
			ButtonList = (Map<String, Object>) Config.get("Buttons");
			if (ButtonList != null && ButtonList.size() > 0) {
				for (String key : ButtonList.keySet()) {
					Button = (Map<String, Object>) ButtonList.get(key);
					list.add(key);
					if (Button.get("Image") != null && Button.get("ImageType") != null)
						buttons.add(new ElementButton(String.valueOf(Button.getOrDefault("Text", "")),
								new ElementButtonImageData(
										Boolean.valueOf(String.valueOf(Button.get("ImageType")))
												? ElementButtonImageData.IMAGE_DATA_TYPE_PATH
												: ElementButtonImageData.IMAGE_DATA_TYPE_URL,
										String.valueOf(Button.get("Image")))));
					else
						buttons.add(new ElementButton(String.valueOf(Button.getOrDefault("Text", ""))));
				}
			}
		}
		if (player.isOp())
			buttons.add(new ElementButton(TextFormat.GREEN + "添加商店"));
		player.showFormWindow(new FormWindowSimple(TextFormat.YELLOW + mis.getName() + TextFormat.GREEN + "商店",
				TextFormat.LIGHT_PURPLE
						+ String.valueOf(
								(Config.get("Content") != null || String.valueOf(Config.get("Content")) != "null")
										? Config.get("Content")
										: "")
						+ ((ButtonList == null || ButtonList.size() < 1) ? (TextFormat.RED + "暂无任何商店") : ""),
				buttons), MakeID.ShopMain.getID());
		mis.shopList.put(player.getName(), list);

	}

	/**
	 * 获取一个随机切不会与以存在的商店重名的商店名字
	 * 
	 * @param id 初始一个值，一般为0
	 * @return
	 */
	public static String getShopName(int id) {
		String name = "Shop" + id;
		if (MiniatureS.mis.ShopListConfig.get("Buttons") != null
				&& MiniatureS.mis.ShopListConfig.get("Buttons") instanceof Map)
			if (((HashMap<String, Object>) MiniatureS.mis.ShopListConfig.get("Buttons")).containsKey(name))
				return getShopName(id + 1);
		return name;
	}
}
