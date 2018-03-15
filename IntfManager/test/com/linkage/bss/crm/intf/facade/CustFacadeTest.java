package com.linkage.bss.crm.intf.facade;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.linkage.bss.BaseTest;
import com.linkage.bss.crm.model.Party;

public class CustFacadeTest extends BaseTest {

//	@Autowired
//	@Qualifier("intfManager.custFacade")
//	
//	private CustFacade custFacade;
//	
//	@Autowired
//	@Qualifier("intfManager.srFacade")
//	private SRFacade srFacade;
//
//	private String areaId = "45101";
//	
//	@Test
//	public void testSaveCustInfo() {
//		Party party = new Party();
//		String areaId = "";
//		String channelId = "";
//		String staffId = "";
//		custFacade.saveCustInfo(party, areaId, channelId, staffId);
//	}
//	
//	@Test
//	public void testGetPartyByAccessNumber() {
//		Party party = custFacade.getPartyByAccessNumber("13351204432");
//		Assert.assertNotNull(party);
//	}
//	
//	@Test
//	public void testGetPartyByIDCard() {
//		Party party = custFacade.getPartyByIDCard("111111111111111");
//		Assert.assertNotNull(party);
//	}
//	
//	@Test
//	public void testGetPartyByOtherCard() {
//		Party party = custFacade.getPartyByOtherCard("321321");
//		Assert.assertNotNull(party);
//	}
//	
//	@Test
//	public void testGetPartyByName() {
//		Party party = custFacade.getPartyByName("¡ı÷æ«’");
//		Assert.assertNotNull(party);
//	}
//	
//	@Test
//	public void testGetPartyById() {
//		Party party = custFacade.getPartyById("513005062853");
//		Assert.assertNotNull(party);
//	}
//	
//	@Test
//	public void testGetPartyByAcctCd() {
//		Party party = custFacade.getPartyByAcctCd("510006603794");
//		Assert.assertNotNull(party);
//	}
//	
//	@Test
//	public void testGetPartyByTerminalCode() {
//		Party party = custFacade.getPartyByTerminalCode("8986031170451048977");
//		Assert.assertNotNull(party);
//	}
//	
//	@Test
//	public String testgetMaterialByCode() throws Exception {
//		String msg = srFacade.getMaterialByCode("8986031170451048977","-1");
//		return msg;
//	}

}
