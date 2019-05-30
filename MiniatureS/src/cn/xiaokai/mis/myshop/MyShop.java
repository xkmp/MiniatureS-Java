package cn.xiaokai.mis.myshop;

import java.io.File;
import java.util.HashMap;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponseCustom;
import cn.xiaokai.mis.MiniatureS;
import me.onebone.economyapi.EconomyAPI;

public class MyShop {
	private Player player;
	private MiniatureS mis;
	private int Count;
	private TonsFx fx;
	private HashMap<String, Object> map;
	private File file;
	private String Key;
	private int Money;

	public MyShop(Player player) {
		this.player = player;
		mis = MiniatureS.mis;
		fx = mis.MyShopData.get(player.getName());
		map = fx.MainItem;
		file = fx.file;
		Key = String.valueOf(map.get("Key"));
	}

	public void Switch(FormResponseCustom data) {
		Count = Float.valueOf(String.valueOf(data.getResponse(1))).intValue();
		Money = Float.valueOf(String.valueOf(map.get("Money"))).intValue();
		switch (String.valueOf(map.get("Type")).toLowerCase()) {
		case "shop":
		case "收购":
			Shop();
			break;
		case "sell":
		case "出售":
		default:
			Sell();
			break;
		}
	}

	private void Shop() {

	}

	private void Sell() {
		if (EconomyAPI.getInstance().myMoney(player) < (Money * Count)) {

		}
	}
}
