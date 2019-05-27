package cn.epicfx.xiaokai.mis.event;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.epicfx.xiaokai.mis.MiniatureS;
import cn.epicfx.xiaokai.mis.form.MakeForm;
import cn.epicfx.xiaokai.mis.form.management.MakeManagFrom;
import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponseSimple;
import cn.nukkit.utils.TextFormat;

@SuppressWarnings("unchecked")
public class FormDispose {
	private MiniatureS mis;

	/**
	 * UI数据处理
	 * 
	 * @param mis 插件主类对象
	 */
	public FormDispose(MiniatureS mis) {
		this.mis = mis;
	}

	/**
	 * 处理玩家点击主页上面的按钮事件
	 * 
	 * @param player       触发这个事件的玩家对象
	 * @param formResponse 传回的数据
	 */
	public void Main(Player player, FormResponseSimple formResponse) {
		if (mis.PlayerMenu.getOrDefault(player.getName(), null) == null) {
			MakeForm.makeTip(player, TextFormat.RED + "数据处理失败！\n\n无法获取玩家视图列表（Main）！");
			return;
		}
		ArrayList<String> list = mis.PlayerMenu.get(player.getName());
		Map<String, Object> map = (HashMap<String, Object>) mis.Menus.get("Buttons");
		if (player.isOp() && (list.size() - 1) < formResponse.getClickedButtonId()
				&& formResponse.getClickedButtonId() < (list.size() + 3)) {
			switch (formResponse.getClickedButtonId() - (list.size() - 1)) {
			case 1:
				(new MakeManagFrom(mis)).MakeGetMainButtonType(player);
				break;
			case 2:
				(new MakeManagFrom(mis)).MakeRemoveButton(player,
						new File(mis.getDataFolder() + MiniatureS.MenuConfigPath, "Main.yml"));
				break;
			case 3:
			default:
				(new MakeManagFrom(mis)).MakeSettingConfig(player);
				break;
			}
			mis.PlayerMenu.remove(player.getName());
			return;
		} else if (list.get(formResponse.getClickedButtonId()) == null) {
			MakeForm.makeTip(player, TextFormat.RED + "数据处理失败！\n\n无法获取玩家视图ID（Main 1）！");
			return;
		} else if (map.getOrDefault(list.get(formResponse.getClickedButtonId()), null) == null) {
			MakeForm.makeTip(player, TextFormat.RED + "数据处理失败！\n\n无法获取玩家视图数据（Main 2）！");
			return;
		}
		// 缺少打开处理时间
		mis.PlayerMenu.remove(player.getName());
	}
}
