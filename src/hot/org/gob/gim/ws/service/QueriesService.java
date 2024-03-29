/**
 * 
 */
package org.gob.gim.ws.service;

import java.text.ParseException;
import java.util.List;

import javax.ejb.Local;

import org.gob.gim.revenue.exception.EntryDefinitionNotFoundException;
import org.gob.loja.gim.ws.dto.digitalReceipts.BondShortDTO;
import org.gob.loja.gim.ws.dto.digitalReceipts.DateFormatException;
import org.gob.loja.gim.ws.dto.digitalReceipts.request.ExternalPaidsRequest;
import org.gob.loja.gim.ws.dto.digitalReceipts.response.BondResponse;
import org.gob.loja.gim.ws.dto.queries.DebtsDTO;
import org.gob.loja.gim.ws.dto.queries.EntryDTO;
import org.gob.loja.gim.ws.dto.queries.LocalDTO;
import org.gob.loja.gim.ws.dto.queries.OperatingPermitDTO;
import org.gob.loja.gim.ws.dto.queries.response.BondDTO;
import org.gob.loja.gim.ws.dto.queries.response.ResidentDTO;

/**
 * @author René
 *
 */
@Local
public interface QueriesService {
	public String LOCAL_NAME = "/gim/QueriesService/local";
	
	BondDTO findBondById(Long bondId) throws EntryDefinitionNotFoundException;
	List<OperatingPermitDTO> findOperatingPermits(String ruc);
	List<LocalDTO> findLocals(String cedRuc);
	EntryDTO findEntry(String code);
	DebtsDTO findDebts(String identification);
	
	List<BondShortDTO> getExternalPayments(ExternalPaidsRequest criteria) throws ParseException, DateFormatException;
	
	BondResponse getBondDto(Long municipalBondId);
	
	ResidentDTO getResidentByIdentification(String identification);
	
	Boolean updateBondPrintNumber(Long bondId);
	
}
