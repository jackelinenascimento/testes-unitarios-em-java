package br.ce.wcaquino.servicos;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Date;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.utils.DataUtils;

public class LocacaoServiceTest {

	@Rule
	public ErrorCollector error = new ErrorCollector();
	
	@Rule
	public ExpectedException exception= ExpectedException.none();

	@Test
	public void testeLocacao() throws Exception {
		// cen�rio
		LocacaoService service = new LocacaoService();
		Usuario usuarioTeste = new Usuario("Jo�o");
		Filme filmeAlugado = new Filme("Orgulho e Preconceito", 5, 10.25);

		// a��o
		Locacao aluguel = service.alugarFilme(usuarioTeste, filmeAlugado);

		// verifica��o
		Assert.assertTrue(DataUtils.isMesmaData(aluguel.getDataLocacao(), new Date()));
		Assert.assertTrue(DataUtils.isMesmaData(aluguel.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)));
		Assert.assertEquals(10.25, aluguel.getValor(), 0.01); // valor esperado, valor real, margem erro

		// verifica��o com AssertThat
		assertThat(aluguel.getValor(), is(10.25)); // com import estatico
		assertThat(aluguel.getValor(), is(CoreMatchers.not(5.0)));
		assertThat(DataUtils.isMesmaData(aluguel.getDataLocacao(), new Date()), is(true));
		assertThat(DataUtils.isMesmaData(aluguel.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)), is(true));

		// utilizando @Rules
		error.checkThat(aluguel.getValor(), is(10.25));
		error.checkThat(aluguel.getValor(), is(CoreMatchers.not(5.0)));
	}

	@Test(expected = Exception.class)
	public void testeLocacao_filmeSemEstoque() throws Exception {
		// cen�rio
		LocacaoService service = new LocacaoService();
		Usuario usuarioTeste = new Usuario("Jo�o");
		Filme filmeAlugado = new Filme("Orgulho e Preconceito", 0, 10.25);

		// a��o
		Locacao aluguel = service.alugarFilme(usuarioTeste, filmeAlugado);
	}
	
	@Test
	public void testeLocacao_filmeSemEstoque_2(){
		
		// cen�rio
		LocacaoService service = new LocacaoService();
		Usuario usuarioTeste = new Usuario("Jo�o");
		Filme filmeAlugado = new Filme("Orgulho e Preconceito", 0, 10.25);
		Locacao aluguel;
				
		// a��o
		try {
			aluguel = service.alugarFilme(usuarioTeste, filmeAlugado);	
			Assert.fail("Deveria ter lan�ado uma exce��o");
		} catch (Exception e) {
			Assert.assertThat(e.getMessage(), is("Filme sem estoque"));
		}
	}
	
	@Test
	public void testeLocacao_filmeSemEstoque_3() throws Exception{
		
		// cen�rio
		LocacaoService service = new LocacaoService();
		Usuario usuarioTeste = new Usuario("Jo�o");
		Filme filmeAlugado = new Filme("Orgulho e Preconceito", 0, 10.25);
		
		exception.expect(Exception.class);
		exception.expectMessage("Filme sem estoque");
						
		// a��o
		service.alugarFilme(usuarioTeste, filmeAlugado);	
	}
}
