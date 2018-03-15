package com.linkage.bss.crm.ws.listener;

import java.util.List;
import java.util.Map;

import com.al.commons.listener.ListenerThread;
import com.linkage.bss.crm.intf.smo.IntfSMO;

public class Intf2BillingMsgListenerThread extends ListenerThread<Map> {

	public Intf2BillingMsgListenerThread(int pthreadIndex, int pareaId, String threadName, long maxSleepTime,
			int processNumber) {
		super(pthreadIndex, pareaId, threadName, maxSleepTime, processNumber);
		// TODO Auto-generated constructor stub
	}

	private IntfSMO intfSMO;

	@Override
	public boolean dealErrorMessage(Map obj, Integer areaId, Throwable e) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean dealMessage(Map obj, Integer areaId) {

		return intfSMO.processMsg(obj);
	}

	@Override
	public boolean initThreadSMO(Object obj) {
		intfSMO = (IntfSMO) obj;
		return true;
	}

	@Override
	public List<Map> readWaitMessages(List<Map> dealingMegs, Integer inProcessNumber, Integer areaId) {

		return intfSMO.Intf2BillingMsgListBySt(this.getThreadIndex(), inProcessNumber);
	}

}
