package sr.will.slashserver;

import com.mojang.brigadier.Command;
import com.velocitypowered.api.command.BrigadierCommand;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.server.RegisteredServer;

public class CommandServer {
    public static BrigadierCommand getCommand(RegisteredServer server) {
        String name = server.getServerInfo().getName().toLowerCase();
        return new BrigadierCommand(BrigadierCommand.literalArgumentBuilder(name)
                // single predicate: Brigadier's requires() replaces, it does not chain.
                // Hidden on the target server itself so backend commands with the same name (e.g. /gungame) still work.
                .requires(cs -> cs instanceof Player player
                        && cs.hasPermission("slashserver." + name)
                        && player.getCurrentServer()
                                .map(conn -> !conn.getServer().equals(server))
                                .orElse(true))
                .executes(ctx -> {
                    Player player = (Player) ctx.getSource();
                    player.createConnectionRequest(server).fireAndForget();
                    return Command.SINGLE_SUCCESS;
                })
                .build());
    }
}
