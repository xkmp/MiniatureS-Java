package cn.xiaokai.mis.myshop.seek;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponseSimple;
import cn.xiaokai.mis.MiniatureS;
import cn.xiaokai.mis.myshop.MakeMyShopForm;
import cn.xiaokai.mis.myshop.TonsFx;

/**
 * @author Winfxk
 */
public class BnsoxK {
	private MiniatureS mis;
	private Player player;
	private FormResponseSimple data;
	private TonsFx fx;

	/**
	 * 搜索结束的事件处理
	 * 
	 * @param player 触发事件的玩家对象
	 * @param data   处罚的数据
	 */
	public BnsoxK(Player player, FormResponseSimple data) {
		mis = MiniatureS.mis;
		this.player = player;
		this.data = data;
		fx = mis.MyShopData.get(player.getName());
	}

	/**
	 * 搜索结束后点击项目开始交易
	 */
	public void Switch() {
		ArrayList<HashMap<String, Object>> list = fx.Seek;
		HashMap<String, Object> map = list.get(data.getClickedButtonId());
		fx.file = (File) map.get("FFF_SB");
		mis.MyShopData.put(player.getName(), fx);
		(new MakeMyShopForm(player)).startPyItem(map, (File) map.get("FFF_SB"));
	}
}
