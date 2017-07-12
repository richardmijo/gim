package ec.gob.gim.finances.model.DTO;

import java.util.Date;

import org.gob.gim.common.NativeQueryResultColumn;
import org.gob.gim.common.NativeQueryResultEntity;

@NativeQueryResultEntity
public class WriteOffRequestDTO {
	
	@NativeQueryResultColumn(index = 0)
	private Long id;
	
	@NativeQueryResultColumn(index = 1)
	private Date date;
	
	@NativeQueryResultColumn(index = 2)
	private String resident_name;
	
	@NativeQueryResultColumn(index = 3)
	private String identificationNumber;
	
	@NativeQueryResultColumn(index = 4)
	private String address;
	
	@NativeQueryResultColumn(index = 5)
	private Integer serviceNumber;
	
	@NativeQueryResultColumn(index = 6)
	private String serial;
	
	@NativeQueryResultColumn(index = 7)
	private String _type;
	
	@NativeQueryResultColumn(index = 8)
	private String number_code;
	
	@NativeQueryResultColumn(index = 9)
	private Integer _year;
	
	@NativeQueryResultColumn(index = 10)
	private String request_number;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getResident_name() {
		return resident_name;
	}

	public void setResident_name(String resident_name) {
		this.resident_name = resident_name;
	}

	public String getIdentificationNumber() {
		return identificationNumber;
	}

	public void setIdentificationNumber(String identificationNumber) {
		this.identificationNumber = identificationNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getServiceNumber() {
		return serviceNumber;
	}

	public void setServiceNumber(Integer serviceNumber) {
		this.serviceNumber = serviceNumber;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String get_type() {
		return _type;
	}

	public void set_type(String _type) {
		this._type = _type;
	}

	public String getNumber_code() {
		return number_code;
	}

	public void setNumber_code(String number_code) {
		this.number_code = number_code;
	}

	public Integer get_year() {
		return _year;
	}

	public void set_year(Integer _year) {
		this._year = _year;
	}
	
	public String completeNumber(){
		return this.number_code + " - "+this._year;
	}

	public String getRequest_number() {
		return request_number;
	}

	public void setRequest_number(String request_number) {
		this.request_number = request_number;
	}
	
}