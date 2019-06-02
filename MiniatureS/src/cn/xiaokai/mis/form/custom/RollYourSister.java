package cn.xiaokai.mis.form.custom;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import cn.nukkit.Player;
import cn.nukkit.utils.Config;
import cn.xiaokai.mis.MiniatureS;
import cn.xiaokai.mis.tool.Tool;

/**
 * @author Winfxk
 */
@SuppressWarnings("unchecked")
public class RollYourSister {
	private MiniatureS mis;
	private Player player;
	private Config config;
	private HashMap<String, Object> Buttons;

	/**
	 * 添加一个自定义类型的按钮
	 * 
	 * @param player
	 */
	public RollYourSister(Player player, File file) {
		this.player = player;
		mis = MiniatureS.mis;
		config = new Config(file, Config.YAML);
		Buttons = (config.get("Buttons") instanceof Map) ? ((HashMap<String, Object>) config.get("Buttons"))
				: new HashMap<>();
	}

	/**
	 * 添加一个自定义类型的按钮
	 * 
	 * @param button    按钮的文本内容
	 * @param FileName  自定义按钮的文本内容
	 * @param money     点击扣费
	 * @param imagePath 贴图路径
	 * @param imageType 贴图类型
	 * @param file
	 */
	public void startPY(String button, String FileName, int money, String imagePath, boolean imageType) {
		FileName = (FileName.lastIndexOf("yml") == FileName.length() - 3) ? FileName : FileName + ".yml";
		HashMap<String, Object> map = new HashMap<>();
		map.put("Type", "Custom");
		map.put("Player", player.getName());
		map.put("Money", money < 1 ? 0 : money);
		map.put("Text", button);
		map.put("FileName", FileName);
		map.put("ImageType", imageType);
		map.put("Image", imagePath);
		map.put("Player", player.getName());
		map.put("Time", Tool.getDate() + " " + Tool.getTime());
		String key = getKey(1);
		Buttons.put(key, map);
		config.set("Buttons", Buttons);
		config.save();
		File newFile = new File(mis.getDataFolder() + MiniatureS.CustomConfigPath, FileName);
		if (!newFile.exists()) {
			LinkedHashMap<String, Object> SB_FFF = new LinkedHashMap<>();
			SB_FFF.put("FormType", "Simple|Modal|Custom");
			SB_FFF.put("Content",
					"本项只有当表单类型(FormType)为[Simple|Modal]时生效，当您配置完毕，请把下面的启用(Enabled)改为真(true)并将表单类型(FormType)设为您想设置的单一选项！");
			SB_FFF.put("Title", "{RandColor}" + mis.getName() + "§f-{RandColor}" + config.getString("Title")
					+ "§f-{RandColor}" + button);
			SB_FFF.put("Items", new HashMap<String, Object>());
			SB_FFF.put("Player", player.getName());
			SB_FFF.put("Time", Tool.getDate() + " " + Tool.getTime());
			SB_FFF.put("Enabled", false);
			Config SB_Config = new Config(newFile, Config.YAML);
			SB_Config.setAll(SB_FFF);
			SB_Config.save();
		}
		player.sendMessage("§6您成功创建一个自定义表单！\n§6Key：§f" + key + "\n§6绑定文件：§4" + FileName
				+ "\n§4绑定的表单文件当前为未生效状态！请尽快配置启用！\n§d更多帮助：§ehttps://github.com/xkmp/MiniatureS-Java");
	}

	/**
	 * 获取一个不重复的Key
	 * 
	 * @return
	 */
	public String getKey(int JJLength) {
		String name = "";
		JJLength = JJLength < 1 ? 1 : JJLength;
		for (int i = 0; i < JJLength; i++)
			name += Tool.getRandString();
		if (Buttons.containsKey(name))
			return getKey(JJLength + 1);
		return name;
	}
}
