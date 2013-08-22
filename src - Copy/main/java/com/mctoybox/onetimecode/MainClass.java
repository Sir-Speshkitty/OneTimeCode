package com.mctoybox.onetimecode;

import java.lang.reflect.Field;

import net.milkbowl.vault.permission.Permission;

import org.bukkit.command.Command;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.mctoybox.onetimecode.command.CreateCommand;
import com.mctoybox.onetimecode.command.UseCommand;

public class MainClass extends JavaPlugin {
	public static Permission perms = null;
	
	@Override
	public void onEnable() {
		saveDefaultConfig();
		
		getCommand("otc").setExecutor(new UseCommand(this));
		getCommand("createotc").setExecutor(new CreateCommand(this));
		
		RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
		perms = rsp.getProvider();
	}
	
	public Command getCmd(String commandName) {
		SimplePluginManager spm = (SimplePluginManager) getServer().getPluginManager();
		
		Field f = null;
		try {
			f = SimplePluginManager.class.getDeclaredField("commandMap");
		}
		catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		catch (SecurityException e) {
			e.printStackTrace();
		}
		f.setAccessible(true);
		
		SimpleCommandMap scm;
		try {
			scm = (SimpleCommandMap) f.get(spm);
			return scm.getCommand(commandName);
			
		}
		catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
		
	}
}
