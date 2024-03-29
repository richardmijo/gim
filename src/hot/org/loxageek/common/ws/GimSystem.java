package org.loxageek.common.ws;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import org.gob.gim.ws.service.BondPrintReport;
import org.gob.gim.ws.service.BondPrintRequest;
import org.gob.gim.ws.service.BondUpdateMail;
import org.gob.gim.ws.service.EmisionResponse;
import org.gob.gim.ws.service.GeneralResponse;
import org.gob.gim.ws.service.InfringementEmisionResponse;
import org.gob.gim.ws.service.MailBondResponse;
import org.gob.gim.ws.service.UserResponse;
import org.gob.loja.gim.ws.dto.EmisionDetail;
import org.gob.loja.gim.ws.dto.InfringementEmisionDetail;
import org.gob.loja.gim.ws.dto.RealEstate;
import org.gob.loja.gim.ws.dto.ServiceRequest;
import org.gob.loja.gim.ws.dto.StatementReport;
import org.gob.loja.gim.ws.dto.Taxpayer;
import org.gob.loja.gim.ws.dto.TaxpayerWP;
import org.gob.loja.gim.ws.exception.AccountIsBlocked;
import org.gob.loja.gim.ws.exception.AccountIsNotActive;
import org.gob.loja.gim.ws.exception.EmissionOrderNotGenerate;
import org.gob.loja.gim.ws.exception.EmissionOrderNotSave;
import org.gob.loja.gim.ws.exception.EntryNotFound;
import org.gob.loja.gim.ws.exception.FiscalPeriodNotFound;
import org.gob.loja.gim.ws.exception.InvalidUser;
import org.gob.loja.gim.ws.exception.RealEstateNotFound;
import org.gob.loja.gim.ws.exception.TaxpayerNonUnique;
import org.gob.loja.gim.ws.exception.TaxpayerNotFound;
import org.gob.loja.gim.ws.exception.TaxpayerNotSaved;
import org.gob.loja.gim.ws.exception.UserNotSaved;
import org.gob.loja.gim.ws.service.GimService;

/**
 * Define los servicios que permiten la consulta y registro de los
 * contribuyentes en el sistema GIM
 * 
 * @author WilmanChamba wilman at loxageek dot com
 * 
 */

@WebService
@HandlerChain(file="handler-chain.xml")
public class GimSystem {

	@EJB
	private GimService gimService;

	@Resource
	WebServiceContext wsContext;

	/**
	 * Permite consultar los contribuyentes con el numero de identificacion
	 * identificationNumber a la que se le ha entregado un username y un
	 * password que se incluyen en el ServiceRequest
	 * 
	 * @param request
	 *            Detalle del peticionario del servicio
	 * @return contribuyente solicitado en el request
	 * @throws TaxpayerNotFound
	 * @throws InvalidUser
	 * @throws AccountIsNotActive
	 * @throws AccountIsBlocked
	 * @throws TaxpayerNonUnique
	 */
	@WebMethod
	public Taxpayer findTaxpayer(ServiceRequest request)
			throws TaxpayerNotFound, InvalidUser, AccountIsNotActive,
			AccountIsBlocked, TaxpayerNonUnique {
		System.out.println("====> FINDING TAXPAYER FOR: "
				+ request.getIdentificationNumber());
		Taxpayer taxpayer = gimService.findTaxpayer(request);
		return taxpayer;
	}

	/**
	 * Permite consultar los contribuyentes con el numero de identificacion
	 * identificationNumber a la que se le ha entregado un username y un
	 * password
	 * 
	 * @param name
	 *            nombre de usuario
	 * @param password
	 * @param identificationNumber
	 *            del contribuyente
	 * @return Map<String, Object> Key=nameAtributos, Object, el valor de los
	 *         atributos;
	 * @throws TaxpayerNotFound
	 * @throws InvalidUser
	 * @throws AccountIsNotActive
	 * @throws AccountIsBlocked
	 * @throws TaxpayerNonUnique
	 */
	@WebMethod
	@XmlJavaTypeAdapter(MapAdapter.class)
	public Map<String, Object> findTaxpayerAsMap(String name, String password,
			String identificationNumber) throws TaxpayerNotFound, InvalidUser,
			AccountIsNotActive, AccountIsBlocked, TaxpayerNonUnique {
		System.out.println("====> FINDING TAXPAYER FOR: "
				+ identificationNumber);
		return gimService.findTaxpayer(name, password, identificationNumber);

	}

	/**
	 * Permite registrar nuevo o actualizar contribuyente desde la plataforma
	 * ppless a la que se le ha entregado un username y un password que se
	 * incluyen en el ServiceRequest
	 * 
	 * @param request
	 *            Detalle del peticionario del servicio
	 * @param taxpayer
	 *            el contribuyente a registrarlo en el GIM
	 * @return contribuyente solicitado en el request
	 * @throws InvalidUser
	 * @throws AccountIsNotActive
	 * @throws AccountIsBlocked
	 * @throws TaxpayerNotSaved
	 */
	@WebMethod
	public Boolean saveTaxpayer(ServiceRequest request, Taxpayer taxpayer)
			throws InvalidUser, AccountIsNotActive, AccountIsBlocked,
			TaxpayerNotSaved {
		System.out.println("SAVE TAXPAYER FOR: "
				+ taxpayer.getIdentificationNumber());
		Boolean ok = gimService.saveTaxpayer(request, taxpayer);
		return ok;
	}

