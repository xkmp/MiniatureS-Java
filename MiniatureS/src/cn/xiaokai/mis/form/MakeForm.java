package cn.xiaokai.mis.form;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementButtonImageData;
import cn.nukkit.form.window.FormWindowModal;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;
import cn.xiaokai.mis.MiniatureS;
import cn.xiaokai.mis.tool.Tool;

public class MakeForm {
	private MiniatureS mis;

	/**
	 * 主要创建各种界面
	 * 
	 * @param mis 插件主累对象
	 */
	public MakeForm(MiniatureS mis) {
		this.mis = mis;
	}

	/**
	 * 创建主界面
	 * 
	 * @param player
	 */
	@SuppressWarnings("unchecked")
	public void makeMain(Player player) {
		Config Menus = new Config(mis.getDataFolder() + "/Main.yml", 2);
		HashMap<String, Object> AllMap = (Menus.get("Buttons") instanceof Map)
				? (HashMap<String, Object>) (Menus.get("Buttons"))
				: (new HashMap<>());
		List<ElementButton> elements = new ArrayList<>();
		FormWindowSimple form = new FormWindowSimple(mis.getMessage().getText(Menus.getString("Title", "")),
				mis.getMessage().getText(Menus.getString("Content", ""))
						+ (AllMap.size() > 0 ? "" : "\n\n当前暂无任何按钮"),
				elements);
		ElementButton button;
		ArrayList<String> Key = new ArrayList<>();
		for (String key : AllMap.keySet()) {
			Map<String, Object> ButtonMap = (HashMap<String, Object>) AllMap.get(key);
			String text = mis.getMessage().getText(String.valueOf(ButtonMap.get("Text")));
			if (ButtonMap.getOrDefault("ImageType", null) != null && ButtonMap.getOrDefault("Image", null) != null) {
				boolean isLocal = (boolean) ButtonMap.getOrDefault("ImageType", null);
				ElementButtonImageData data = new ElementButtonImageData(
						ButtonMap.getOrDefault("ImageType", null) == null ? null
								: (isLocal ? ElementButtonImageData.IMAGE_DATA_TYPE_PATH
										: ElementButtonImageData.IMAGE_DATA_TYPE_URL),
						ButtonMap.getOrDefault("ImageType", null) == null ? null
								: ((String) ButtonMap.getOrDefault("Image", null)));
				button = new ElementButton(text, data);
			} else
				button = new ElementButton(text);
			elements.add(button);
			Key.add(key);
		}
		if (player.isOp()) {
			elements.add(new ElementButton(TextFormat.GREEN + "添加按钮"));
			elements.add(new ElementButton(TextFormat.RED + "删除按钮"));
			elements.add(new ElementButton(TextFormat.YELLOW + "配置数据"));
		}
		mis.PlayerMenuData.put(player.getName(), AllMap);
		mis.PlayerMenu.put(player.getName(), Key);
		mis.PlayerMenuBack.put(player.getName(), "/Main.yml");
		player.showFormWindow(form, MakeID.MainFormID.getID());
	}

	/**
	 * 创建一个简单类型的提示窗口
	 * 
	 * @param player 要显示这个窗口的玩家对象
	 * @param centnt 要显示的窗口内容
	 */
	public static void makeTip(Player player, String centnt) {
		makeTip(player, centnt, "确定", "取消");
	}

	/**
	 * 显示一个提示窗口
	 * 
	 * @param player  要显示这个提示窗口的玩家对象
	 * @param centnt  提示窗口的内容
	 * @param Button1 这个提示窗口的按钮一的文本内容
	 * @param Button2 这个提示窗口的按钮二的文本内容
	 */
	public static void makeTip(Player player, String centnt, String Button1, String Button2) {
		player.showFormWindow(new FormWindowModal(Tool.getRandColor() + MiniatureS.mis.getName() + Tool.getRandColor()
				+ "-" + Tool.getRandColor() + player.getName(), centnt, Button1, Button2));
	}
}
