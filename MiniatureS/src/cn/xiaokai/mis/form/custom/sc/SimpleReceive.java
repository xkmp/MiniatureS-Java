package cn.xiaokai.mis.form.custom.sc;

import java.util.ArrayList;
import java.util.HashMap;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponseSimple;
import cn.nukkit.utils.TextFormat;
import cn.xiaokai.mis.MiniatureS;
import cn.xiaokai.mis.form.custom.CustomData;

/**
 * @author Winfxk
 */
@SuppressWarnings("unchecked")
public class SimpleReceive {
	private Player player;
	private MiniatureS mis;
	private CustomData fx;
	private HashMap<String, Object> Items;
	private ArrayList<String> Keys;
	private String Key;
	private HashMap<String, Object> Item;

	/**
	 * 如果发回的数据是自定义的简单型表单数据
	 * 
	 * @param player 触发事件的玩家对象
	 * @param data   发回的数据对象
	 */
	public SimpleReceive(Player player, FormResponseSimple data) {
		this.player = player;
		mis = MiniatureS.mis;
		fx = mis.Custom.get(player.getName());
		Items = fx.AllData;
		Keys = fx.Keys;
		Key = Keys.get(data.getClickedButtonId());
		Item = (HashMap<String, Object>) Items.get(Key);
		if (!fx.FormType.equals("SimpleType"))
			mis.getLogger().info(TextFormat.RED + "数据不符！请检查！" + fx.file.getName());
	}

	/**
	 * 开始♂
	 */
	public void startPY() {
		if (fx.OverallCommadn != null || (Item.get("Command") != null && !Item.get("Command").toString().isEmpty())) {
			String FxCommand = "";
			String Command = null;
			if (Item.get("Command") != null && !Item.get("Command").toString().isEmpty())
				Command = Item.get("Command").toString();
			for (String ike : Items.keySet()) {
				HashMap<String, Object> map = (HashMap<String, Object>) Items.get(ike);
				if (fx.OverallCommadn != null && fx.OverallCommadn.contains(ike))
					FxCommand = mis.getMessage().getText(fx.OverallCommadn,
							new String[] { "{" + ike + "}", "{" + ike + "-ID}", "{Player}", "{file}", "{Content}",
									"{Title}" },
							new Object[] { map.get("Text"), Keys.indexOf(ike), player.getName(), fx.file.getName(),
									fx.IWantAGirl, fx.Title });
				if (Item.get("Command") != null && !Item.get("Command").toString().isEmpty())
					Command = mis.getMessage().getText(Command,
							new String[] { "{" + ike + "}", "{" + ike + "-ID}", "{Player}", "{file}", "{Content}",
									"{Title}" },
							new Object[] { map.get("Text"), Keys.indexOf(ike), player.getName(), fx.file.getName(),
									fx.IWantAGirl, fx.Title });
			}
			if (fx.OverallCommadn != null && !FxCommand.isEmpty())
				SendCommand.toCommand(player, FxCommand, fx.OverallCommander);
			if (Command != null && !Command.isEmpty())
				SendCommand.toCommand(player, Command, fx.OverallCommander);
		}
	}
}
