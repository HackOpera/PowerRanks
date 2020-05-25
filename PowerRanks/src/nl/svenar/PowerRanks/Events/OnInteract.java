package nl.svenar.PowerRanks.Events;

import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import nl.svenar.PowerRanks.Main;
import nl.svenar.PowerRanks.Util;
import nl.svenar.PowerRanks.Data.Users;

public class OnInteract implements Listener {

	Main m;

	public OnInteract(Main m) {
		this.m = m;
	}

	@EventHandler(ignoreCancelled = true)
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			Player player = event.getPlayer();
			Block block = event.getClickedBlock();
			if (block.getState() instanceof Sign) {
				Sign sign = (Sign) block.getState();
				if (Util.isPowerRanksSign(this.m, sign)) {
					handlePowerRanksSign(sign, player);
				}
			}
		}
	}

	private void handlePowerRanksSign(Sign sign, Player player) {
		final Users s = new Users(this.m);
		String sign_command = sign.getLine(1);
		String sign_argument = sign.getLine(2);
		boolean sign_error = sign.getLine(3).toLowerCase().contains("error");

		if (!sign_error) {
			if (sign_command.equalsIgnoreCase("promote")) {
				if (s.promote(player.getName()))
					this.m.messageCommandPromoteSuccess(player, player.getName());
				else
					this.m.messageCommandPromoteError(player, player.getName());
			} else if (sign_command.equalsIgnoreCase("demote")) {
				if (s.demote(player.getName()))
					this.m.messageCommandDemoteSuccess(player, player.getName());
				else
					this.m.messageCommandDemoteError(player, player.getName());
			} else if (sign_command.equalsIgnoreCase("set")) {
				s.setGroup(player, s.getRankIgnoreCase(sign_argument));
			} else if (sign_command.equalsIgnoreCase("check")) {
				s.getGroup(player.getName(), player.getName());
			} else {
				if (player.hasPermission("powerranks.cmd.admin")) {
					this.m.messageSignUnknownCommand(player);
				}
			}
		}
	}
}