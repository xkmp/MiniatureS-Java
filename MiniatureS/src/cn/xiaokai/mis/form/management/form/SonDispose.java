package cn.xiaokai.mis.form.management.form;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponseSimple;
import cn.nukkit.utils.TextFormat;
import cn.xiaokai.mis.MiniatureS;
import cn.xiaokai.mis.form.FormStatic;
import cn.xiaokai.mis.form.MakeForm;
import cn.xiaokai.mis.form.custom.MakeCustom;

/**
 * @author Winfxk
 */
public class SonDispose {
	/**
	 * 判断这帮沙雕点击的是哪一种类型的按钮
	 * 
	 * @param player 点击按钮的玩家对象
	 * @param data   玩家点击发回的数据包
	 */
	public void Switch(Player player, FormResponseSimple data) {
		SonMakeWindow make = new SonMakeWindow(player);
		switch (FormStatic.getButtonOpenTypeArrayList().get(data.getClickedButtonId())) {
		case "自定义命令":
			(new MakeCustom()).startPY(player, MiniatureS.mis.sb.get(player.getName()).file);
			break;
		case "打开一个新的界面":
			make.addOpenWindows();
			break;
		case "打开商店":
			make.addOpenShop();
			break;
		case "传送玩家":
			make.addTransfer();
			break;
		case "执行命令":
			make.addCommand();
			break;
		case "提示一个窗口":
			make.addTip();
			break;
		default:
			MakeForm.makeTip(player, TextFormat.RED + "无法获取需要创建的按钮类型！");
			break;
		}
	}
}
