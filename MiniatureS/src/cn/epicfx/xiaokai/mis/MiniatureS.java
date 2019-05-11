package cn.epicfx.xiaokai.mis;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.epicfx.xiaokai.mis.cmd.PlayerCommand;
import cn.epicfx.xiaokai.mis.event.FormCallback;
import cn.epicfx.xiaokai.mis.event.PlayerEvent;
import cn.epicfx.xiaokai.mis.form.MakeForm;
import cn.epicfx.xiaokai.mis.msg.Message;
import cn.epicfx.xiaokai.mis.shop.ShopMakeForm;
import cn.epicfx.xiaokai.mis.tool.Tool;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.event.Listener;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.plugin.PluginManager;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;
import cn.nukkit.utils.Utils;

public class MiniatureS extends PluginBase implements Listener {
	public static String[] ConfigNameList = { "Config.yml", "Message.yml", "Main.yml" };
	public Map<String, String> PlayerMenuBack = new HashMap<String, String>();
	public Map<String, ArrayList<String>> PlayerMenu = new HashMap<String, ArrayList<String>>();
	public static final String MenuConfigPath = "/Menus/";
	public Config config, MsgConfig, Menus;
	public static MiniatureS mis;
	public MakeForm makeForm;
	private Message message;
	private PlayerCommand cmd;
	public Config ShopListConfig;
	public Map<String, ArrayList<String>> shopList = new HashMap<String, ArrayList<String>>();
	public ShopMakeForm shopMakeForm;

	@Override
	public void onEnable() {
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(this, this);
		pm.registerEvents(new PlayerEvent(this), this);
		pm.registerEvents(new FormCallback(this), this);
		message = new Message(this);
		makeForm = new MakeForm(this);
		config = new Config(this.getDataFolder() + "/Config.yml", 2);
		MsgConfig = new Config(this.getDataFolder() + "/Message.yml", 2);
		Menus = new Config(this.getDataFolder() + "/Main.yml", 2);
		cmd = new PlayerCommand(this);
		shopMakeForm = new ShopMakeForm(this);
		File file = new File(mis.getDataFolder(), "Shops/");
		if (!file.exists())
			file.mkdirs();
		this.ShopListConfig = new Config(mis.getDataFolder() + "/ShopList.yml", 2);
		Plugin EconomyAPI = mis.getServer().getPluginManager().getPlugin("EconomyAPI");
		if (EconomyAPI == null || !EconomyAPI.isEnabled())
			mis.getLogger().info(TextFormat.GREEN + "EconomyAPI" + TextFormat.RED + "未安装或未启用，您无法使用" + TextFormat.GREEN
					+ mis.getName() + TextFormat.RED + "商店的部分功能！");
		mis.getLogger().info(TextFormat.YELLOW + mis.getName() + TextFormat.GRAY + "商店" + TextFormat.GREEN + "启动！");
		this.getServer().getLogger().info(Tool.getColorFont(this.getName() + "启动！"));
		super.onEnable();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		return cmd.onCommand(sender, command, label, args);
	}

	@Override
	public void onLoad() {
		super.onLoad();
		mis = this;
		this.getServer().getLogger().info(Tool.getColorFont(this.getName() + "正在加载..."));
		File file = new File(this.getDataFolder() + MenuConfigPath);
		if (!file.exists())
			file.mkdirs();
		for (int i = 0; i < ConfigNameList.length; i++) {
			try {
				file = new File(this.getDataFolder(), ConfigNameList[i]);
				if (!file.exists()) {
					this.getServer().getLogger().info(TextFormat.RED + "初始化资源：" + TextFormat.GREEN + ConfigNameList[i]);
					Utils.writeFile(file, this.getClass().getResourceAsStream("/resources/" + ConfigNameList[i]));
				}
			} catch (IOException e) {
				this.getServer().getLogger().info(TextFormat.RED + "资源：" + TextFormat.GREEN + ConfigNameList[i]
						+ TextFormat.RED + "加载错误！\n" + TextFormat.WHITE + "错误详情：" + e.getMessage());
				this.getServer().getPluginManager().disablePlugin(this);
			}
		}
	}

	@Override
	public void onDisable() {
		super.onDisable();
		this.getServer().getLogger().info(Tool.getColorFont(this.getName() + "关闭！"));
	}

	public Message getMessage() {
		return message;
	}
}
