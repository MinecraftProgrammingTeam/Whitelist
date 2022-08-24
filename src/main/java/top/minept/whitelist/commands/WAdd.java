package top.minept.whitelist.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import top.minept.whitelist.Whitelist;

import java.util.List;

import static top.minept.whitelist.Whitelist.normal;

public class WAdd implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!sender.isOp()){
            sender.sendMessage(normal + ChatColor.RED + "您还不是OP，不可以执行此指令。");
            return false;
        }
        if (args.length != 1) {
            sender.sendMessage(normal + ChatColor.RED + "正确格式:/wadd <玩家名>");
            return false;
        }
        Plugin configP = Whitelist.getPlugin(Whitelist.class);

        List<String> whitelist = configP.getConfig().getStringList("whitelisted-players");
        if(whitelist.contains(args[0])){
            sender.sendMessage(normal + ChatColor.RED + "玩家已在白名单列表中，请勿重复添加！");
            return false;
        }
        whitelist.add(args[0]);
        configP.getConfig().set("whitelisted-players", whitelist);
        configP.saveConfig();
        sender.sendMessage(normal + "添加成功!");
        return false;
    }
}
