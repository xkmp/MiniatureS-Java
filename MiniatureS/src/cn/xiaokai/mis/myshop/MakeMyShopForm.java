package cn.xiaokai.mis.myshop;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.nukkit.Player;
import cn.nukkit.form.element.Element;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementButtonImageData;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.element.ElementLabel;
import cn.nukkit.form.element.ElementSlider;
import cn.nukkit.form.element.ElementStepSlider;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.inventory.PlayerInventory;
import cn.nukkit.item.Item;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;

import cn.xiaokai.mis.MiniatureS;
import cn.xiaokai.mis.form.FormStatic;
import cn.xiaokai.mis.form.MakeForm;
import cn.xiaokai.mis.form.MakeID;
import cn.xiaokai.mis.tool.ItemIDSunName;
import cn.xiaokai.mis.tool.Tool;
import me.onebone.economyapi.EconomyAPI;

/**
 * @author Winfxk
 */
@SuppressWarnings("unchecked")
public class MakeMyShopForm {
	private Player player;
	private MiniatureS mis;

	/**
	 * 创建个人商店的UI
	 * 
	 * @param player 要显示UI的玩家对象
	 */
	public MakeMyShopForm(Player player) {
		this.player = player;
		this.mis = MiniatureS.mis;
	}

	/**
	 * 开始交易
	 * 
	 * @param map  要交易的项目的数据
	 * @param file 项目所在的文件对象
	 */
	public void startPyItem(HashMap<String, Object> map, File file) {
		if (!mis.config.getBoolean("个人商店")) {
			MakeForm.makeTip(player, TextFormat.RED + "个人商店已关闭！若需使用请联系管理员！");
			return;
		}
		List<Element> list = new ArrayList<Element>();
		String ShopType = String.valueOf(map.get("Type")).toLowerCase() == "sell" ? "出售" : "回收";
		int Money = Float.valueOf(String.valueOf(map.get("Money"))).intValue();
		String Msg = "";
		int ItemCount = Float.valueOf(String.valueOf(map.get("Count"))).intValue();
		String ID = String.valueOf(map.get("Item"));
		if (ShopType == "出售") {
			Map<Integer, Item> Items = player.getInventory().getContents();
			int Count = 0;
			for (Integer i : Items.keySet()) {
				Item item = Items.get(i);
				if (Tool.isMateID(ID, item.getId() + ":" + item.getDamage()))
					Count += item.getCount();
			}
			Msg = TextFormat.WHITE + "您当前有" + TextFormat.GREEN + Count + TextFormat.WHITE + "个"
					+ ItemIDSunName.getIDByName(ID) + "\n" + TextFormat.GREEN + "全部出售约能获利" + TextFormat.RED
					+ ((Count > ItemCount ? ItemCount : Count) * Money) + TextFormat.GREEN + mis.getMoneyName();
		} else {
			Msg = TextFormat.WHITE + "您当前有" + TextFormat.GREEN + EconomyAPI.getInstance().myMoney(player)
					+ TextFormat.WHITE + mis.getMoneyName() + "\n" + TextFormat.WHITE + "最多约能买" + TextFormat.RED
					+ (((EconomyAPI.getInstance().myMoney(player) / Money) > ItemCount) ? ItemCount
							: ((EconomyAPI.getInstance().myMoney(player) / Money)))
					+ "" + TextFormat.WHITE + "个" + TextFormat.GREEN + ItemIDSunName.getIDByName(ID);
		}
		list.add(new ElementLabel(TextFormat.WHITE + "您需要" + ShopType + "多少个" + TextFormat.GREEN
				+ ItemIDSunName.getIDByName(ID) + TextFormat.WHITE + "？\n" + Msg + "\n" + TextFormat.YELLOW + "每个"
				+ TextFormat.GREEN + ItemIDSunName.getIDByName(ID) + TextFormat.RED + Money + TextFormat.YELLOW
				+ mis.getMoneyName()));
		list.add(new ElementSlider(TextFormat.WHITE + ShopType + "数量", 1, ItemCount, 1, 1));
		TonsFx fx = mis.MyShopData.get(player.getName());
		fx.MainItem = map;
		mis.MyShopData.put(player.getName(), fx);
		player.showFormWindow(new FormWindowCustom(
				Tool.getRandColor() + String.valueOf(map.get("Player")) + Tool.getRandColor() + "的商店", list,
				new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH, ItemIDSunName.getIDByName(ID))),
				MakeID.startMyShopItem.getID());
	}

	/**
	 * 显示某个玩家的商店
	 * 
	 * @param player 要查看的玩家的对象
	 */
	public void ShowPlayer(Player player) {
		ShowPlayer(new File(mis.getDataFolder() + MiniatureS.MyShopConfigPath, player.getName() + ".yml"));
	}

	/**
	 * 显示某个配置文件的项目列表
	 * 
	 * @param file 要被显示的配置文件对象
	 */
	public void ShowPlayer(File file) {
		if (!mis.config.getBoolean("个人商店")) {
			MakeForm.makeTip(player, TextFormat.RED + "个人商店已关闭！若需使用请联系管理员！");
			return;
		}
		Config config = new Config(file, Config.YAML);
		if (!(config.getSection("Item") instanceof Map)) {
			MakeForm.makeTip(player, TextFormat.RED + "这个人貌似还没有上架任何东西o!");
			return;
		}
		HashMap<String, Object> Items = config.getSection("Items");
		List<ElementButton> list = new ArrayList<ElementButton>();
		ArrayList<HashMap<String, Object>> arrayList = new ArrayList<>();
		for (String Key : Items.keySet()) {
			HashMap<String, Object> item = (HashMap<String, Object>) Items.get(Key);
			arrayList.add(item);
			list.add(new ElementButton(
					TextFormat.AQUA + String.valueOf(item.get("Player")) + TextFormat.LIGHT_PURPLE + "|"
							+ TextFormat.DARK_BLUE + ItemIDSunName.getIDByName(String.valueOf(item.get("Item")))
							+ TextFormat.LIGHT_PURPLE + "|" + TextFormat.GREEN + String.valueOf(item.get("Money"))
							+ TextFormat.LIGHT_PURPLE + "|" + Tool.getRandColor() + String.valueOf(item.get("Type")),
					new ElementButtonImageData(ElementButtonImageData.IMAGE_DATA_TYPE_PATH,
							ItemIDSunName.getIDByPath(String.valueOf(item.get("Item"))))));
		}
		if (list.size() < 1) {
			MakeForm.makeTip(player, TextFormat.RED + "这个人貌似还没有上架任何东西o!");
			return;
		}
		TonsFx fx = new TonsFx();
		fx.ShopItems = arrayList;
		fx.file = file;
		mis.MyShopData.put(player.getName(), fx);
		player.showFormWindow(
				new FormWindowSimple(Tool.getRandColor() + config.getString("Player") + Tool.getColorFont(" 的商店"),
						config.getString("Contxt"), list),
				MakeID.MyShopItem.getID());
	}

	/**
	 * 搜索个人商店上面的东西
	 */
	public void Seek() {
		if (!mis.config.getBoolean("个人商店")) {
			MakeForm.makeTip(player, TextFormat.RED + "个人商店已关闭！若需使用请联系管理员！");
			return;
		}
		List<Element> list = new ArrayList<>();
		list.add(new ElementInput(TextFormat.WHITE + "请输入您想要搜索的物品ID或名称"));
		list.add(new ElementStepSlider(TextFormat.WHITE + "请选择搜索范围", Arrays.asList(FormStatic.SeekMyShopType), 0));
		player.showFormWindow(
				new FormWindowCustom(Tool.getColorFont(mis.getName()) + TextFormat.WHITE + "-"
						+ Tool.getColorFont(player.getName()) + TextFormat.WHITE + "-" + Tool.getColorFont("搜索"), list),
				MakeID.MyShopSeekMain.getID());
	}

	/**
	 * 添加项目到个人商店
	 */
	public void addItem() {
		if (!mis.config.getBoolean("个人商店")) {
			MakeForm.makeTip(player, TextFormat.RED + "个人商店已关闭！若需使用请联系管理员！");
			return;
		}
		PlayerInventory inventory = player.getInventory();
		String id = inventory.getItemInHand().getId() != 0
				? (inventory.getItemInHand().getId() + ":" + inventory.getItemInHand().getDamage())
				: "";
		int Count = 0;
		if (!id.isEmpty()) {
			Map<Integer, Item> items = inventory.getContents();
			for (Integer i : items.keySet()) {
				Item item = items.get(i);
				if (Tool.isMateID(item.getId() + ":" + item.getDamage(), id))
					Count += item.getCount();
			}
		}
		List<Element> list = new ArrayList<>();
		list.add(new ElementInput(TextFormat.WHITE + "请输入您需要上架的物品名称或物品ID", "x表示不限制，即[x:x]表示搜索所有", id));
		list.add(new ElementInput(TextFormat.WHITE + "请输入您想要上架的数量", "", String.valueOf(Count)));
		list.add(new ElementInput(TextFormat.WHITE + "请输入您上架的物品单价", "您认为合适的物品单价",
				String.valueOf(10000 / (Count < 1 ? 1 : Count))));
		list.add(new ElementStepSlider(TextFormat.WHITE + "请输入您的商店类型", Arrays.asList(FormStatic.MyShopType), 0));
		player.showFormWindow(new FormWindowCustom(Tool.getColorFont(mis.getName()) + TextFormat.WHITE + "-"
				+ Tool.getColorFont(player.getName()) + TextFormat.WHITE + "-" + Tool.getColorFont("Add Item"), list),
				MakeID.newMyShopItem.getID());
	}

	/**
	 * 个人商店主页
	 */
	public void MakeMain() {
		if (!mis.config.getBoolean("个人商店")) {
			MakeForm.makeTip(player, TextFormat.RED + "个人商店已关闭！若需使用请联系管理员！");
			return;
		}
		File file = new File(mis.getDataFolder() + MiniatureS.MyShopConfigPath);
		List<ElementButton> buttons = new ArrayList<ElementButton>();
		ArrayList<File> files = new ArrayList<>();
		if (file.list() != null && file.list().length > 0)
			for (String f : file.list()) {
				File fx = new File(mis.getDataFolder() + MiniatureS.MyShopConfigPath, f);
				Config config = new Config(fx, Config.YAML);
				if (!(config.get("Items") instanceof Map)) {
					fx.delete();
					continue;
				}
				buttons.add(new ElementButton(Tool.getRandColor() + config.getString("Player") + TextFormat.LIGHT_PURPLE
						+ "|" + TextFormat.WHITE + " 商品：" + TextFormat.YELLOW
						+ ((HashMap<String, Object>) config.get("Items")).size()));
				files.add(fx);
			}
		TonsFx fxaFx = new TonsFx();
		fxaFx.files = files;
		fxaFx.file = file;
		mis.MyShopData.put(player.getName(), fxaFx);
		buttons.add(new ElementButton(Tool.getColorFont("上架物品")));
		buttons.add(new ElementButton(Tool.getColorFont("搜索物品")));
		player.showFormWindow(
				new FormWindowSimple(
						Tool.getColorFont(mis.getName()) + TextFormat.WHITE + "-" + TextFormat.YELLOW + "MyShop",
						buttons.size() < 1 ? (TextFormat.RED + "暂无任何人上架物品！快来添加一个吧~") : "", buttons),
				MakeID.MyShopMain.getID());
	}
}