	/**
	 * Permite registrar un nuevo contribuyente o actualizarlo desde un Map a
	 * través de la plataforma ppless a la que se le ha entregado un username y
	 * un password que se incluyen en el ServiceRequest
	 * 
	 * @param name
	 *            nombre de usuario
	 * @param password
	 *            clave de usuario
	 * @param Map
	 *            conjunto de key (nombres de los atributos de un taxPayer)
	 *            value, los valores a fijar
	 * @return contribuyente solicitado en el request
	 * @throws InvalidUser
	 * @throws AccountIsNotActive
	 * @throws AccountIsBlocked
	 * @throws TaxpayerNotSaved
	 */
	@WebMethod
	public Boolean saveTaxpayerFromMap(String name, String password,
			@XmlJavaTypeAdapter(MapAdapter.class) Map<String, Object> map)
			throws InvalidUser, AccountIsNotActive, AccountIsBlocked,
			TaxpayerNotSaved {
		System.out.println("SAVE TAXPAYER FOR: "
				+ map.get("identificationNumber"));
		Boolean ok = gimService.saveTaxpayer(name, password, map);
		return ok;
	}

	/**
	 * Permite consultar el predio con el codigo Catastral al usuario que se le
	 * ha entregado un username y un password
	 * 
	 * @param name
	 *            nombre de usuario
	 * @param password
	 * @param cadastralCode
	 *            codigo Catastral del predio
	 * @return Map<String, Object> Key=nameAtributos, Object, el valor de los
	 *         atributos;
	 * @throws RealEstateNotFound
	 * @throws TaxpayerNotFound
	 * @throws InvalidUser
	 * @throws AccountIsNotActive
	 * @throws AccountIsBlocked
	 * @throws TaxpayerNonUnique
	 */
	@WebMethod
	@XmlJavaTypeAdapter(MapAdapter.class)
	public Map<String, Object> findRealEstateAsMap(String name,
			String password, String cadastralCode) throws RealEstateNotFound,
			TaxpayerNotFound, InvalidUser, AccountIsNotActive,
			AccountIsBlocked, TaxpayerNonUnique {
		System.out.println("====> FINDING REALESTATE FOR: " + cadastralCode);
		return gimService.findRealEstate(name, password, cadastralCode);
	}

	/**
	 * 
	 * @param name
	 * @param password
	 * @param identificationNumber
	 * @param entryCode
	 * @return
	 * @throws TaxpayerNotFound
	 * @throws TaxpayerNonUnique
	 * @throws EntryNotFound
	 * @throws FiscalPeriodNotFound
	 * @throws EmissionOrderNotGenerate
	 * @throws EmissionOrderNotSave
	 * @throws InvalidUser
	 * @throws AccountIsNotActive
	 * @throws AccountIsBlocked
	 */
	@WebMethod
	public Boolean generateEmissionOrder(String name, String password,
			String identificationNumber, String accountCode, String pplessuser)
			throws TaxpayerNotFound, TaxpayerNonUnique, EntryNotFound,
			FiscalPeriodNotFound, EmissionOrderNotGenerate,
			EmissionOrderNotSave, InvalidUser, AccountIsNotActive,
			AccountIsBlocked {
		System.out.println("====> GENERATE EmissionOrder FOR: " + accountCode);
		return gimService.generateEmissionOrder(name, password,
				identificationNumber, accountCode, pplessuser);
	}

	/**
	 * @author rfarmijosm Permite al usuario emitir multas en este caso solo es
	 *         para fotomultas
	 * @param name
	 * @param password
	 * @param identificationNumber
	 * @param accountCode
	 * @param emisionDetail
	 * @return
	 * @throws TaxpayerNotFound
	 * @throws TaxpayerNonUnique
	 * @throws EntryNotFound
	 * @throws FiscalPeriodNotFound
	 * @throws EmissionOrderNotGenerate
	 * @throws EmissionOrderNotSave
	 * @throws InvalidUser
	 * @throws AccountIsNotActive
	 * @throws AccountIsBlocked
	 */
	@WebMethod
	public EmisionResponse generateEmissionOrderANT(String name,
			String password, String identificationNumber, String accountCode,
			EmisionDetail emisionDetail) throws TaxpayerNotFound,
			TaxpayerNonUnique, EntryNotFound, FiscalPeriodNotFound,
			EmissionOrderNotGenerate, EmissionOrderNotSave, InvalidUser,
			AccountIsNotActive, AccountIsBlocked {
		
		EmisionResponse response = gimService.generateEmissionOrder(name,
				password, identificationNumber, accountCode, emisionDetail);
		return response;
	}
	
