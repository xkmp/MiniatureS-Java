package cn.epicfx.xiaokai.mis.form;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.epicfx.xiaokai.mis.MiniatureS;
import cn.epicfx.xiaokai.mis.tool.Tool;
import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementButtonImageData;
import cn.nukkit.form.window.FormWindowModal;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.utils.TextFormat;

public class MakeForm {
	private MiniatureS mis;

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
		Map<String, Object> AllMap = (HashMap<String, Object>) mis.Menus.getAll().get("Buttons");
		List<ElementButton> elements = new ArrayList<>();
		FormWindowSimple form = new FormWindowSimple(mis.getMessage().getText(mis.Menus.getString("Title", "")),
				mis.getMessage().getText(mis.Menus.getString("Content", ""))
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
						isLocal ? ElementButtonImageData.IMAGE_DATA_TYPE_PATH
								: ElementButtonImageData.IMAGE_DATA_TYPE_URL,
						(String) ButtonMap.getOrDefault("Image", null));
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
		mis.PlayerMenu.put(player.getName(), Key);
		mis.PlayerMenuBack.put(player.getName(), "Main");
		player.showFormWindow(form, MakeID.MainFormID.getID());
	}

	public static void makeTip(Player player, String centnt) {
		makeTip(player, centnt, "确定", "取消");
	}

	public static void makeTip(Player player, String centnt, String Button1, String Button2) {
		player.showFormWindow(new FormWindowModal(Tool.getRandColor() + MiniatureS.mis.getName() + Tool.getRandColor()
				+ "-" + Tool.getRandColor() + player.getName(), centnt, Button1, Button2));
	}
}
