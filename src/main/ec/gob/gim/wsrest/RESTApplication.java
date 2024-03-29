/**
 * 
 */
package ec.gob.gim.wsrest;

import java.util.HashSet;
import java.util.Set;

import org.gob.loja.gim.wsrest.Hygiene.HygieneWS;
import org.gob.loja.gim.wsrest.cadaster.CadasterWS;
import org.gob.loja.gim.wsrest.digitalReceipts.DigitalReceiptsWS;
import org.gob.loja.gim.wsrest.income.preemission.PreemissionWS;
import org.gob.loja.gim.wsrest.queries.QueriesWS;



/**
 * @author Rene
 *
 */
public class RESTApplication extends javax.ws.rs.core.Application{
	
	private Set<Object> singletons = new HashSet<Object>();
	
	public RESTApplication () {
	    singletons.add(new CadasterWS());
	    singletons.add(new HygieneWS());
	    singletons.add(new PreemissionWS());
	    singletons.add(new QueriesWS());
	    singletons.add(new DigitalReceiptsWS());
	    //singletons.add(new EmissionWS());
	}
	
	@Override
	public Set<Object> getSingletons() {
	    return singletons;
	}

}
