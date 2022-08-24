package top.minept.whitelist.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import top.minept.whitelist.Whitelist;

import java.util.List;
import java.util.Objects;

import static top.minept.whitelist.Whitelist.normal;

public class WDel implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!sender.isOp()){
            sender.sendMessage(normal + ChatColor.RED + "您还不是OP，不可以执行此指令。");
            return false;
        }
        if(args.length != 1){
            sender.sendMessage(normal + ChatColor.RED + "格式错误！用法:/wdel <玩家名>");
            return false;
        }
        Plugin configP = Whitelist.getPlugin(Whitelist.class);
        List<String> whitelist = configP.getConfig().getStringList("whitelisted-players");
        if(whitelist.contains(args[0])){
            Player delPlayer = Bukkit.getPlayer(args[0]); // 操控player

            if(delPlayer == null){
                sender.sendMessage(normal + ChatColor.RED + "此玩家虽然在白名单里，但是服务器里没有此玩家的数据，" +
                        "因而删除无效，请先让此玩家上一下游戏再删除。");
                return false;
            }
            whitelist.remove(args[0]);
            configP.getConfig().set("whitelisted-players", whitelist);
            configP.saveConfig();
            sender.sendMessage(normal + "删除成功！");
            if(delPlayer.isOnline()){
                sender.sendMessage(normal + ChatColor.RED + "检测到被删除玩家在线，正在进行强制退场。");
                delPlayer.kickPlayer(ChatColor.translateAlternateColorCodes('&',
                        Objects.requireNonNull(configP.getConfig().getString("bwm"))
                        )
                );
                sender.sendMessage(normal + ChatColor.GRAY + "玩家已被强制退场。");
            }
        } else {
            sender.sendMessage(normal + ChatColor.RED + "该玩家不在白名单列表中，请检查玩家名是否输入正确。");
        }
        return false;
    }
}
