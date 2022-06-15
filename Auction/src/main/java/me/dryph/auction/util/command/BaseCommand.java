package me.tulio.auction.util.command;

public abstract class BaseCommand {

    public BaseCommand() {
        CommandFramework.getInstance().registerCommands(this, null);
    }

    public abstract void onCommand(CommandArgs command);
}
