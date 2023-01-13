package me.WesBag.CustomQuests.Rewards;

import java.util.Map;

import org.bukkit.entity.Player;

import me.WesBag.Toontown.BattleCore.Toons.Toon;
import me.WesBag.Toontown.BattleCore.Toons.ToonsController;
import me.blackvein.quests.CustomReward;

public class GagPouchReward extends CustomReward {
	
	public GagPouchReward() {
		this.setName("Gag Pouch Reward");
		this.setAuthor("WesBag");
		
		this.addStringPrompt("Increase-Amt", "Enter the amount the gag pouch will increase", 0);
		//this.setRewardName(getModuleName());
	}
	
	@Override
	public void giveReward(Player player, Map<String, Object> data) {
		int increaseAmt = Integer.parseInt((String) data.get("Increase-Amt"));
		Toon toon = ToonsController.getToon(player.getUniqueId());
		int newAmt = toon.getMaxGags() + increaseAmt;
		toon.setMaxGags(newAmt);
	}

}
