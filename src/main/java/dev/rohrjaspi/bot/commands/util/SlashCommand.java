package dev.rohrjaspi.bot.commands.util;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.List;

public abstract class SlashCommand {

    public abstract String getName();

    public abstract String getDescription();

    public abstract List<OptionData> getOptions();

    public abstract void execute(SlashCommandInteractionEvent event);

}