	/**
	 * 2016-01-30
	 * 
	 * @author rfarmijosm Metodo que facilita consultar al emisor la obligaiones
	 *         por estado y fecha
	 * @param request
	 * @param startDate
	 * @param endDate
	 * @param reportType
	 * @param entryId
	 * @return
	 * @throws TaxpayerNotFound
	 * @throws TaxpayerNonUnique
	 * @throws EntryNotFound
	 * @throws FiscalPeriodNotFound
	 * @throws EmissionOrderNotGenerate
	 * @throws EmissionOrderNotSave
	 * @throws InvalidUser
	 * @throws AccountIsNotActive
	 * @throws AccountIsBlocked
	 */
	@WebMethod
	public StatementReport buildReport(ServiceRequest request, Date startDate,
			Date endDate, String reportType, Long entryId)
			throws TaxpayerNotFound, TaxpayerNonUnique, EntryNotFound,
			FiscalPeriodNotFound, EmissionOrderNotGenerate,
			EmissionOrderNotSave, InvalidUser, AccountIsNotActive,
			AccountIsBlocked {
		StatementReport sr = gimService.buildReport(request, startDate,
				endDate, reportType, entryId);
		return sr;
	}

	@WebMethod
	public UserResponse createUser(ServiceRequest request, String username,
			String password) throws InvalidUser, AccountIsNotActive,
			AccountIsBlocked, UserNotSaved {
		UserResponse data = gimService.saveUser(request, username, password);
		return data;
	}

	@WebMethod
	public UserResponse login(ServiceRequest request, String username,
			String password) throws InvalidUser, AccountIsNotActive,
			AccountIsBlocked, UserNotSaved {
		UserResponse data = gimService.login(username, password);
		return data;
	}

	// @WebMethod
	// public Boolean saveTaxpayer(ServiceRequest request, Taxpayer taxpayer)
	// throws InvalidUser, AccountIsNotActive, AccountIsBlocked,
	// TaxpayerNotSaved{
	// System.out.println("SAVE TAXPAYER FOR: "+taxpayer.getIdentificationNumber());
	// Boolean ok = gimService.saveTaxpayer(request, taxpayer);
	// InvalidateSession();
	// return ok;
	// }

	public List<RealEstate> findProperties(ServiceRequest request) {
		List<RealEstate> properties = gimService.findProperties(request);
		return properties;
	}

	@WebMethod
	public Boolean generateEmissionOrderQuantity(String name, String password,
			String identificationNumber, String accountCode, String pplessuser,
			Integer quantity) throws TaxpayerNotFound, TaxpayerNonUnique,
			EntryNotFound, FiscalPeriodNotFound, EmissionOrderNotGenerate,
			EmissionOrderNotSave, InvalidUser, AccountIsNotActive,
			AccountIsBlocked {
		System.out.println("====> GENERATE EmissionOrder FOR: " + accountCode);
		return gimService.generateEmissionOrder(name, password,
				identificationNumber, accountCode, pplessuser, quantity);
	}

	@WebMethod
	public Boolean checkForDebts(ServiceRequest request)
			throws TaxpayerNotFound, InvalidUser, AccountIsNotActive,
			AccountIsBlocked, TaxpayerNonUnique {
		return gimService.searchDueDebts(request);
	}
	
	@WebMethod
	public InfringementEmisionResponse generateANTEmissionInfringement(String name, String password,
			String identificationNumber, String accountCode, InfringementEmisionDetail emisionDetail) {
		try {
			return gimService.generateANTEmissionInfringement(name, password, identificationNumber, accountCode, emisionDetail);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@WebMethod
	public InfringementEmisionResponse confirmANTEmissionInfringement(String name, String password,
			InfringementEmisionDetail emisionDetail) {
		try {
			return gimService.confirmANTEmissionInfringement(name, password, emisionDetail);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@WebMethod
	public GeneralResponse findBondsExternalPayment(ServiceRequest request) {
		return gimService.bondsByExternalPayment(request);
	}
	
	@WebMethod
	public GeneralResponse updateBondPrinterNumber(ServiceRequest request, BondPrintRequest bonds) {
		return gimService.updateBondPrinterNumber(request, bonds);
	}
	
	
	@WebMethod
	public MailBondResponse bondsToSendMail(ServiceRequest request, Integer max) {
		return gimService.bondsTosendMail(request, max);
	}

	
	@WebMethod
	public void updateMailInformation(ServiceRequest request, List<BondUpdateMail> listUpdate) {
		 gimService.updateBondPrinterNumber(request, listUpdate);
	}
	
	/**
	 * Para envio desde la pagina web GAD LOJA
	 * @param request
	 * @return
	 * @throws TaxpayerNotFound
	 * @throws InvalidUser
	 * @throws AccountIsNotActive
	 * @throws AccountIsBlocked
	 * @throws TaxpayerNonUnique
	 */
	@WebMethod
	public TaxpayerWP findTaxpayerWP(ServiceRequest request)
			throws TaxpayerNotFound, InvalidUser, AccountIsNotActive,
			AccountIsBlocked, TaxpayerNonUnique {
		System.out.println("====> FINDING TAXPAYERWP FOR: "
				+ request.getIdentificationNumber());
		TaxpayerWP taxpayer = gimService.findTaxpayerWP(request);
		return taxpayer;
	}
}
