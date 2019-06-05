package cn.xiaokai.mis.form.custom.sc;

import java.util.ArrayList;
import java.util.HashMap;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponseCustom;
import cn.xiaokai.mis.MiniatureS;
import cn.xiaokai.mis.form.MakeForm;
import cn.xiaokai.mis.form.custom.CustomData;
import cn.xiaokai.mis.msg.Message;
import cn.xiaokai.mis.tool.Tool;

/**
 * @author Winfxk
 */
@SuppressWarnings("unchecked")
public class CustomReceive {
	public Player player;
	public MiniatureS mis;
	public FormResponseCustom data;
	public CustomData fx;
	private HashMap<String, Object> Items;
	private ArrayList<String> Keys;
	private String Command;
	private String Commander;

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
		fx = mis.Custom.get(player.getName());
		Items = fx.AllData;
		Keys = fx.Keys;
		Command = fx.OverallCommadn;
		Commander = fx.OverallCommander;
	}

	/**
	 * 开始♂
	 */
	public void startPY() {
		if (fx.OverallCommadn == null)
			return;
		int dK = 0;
		boolean FFSB = true;
		for (String ike : Keys) {
			if (!Switch(ike, dK)) {
				FFSB = false;
				break;
			}
			dK++;
		}
		if (!FFSB)
			return;
		SendCommand.toCommand(player, Command, Commander);
	}

	public boolean Switch(String ike, int dk) {
		Message msg = mis.getMessage();
		HashMap<String, Object> item = (HashMap<String, Object>) Items.get(ike);
		String[] Key;
		Object[] data;
		switch (String.valueOf(item.get("Type")).toLowerCase()) {
		case "dropdown":
		case "d":
		case "下拉":
		case "下拉框":
		case "stepslider":
		case "steps":
		case "ss":
		case "滑动选择":
			Key = new String[] { "{" + ike + "}", "{" + ike + "-ID}", "{" + ike + "-Text}" };
			data = new Object[] { item.get("Text"), dk, this.data.getResponse(dk) };
			Command = msg.getText(Command, Key, data);
			return true;
		case "slider":
		case "sl":
		case "滑动条":
		case "滑动":
			int slider = Float.valueOf(this.data.getResponse(dk).toString()).intValue();
			Key = new String[] { "{" + ike + "}", "{" + ike + "-ID}", "{" + ike + "-Size}" };
			data = new Object[] { item.get("Text"), dk, slider };
			Command = msg.getText(Command, Key, data);
			return true;
		case "toggle":
		case "开关":
		case "togg":
			String kk = item.get("开") == null ? "开" : (String) item.get("开");
			String gg = item.get("关") == null ? "关" : (String) item.get("关");
			String k;
			boolean sb = Boolean.valueOf(this.data.getResponse(dk).toString());
			if (sb)
				k = kk;
			else
				k = gg;
			if (Command.contains("{" + ike + "-Command}"))
				Command = Command.replace("{" + ike + "-Command}", k);
			Key = new String[] { "{" + ike + "}", "{" + ike + "-ID}", "{" + ike + "-K}", "{" + ike + "-G}" };
			data = new Object[] { item.get("Text"), dk, kk, gg };
			Command = msg.getText(Command, Key, data);
			return true;
		case "input":
		case "i":
		case "输入框":
		case "编辑框":
		case "edittxt":
		case "ed":
			String input = this.data.getResponse(dk) == null ? "" : (String) this.data.getResponse(dk);
			if (!(item.get("允许为空") == null || !(item.get("允许为空") instanceof Boolean)
					|| Boolean.valueOf(item.get("允许为空").toString())) && input.isEmpty()) {
				MakeForm.makeTip(player, msg.getSon("Custom", "SBPlayerInputNull", new String[] { "{InputTitle}" },
						new Object[] { item.get("Title") == null ? ike : item.get("Title") }));
				return false;
			}
			if (!(item.get("纯数字") == null || !(item.get("纯数字") instanceof Boolean)
					|| Boolean.valueOf(item.get("纯数字").toString())) && !input.isEmpty() && !Tool.isInteger(input)) {
				MakeForm.makeTip(player, msg.getSon("Custom", "SBPlayerInputNotNum", new String[] { "{InputTitle}" },
						new Object[] { item.get("Title") == null ? ike : item.get("Title") }));
				return false;
			}
			String Show = item.get("显示") == null ? "" : (String) item.get("显示");
			String d = item.get("默认") == null ? "" : (String) item.get("默认");
			Key = new String[] { "{" + ike + "}", "{" + ike + "-ID}", "{" + ike + "-Title}", "{" + ike + "-Text}",
					"{" + ike + "-Show}", "{" + ike + "-D}" };
			data = new Object[] { item.get("Text"), dk, item.get("Title") == null ? ike : item.get("Title"), input,
					Show, d };
			Command = msg.getText(Command, Key, data);
			return true;
		case "标签":
		case "label":
		case "txt":
		case "text":
		case "t":
		case "l":
		case "文本":
		default:
			Key = new String[] { "{" + ike + "}", "{" + ike + "-ID}" };
			data = new Object[] { item.get("Text"), dk };
			Command = msg.getText(Command, Key, data);
			return true;
		}
	}
}
