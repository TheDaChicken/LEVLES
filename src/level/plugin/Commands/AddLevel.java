package level.plugin.Commands;

import level.plugin.Exceptions.Player.PlayerNameDoesntExist;
import level.plugin.Exceptions.Player.PlayerNotPlayedBefore;
import level.plugin.Main;
import level.plugin.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class AddLevel implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("addlevel")) {
            Player player = (Player) sender;
            if (args.length < 2) {
                sender.sendMessage(Messages.getMessage(player, "AddLevelUsage"));
            } else {
                String player_to_string = args[0];
                Integer number = Main.convertStringToInt(args[1]);
                if (number == null) {
                    HashMap<String, String> extra_hashmap = new HashMap<>();
                    extra_hashmap.put("number", args[1]);
                    sender.sendMessage(Messages.getMessage(player, "NotANumber", extra_hashmap));
                    return true;
                }
                try {
                    Main.getPlayerData(player_to_string).addLevel(number);
                } catch (PlayerNameDoesntExist playerNameDoesntExist) {
                    HashMap<String, String> extra_hashmap = new HashMap<>();
                    extra_hashmap.put("player_to", player_to_string);
                    sender.sendMessage(Messages.getMessage(player, "PlayerNotExist", extra_hashmap));
                } catch (PlayerNotPlayedBefore playerNotPlayedBefore) {
                    HashMap<String, String> extra_hashmap = new HashMap<>();
                    extra_hashmap.put("player_to", player_to_string);
                    sender.sendMessage(Messages.getMessage(player, "PlayerNotPlayedBefore", extra_hashmap));
                }
                return true;
            }
        }
        return false;
    }

    private Player getSenderPlayer(CommandSender sender) {
        if (sender instanceof Player) {
            return (Player) sender;
        }
        return null;
    }
}
