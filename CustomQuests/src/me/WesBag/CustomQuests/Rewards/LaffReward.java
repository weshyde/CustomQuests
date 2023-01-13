package me.WesBag.CustomQuests.Rewards;

import java.util.Map;

import org.bukkit.entity.Player;

import me.WesBag.Toontown.BattleCore.Toons.Toon;
import me.WesBag.Toontown.BattleCore.Toons.ToonsController;
import me.blackvein.quests.CustomReward;

public class LaffReward extends CustomReward {
	
	public LaffReward() {
		this.setName("Laff Reward");
		this.setAuthor("WesBag");
		
		this.addStringPrompt("Increase-Amt", "Enter the amount of laff increase", 0);
	}
	
	@Override
	public void giveReward(Player player, Map<String, Object> data) {
		int increaseAmt = Integer.parseInt((String) data.get("Increase-Amt"));
		Toon toon = ToonsController.getToon(player.getUniqueId());
		int newAmt = toon.getMaxHealth() + increaseAmt;
		toon.setMaxHealth(newAmt);
	}
}
