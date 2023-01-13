package me.WesBag.CustomQuests.Rewards;

import java.util.Map;

import org.bukkit.entity.Player;

import me.WesBag.Toontown.BattleCore.Toons.Toon;
import me.WesBag.Toontown.BattleCore.Toons.ToonsController;
import me.blackvein.quests.CustomReward;

public class TaskMaxReward extends CustomReward {
	
	public TaskMaxReward() {
		this.setName("TaskMax Reward");
		this.setAuthor("WesBag");
		
	}
	
	@Override
	public void giveReward(Player player, Map<String, Object> data) {
		Toon toon = ToonsController.getToon(player.getUniqueId());
		toon.incrementMaxTasks();
	}

}
