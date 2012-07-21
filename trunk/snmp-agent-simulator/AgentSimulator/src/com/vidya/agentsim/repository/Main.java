package com.mindtree.agentsim.repository;


import com.mindtree.agentsim.mibble.MibEntry;

public class Main {
	
	
	public static void main(String[] args) {
		HibernateUtil.setup("create table EVENTS ( uid int, name VARCHAR, start_Date date, duration int);");
		
		// hibernate code start

        RepositoryDao eventDao = null;
        MibEntry mibentry = new MibEntry();
        mibentry.setEntryName("Name");

        eventDao = DaoRegistry.getEventDao();
        eventDao.saveOrUpdate(mibentry);

        
        HibernateUtil.checkData("select uid, name from events");        

        
		// hibernate code end
	}
	
}