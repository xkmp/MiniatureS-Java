package cn.epicfx.xiaokai.mis.form.openbt;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.epicfx.xiaokai.mis.MiniatureS;
import cn.epicfx.xiaokai.mis.form.MakeID;
import cn.epicfx.xiaokai.mis.msg.Message;
import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementButtonImageData;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;

@SuppressWarnings("unchecked")
public class MakeWindow {
	private MiniatureS mis;
	private Player player;
	private File file;
	private Config config;

	/**
	 * 准备创建一个新的菜单发送给玩家
	 * 
	 * @param player 要显示菜单的玩家对象
	 * @param file   菜单的配置文件对象
	 */
	public MakeWindow(Player player, File file) {
		mis = MiniatureS.mis;
		this.player = player;
		this.file = file;
		config = new Config(file, Config.YAML);
	}

	/**
	 * 创建一个菜单界面，并且给玩家显示出来
	 */
	public void MakeForm() {
		Message msg = mis.getMessage();
		HashMap<String, Object> Buttons = (config.get("Buttons") instanceof Map)
				? ((HashMap<String, Object>) config.get("Buttons"))
				: (new HashMap<String, Object>());
		String Title = (config.get("Title") != null && !((String) config.get("Title")).isEmpty())
				? msg.getText(String.valueOf(config.get("Title")))
				: "";
		String Content = ((config.get("Content") != null && !((String) config.get("Content")).isEmpty())
				? msg.getText(String.valueOf(config.get("Content")))
				: "")
				+ (Buttons.size() > 1 ? ""
						: ("\n" + TextFormat.YELLOW + "还没有任何项目哦！快去" + (player.isOp() ? "" : "找管理员") + "添加一个吧~"));
		List<ElementButton> list = new ArrayList<ElementButton>();
		ArrayList<String> keyList = new ArrayList<>();
		for (String Key : Buttons.keySet()) {
			HashMap<String, Object> button = (HashMap<String, Object>) Buttons.get(Key);
			boolean ImageType = Boolean.valueOf(String.valueOf(button.get("ImageType")));
			String ImagePath = (button.get("Image") != null && !((String) button.get("Image")).isEmpty())
					? String.valueOf(button.get("Image"))
					: null;
			list.add(new ElementButton(msg.getText(String.valueOf(config.get("Text"))),
					new ElementButtonImageData(ImagePath == null ? ImagePath
							: (ImageType ? ElementButtonImageData.IMAGE_DATA_TYPE_PATH
									: ElementButtonImageData.IMAGE_DATA_TYPE_URL),
							ImagePath)));
			keyList.add(Key);
		}
		mis.PlayerMenu.put(player.getName(), keyList);
		mis.PlayerMenuBack.put(player.getName(), file.getName());
		mis.PlayerMenuData.put(player.getName(), Buttons);
		if (player.isOp()) {
			list.add(new ElementButton(TextFormat.GREEN + "添加按钮"));
			list.add(new ElementButton(TextFormat.RED + "删除按钮"));
			list.add(new ElementButton(TextFormat.YELLOW + "配置数据"));
		}
		player.showFormWindow(new FormWindowSimple(Title, Content, list), MakeID.ShowWindow.getID());
	}
}