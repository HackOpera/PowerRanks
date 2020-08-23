package nl.svenar.PowerRanks.events;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.event.EventHandler;
import nl.svenar.PowerRanks.PowerRanksConfiguration;
import nl.svenar.PowerRanks.PowerRanksExceptionsHandler;

public class onJoin implements Listener {

	@EventHandler
    public void onPostLogin(PostLoginEvent event) {
		ProxiedPlayer player = event.getPlayer();
		String player_uuid = player.getUniqueId().toString();
		player.sendMessage(new ComponentBuilder("Hello " + player.getName() + "!").create());
		
		try {
			Configuration yml_players = new PowerRanksConfiguration().load_config();
			if (!yml_players.contains("players." + player_uuid)) {
				yml_players.set("players." + player_uuid + ".name", player.getName());
			}
			new PowerRanksConfiguration().save_players(yml_players);
			throw new IOException("TEST");
		} catch (IOException e) {
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			PowerRanksExceptionsHandler.except(this.getClass().getName(), errors.toString());
		}
    }
}