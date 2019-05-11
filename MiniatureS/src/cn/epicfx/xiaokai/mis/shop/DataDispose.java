package cn.epicfx.xiaokai.mis.shop;

import cn.epicfx.xiaokai.mis.MiniatureS;
import cn.epicfx.xiaokai.mis.form.MakeForm;
import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.response.FormResponseSimple;
import cn.nukkit.utils.TextFormat;

public class DataDispose {
	private MiniatureS mis;

	public DataDispose(MiniatureS shop) {
		this.mis = shop;
	}

	public void addShopShow(Player player, FormResponseCustom data) {
		System.out.println(data.getResponse(0));
	}

	public void Main(Player player, FormResponseSimple data) {
		if (mis.shopList.get(player.getName()) == null) {
			MakeForm.makeTip(player, TextFormat.RED + "数据解析错误：无法获取玩家视图列表(Shop Main)！");
			return;
		}
		int list_int = mis.shopList.get(player.getName()).size();
		if (!player.isOp() && data != null && list_int < (data.getClickedButtonId() + 1)) {
			MakeForm.makeTip(player, TextFormat.RED + "数据解析错误：未知的数据Key或您无权限！");
			return;
		}
		if (player.isOp() && (data == null || data.getClickedButtonId() >= list_int)) {
			mis.shopList.remove(player.getName());
			mis.shopMakeForm.MakeAddShop(player);
			return;
		}
	}
}
