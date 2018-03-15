package com.linkage.bss.crm.ws.listener;

import java.util.List;
import java.util.Map;

import com.al.commons.listener.ListenerThread;
import com.linkage.bss.crm.intf.smo.IntfSMO;

public class CsipPCRFListenerThread extends ListenerThread<Map> {

	private IntfSMO intfSMO;

	public CsipPCRFListenerThread(int pthreadIndex, int pareaId, String threadName, long maxSleepTime, int processNumber) {
		super(pthreadIndex, pareaId, threadName, maxSleepTime, processNumber);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean dealErrorMessage(Map obj, Integer areaId, Throwable e) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean dealMessage(Map obj, Integer areaId) {

		return intfSMO.getInfoByCRM(obj);
	}

	@Override
	public boolean initThreadSMO(Object obj) {
		intfSMO = (IntfSMO) obj;
		return true;
	}

	@Override
	public List<Map> readWaitMessages(List<Map> dealingMegs, Integer inProcessNumber, Integer areaId) {
		List<Map> msgList = intfSMO.csipPCRFListByst(this.getThreadIndex());
		return msgList;
	}

}
