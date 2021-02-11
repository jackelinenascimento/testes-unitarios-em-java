package calculadora;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.ce.wcaquino.calculadora.Calculadora;
import br.ce.wcaquino.exceptions.NaoPodeDividirPorZeroException;


public class CalculadoraTest {
	
	private Calculadora calc;
	
	@Before
	public void setUp() {
		calc = new Calculadora();
	}
	
	@Test
	public void deveSomarDoisValores() {
		//cenario
		int a = 5;
		int b = 3;
		
		//acao
		int resultado = calc.somar(a,b);
		
		//verificacao
		Assert.assertEquals(8, resultado);
	}
	
	@Test
	public void deveSubtrairDoisValores() {
		//cenario
		int a = 5;
		int b = 3;
		
		//acao
		int resultado = calc.subtrair(a,b);
		
		//verificacao
		Assert.assertEquals(2, resultado);
	}
	
	@Test
	public void deveDividirDoisValores() throws NaoPodeDividirPorZeroException {
		//cenario
		int a = 6;
		int b = 3;
		
		//acao
		int resultado = calc.dividir(a,b);
		
		//verificacao
		Assert.assertEquals(2, resultado);
	}
	
	@Test(expected = NaoPodeDividirPorZeroException.class)
	public void deveLancarExcecaoAoDividirPorZero() throws NaoPodeDividirPorZeroException {
		//cenario
		int a = 6;
		int b = 0;
		
		//acao
		calc.dividir(a, b);
	}

}
