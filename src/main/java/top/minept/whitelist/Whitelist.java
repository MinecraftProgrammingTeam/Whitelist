package top.minept.whitelist;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import top.minept.whitelist.commands.WAdd;
import top.minept.whitelist.commands.WDel;
import top.minept.whitelist.events.OnLogin;

import java.util.Objects;

public final class Whitelist extends JavaPlugin {
    public static String normal = ChatColor.BLUE + "[Whitelist] ";
    @Override
    public void onEnable() {
        // Plugin startup logic
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        getServer().getPluginManager().registerEvents(new OnLogin(), this);
        Objects.requireNonNull(getCommand("wadd")).setExecutor(new WAdd());
        Objects.requireNonNull(getCommand("wdel")).setExecutor(new WDel());
        System.out.println(normal + "插件已启用");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println(normal + "插件已卸载");
    }
}
