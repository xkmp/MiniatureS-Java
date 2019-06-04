package cn.xiaokai.mis.form.custom.sc;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponseCustom;
import cn.xiaokai.mis.MiniatureS;
import cn.xiaokai.mis.form.custom.CustomData;

/**
 * @author Winfxk
 */
public class CustomReceive {
	private Player player;
	private MiniatureS mis;
	private FormResponseCustom data;
	private CustomData fx;

	/**
	 * 如果发回的数据是复杂型表单数据
	 * 
	 * @param player 触发事件的玩家对象
	 * @param data   发挥的数据
	 */
	public CustomReceive(Player player, FormResponseCustom data) {
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
