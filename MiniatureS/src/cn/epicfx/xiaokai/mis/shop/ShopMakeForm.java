package cn.epicfx.xiaokai.mis.shop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import cn.epicfx.xiaokai.mis.MiniatureS;
import cn.epicfx.xiaokai.mis.form.FormStatic;
import cn.epicfx.xiaokai.mis.form.MakeID;
import cn.epicfx.xiaokai.mis.tool.Tool;
import cn.nukkit.Player;
import cn.nukkit.form.element.Element;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementButtonImageData;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.element.ElementStepSlider;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.utils.TextFormat;

@SuppressWarnings("unchecked")
public class ShopMakeForm {
	private MiniatureS mis;

	public ShopMakeForm(MiniatureS miniatureS) {
		this.mis = miniatureS;
	}

	public void MakeAddShop(Player player) {
		List<Element> list = new ArrayList<>();
		list.add(new ElementInput(TextFormat.GREEN + "请输入商店分页名称", getShopName(0)));
		list.add(new ElementInput(TextFormat.GREEN + "请输入商店配置文件名称", getShopConfigName()));
		list.add(new ElementStepSlider(TextFormat.GREEN + "请选择商店使用范围" + TextFormat.YELLOW,
				Arrays.asList(FormStatic.shopGetTypeStrings)));
		list.add(new ElementInput(TextFormat.GREEN + "黑名单世界" + TextFormat.RED + "   为空时不使用黑名单、多个世界用;分割"));
		list.add(new ElementInput(TextFormat.GREEN + "黑名单玩家" + TextFormat.RED + "   为空时不使用、多个玩家使用;分割"));
		player.showFormWindow(
				new FormWindowCustom(Tool.getRandColor() + mis.getName() + TextFormat.GREEN + "添加商店分页", list),
				MakeID.ShopAddShop.getID());
	}

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
					buttons.add(new ElementButton(String.valueOf(Button.get("Text")),
							new ElementButtonImageData(
									Boolean.valueOf(String.valueOf(Button.get("ImageType")))
											? ElementButtonImageData.IMAGE_DATA_TYPE_PATH
											: ElementButtonImageData.IMAGE_DATA_TYPE_URL,
									String.valueOf(Button.get("Image")))));
				else
					buttons.add(new ElementButton(String.valueOf(Button.get("Text"))));
			}
		}
		if (player.isOp())
			buttons.add(new ElementButton(TextFormat.GREEN + "添加商店"));
		player.showFormWindow(new FormWindowSimple(TextFormat.YELLOW + mis.getName() + TextFormat.GREEN + "商店",
				TextFormat.LIGHT_PURPLE + String.valueOf(Config.get("Content"))
						+ ((ButtonList == null || ButtonList.size() < 1) ? (TextFormat.RED + "暂无任何商店") : ""),
				buttons), MakeID.ShopMain.getID());
		mis.shopList.put(player.getName(), list);

	}

	private String getShopConfigName() {
		String nameString = "";
		int length = Tool.getRand(5, 20);
		for (int i = 0; i < length; i++) {
			nameString += Tool.getRandString();
		}
		return nameString;
	}

	private String getShopName(int id) {
		String name = "Shop" + id;
		if (mis.ShopListConfig.get(name, null) != null)
			return getShopName(id + 1);
		return name;
	}
}
