package com.mindtree.agentsim.repository;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.mindtree.agentsim.common.AgentSimConstants;
import com.mindtree.agentsim.mibble.MibEntry;
import com.mindtree.agentsim.parser.Device;

/**
 * @author VidyaSagar
 *
 */
public class RepositoryDao extends AbstractSpringDao implements RepositoryDaoIntf
{	
	private LRUCache lruCache;

	public RepositoryDao(LRUCache lruCache) {

		this.lruCache = lruCache;
	}

	public MibEntry find(Long id) {
		return (MibEntry) super.find(MibEntry.class, id);
	}

	public void saveOrUpdate(MibEntry event) {
		super.saveOrUpdate(event);
	}

	public void saveOrUpdate(List mibEntry) {
		super.saveOrUpdate(mibEntry);
	}

	public void delete(MibEntry event) {
		super.delete(event);
	}

	public List findAll() {
		return super.findAll(MibEntry.class);
	}

	public MibEntry getMibEntry(String oid) throws Exception 
	{
		MibEntry entry = null;
		if(null == lruCache.get(oid))
		{
			DetachedCriteria criteria = DetachedCriteria.forClass(MibEntry.class);
			criteria.add(Restrictions.eq("oid", oid));

			List list = getHibernateTemplate().findByCriteria(criteria);
			if ((null != list) && (list.size() > 0)) 
			{
				entry = ((MibEntry) list.get(0));
				lruCache.put(entry.getOid(), entry);
			}
		}else
		{
			entry = (MibEntry)lruCache.get(oid);
		}

		return entry;
	}

	public List getAllMibEntries() throws Exception {
		return super.findAll(MibEntry.class);
	}

	//getting next MibEntry of the given OID and DeviceIP
	public MibEntry getNextMibEntry(String deviceIp,String oid) throws Exception
	{
		MibEntry nextMibEntry = null;
		Device device = getDevice(deviceIp);
		List mibentrySet = device.getMibEntries();
		Iterator itr = mibentrySet.iterator(); 
		while(itr.hasNext()) 
		{
			Object element = itr.next(); 
			MibEntry entry = (MibEntry)element;
			if(entry.getOid().equals(oid))
			{
				nextMibEntry = (MibEntry)itr.next();
				String access = nextMibEntry.getAccess();
				while(!((access.equals(AgentSimConstants.READ_ONLY)) || (access.equals(AgentSimConstants.READ_WRITE)) || (access.equals(AgentSimConstants.READ_CREATE))))
				{	
					nextMibEntry = (MibEntry)itr.next();
					access = nextMibEntry.getAccess();
				}
				if(null != nextMibEntry)
					lruCache.put(nextMibEntry.getOid(), nextMibEntry);
				
				return nextMibEntry;
			}
		}
		throw new Exception("Next MibEntry not found for the device ::"+deviceIp+" MibEntry OID ::"+oid);
	}

	public void saveOrUpdate(Device device) {

		super.saveOrUpdate(device);
	}

	public List getAllDevices()
	{
		return super.findAll(Device.class);
	}

	public Device getDevice(String ipAddress) throws Exception
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(Device.class);
		criteria.add(Restrictions.eq("deviceIPAddress", ipAddress));
		MibEntry nextMibEntry = null;

		List list = getHibernateTemplate().findByCriteria(criteria);
		if((null != list) && (list.size() > 0))
		{
			Object obj = list.get(0);
			if(obj instanceof Device)
				return (Device)obj;
			else
				throw new Exception("Fetched a different object which is not useful");
		}
		throw new Exception("Device for the IPAddress ::"+ipAddress+" cannot be found");
	}
	
//	public MibEntry getNextMibEntry(String deviceIp,String oid) throws Exception
//	{
//		Session currSession = getHibernateTemplate().getSessionFactory().openSession();
//		Query query = currSession.createSQLQuery("select m.id from devices d,mibentry m where d.deviceIPAddress = :deviceIPAddress and m.oid = :oid").setParameter("deviceIPAddress", deviceIp).setParameter("oid", oid);
//		
//		List result = query.list();
//		if((null != result) && (result.size() > 0))
//		{
//			int id = Integer.parseInt(result.get(0).toString());
//			System.out.println("Current ID ::"+id);
//			
//			Query query2 = currSession.createSQLQuery("select * from devices d,mibentry m where (d.deviceIPAddress = :deviceIPAddress) and ((m.access = 'read-only') or (m.access = 'read-write') or (m.access = 'read-create')) and (m.id > :id) limit 1").addEntity(MibEntry.class).setParameter("deviceIPAddress", deviceIp).setParameter("id",id);
//			List result2 = query2.list();
//			if((null != result) && (result.size() > 0))
//			{
//				return (MibEntry)result2.get(0);
//			}
//		}
//		return null;
//	}
}
