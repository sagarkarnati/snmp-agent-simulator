package com.mindtree.agentsim.repository;

import com.mindtree.agentsim.mibble.MibEntry;

public class RepositoryUtil 
{

	private static RepositoryUtil repositoryUtil;
	private LRUCache<String,MibEntry> lruCache;

	private RepositoryUtil() {

		lruCache = new LRUCache<String, MibEntry>(2500);
	}

	public static synchronized RepositoryUtil getInstance()
	{
		if(null == repositoryUtil)
			repositoryUtil = new RepositoryUtil();;

			return repositoryUtil;
	}

	public synchronized String getOIDValue(String oid)
	{
		return "";
	}

	public MibEntry getMibEntry(String oid)
	{
		MibEntry entry = lruCache.get(oid);
		if(null == entry)
		{
			//entry = getFromRepository(oid);
			lruCache.put(oid, entry);
		}
		return entry;
	}

	//	public MibEntry getFromRepository(String oid)
	//	{
	//        MibEntryDao mibEntryDao = DaoRegistry.getEventDao();
	//        mibEntryDao.saveOrUpdate(mibentry);
	//	}
}