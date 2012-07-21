package com.mindtree.agentsim.common;

import java.util.ArrayList;
import java.util.List;

public class BlockingQueue {

	private List queue;
	private int  limit = 10;

	public BlockingQueue(int limit){
		this.limit = limit;
		queue = new ArrayList(limit);
		//System.out.println("Limit is ::"+limit);
	}

	public synchronized void enqueue(Object item) throws InterruptedException  
	{
		while(this.queue.size() == this.limit) {
			wait();
		}
		if(this.queue.size() == 0) {
			notifyAll();
		}
		this.queue.add(item);
	}


	public synchronized Object dequeue() throws InterruptedException
	{
		while(this.queue.size() == 0)
		{
			wait();
		}
		if(this.queue.size() == this.limit){
			notifyAll();
		}
		return this.queue.remove(0);
	}
	
	public synchronized int getQSize() throws Exception
	{
		return queue.size();
	}	
}
