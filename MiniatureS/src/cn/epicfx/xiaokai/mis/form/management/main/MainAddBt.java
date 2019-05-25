package cn.epicfx.xiaokai.mis.form.management.main;

import java.util.HashMap;
import java.util.Map;

import cn.epicfx.xiaokai.mis.MiniatureS;
import cn.epicfx.xiaokai.mis.tool.Tool;
import cn.nukkit.Player;
import cn.nukkit.utils.TextFormat;

@SuppressWarnings("unchecked")
public class MainAddBt {
	private MiniatureS mis;

	public MainAddBt() {
		this.mis = MiniatureS.mis;
	}

	public void addTip(Player player, String Button, String Contxt, String Bt1, String Bt2, int Money,
			boolean ImageType, String ImagePath, String Command) {
		HashMap<String, Object> Buttons = (mis.Menus.get("Buttons") instanceof Map)
				? (HashMap<String, Object>) (mis.Menus.get("Buttons"))
				: (new HashMap<>());
		HashMap<String, Object> map = new HashMap<>();
		map.put("Type", "make");
		map.put("Text", Button);
		map.put("ImageType", ImageType);
		map.put("Image", ImagePath);
		map.put("Content", Contxt);
		map.put("bt1", Bt1);
		map.put("bt2", Bt2);
		map.put("Money", Money <= 0 ? 0 : Money);
		map.put("Command", Command == "null" ? null : Command);
		map.put("Player", player.getName());
		map.put("Time", Tool.getDate() + " " + Tool.getTime());
		String Key = getButtonKey();
		Buttons.put(Key, map);
		mis.Menus.set("Buttons", Buttons);
		mis.Menus.save();
		player.sendMessage(TextFormat.GREEN + "您成功创建一个提示框类型的按钮！Key：" + Key);
	}

	public static String getButtonKey() {
		String Key = "";
		int lengtjh = Tool.getRand(5, 20);
		for (int i = 0; i < lengtjh; i++)
			Key += Tool.getRandString();
		if ((MiniatureS.mis.Menus.get("Buttons") instanceof Map)
				&& ((HashMap<String, Object>) MiniatureS.mis.Menus.get("Buttons")).containsKey(Key))
			return getButtonKey();
		return Key;
	}
}
