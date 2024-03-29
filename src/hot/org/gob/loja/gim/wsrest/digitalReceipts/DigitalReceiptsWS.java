/**
 * 
 */
package org.gob.loja.gim.wsrest.digitalReceipts;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.gob.gim.common.GimUtils;
import org.gob.gim.common.ServiceLocator;
import org.gob.gim.ws.service.QueriesService;
import org.gob.loja.gim.ws.dto.digitalReceipts.BondShortDTO;
import org.gob.loja.gim.ws.dto.digitalReceipts.DateFormatException;
import org.gob.loja.gim.ws.dto.digitalReceipts.request.ExternalPaidsRequest;
import org.gob.loja.gim.ws.dto.digitalReceipts.request.PDFBondRequest;
import org.gob.loja.gim.ws.dto.digitalReceipts.request.UpdateBondPrintNumberRequest;
import org.gob.loja.gim.ws.dto.digitalReceipts.response.BondResponse;
import org.gob.loja.gim.ws.dto.digitalReceipts.response.ExternalPaidsResponse;
import org.gob.loja.gim.ws.dto.digitalReceipts.response.UpdateBondPrintNumberResponse;
import org.gob.loja.gim.ws.dto.queries.response.ResidentDTO;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Transactional;

/**
 * @author René
 *
 */

@Name("DigitalReceiptsWS")
@Path("/digitalReceipts")
@Transactional
public class DigitalReceiptsWS {

	@In(create = true, required = false, value = "queriesService")
	private QueriesService queriesService;

	@POST
	@Path("/externalPaids")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getExternalPaids(@Valid ExternalPaidsRequest request) {
		
		ExternalPaidsResponse resp = new ExternalPaidsResponse();
		
		try {
			// System.out.println(request);
			//System.out.println(request.getFromDate());
			//System.out.println(request.getToDate());

			List<String> errorsValidation = GimUtils.validateRequest(request);
			if (errorsValidation.size() > 0) {
				resp.setMessage("Error en validaciones de request");
				resp.setErrors(errorsValidation);
				resp.setBonds(null);
				return Response.ok(resp)
						.header("Access-Control-Allow-Origin", "*")
						.header("Content-Language", "es-EC").build();
			}

			if (queriesService == null) {
				queriesService = ServiceLocator.getInstance().findResource(
						queriesService.LOCAL_NAME);
			}

			
			ResidentDTO res = queriesService.getResidentByIdentification(request.getIdentification());
			resp.setResident(res);
			
			if(res == null) {
				resp.setMessage("No existe contribuyente con la identificación "+request.getIdentification());
				resp.setBonds(null);
			}else {
				List<BondShortDTO> bonds = queriesService
						.getExternalPayments(request);
				resp.setBonds(bonds);
				resp.setMessage("OK");
			}

			return Response.ok(resp).header("Access-Control-Allow-Origin", "*")
					.header("Content-Language", "es-EC").build();
		}
		catch (Exception e) {
			if(e instanceof DateFormatException){
				List<String> errors = new ArrayList<String>();
				errors.add("Formato de fechas incorrecto. Formato permitido yyyy-MM-dd");
				resp.setErrors(errors);
				resp.setBonds(null);
				resp.setResident(null);
				return Response.ok(resp).header("Access-Control-Allow-Origin", "*")
						.header("Content-Language", "es-EC").build();
			}
			e.printStackTrace();
			return Response.serverError()
					.header("Access-Control-Allow-Origin", "*")
					.header("Content-Language", "es-EC").build();
		}
	}

	@POST
	@Path("/getBondData")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getBondData(@Valid PDFBondRequest request) {
		try {
			// System.out.println(request);

			BondResponse resp = new BondResponse();


			List<String> errorsValidation = GimUtils.validateRequest(request);
			if (errorsValidation.size() > 0) {
				resp.setMessage("Error en validaciones de request");
				resp.setErrors(errorsValidation);
				return Response.ok(resp)
						.header("Access-Control-Allow-Origin", "*")
						.header("Content-Language", "es-EC").build();
			}

			if (queriesService == null) {
				queriesService = ServiceLocator.getInstance().findResource(
						queriesService.LOCAL_NAME);
			}

			BondResponse respAux = queriesService.getBondDto(request
					.getBondId());
			resp.setBond(respAux.getBond());
			resp.setDeposits(respAux.getDeposits());

			resp.setInstitution(respAux.getInstitution());

			resp.setBranchMain(respAux.isBranchMain());
			resp.setBranchOfficeAddress(respAux.getBranchOfficeAddress());
			resp.setBranchOfficeName(respAux.getBranchOfficeName());

			resp.setAdjunctDetails(respAux.getAdjunctDetails());

			resp.setMessage("OK");

			return Response.ok(resp).header("Access-Control-Allow-Origin", "*")
					.header("Content-Language", "es-EC").build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError()
					.header("Access-Control-Allow-Origin", "*")
					.header("Content-Language", "es-EC").build();
		}
	}
	
	@POST
	@Path("/updateBondPrintNumber")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateBondPrintNumber(@Valid UpdateBondPrintNumberRequest request) {
		try {

			UpdateBondPrintNumberResponse resp = new UpdateBondPrintNumberResponse();

			List<String> errorsValidation = GimUtils.validateRequest(request);
			if (errorsValidation.size() > 0) {
				resp.setMessage("Error en validaciones de request");
				resp.setErrors(errorsValidation);
				return Response.ok(resp)
						.header("Access-Control-Allow-Origin", "*")
						.header("Content-Language", "es-EC").build();
			}

			if (queriesService == null) {
				queriesService = ServiceLocator.getInstance().findResource(
						queriesService.LOCAL_NAME);
			}

			Boolean result = queriesService.updateBondPrintNumber(request
					.getBondId());
			resp.setOk(result);
			resp.setMessage("OK");

			return Response.ok(resp).header("Access-Control-Allow-Origin", "*")
					.header("Content-Language", "es-EC").build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError()
					.header("Access-Control-Allow-Origin", "*")
					.header("Content-Language", "es-EC").build();
		}
	}
}
