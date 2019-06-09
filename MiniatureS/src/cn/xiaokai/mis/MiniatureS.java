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
package cn.xiaokai.mis;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.plugin.PluginManager;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;
import cn.nukkit.utils.Utils;
import cn.xiaokai.mis.cmd.AdminCommand;
import cn.xiaokai.mis.cmd.MainCommand;
import cn.xiaokai.mis.cmd.MyShopCommand;
import cn.xiaokai.mis.cmd.ShopCommand;
import cn.xiaokai.mis.event.FormCallback;
import cn.xiaokai.mis.event.PlayerEvent;
import cn.xiaokai.mis.event.yousb.SBPlayerData;
import cn.xiaokai.mis.form.MakeForm;
import cn.xiaokai.mis.form.custom.CustomData;
import cn.xiaokai.mis.form.openbt.HandsomeXiaoKai;
import cn.xiaokai.mis.msg.Message;
import cn.xiaokai.mis.msg.ReloadConfig;
import cn.xiaokai.mis.myshop.TonsFx;
import cn.xiaokai.mis.shop.ShopData;
import cn.xiaokai.mis.shop.ShopMakeForm;
import cn.xiaokai.mis.tool.Tool;
import cn.xiaokai.mis.tool.up.Update;

/**
 * @author Winfxk
 */
public class MiniatureS extends PluginBase {
	private Instant loadTime = Instant.now();
	/**
	 * <b>内行看笑话，外行看装逼，且看我将此逼装也</b>
	 */
	public LinkedHashMap<String, HandsomeXiaoKai> sb;
	/**
	 * <b>存储玩家看到的菜单列表</b> </br>
	 * <b>PS</b>：懒，不想再读取一次配置文件，存着好了</br>
	 * <b>PS</b>：我记得之前定义过类似的玩意来着，但是我现在忘记我是定义哪一个了，就从新来一个吧</br>
	 * <b>PS</b>：难道这就是传说中的内存轰炸机？？？
	 */
	public LinkedHashMap<String, HashMap<String, Object>> PlayerMenuData;
	/**
	 * 当玩家在创建一个按钮时，并且这个按钮时打开商店分页的时候用来存储获得的商店分页的数据
	 */
	public LinkedHashMap<String, HashMap<String, String>> PlayerAddButtonByOpenShop;
	/**
	 * 要初始化的配置文件
	 */
	public static String[] ConfigNameList = { "Config.yml", "Message.yml", "Main.yml", "ShopList.yml" };
	/**
	 * 玩家点的这个菜单的上一级
	 */
	public LinkedHashMap<String, String> PlayerMenuBack;
	/**
	 * 玩家当前页面的按钮列表
	 */
	public LinkedHashMap<String, ArrayList<String>> PlayerMenu;
	/**
	 * 存储玩家在购买东西时点击项目的项目数据
	 */
	public LinkedHashMap<String, HashMap<String, Object>> PlayerShopInteract;
	/**
	 * 存储玩家在购买东西时点击项目的项目数据
	 */
	public LinkedHashMap<String, ShopData> PlayerShopItemData;
	/**
	 * 菜单配置文件存储路径
	 */
	public static final String MenuConfigPath = "/Menus/";
	public static final String ShopConfigPath = "/Shops/";
	public static final String MyShopConfigPath = "/MyShops/";
	public static final String PlayerConfigPath = "/Players/";
	public static final String CustomConfigPath = "/Custom/";
	public static File[] DIRS_STRINGS;
	public static String Title;
	/**
	 * 插件主配置文件
	 */
	public Config config;
	/**
	 * 插件消息文本文件
	 */
	public Config MsgConfig;
	/**
	 * 存储个人商店积分的地方
	 */
	public Config MyShopPlayerMoneyConfig;
	/**
	 * 菜单key配置文件名对照表 同时也是主页菜单列表
	 */
	public Config Menus;
	/**
	 * 当前插件主类对象
	 */
	public static MiniatureS mis;
	/**
	 * 创建界面也对象
	 */
	public MakeForm makeForm;
	/**
	 * 各种自定义消息类对象
	 */
	private Message message;
	/**
	 * 玩家执行命令处理的地方
	 */
	private ShopCommand ShopCmd;
	/**
	 * 商店按钮列表
	 */
	public Config ShopListConfig;
	/**
	 * 用于缓存玩家打开的上点分页的商店项目列表
	 */
	public LinkedHashMap<String, ArrayList<String>> shopList;
	/**
	 * 用于创建商店UI的地方
	 */
	public ShopMakeForm shopMakeForm;
	/**
	 * 当玩家需要删除按钮的时候，就用这个玩意来存储玩家要删除的文件对象
	 */
	public LinkedHashMap<String, File> RemoveButtonFile;
	/**
	 * 当玩家要删除按钮的时候，用这个来存储玩家要删除按钮的那个界面的按钮列表，分别是按钮的文本内容为key，按钮的key为内容
	 */
	public LinkedHashMap<String, ArrayList<String>> RemoveButtonKeyList;
	/**
	 * 存储玩家在删除按钮时点击的是第几个按钮
	 */
	public LinkedHashMap<String, Integer> RemoveButtonKeyID;
	/**
	 * 存储正在MyShop操作的对象
	 */
	public LinkedHashMap<String, TonsFx> MyShopData;
	/**
	 * 处理来至个人商店的命令
	 */
	public MyShopCommand msc;
	/**
	 * 处理主要命令
	 */
	public MainCommand MainCommand;

