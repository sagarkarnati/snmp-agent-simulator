package com.mindtree.agentsim.mina.server;

import java.util.concurrent.ExecutorService;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.mindtree.agentsim.common.BlockingQueue;
import com.mindtree.agentsim.common.RequestObj;

public class ServerHandler extends IoHandlerAdapter
{
	private static Logger logger = Logger.getLogger(ServerHandler.class);
	private BlockingQueue requestQ_;
	private ExecutorService service_;
	
	public ServerHandler(BlockingQueue requestQ,ExecutorService service,int reqDispatcherCnt)
	{
		this.requestQ_ = requestQ;
		this.service_ = service;
		
		for(int i=0; i< reqDispatcherCnt; i++)
		{
			service_.submit(new RequestDispatcherThread());
		}
	}

	public void exceptionCaught( IoSession session, Throwable cause ) throws Exception
	{
		logger.error("Exception catched in Server Handler",cause);
	}

	public void messageReceived( IoSession session, Object message ) throws Exception
	{
		logger.debug("Message Received at server handler");
		RequestObj obj = new RequestObj();
		obj.setSession(session);
		obj.setSnmpObj(message);
		
		requestQ_.enqueue(obj);
	}

	public void sessionIdle( IoSession session, IdleStatus status ) throws Exception
	{
		logger.debug( "IDLE " + session.getIdleCount( status ));
	}

	class RequestDispatcherThread implements Runnable
	{
		public RequestDispatcherThread() 
		{
			logger.debug("RequestDispatcherThread is created");
		}

		public void run() {

			while(true)
			{
				try
				{
					Object qHead = requestQ_.dequeue();
					if(null != qHead)
						service_.submit(new RequestProcessWorker(qHead));
					else
						logger.debug("Object returned by Q is null");
				}catch(Exception e)
				{
					logger.error(e.getMessage(), e);
				}
			}
		}
	}
}
