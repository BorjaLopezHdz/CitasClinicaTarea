package org.iesalandalus.programacion.citasclinica.modelo;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.BeforeClass;
import org.junit.Test;

public class CitaTest {

	private static final String ERROR_CITA_NULA = "ERROR: No se puede copiar una cita nula.";
	private static final String ERROR_PACIENTE_NULO = "ERROR: El paciente de una cita no puede ser nulo.";
	private static final String ERROR_FECHA_HORA_NULA = "ERROR: La fecha y hora de una cita no puede ser nula.";
	private static final String PACIENTE_INCORRECTO = "Debería haber saltado una excepción indicando que el paciente es incorrecto";
	private static final String FECHA_HORA_INCORRECTA = "Debería haber saltado una excepción indicando que la fecha y hora son incorrectas";
	private static final String CITA_NULA = "Debería haber saltado una excepción indicando que no se puede copiar una cita nula.";
	private static final String MENSAJE_NO_CORRECTO = "El mensaje devuelto por la excepción no es correcto.";
	private static final String TIPO_NO_CORRECTO = "El tipo de la excepción no es correcto.";
	private static final String CADENA_NO_ESPERADA = "La cadena devuelta no es la esperada.";
	private static final String CITA_NO_ESPERADA = "La cita copiada debería ser la misma que la pasada como parámetro.";
	private static final String REFERENCIA_CITA_NO_ESPERADA = "La referencia de la cita devuelta es la misma que la pasada al constructor.";
	private static final String PACIENTE_NO_ESPERADO = "El paciente devuelto no es el mismo que el pasado al constructor.";
	private static final String REFERENCIA_PACIENTE_NO_ESPERADA = "La referencia del paciente devuelta es la misma que la pasada al constructor.";
	private static final String FECHA_HORA_NO_ESPERADA = "La fehca y hora no son las mismas que las pasadas al constructor.";
	private static final String OBJETO_DEBERIA_SER_NULO = "No se debería haber creado el objeto cita.";
	
	private static Paciente paciente1;
	private static Paciente paciente2;
	private static LocalDateTime fechaHora1;
	private static LocalDateTime fechaHora2;
	
	@BeforeClass
	public static void asignarValoresAtributos() {
		paciente1 = new Paciente("José Ramón Jiménez Reyes", "11223344B", "950112233");
		paciente2 = new Paciente("Andrés Rubio Del Río", "22334455Y", "666223344");
		fechaHora1 = LocalDateTime.of(2019, 9, 15, 12, 0);
		fechaHora2 = LocalDateTime.of(2019, 9, 15, 12, 30);
	}

	@Test
	public void constructorPacienteValidoFechaHoraValidaCreaCitaCorrectamente() {
		Cita cita = new Cita(paciente1, fechaHora1);
		assertThat(PACIENTE_NO_ESPERADO, cita.getPaciente(), is(paciente1));
		assertThat(REFERENCIA_PACIENTE_NO_ESPERADA, cita.getPaciente(), not(sameInstance(paciente1)));
		assertThat(FECHA_HORA_NO_ESPERADA, cita.getFechaHora(), is(fechaHora1));
		cita = new Cita(paciente2, fechaHora2);
		assertThat(PACIENTE_NO_ESPERADO, cita.getPaciente(), is(paciente2));
		assertThat(REFERENCIA_PACIENTE_NO_ESPERADA, cita.getPaciente(), not(sameInstance(paciente2)));
		assertThat(FECHA_HORA_NO_ESPERADA, cita.getFechaHora(), is(fechaHora2));
	}
	
	@Test
	public void constructorPacienteNoValidoFechaHoraValidaLanzaExcepcion() {
		Cita cita = null;
		try {
			cita = new Cita(null, fechaHora1);
			fail(PACIENTE_INCORRECTO);
		} catch (NullPointerException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_PACIENTE_NULO));
			assertThat(OBJETO_DEBERIA_SER_NULO, cita, is(nullValue()));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
	}
	
	@Test
	public void constructorPacienteValidoFechaHoraNoValidaLanzaExcepcion() {
		Cita cita = null;
		try {
			cita = new Cita(paciente1, null);
			fail(FECHA_HORA_INCORRECTA);
		} catch (NullPointerException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_FECHA_HORA_NULA));
			assertThat(OBJETO_DEBERIA_SER_NULO, cita, is(nullValue()));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
	}
	
	@Test
	public void constructorCitaValidaCopiaCitaCorrectamente() {
		Cita cita1 = new Cita(paciente1, fechaHora1);
		Cita cita2 = new Cita(cita1);
		assertThat(CITA_NO_ESPERADA, cita2, is(cita1));
		assertThat(REFERENCIA_CITA_NO_ESPERADA, cita2, not(sameInstance(cita1)));
		assertThat(PACIENTE_NO_ESPERADO, cita2.getPaciente(), is(paciente1));
		assertThat(REFERENCIA_PACIENTE_NO_ESPERADA, cita2.getPaciente(), not(sameInstance(paciente1)));
		assertThat(FECHA_HORA_NO_ESPERADA, cita2.getFechaHora(), is(fechaHora1));
	}
	
	@Test
	public void constructorCitaNulaLanzaExcepcion() {
		Cita cita = null;
		try {
			cita = new Cita(null);
			fail(CITA_NULA);
		} catch (NullPointerException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_CITA_NULA));
			assertThat(OBJETO_DEBERIA_SER_NULO, cita, is(nullValue()));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
	}
	
	@Test
	public void toStringDevuelveLaCadenaEsperada() {
		Cita cita = new Cita(paciente1, fechaHora1);
		assertThat(CADENA_NO_ESPERADA, cita.toString(), is(String.format("%s, fechaHora=%s", paciente1, fechaHora1.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")))));
	}

}
