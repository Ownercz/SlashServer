package sr.will.slashserver;


import com.google.inject.Inject;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.server.ServerRegisteredEvent;
import com.velocitypowered.api.event.proxy.server.ServerUnregisteredEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;

import java.util.ArrayList;
import java.util.List;

@Plugin(id = PluginInfo.ID, name = PluginInfo.NAME, version = PluginInfo.VERSION, description = PluginInfo.DESCRIPTION)
public class SlashServer {
    @Inject
    private ProxyServer proxy;
    private final List<String> registeredCommands = new ArrayList<>();
    private CommandMeta reloadCommandMeta;

    @Subscribe
    public void onProxyInitialize(ProxyInitializeEvent event) {
        CommandManager commandManager = proxy.getCommandManager();
        reloadCommandMeta = commandManager.metaBuilder("ssreload").build();
        commandManager.register(reloadCommandMeta, CommandReload.getCommand(this));

        reload();
    }

    @Subscribe
    public void onServerRegistered(ServerRegisteredEvent event) {
        registerServerCommand(event.registeredServer());
    }

    @Subscribe
    public void onServerUnregistered(ServerUnregisteredEvent event) {
        String name = event.unregisteredServer().getServerInfo().getName().toLowerCase();
        proxy.getCommandManager().unregister(name);
        registeredCommands.remove(name);
    }

    public void reload() {
        registeredCommands.forEach(name -> proxy.getCommandManager().unregister(name));
        registeredCommands.clear();

        for (RegisteredServer server : proxy.getAllServers()) {
            registerServerCommand(server);
        }
    }

    private void registerServerCommand(RegisteredServer server) {
        String name = server.getServerInfo().getName().toLowerCase();
        CommandMeta commandMeta = proxy.getCommandManager().metaBuilder(name).build();
        proxy.getCommandManager().register(commandMeta, CommandServer.getCommand(server));
        registeredCommands.add(name);
    }
}
