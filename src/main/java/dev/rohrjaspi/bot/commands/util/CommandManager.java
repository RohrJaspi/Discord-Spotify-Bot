package dev.rohrjaspi.bot.commands.util;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CommandManager extends ListenerAdapter {

    /**
     * A list containing all registered SlashCommand instances.
     */
    private final List<SlashCommand> commands = new ArrayList<>();


    @Override
    public void onReady(@NotNull ReadyEvent event) {
        // Iterate through all guilds the bot is connected to
        for (Guild guild : event.getJDA().getGuilds()) {
            // Register each command in the list
            for (SlashCommand command : commands) {
                if (command.getOptions() == null) {
                    // Register command without options
                    guild.upsertCommand(command.getName(), command.getDescription()).queue();
                } else {
                    // Register command with options
                    guild.upsertCommand(command.getName(), command.getDescription())
                            .addOptions(command.getOptions())
                            .queue();
                }
            }
        }
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        // Iterate through all registered commands
        for (SlashCommand command : commands) {
            // Check if the command name matches the event name
            if (command.getName().equals(event.getName())) {
                // Execute the matched command
                command.execute(event);
                return;
            }
        }
    }

    /**
     * Add a SlashCommand to the command list.
     * Commands added here will be registered on bot startup.
     *
     * @param command The {@link SlashCommand} instance to be added.
     */
    public void add(SlashCommand command) {
        commands.add(command);
    }
}
