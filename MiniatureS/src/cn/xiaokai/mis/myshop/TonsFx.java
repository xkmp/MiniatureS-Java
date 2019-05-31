package cn.xiaokai.mis.myshop;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import cn.nukkit.Player;
/**
 * @author Winfxk
 */
public class TonsFx {
	/**
	 * 创建的个人商店主页的任务列表
	 */
	public ArrayList<File> files;
	/**
	 * 存储玩家看到的商店项目
	 */
	public ArrayList<HashMap<String, Object>> ShopItems;
	/**
	 * 玩家看到的商店界面的配置文件文件对象
	 */
	public File file;
	public Player player;
	/**
	 * 搜索展现给玩家看到的列表
	 */
	public ArrayList<HashMap<String, Object>> Seek;
	/**
	 * 当前要购买的项目数据
	 */
	public HashMap<String, Object> MainItem;
}
