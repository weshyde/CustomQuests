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

public class CogsDestroyedObjective extends CustomObjective implements Listener {
	private static Quests qp = (Quests) Bukkit.getServer().getPluginManager().getPlugin("Quests");
	
	public CogsDestroyedObjective() {
		this.setName("Cogs Destroyed");
		this.setAuthor("WesBag");
		this.setShowCount(true);
		this.setCountPrompt("Set the amount of cogs the player must destroy");
		this.setDisplay("Destroy %count%: %Cog-Name%");
		this.addStringPrompt("Cog-Name", "Enter the name of the Cog the player must destroy", "All");
		this.addStringPrompt("Cog-Level", "Enter the level the Cog must at least be", 1);
	}
	
	@EventHandler
	public void onPlayerDestroyCog2(BattleFinishEvent e) {
		if (e.getPlayers().isEmpty()) return;
		if (e.getCogs().isEmpty()) return;
		
		List<Toon> players = e.getPlayers();
		List<Cog> cogs = e.getCogs();
		for (Toon toon : players) {
			for (Quest quest : qp.getQuester(toon.getUUID()).getCurrentQuests().keySet()) {
				Map<String, Object> map = getDataForPlayer(Bukkit.getPlayer(toon.getUUID()), this, quest);
				if (map == null) continue;
				
				String neededCog = (String) map.get("Cog-Name");
				int neededLevel = Integer.parseInt((String) map.get("Cog-Level"));
				
				for (Cog cog : cogs) {
					if (neededCog.equalsIgnoreCase(cog.getJustCogName()) || neededCog.equalsIgnoreCase("All")) {
						if (cog.getCogLevel() >= neededLevel) {
							incrementObjective(toon.getToon(), this, 1, quest);
						}
					}
				}
				
			}
		}
	}
	/*
	@EventHandler
	public void onPlayerDestroyCog(PlayerDestroyCogEvent e) {
		if ((!e.getCogName().contains("Level"))) return;
		if (e.getDestroyers().isEmpty()) return;
		
		for (UUID pUUID : e.getDestroyers()) {
			for (Quest quest : qp.getQuester(pUUID).getCurrentQuests().keySet()) {
				Map<String, Object> map = getDataForPlayer(Bukkit.getPlayer(pUUID), this, quest);
				
				String deadCogName = e.getCogName();
				String neededCogName = (String) map.get("Cog-Name");
				
				if (deadCogName.equals(neededCogName))
					incrementObjective(Bukkit.getPlayer(pUUID), this, 1, quest);
			}
		}
	}
	*/
}
