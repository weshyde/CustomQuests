package me.WesBag.CustomQuests.Rewards;

import java.util.Map;

import org.bukkit.entity.Player;

import me.WesBag.Toontown.BattleCore.Toons.Toon;
import me.WesBag.Toontown.BattleCore.Toons.ToonsController;
import me.blackvein.quests.CustomReward;

public class JellybeanReward extends CustomReward {

	public JellybeanReward() {
		this.setName("Jellybean Reward");
		this.setAuthor("WesBag");
		
		this.addStringPrompt("Jellybean-Amt", "Enter the amount the jellybeans", 0);
	}
	
	@Override
	public void giveReward(Player player, Map<String, Object> data) {
		int jellybeans = Integer.parseInt((String) data.get("Jellybean-Amt"));
		Toon toon = ToonsController.getToon(player.getUniqueId());
		toon.addPouchJellybeans(jellybeans);
	}
	
}
