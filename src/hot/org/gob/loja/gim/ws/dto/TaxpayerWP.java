package org.gob.loja.gim.ws.dto;

public class TaxpayerWP {
	private Long id;
	private String identificationNumber;
	private String firstName; // firstName en caso de Entidades Legales es el name
	private String lastName;
	private String email;
	private String street;
	private String phoneNumber;
	private String fullName;
	
	public TaxpayerWP() {
	}
	
	public TaxpayerWP(Long id, String identificationNumber, String name, String email, String fullName) {
		this();
		this.id = id;
		this.identificationNumber = identificationNumber;
		this.firstName = name; 
		this.lastName = name; // Tambien se fija en para los EntityLegal
		this.email = email;
		this.fullName = fullName;
	}
	
	/**
	 * @Deprecated debido a que existen contribuyentes que no tienen registrada su direccion actual
	 * se reemplaza por el constructor 
	 * public Taxpayer(Long id, String identificationNumber, String name, 
			String email)
	 */
	@Deprecated
	public TaxpayerWP(Long id, String identificationNumber, String name, 
			String email, String street, String phoneNumber, String fullName) {
		this();
		this.id = id;
		this.identificationNumber = identificationNumber;
		this.firstName = name;
		this.email = email;
		this.street = street;
		this.phoneNumber = phoneNumber;
		this.fullName = fullName;
	}
	
	/**
	 * @Deprecated debido a que existen contribuyentes que no tienen registrada su direccion actual
	 * se reemplaza por el constructor 
	 * public Taxpayer(Long id, String identificationNumber, String firstName, String lastName, 
			String email)
	 */
	//@Deprecated 2020-02-03 rfam se habilita, la informacion del contribuyente debe estar actulizada y tambien retornar lo q hay del contribuyente
	public TaxpayerWP(Long id, String identificationNumber, String firstName, String lastName, 
			String email, String street, String phoneNumber, String fullName) {
		this();
		this.id = id;
		this.identificationNumber = identificationNumber;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.street = street;
		this.phoneNumber = phoneNumber;
		this.fullName = fullName;
	}
	
	public TaxpayerWP(Long id, String identificationNumber, String firstName, String lastName, 
			String email, String fullName) {
		this();
		this.id = id;
		this.identificationNumber = identificationNumber;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.fullName = fullName;
	}
		
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getIdentificationNumber() {
		return identificationNumber;
	}
	public void setIdentificationNumber(String identificationNumber) {
		this.identificationNumber = identificationNumber;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}		
}
