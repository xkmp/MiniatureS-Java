package cn.xiaokai.mis.form.custom.sc;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponseModal;
import cn.xiaokai.mis.MiniatureS;

/**
 * @author Winfxk
 */
public class ModalReceive {
	private Player player;
	private MiniatureS mis;
	private FormResponseModal data;

	/**
	 * 如果发回的数据集是选择型表单数据
	 * 
	 * @param player 触发事件的玩家对象
	 * @param data   发挥的数据
	 */
	public ModalReceive(Player player, FormResponseModal data) {
		this.player = player;
		this.data = data;
		mis = MiniatureS.mis;
	}

	/**
	 * 开始♂
	 */
	public void startPY() {

	}
}
