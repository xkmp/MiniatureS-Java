package cn.epicfx.xiaokai.mis.form.management.main;

import cn.epicfx.xiaokai.mis.MiniatureS;
import cn.epicfx.xiaokai.mis.form.FormStatic;
import cn.epicfx.xiaokai.mis.form.MakeForm;
import cn.epicfx.xiaokai.mis.tool.ItemIDSunName;
import cn.epicfx.xiaokai.mis.tool.Tool;
import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.utils.TextFormat;

public class MainDispose {
	private MiniatureS mis;
	private Player player;
	private MainAddBt mainAddBt;

	/**
	 * 处理在主页创建按钮UI的返回数据
	 * 
	 * @param mis    插件主累对象
	 * @param player 触发这个事件的玩家对象
	 */
	public MainDispose(MiniatureS mis, Player player) {
		this.mis = mis;
		this.player = player;
		mainAddBt = new MainAddBt();
	}

	/**
	 * 处理在主页创建一个提示窗按钮的UI发回的数据
	 * 
	 * @param data 发回的数据
	 */
	public void addTip(FormResponseCustom data) {
		String buttonString = String.valueOf(data.getResponse(0));
		if (buttonString.isEmpty()) {
			MakeForm.makeTip(player, TextFormat.RED + "请输入有效的按钮内容！");
			return;
		}
		String Contxt = String.valueOf(data.getResponse(1));
		if (Contxt.isEmpty()) {
			MakeForm.makeTip(player, TextFormat.RED + "请输入有效的界面内容！");
			return;
		}
		String bt1 = String.valueOf(data.getResponse(2));
		bt1 = bt1.isEmpty() ? (TextFormat.GREEN + "确定") : bt1;
		String bt2 = String.valueOf(data.getResponse(3));
		bt2 = bt2.isEmpty() ? (TextFormat.RED + "取消") : bt2;
		String moneyString = String.valueOf(data.getResponse(4));
		if (moneyString.isEmpty()) {
			MakeForm.makeTip(player, TextFormat.RED + "请输入有效的" + mis.getMoneyName() + "数量！");
			return;
		}
		if (!Tool.isInteger(moneyString)) {
			MakeForm.makeTip(player, TextFormat.RED + "请输入有效的" + mis.getMoneyName() + "数(存整数)！");
			return;
		}
		int Money = Float.valueOf(String.valueOf(moneyString)).intValue();
		Money = Money >= 0 ? Money : 0;
		String Command = String.valueOf(data.getResponse(5));
		boolean ImageType = false;
		if (data.getResponse(4) != null)
			ImageType = String.valueOf(data.getResponse(6)) == FormStatic.ShopImageType[0] ? false
					: String.valueOf(data.getResponse(6)) == FormStatic.ShopImageType[1] ? true : false;
		String ImagePath = ImageType ? ItemIDSunName.UnknownToPath(String.valueOf(data.getResponse(7)))
				: String.valueOf(data.getResponse(7));
		mainAddBt.addTip(player, buttonString, Contxt, bt1, bt2, Money, ImageType, ImagePath,Command);
	}
}
