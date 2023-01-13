package me.WesBag.CustomQuests.Objectives;

import java.util.List;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.WesBag.Toontown.BattleCore.Cogs.Cog;
import me.WesBag.Toontown.BattleCore.Toons.Toon;
import me.WesBag.Toontown.Tasks.CustomEvents.BattleFinishEvent;
import me.blackvein.quests.CustomObjective;
import me.blackvein.quests.Quest;
import me.blackvein.quests.Quests;

public class CogSuitsDestroyedObjective extends CustomObjective implements Listener {
	
	private static Quests qp = (Quests) Bukkit.getServer().getPluginManager().getPlugin("Quests");
	
	public CogSuitsDestroyedObjective() {
		this.setName("Cog Suits Destroyed");
		this.setAuthor("WesBag");
		this.setShowCount(true);
		this.setCountPrompt("Set the amount of the cog suit the player must destroy");
		this.addStringPrompt("Cog-Suit", "Enter the name of the suit the player must destroy", "Sellbot");
		this.addStringPrompt("Cog-Level", "Enter the level the Cog must at least be", 1);
		this.setDisplay("Destroy %count%: %Cog-Suit%");
	}
	
	@EventHandler
	public void onPlayerDestroyCog2(BattleFinishEvent e) {
		if (e.getPlayers().isEmpty()) return;
		if (e.getCogs().isEmpty()) return;
		
		List<Toon> players = e.getPlayers();
		List<Cog> cogs = e.getCogs();
		
		for (Toon toon : players) {
			for (Quest quest : qp.getQuester(toon.getUUID()).getCurrentQuests().keySet()) {
				Map<String, Object> map = getDataForPlayer(toon.getToon(), this, quest);
				if (map == null) continue;
				
				String neededSuit = (String) map.get("Cog-Suit");
				int neededLevel = Integer.parseInt((String) map.get("Cog-Level"));
				
				for (Cog cog : cogs) {
					if (cog.getCogSuit().equalsIgnoreCase(neededSuit)) {
						if (cog.getCogLevel() >= neededLevel) {
							incrementObjective(toon.getToon(), this, 1, quest);
						}
					}
				}
				
			}
		}
	}
}
