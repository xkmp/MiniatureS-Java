package cn.epicfx.xiaokai.mis.form.openbt;

import java.util.HashMap;

import cn.epicfx.xiaokai.mis.MiniatureS;
import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponseSimple;

@SuppressWarnings("unchecked")
public class DealWith {
	private Player player;
	private FormResponseSimple data;
	private MiniatureS mis;

	/**
	 * 处理玩家点击菜单UI项目的事件
	 * 
	 * @param player 触发事件的玩家对象
	 * @param data   表单发回的数据
	 */
	public DealWith(Player player, FormResponseSimple data) {
		this.player = player;
		this.mis = MiniatureS.mis;
		this.data = data;
	}

	/**
	 * 开始处♂理
	 */
	public void startPy() {
		int JJLength = data.getClickedButtonId();
		String PyName = mis.PlayerMenu.get(player.getName()).get(JJLength);
		HashMap<String, Object> Py = (HashMap<String, Object>) mis.PlayerMenuData.get(player.getName()).get(PyName);
		KickDownTheLadder();
		(new OpenButton(player, Py)).Switch();
	}

	/**
	 * 既然P♂Y完了，该处理后事了！免得怀孕就不好了！咳咳咳而且不收拾残局被警察看到怎么办...
	 */
	protected void KickDownTheLadder() {
		mis.PlayerMenuData.remove(player.getName());
		mis.PlayerMenuBack.remove(player.getName());
		mis.PlayerMenu.remove(player.getName());
	}
}
