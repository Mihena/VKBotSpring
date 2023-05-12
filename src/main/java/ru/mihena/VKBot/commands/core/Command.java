package ru.mihena.VKBot.commands.core;

import ru.mihena.VKBot.utils.Answer;

public interface Command {
    Answer execute();
    String getName();
    String getDescription();
    String[] getAliases();
    RoleName getAccessLevel();
}