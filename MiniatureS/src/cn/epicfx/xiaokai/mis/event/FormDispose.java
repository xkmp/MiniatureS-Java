package cn.epicfx.xiaokai.mis.event;

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

	public FormDispose(MiniatureS mis) {
		this.mis = mis;
	}

	public void AddButtonType(Player player, FormResponseSimple formResponse) {

	}

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
				(new MakeManagFrom(mis)).MakeGetButtonType(player);
				break;
			case 2:
				(new MakeManagFrom(mis)).MakeRemoveButton(player, mis.PlayerMenuBack.get(player.getName()));
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
