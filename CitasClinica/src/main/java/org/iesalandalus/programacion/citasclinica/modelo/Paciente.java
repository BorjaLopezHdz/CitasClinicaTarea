package org.iesalandalus.programacion.citasclinica.modelo;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Paciente {
	private static final String ER_DNI = ("([0-9]{8})([A-Za-z])");
	private static final String ER_TELEFONO = ("[69][0-9]{8}");
	private String nombre;
	private String dni;
	private String telefono;
	
	public Paciente(String nombre, String dni, String telefono) {
		setNombre(nombre);
		setDni(dni);
		setTelefono(telefono);
	}
	
	public Paciente(Paciente copiaPaciente)
	{
		if (copiaPaciente == null) {
			throw new NullPointerException("ERROR: No es posible copiar un paciente nulo.");
		} else {
			setNombre(copiaPaciente.nombre);
			setDni(copiaPaciente.dni);
			setTelefono(copiaPaciente.telefono);
		}
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		if (nombre == null || nombre.trim().isEmpty()) {
			throw new NullPointerException("ERROR: El nombre del paciente no puede ser nulo.");
		} else {
			this.nombre = formateaNombre(nombre);
		}
	}
	
	private String formateaNombre(String nombre) {
		String cambiar = this.nombre;
		cambiar = cambiar.trim().replaceAll("+", " ").replaceAll("[^a-z-A-Z-á-Á-é-É-í-Í-ó-Ó-ú-Ú] ", "").toLowerCase();
		char[] caracteres = cambiar.toCharArray();
		for(int i = 0; i < cambiar.length(); i++ ) {
			caracteres[0] = Character.toUpperCase(caracteres[0]);
			if (caracteres[i] == ' ') {
				caracteres[i+1] = Character.toUpperCase(caracteres[i + 1]);
			}
		}
		cambiar = String.valueOf(caracteres);
		return cambiar;
	}
	
	public String getDni() {
		return dni;
	}
	
	private void setDni(String dni) {
		if (dni == null || dni.trim().isEmpty()) {
			throw new NullPointerException("ERROR: El DNI de un paciente no puede ser nulo.");
		} else {
			Pattern patron;
			Matcher comparador;
			
			patron = Pattern.compile(ER_DNI);
			comparador = patron.matcher(dni);
			
			if (comparador.matches()) {
				if (comprobarLetraDni(dni)==true) {
					this.dni = dni;
				} else {
					throw new IllegalArgumentException("ERROR: La letra del DNI no es correcta.");
				}
			} else {
				throw new IllegalArgumentException("ERROR: El formato del DNI no es válido.");
			}
		}
	}
	
	private boolean comprobarLetraDni(String Dni) {
		boolean verificador = false;
		
		Pattern patron;
		Matcher comparador;
		
		patron = Pattern.compile(ER_DNI);
		comparador = patron.matcher(dni);
		
		if (comparador.matches()) {
			int numeroDni = Integer.parseInt(comparador.group(1));
			String letraDni = comparador.group(2);
			String [] letraValida = { " T " , " R " , " W " , " A " , " G ", " M " , " Y " , " F " , " P " , " D " , " X " , " B " , " N " , " J " , " Z " , " S " , " V " , " H " , " L " , " C " , " K " , " E " };
			if (comparador.group(2).equals(letraValida[numeroDni % 23 ])) {
				verificador = true;
			}
		}
		return verificador;
	}
	
	public String getTelefono() {
		return telefono;
	}
	
	public void setTelefono(String telefono) {
		if (telefono == null || telefono.trim().isEmpty()) {
			throw new NullPointerException("ERROR: No puede haber un telefono de paciente no válido.");
		} else if (telefono.matches(ER_TELEFONO)) {
			this.telefono = telefono;
		} else {
			throw new IllegalArgumentException("ERROR: El formato del telefono no es válido.");
		}
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(dni);
	}
	
	@Override
	public boolean equals(Object objeto) {
		if (this == objeto) {
			return true;
		}
		if (!(objeto instanceof Paciente)) {
			return false;
		}
		Paciente other = (Paciente) objeto;
		return Objects.equals(dni, other.dni);
	}
	
	@Override
	public String toString() {
		return String.format("nombre=%s (%s), DNI=%s, telefono=%s", getNombre(), getIniciales(), getDni(), getTelefono()); 
	}
	private String getIniciales() {
		String []palabra=nombre.split("");
		String iniciales="";
		for (int i=0;i<palabra.length;i++) {
			iniciales+=palabra[i].charAt(0);
		} return iniciales.toUpperCase();
	}
}