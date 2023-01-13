package me.WesBag.CustomQuests.Objectives;

import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import me.WesBag.Toontown.Tasks.CustomEvents.PlayerCatchFishEvent;
import me.blackvein.quests.CustomObjective;
//import me.blackvein.quests.module.ICustomObjective;
import me.blackvein.quests.Quest;
import me.blackvein.quests.Quests;

public class ItemsCaughtFishingObjective extends CustomObjective implements Listener {
	
	private static Quests qp = (Quests) Bukkit.getServer().getPluginManager().getPlugin("Quests");

	public ItemsCaughtFishingObjective() {
		this.setName("Items Caught");
		this.setAuthor("WesBag");
		this.setShowCount(true);
		this.setCountPrompt("Set the amount the player must catch");
		this.addStringPrompt("Recover-Item", "Set the item/fish the player must recover", "Clown Fish");
		this.addStringPrompt("Recover-Chance", "Set the chance of recovery" , "100");
		this.setDisplay("Catch %count%: %Recover-Item%s from any fishing pond");
	}
	
	@EventHandler
	public void onPlayerCatchFish(PlayerCatchFishEvent e) {
		if (e.getPlayerUUID() == null) return;
		
		UUID pUUID = e.getPlayerUUID();
		Player p = Bukkit.getPlayer(pUUID);
		for (Quest quest : qp.getQuester(pUUID).getCurrentQuests().keySet()) {
			Map<String, Object> map = getDataForPlayer(p, this, quest);
			if (map == null) continue;
			
			int recoverChance = Integer.parseInt((String) map.get("Recover-Chance"));
			String neededItem = (String) map.get("Recover-Item");
			
			Random r = new Random();
			int randomInt = r.nextInt(100) + 1;
			if (randomInt <= recoverChance) {
				p.sendMessage(ItemsFromCogsObjective.TasksPrefix + " You recovered a " + neededItem + "!");
				incrementObjective(p, this, 1, quest);
			}
		}
	}
}