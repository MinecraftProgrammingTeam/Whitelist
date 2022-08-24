package top.minept.whitelist.events;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.Plugin;
import top.minept.whitelist.Whitelist;

import java.util.List;
import java.util.Objects;

public class OnLogin implements Listener {
    Plugin configP = Whitelist.getPlugin(Whitelist.class);
    @EventHandler
    public void PlayerOnLogin(PlayerLoginEvent loginEvent){
        // 自动reload
        configP.reloadConfig();
        // 是否开启
        if(configP.getConfig().getBoolean("enable-whitelist")){
            String loginPlayer = loginEvent.getPlayer().getName();
            List<String> wedPlayer = configP.getConfig().getStringList("whitelisted-players");
            if(!wedPlayer.contains(loginPlayer)){
                // 不允许玩家登录
                loginEvent.setResult(PlayerLoginEvent.Result.KICK_WHITELIST);
                loginEvent.disallow(loginEvent.getResult(), ChatColor.translateAlternateColorCodes
                        ('&', Objects.requireNonNull(
                                configP.getConfig().getString("nwm"))
                        )
                );
            } else {
                // 允许玩家登录
                loginEvent.allow();
            }
        } else {
            System.out.println(Whitelist.normal + "未启用插件");
        }
    }
}
