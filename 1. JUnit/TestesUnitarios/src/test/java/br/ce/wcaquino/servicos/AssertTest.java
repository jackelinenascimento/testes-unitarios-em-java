package br.ce.wcaquino.servicos;

import org.junit.Assert;
import org.junit.Test;

import br.ce.wcaquino.entidades.Usuario;

public class AssertTest {

	@Test
	public void test() {
		Assert.assertTrue(true);
		Assert.assertFalse(false);
		
		Assert.assertEquals(1, 1);
		Assert.assertEquals(0.51, 0.51, 0.01);
		Assert.assertEquals(Math.PI, 3.14, 0.01);
		
		int i = 5;
		Integer i2 = 5;
		Assert.assertEquals(Integer.valueOf(i), i2);
		Assert.assertEquals(i, i2.intValue());
		
		Assert.assertEquals("teste", "teste");
		Assert.assertTrue("teste".equalsIgnoreCase("Teste"));
		Assert.assertTrue("teste".startsWith("te"));
		
		Usuario u1 = new Usuario("Usuario 1");
		Usuario u2 = new Usuario("Usuario 1");
		Usuario u3 = null;
		
		Assert.assertEquals(u1, u2); //implementar equals na classe para comparação
		
		Assert.assertSame(u1, u1); //compara instancias
		Assert.assertNotSame(u1, u2);
		
		Assert.assertNull(u3);
		Assert.assertNotNull(u2);	
	}
	
}
