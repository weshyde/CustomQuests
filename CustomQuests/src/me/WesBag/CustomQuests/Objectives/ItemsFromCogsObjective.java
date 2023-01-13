package me.WesBag.CustomQuests.Objectives;

import java.util.List;
import java.util.Map;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.WesBag.Toontown.BattleCore.Cogs.Cog;
import me.WesBag.Toontown.BattleCore.Toons.Toon;
import me.WesBag.Toontown.Tasks.CustomEvents.BattleFinishEvent;
import me.blackvein.quests.CustomObjective;
import me.blackvein.quests.Quest;
import me.blackvein.quests.Quests;
import net.md_5.bungee.api.ChatColor;

public class ItemsFromCogsObjective extends CustomObjective implements Listener {

private static Quests qp = (Quests) Bukkit.getServer().getPluginManager().getPlugin("Quests");
public static String TasksPrefix = ChatColor.DARK_PURPLE + "[" + ChatColor.DARK_GREEN + "Tasks" + ChatColor.DARK_PURPLE + "]";
	

	public ItemsFromCogsObjective() {
		this.setName("Items collected from Cogs");
		this.setAuthor("WesBag");
		this.setShowCount(true);
		this.setCountPrompt("Set the amount of items the player must recover");
		this.setDisplay("Recover %count% %Recover-Name% from the %Cog-Name%s");
		this.addStringPrompt("Recover-Name", "Enter the name of the item the player must recover", "Flashdrive");
		this.addStringPrompt("Cog-Name", "Enter the name of the Cog the player must recover the items from. ('Cogs' for all)", "Cogs");
		this.addStringPrompt("Cog-Level", "Enter the level the Cog must at least be", 1);
		this.addStringPrompt("Recover-Chance", "Enter the chance of recover the item from each cog", 100);
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
				
				String neededCog = (String) map.get("Cog-Name");
				String neededItem = (String) map.get("Recover-Name");
				int neededLevel = Integer.parseInt((String) map.get("Cog-Level"));
				int recoverChance = Integer.parseInt((String) map.get("Recover-Chance"));
				for (Cog cog : cogs) {
					if (cog.getJustCogName().equalsIgnoreCase(neededCog) || cog.getCogSuit().equalsIgnoreCase(neededCog) || neededCog.equalsIgnoreCase("Cogs")) {
						if (cog.getCogLevel() >= neededLevel) {
							Random r = new Random();
							int randomInt = r.nextInt(100) + 1;
							if (randomInt <= recoverChance) {
								toon.getToon().sendMessage(TasksPrefix + " You recovered a " + neededItem + "!");
								incrementObjective(toon.getToon(), this, 1, quest);
							}
							
							else { //Delete eventually!
								toon.getToon().sendMessage(TasksPrefix + " Sorry, you didnt recover a " + neededItem + " this time!");
							}
						}
					}
				}
			}
		}
	}
	/*
	@EventHandler
	public void onPlayerDestroyCog(PlayerDestroyCogEvent e) {
		if ((!e.getCogFullName().contains("Level"))) return;
		if (e.getDestroyers().isEmpty()) return;
	
		
		for (UUID pUUID : e.getDestroyers()) {
			//System.out.println("Destroyer found!");
			for (Quest quest : qp.getQuester(pUUID).getCurrentQuests().keySet()) {
				Map<String, Object> map = getDataForPlayer(Bukkit.getPlayer(pUUID), this, quest);
				
				String deadCogName = e.getCogName();
				String neededCogName = (String) map.get("Cog-Name");
				String itemName = (String) map.get("Recover-Name");
				int recoverChance = Integer.parseInt((String) map.get("Recover-Chance"));
				
				if (deadCogName.equals(neededCogName) || neededCogName.equalsIgnoreCase("Cogs")) {
					System.out.println("Destroyer found! 2!!");
					Random r = new Random();
					int randomInt = r.nextInt(100) + 1;
					if (randomInt <= recoverChance) {
						Bukkit.getPlayer(pUUID).sendMessage(TasksPrefix + " You recovered 1 " + itemName + "!");
						incrementObjective(Bukkit.getPlayer(pUUID), this, 1, quest);
					}
					else {
						Bukkit.getPlayer(pUUID).sendMessage(TasksPrefix + " Sorry, you didnt recover a " + itemName + " this time!");
						//System.out.println("Task Item Recovery debug:");
						//System.out.println("Needed <= " + recoverChance);
						//System.out.println("Rolled: " + randomInt);
					}
				}
			}
		}
	}
	*/
}
