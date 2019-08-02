package frc.team5431.titan.core.components.robot;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TitanParallelCommandGroup<T extends TitanRobot<T>> extends TitanCommand<T> {
    private final List<TitanCommandQueue<T>> queues = new ArrayList<>();

    private boolean init = false;

    public TitanParallelCommandGroup(){
        name = "ParallelCommandGroup";
        properties = "Runs a group of commands in parallel";
    }

    public void addCommand(final TitanCommand<T> command){
        final TitanCommandQueue<T> newQueue = new TitanCommandQueue<T>();
        newQueue.add(command);
        addQueue(newQueue);
    }

    public void addQueue(final TitanCommandQueue<T> queue){
        if(init){
            throw new IllegalArgumentException("Can't add new commands to a ParallelCommandGroup after init() was called");
        }
        queues.add(queue);
    }

    public void addQueue(final List<TitanCommand<T>> list){
        addQueue(new TitanCommandQueue<T>(list));
    }

    public void init(final T robot){
        for(final TitanCommandQueue<T> queue : queues){
            queue.init(robot);
        }
        init = true;
    }

    public CommandResult update(final T robot){
        final Iterator<TitanCommandQueue<T>> queueIter = queues.iterator();
        while(queueIter.hasNext()){
            final TitanCommandQueue<T> queue = queueIter.next();
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
        for(final TitanCommandQueue<T> queue : queues){
            queue.done(robot);
        }
    }

    public boolean isEmpty(){
        return queues.isEmpty();
    }
}
