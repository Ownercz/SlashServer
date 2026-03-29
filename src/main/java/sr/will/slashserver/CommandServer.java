package sr.will.slashserver;

import com.mojang.brigadier.Command;
import com.velocitypowered.api.command.BrigadierCommand;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.server.RegisteredServer;

public class CommandServer {
    public static BrigadierCommand getCommand(RegisteredServer server) {
        String name = server.getServerInfo().getName().toLowerCase();
        return new BrigadierCommand(BrigadierCommand.literalArgumentBuilder(name)
                .requires(cs -> cs.hasPermission("slashserver." + name))
                .requires(cs -> cs instanceof Player)
                .executes(ctx -> {
                    Player player = (Player) ctx.getSource();
                    player.createConnectionRequest(server).fireAndForget();
                    return Command.SINGLE_SUCCESS;
                })
                .build());
    }
}
