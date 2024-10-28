package net.sonicrushxii.chaos_emerald.scheduler;

import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Scheduler {
    private static final List<ScheduledTask> tasks = new ArrayList<>();

    public static ScheduledTask scheduleTask(Runnable task, int delayInTicks) {
        ScheduledTask scheduledTask = new ScheduledTask(task, delayInTicks);
        tasks.add(scheduledTask);
        return scheduledTask;
    }

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event)
    {
        if(event.phase == TickEvent.Phase.END)
            return;

        Iterator<ScheduledTask> iterator = tasks.iterator();
        while (iterator.hasNext()) {
            ScheduledTask scheduledTask = iterator.next();
            scheduledTask.tick();
            if (scheduledTask.isReady()) {
                scheduledTask.run();
                iterator.remove();
            } else if (scheduledTask.isCancelled()) {
                iterator.remove();
            }
        }
    }
}
