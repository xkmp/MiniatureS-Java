package cn.epicfx.xiaokai.mis.shop;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.epicfx.xiaokai.mis.MiniatureS;
import cn.epicfx.xiaokai.mis.form.FormStatic;
import cn.epicfx.xiaokai.mis.form.MakeID;
import cn.epicfx.xiaokai.mis.tool.ItemIDSunName;
import cn.epicfx.xiaokai.mis.tool.Tool;
import cn.nukkit.Player;
import cn.nukkit.form.element.Element;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementButtonImageData;
import cn.nukkit.form.element.ElementInput;
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
	 * 添加一个物品到商店
	 * 
	 * @param player
	 */
	public void addItemToShop(Player player) {
	}

	/**
	 * 选择要添加的物品商店类型
	 * 
	 * @param player
	 */
	public void SelectShopType(Player player) {
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
				ItemIDSunName.getIDByPath(player.getInventory().getItemInHand().getId(),
						player.getInventory().getItemInHand().getDamage())));
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
		if (((ArrayList<String>) Button.get("Back_Player")).contains(player.getName())) {
			player.sendMessage(TextFormat.RED + "您无法使用该商店！您已被该商店列为黑名单！");
			return;
		}
		if (((ArrayList<String>) Button.get("Back_world")).contains(player.getLevel().getFolderName())) {
			player.sendMessage(TextFormat.RED + "您无法在该世界使用该商店！您所在的世界已被该商店列为黑名单！");
			return;
		}
		Config config = new Config(file, 2);
		List<ElementButton> List = new ArrayList<ElementButton>();
		ArrayList<String> strings = new ArrayList<>();
		Map<String, Object> bs = (Map<String, Object>) config.get("Buttons");
		for (String Key : bs.keySet()) {
			strings.add(Key);
			Map<String, Object> map = (Map<String, Object>) bs.get(Key);
			List.add(new ElementButton(String.valueOf(map.get("Text")),
					new ElementButtonImageData(Boolean.valueOf(String.valueOf(map.get("ImageType")))
							? ElementButtonImageData.IMAGE_DATA_TYPE_PATH
							: ElementButtonImageData.IMAGE_DATA_TYPE_URL, String.valueOf(map.get("Image")))));
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
		Map<String, Object> ButtonList = (Map<String, Object>) Config.get("Buttons");
		Map<String, Object> Button;
		ArrayList<String> list = new ArrayList<>();
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
		if (player.isOp())
			buttons.add(new ElementButton(TextFormat.GREEN + "添加商店"));
		player.showFormWindow(new FormWindowSimple(TextFormat.YELLOW + mis.getName() + TextFormat.GREEN + "商店",
				TextFormat.LIGHT_PURPLE + String.valueOf(Config.containsKey("Content") ? Config.get("Content") : "")
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
		if (((HashMap<String, Object>) MiniatureS.mis.ShopListConfig.get("Buttons")).containsKey(name))
			return getShopName(id + 1);
		return name;
	}
}
