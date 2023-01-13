package me.WesBag.CustomQuests.Objectives;

import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.WesBag.Toontown.BattleCore.Toons.Toon;
import me.WesBag.Toontown.Tasks.CustomEvents.PlayerDestroyBuildingEvent;
import me.blackvein.quests.CustomObjective;
import me.blackvein.quests.Quest;
import me.blackvein.quests.Quests;

public class CogBuildingSuitsDestroyedObjective extends CustomObjective implements Listener {

	private static Quests qp = (Quests) Bukkit.getServer().getPluginManager().getPlugin("Quests");
	
	public CogBuildingSuitsDestroyedObjective() {
		this.setName("Building Suits Destroyed");
		this.setAuthor("WesBag");
		this.setShowCount(true);
		this.setCountPrompt("Set the amount of buildings the player must destroy");
		this.addStringPrompt("Level-Needed", "Set the minimum level the cog building must be (1-5)", "1");
		this.addStringPrompt("Suit-Needed", "Set the suit of building the player must destroy", "Sellbot");
		this.setDisplay("Destroy %count%: %Level-Needed%+ story %Suit-Needed% Cog Buildings");
	}
	
	@EventHandler
	public void onPlayerDestroyBuilding(PlayerDestroyBuildingEvent e) {
		if (e.getDestroyers().isEmpty()) return;
		
		List<Toon> toons = e.getDestroyers();
		
		for (Toon toon : toons) {
			for (Quest quest : qp.getQuester(toon.getUUID()).getCurrentQuests().keySet()) {
				Map<String, Object> map = getDataForPlayer(toon.getToon(), this, quest);
				if (map == null) continue;
				
				int levelNeeded = (int) map.get("Level-Needed");
				int levels = e.getBuildingLevels();
				String suitNeeded = (String) map.get("Suit-Needed");
				String suit = e.getBuildingSuit();
				
				if (levelNeeded >= levels && suit.equalsIgnoreCase(suitNeeded)) {
					toon.getToon().sendMessage(ItemsFromCogsObjective.TasksPrefix + " Great job defeating that building!");
					incrementObjective(toon.getToon(), this, 1, quest);
				}
			}
			
		}
	}
}
