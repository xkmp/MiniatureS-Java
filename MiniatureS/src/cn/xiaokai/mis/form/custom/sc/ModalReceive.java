package cn.xiaokai.mis.form.custom.sc;

import java.util.HashMap;
import java.util.Map;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponseModal;
import cn.xiaokai.mis.MiniatureS;
import cn.xiaokai.mis.form.custom.CustomData;

/**
 * @author Winfxk
 */
@SuppressWarnings("unchecked")
public class ModalReceive {
	private Player player;
	private MiniatureS mis;
	private FormResponseModal data;
	private String IWantAGirl;
	private CustomData fx;
	private String Title;
	private HashMap<String, Object> AllData;

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
		fx = mis.Custom.get(player.getName());
		Title = fx.Title;
		IWantAGirl = fx.IWantAGirl;
		AllData = fx.AllData;
	}

	/**
	 * 开始♂
	 */
	public void startPY() {
		String Buttonkey = "Button" + (data.getClickedButtonId() + 1);
		String OverallCommadn = fx.OverallCommadn;
		String OverallCommader = fx.OverallCommander;
		String ButtonClickText = "确定";
		String ButtonClickCommand = "";
		String ButtonClickCommander = "";
		if (AllData.containsKey(Buttonkey) && AllData.get(Buttonkey) != null
				&& (AllData.get(Buttonkey) instanceof Map)) {
			HashMap<String, Object> ButtonClick = (HashMap<String, Object>) AllData.get(Buttonkey);
			ButtonClickText = (ButtonClick.get("Text") != null && !ButtonClick.get("Text").toString().isEmpty())
					? ButtonClick.get("Text").toString()
					: "确定";
			ButtonClickCommand = (ButtonClick.get("Command") != null
					&& !ButtonClick.get("Command").toString().isEmpty()) ? ButtonClick.get("Command").toString() : "";
			ButtonClickCommander = (!ButtonClickCommand.isEmpty() && ButtonClick.get("Commander") != null
					&& !ButtonClick.get("Commander").toString().isEmpty()) ? ButtonClick.get("Commander").toString()
							: "";
		}
		if (ButtonClickCommand != null && !ButtonClickCommand.isEmpty() && ButtonClickCommander != null
				&& !ButtonClickCommander.isEmpty())
			SendCommand.toCommand(player,
					mis.getMessage().getText(ButtonClickCommand,
							new String[] { "{Click}", "{Button1Text}", "{Button2Text}", "{Click-ID}", "{Click-Key}",
									"{file}", "{Player}", "{Content}", "{Title}" },
							new Object[] { ButtonClickText, fx.Button1, fx.Button2, data.getClickedButtonId(),
									Buttonkey, fx.file.getName(), player.getName(), IWantAGirl, Title }),
					ButtonClickCommander);
		if (OverallCommadn != null && !OverallCommadn.isEmpty() && OverallCommader != null
				&& !OverallCommader.isEmpty())
			SendCommand.toCommand(player,
					mis.getMessage().getText(OverallCommadn,
							new String[] { "{Click}", "{Button1Text}", "{Button2Text}", "{Click-ID}", "{Click-Key}",
									"{file}", "{Player}", "{Content}", "{Title}" },
							new Object[] { ButtonClickText, fx.Button1, fx.Button2, data.getClickedButtonId(),
									Buttonkey, fx.file.getName(), player.getName(), IWantAGirl, Title }),
					OverallCommader);
	}

}
