package cn.epicfx.xiaokai.mis.form.management;

import cn.epicfx.xiaokai.mis.MiniatureS;
import cn.epicfx.xiaokai.mis.form.FormStatic;
import cn.epicfx.xiaokai.mis.form.MakeForm;
import cn.epicfx.xiaokai.mis.form.management.main.MainMake;
import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponseSimple;
import cn.nukkit.utils.TextFormat;

public class EventClassification {
	private Player player;
	private MainMake make;

	/**
	 * 处理玩家要创建按钮时的类型
	 * 
	 * @param mis    插件主类对象
	 * @param player 触发这个事件的玩家对象
	 */
	public EventClassification(MiniatureS mis, Player player) {
		this.player = player;
		this.make = new MainMake(mis);
	}

	/**
	 * 判断玩家要在主页创建那种窗口
	 * 
	 * @param data 返回的数据
	 */
	public void MainAddButtonTypeDispose(FormResponseSimple data) {
		int id = data.getClickedButtonId();
		switch (FormStatic.getButtonOpenTypeArrayList().get(id)) {
		case "打开一个新的界面":
			make.addOpenWindows(player);
			break;
		case "打开商店":
			make.addOpenShop(player);
			break;
		case "传送玩家":
			make.addTransfer(player);
			break;
		case "执行命令":
			make.addCommand(player);
			break;
		case "提示一个窗口":
			make.addTip(player);
			break;
		default:
			MakeForm.makeTip(player, TextFormat.RED + "无法获取需要创建的按钮类型！");
			break;
		}
	}
}