	/**
	 * 处理管理命令
	 */
	public AdminCommand AdminCommand;
	/**
	 * 存储自定义界面的数据
	 */
	public LinkedHashMap<String, CustomData> Custom;
	public LinkedHashMap<String, SBPlayerData> MakeFormTime = new LinkedHashMap<>();

	/**
	 * 明人不说暗话！这就是插件启动事件
	 */
	@Override
	public void onEnable() {
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(new PlayerEvent(this), this);
		pm.registerEvents(new FormCallback(this), this);
		this.ShopListConfig = new Config(mis.getDataFolder() + "/ShopList.yml", 2);
		Plugin Economy = getServer().getPluginManager().getPlugin("EconomyAPI");
		if (getServer().getPluginManager().isPluginEnabled(Economy))
			this.getLogger()
					.info(TextFormat.YELLOW + mis.getName() + TextFormat.GRAY + "商店" + TextFormat.GREEN + "启动！");
		super.onEnable();
		this.getServer().getLogger()
				.info(Tool.getColorFont(this.getName() + "启动！") + TextFormat.GREEN + "耗时：" + TextFormat.BLUE
						+ ((float) (Duration.between(loadTime, Instant.now()).toMillis()) / 1000) + TextFormat.GREEN
						+ "s");
	}

	/**
	 * ZZ玩家又开始瞎搞了，屌毛们执行命令事件
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		switch (command.getName().toLowerCase()) {
		case "admis":
			return AdminCommand.onCommand(sender, label, args);
		case "mis":
			return MainCommand.onCommand(sender, label, args);
		case "myshop":
			return msc.onCommand(sender, label, args);
		case "mshop":
			return ShopCmd.onCommand(sender, label, args);
		default:
			return false;
		}
	}

	/**
	 * PY已准备好！插件加载事件
	 */
	@Override
	public void onLoad() {
		this.getServer().getLogger().info(Tool.getColorFont(this.getName() + "正在加载..."));
		mis = this;
		Title = "§f[§9" + getName() + "§f]" + Tool.getRandColor();
		DIRS_STRINGS = new File[] { mis.getDataFolder(), new File(mis.getDataFolder(), ShopConfigPath),
				new File(mis.getDataFolder(), MyShopConfigPath), new File(this.getDataFolder(), MenuConfigPath),
				new File(this.getDataFolder(), PlayerConfigPath), new File(mis.getDataFolder(), CustomConfigPath) };
		File file;
		for (File filex : DIRS_STRINGS)
			if (!filex.exists())
				filex.mkdirs();
		for (int i = 0; i < ConfigNameList.length; i++)
			try {
				file = new File(this.getDataFolder(), ConfigNameList[i]);
				if (!file.exists()) {
					this.getServer().getLogger().info("§6初始化资源：§c" + ConfigNameList[i]);
					Utils.writeFile(file, this.getClass().getResourceAsStream("/resources/" + ConfigNameList[i]));
				}
			} catch (IOException e) {
				this.getServer().getLogger()
						.info("§4资源：§6" + ConfigNameList[i] + "§4加载错误！\n" + "§f错误详情：" + e.getMessage());
				this.getServer().getPluginManager().disablePlugin(this);
			}
		ReloadConfig.start();
		config = new Config(this.getDataFolder() + "/Config.yml", 2);
		PlayerAddButtonByOpenShop = new LinkedHashMap<>();
		PlayerShopInteract = new LinkedHashMap<>();
		PlayerMenu = new LinkedHashMap<String, ArrayList<String>>();
		PlayerMenuBack = new LinkedHashMap<String, String>();
		PlayerMenuData = new LinkedHashMap<>();
		sb = new LinkedHashMap<>();
		PlayerShopItemData = new LinkedHashMap<>();
		shopList = new LinkedHashMap<String, ArrayList<String>>();
		RemoveButtonFile = new LinkedHashMap<String, File>();
		RemoveButtonKeyList = new LinkedHashMap<>();
		RemoveButtonKeyID = new LinkedHashMap<>();
		MyShopData = new LinkedHashMap<>();
		makeForm = new MakeForm(this);
		MsgConfig = new Config(this.getDataFolder() + "/Message.yml", 2);
		message = new Message(this);
		Menus = new Config(this.getDataFolder() + "/Main.yml", 2);
		MyShopPlayerMoneyConfig = new Config(this.getDataFolder() + "/MyShopIc.yml", Config.YAML);
		ShopCmd = new ShopCommand(this);
		shopMakeForm = new ShopMakeForm(this);
		MainCommand = new MainCommand(this);
		msc = new MyShopCommand(this);
		AdminCommand = new AdminCommand(this);
		Custom = new LinkedHashMap<>();
		EfficacyConfig.startPY();
		if (config.getBoolean("检测更新"))
			(new Update(this)).start();
		file = new File(getDataFolder(), "MiniatureS自定义控件介绍.docx");
		try {
			Utils.writeFile(file, mis.getClass().getResourceAsStream("/resources/MiniatureS自定义控件介绍.docx"));
			getLogger().info("§6帮助文件已导出至§4" + file.getName());
		} catch (IOException e) {
		}
		super.onLoad();
	}

	/**
	 * ????这都看不懂？？这是插件关闭事件
	 */
	@Override
	public void onDisable() {
		this.getServer().getLogger()
				.info(Tool.getColorFont(this.getName() + "关闭！") + TextFormat.GREEN + "本次运行时长" + TextFormat.BLUE
						+ Tool.getTimeBy(((float) (Duration.between(loadTime, Instant.now()).toMillis()) / 1000))
						+ TextFormat.GREEN + "s");
		super.onDisable();
	}

	/**
	 * 返回货币的名称，如“金币”
	 * 
	 * @return
	 */
	public String getMoneyName() {
		return config.getString("货币单位");
	}

	/**
	 * 各种可以自定义以及变量替换类对象,emmm，这么说没问题吧？
	 * 
	 * @return
	 */
	public Message getMessage() {
		return message;
	}

	/**
	 * 快来和本插件PY交易吧~
	 * 
	 * @return
	 */
	public static MiniatureS getPY() {
		return mis;
	}
}
