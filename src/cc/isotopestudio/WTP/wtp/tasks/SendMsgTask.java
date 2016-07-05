package cc.isotopestudio.WTP.wtp.tasks;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

class SendMsgTask extends BukkitRunnable {
    private final Player player;
    private final String msg;

    public SendMsgTask(Player player, String msg) {
        this.player = player;
        this.msg = msg;
    }

    @Override
    public void run() {
        player.sendMessage(msg);
    }

}
