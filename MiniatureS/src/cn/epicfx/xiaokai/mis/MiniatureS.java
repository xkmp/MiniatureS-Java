//                       _oo0oo_
//                      o8888888o
//                      88" . "88
//                      (| -_- |)
//                      0\  =  /0
//                    ___/`---'\___
//                  .' \\|     |// '.
//                 / \\|||  :  |||// \
//                / _||||| -:- |||||- \
//               |   | \\\  -  /// |   |
//               | \_|  ''\---/''  |_/ |
//               \  .-\__  '-'  ___/-. /
//             ___'. .'  /--.--\  `. .'___
//          ."" '<  `.___\_<|>_/___.' >' "".
//         | | :  `- \`.;`\ _ /`;.`/ - ` : | |
//         \  \ `_.   \_ __\ /__ _/   .-` /  /
//     =====`-.____`.___ \_____/___.-`___.-'=====
//                       `=---='
//     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//               佛祖保佑         永无BUG
//          佛曰:
//              写字楼里写字间, 写字间里程序员；
//              程序人员写程序, 又拿程序换酒钱。
//              酒醒只在网上坐, 酒醉还来网下眠；
//              酒醉酒醒日复日, 网上网下年复年。
//              但愿老死电脑间, 不愿鞠躬老板前；
//              奔驰宝马贵者趣, 公交自行程序员。
//              别人笑我忒疯癫, 我笑自己命太贱；
//              不见满街漂亮妹, 哪个归得程序员？
package cn.epicfx.xiaokai.mis;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import cn.epicfx.xiaokai.mis.cmd.PlayerCommand;
import cn.epicfx.xiaokai.mis.event.FormCallback;
import cn.epicfx.xiaokai.mis.event.PlayerEvent;
import cn.epicfx.xiaokai.mis.form.MakeForm;
import cn.epicfx.xiaokai.mis.msg.Message;
import cn.epicfx.xiaokai.mis.shop.ShopData;
import cn.epicfx.xiaokai.mis.shop.ShopMakeForm;
import cn.epicfx.xiaokai.mis.tool.Tool;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.plugin.PluginManager;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;
import cn.nukkit.utils.Utils;

public class MiniatureS extends PluginBase {
	public static String[] ConfigNameList = { "Config.yml", "Message.yml", "Main.yml", "ShopList.yml" };
	/**
	 * 玩家点的这个菜单的上一级
	 */
	public LinkedHashMap<String, String> PlayerMenuBack = new LinkedHashMap<String, String>();
	/**
	 * 玩家当前页面的按钮列表
	 */
	public LinkedHashMap<String, ArrayList<String>> PlayerMenu = new LinkedHashMap<String, ArrayList<String>>();
	/**
	 * 存储玩家在购买东西时点击项目的项目数据
	 */
	public LinkedHashMap<String, HashMap<String, Object>> PlayerShopInteract = new LinkedHashMap<>();
	/**
	 * 存储玩家在购买东西时点击项目的项目数据
	 */
	public LinkedHashMap<String, ShopData> PlayerShopItemData = new LinkedHashMap<>();
	/**
	 * 菜单配置文件存储路径
	 */
	public static final String MenuConfigPath = "/Menus/";
	public static final String ShopConfigPath = "/Shops/";
	public Config config;
	/**
	 * 插件消息文本文件
	 */
	public Config MsgConfig;
	/**
	 * 菜单key配置文件名对照表
	 */
	public Config Menus;
	public static MiniatureS mis;
	public MakeForm makeForm;
	private Message message;
	private PlayerCommand cmd;
	/**
	 * 商店按钮列表
	 */
	public Config ShopListConfig;
	public LinkedHashMap<String, ArrayList<String>> shopList = new LinkedHashMap<String, ArrayList<String>>();
	public ShopMakeForm shopMakeForm;

	@Override
	public void onEnable() {
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(new PlayerEvent(this), this);
		pm.registerEvents(new FormCallback(this), this);
		message = new Message(this);
		makeForm = new MakeForm(this);
		config = new Config(this.getDataFolder() + "/Config.yml", 2);
		MsgConfig = new Config(this.getDataFolder() + "/Message.yml", 2);
		Menus = new Config(this.getDataFolder() + "/Main.yml", 2);
		cmd = new PlayerCommand(this);
		shopMakeForm = new ShopMakeForm(this);
		File file = new File(mis.getDataFolder() + ShopConfigPath);
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

	/**
	 * 返回货币的名称，如“金币”
	 * 
	 * @return
	 */
	public String getMoneyName() {
		return config.getString("货币单位");
	}

	public Message getMessage() {
		return message;
	}
}
