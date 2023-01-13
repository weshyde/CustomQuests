package me.WesBag.CustomQuests.Objectives;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.blackvein.quests.CustomObjective;
import me.blackvein.quests.Quest;
import me.blackvein.quests.Quests;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;
import net.md_5.bungee.api.ChatColor;

public class TalkToHQOfficerObjective extends CustomObjective implements Listener {
	private static Quests qp = (Quests) Bukkit.getServer().getPluginManager().getPlugin("Quests");
	
	public TalkToHQOfficerObjective() {
		this.setName("Talk to HQ");
		this.setAuthor("WesBag");
		this.setShowCount(true);
		this.setCountPrompt("Enter 1");
		this.setDisplay("Talk to an HQ Officer");
	}
	
	@EventHandler
	public void onPlayerRightClickOfficer(NPCRightClickEvent e) {
		NPC npc = e.getNPC();
		Player p = e.getClicker();
		String unformattedNPCName = ChatColor.stripColor(npc.getFullName());
		if (unformattedNPCName.contains("HQ")) {
			for (Quest quest : qp.getQuester(p.getUniqueId()).getCurrentQuests().keySet()) {
				Map<String, Object> map = getDataForPlayer(p, this, quest);
				if (map == null) continue;
				
				if (unformattedNPCName.contains("HQ")) {
					incrementObjective(p, this, 1, quest);
				}
			}
		}
	}
}
