package frc.team5431.titan.core.robot;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ParallelCommandGroup<T extends Robot<T>> extends Command<T> {
    private final List<CommandQueue<T>> queues = new ArrayList<>();

    private boolean init = false;

    public ParallelCommandGroup(){
        name = "ParallelCommandGroup";
        properties = "Runs a group of commands in parallel";
    }

    public void addCommand(final Command<T> command){
        final CommandQueue<T> newQueue = new CommandQueue<T>();
        newQueue.add(command);
        addQueue(newQueue);
    }

    public void addQueue(final CommandQueue<T> queue){
        if(init){
            throw new IllegalArgumentException("Can't add new commands to a ParallelCommandGroup after init() was called");
        }
        queues.add(queue);
    }

    public void addQueue(final List<Command<T>> list){
        addQueue(new CommandQueue<T>(list));
    }

    public void init(final T robot){
        for(final CommandQueue<T> queue : queues){
            queue.init(robot);
        }
        init = true;
    }

    public CommandResult update(final T robot){
        final Iterator<CommandQueue<T>> queueIter = queues.iterator();
        while(queueIter.hasNext()){
            final CommandQueue<T> queue = queueIter.next();
            if(!queue.update(robot)){
                queueIter.remove();
            }
        }

        if(queues.isEmpty()){
            return CommandResult.COMPLETE;
        }

        return CommandResult.IN_PROGRESS;
    }

    public void done(final T robot){
        for(final CommandQueue<T> queue : queues){
            queue.done(robot);
        }
    }

    public boolean isEmpty(){
        return queues.isEmpty();
    }
}
