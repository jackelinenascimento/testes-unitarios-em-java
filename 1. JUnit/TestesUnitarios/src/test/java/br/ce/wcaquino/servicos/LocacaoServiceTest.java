package br.ce.wcaquino.servicos;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;
import br.ce.wcaquino.utils.DataUtils;

public class LocacaoServiceTest {
	
	private LocacaoService service;
	
	@Rule
	public ErrorCollector error = new ErrorCollector();
	
	@Rule
	public ExpectedException exception= ExpectedException.none();

	@Before
	public void setup() {
		service = new LocacaoService();	
	}
	
	@After
	public void tearDown() {
	}
	
	@Test
	public void deveAlugarFilme() throws Exception {
		// cen�rio
		Usuario usuarioTeste = new Usuario("Jo�o");
		List<Filme> filmes = Arrays.asList(new Filme("Orgulho e Preconceito", 5, 10.25));

		// a��o
		Locacao aluguel = service.alugarFilme(usuarioTeste, filmes);

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

	//forma elegante
	@Test(expected = FilmeSemEstoqueException.class)
	public void deveLancarExcecaoAoAlugarFilmeSemEstoque() throws Exception {
		// cen�rio
		Usuario usuarioTeste = new Usuario("Jo�o");
		List<Filme> filmes = Arrays.asList(new Filme("Orgulho e Preconceito", 0, 10.25));

		// a��o
		service.alugarFilme(usuarioTeste, filmes);
	}
	
	@Test
	public void deveLancarExcecaoAoAlugarFilmeSemUsuario() throws FilmeSemEstoqueException {
		
		//cen�rio
		List<Filme> filmes = Arrays.asList(new Filme("Orgulho e Preconceito", 5, 10.25));
		
		//a��o
		try {
			service.alugarFilme(null, filmes);	
			Assert.fail();
		} catch (LocadoraException e) {
			Assert.assertThat(e.getMessage(), is("Usu�rio vazio"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void devePagar75PctNoFilme3() throws Exception {
		//cenario
		Usuario usuarioTeste = new Usuario("Jo�o");
		List<Filme> filmes = Arrays.asList(
				new Filme("Filme 1", 3, 4.00), new Filme("Filme 2", 3, 4.00), new Filme("Filme 3", 3, 4.00));
		
		//acao
		Locacao resultado = service.alugarFilme(usuarioTeste, filmes);
		
		//verificacao
		Assert.assertThat(resultado.getValor(), is(11.0));
	}
	
	@Test
	public void devePaga50PctNoFilme4() throws Exception {
		//cenario
		Usuario usuarioTeste = new Usuario("Jo�o");
		List<Filme> filmes = Arrays.asList(
				new Filme("Filme 1", 3, 4.00), new Filme("Filme 2", 3, 4.00), new Filme("Filme 3", 3, 4.00), new Filme("Filme 4", 3, 4.00));
		
		//acao
		Locacao resultado = service.alugarFilme(usuarioTeste, filmes);
		
		//verificacao
		Assert.assertThat(resultado.getValor(), is(13.0));
	}
	
	@Test
	public void devePaga25PctNoFilme5() throws Exception {
		//cenario
		Usuario usuarioTeste = new Usuario("Jo�o");
		List<Filme> filmes = Arrays.asList(
				new Filme("Filme 1", 3, 4.00), new Filme("Filme 2", 3, 4.00), new Filme("Filme 3", 3, 4.00), new Filme("Filme 4", 3, 4.00),  new Filme("Filme 5", 3, 4.00));
		
		//acao
		Locacao resultado = service.alugarFilme(usuarioTeste, filmes);
		
		//verificacao
		Assert.assertThat(resultado.getValor(), is(14.0));
	}
	
	@Test
	public void devePaga0PctNoFilme6() throws Exception {
		//cenario
		Usuario usuarioTeste = new Usuario("Jo�o");
		List<Filme> filmes = Arrays.asList(
				new Filme("Filme 1", 3, 4.00), new Filme("Filme 2", 3, 4.00), new Filme("Filme 3", 3, 4.00), new Filme("Filme 4", 3, 4.00),  new Filme("Filme 5", 3, 4.00), new Filme("Filme 6", 3, 4.00));
		
		//acao
		Locacao resultado = service.alugarFilme(usuarioTeste, filmes);
		
		//verificacao
		Assert.assertThat(resultado.getValor(), is(14.0));
	}
	
	@Test
	public void naoDeveDevolverFilmeAosDomingos() throws Exception {
		//cenario
		Usuario usuarioTeste = new Usuario("Jo�o");
		List<Filme> filmes = Arrays.asList(
				new Filme("Filme 1", 3, 4.00), new Filme("Filme 2", 3, 4.00), new Filme("Filme 3", 3, 4.00), new Filme("Filme 4", 3, 4.00),  new Filme("Filme 5", 3, 4.00), new Filme("Filme 6", 3, 4.00));
		
		//acao
		Locacao retorno = service.alugarFilme(usuarioTeste, filmes);
				
		//verificacao
		boolean ehSegunda = DataUtils.verificarDiaSemana(retorno.getDataRetorno(), Calendar.MONDAY);
		Assert.assertTrue(ehSegunda);
	}
	
	
	//forma robusta
	//@Test
	//public void testeLocacao_filmeSemEstoque_2(){
		
		// cen�rio
	//	LocacaoService service = new LocacaoService();
	//	Usuario usuarioTeste = new Usuario("Jo�o");
	//	Filme filmeAlugado = new Filme("Orgulho e Preconceito", 0, 10.25);
	//	Locacao aluguel;
				
		// a��o
	//	try {
	//		aluguel = service.alugarFilme(usuarioTeste, filmeAlugado);	
	//		Assert.fail("Deveria ter lan�ado uma exce��o");
	//	} catch (Exception e) {
	//		Assert.assertThat(e.getMessage(), is("Filme sem estoque"));
	//	}
	//}
	
	//forma nova
	//@Test
	//public void testeLocacao_filmeSemEstoque_3() throws Exception{
		
		// cen�rio
	//	LocacaoService service = new LocacaoService();
	//	Usuario usuarioTeste = new Usuario("Jo�o");
	//	Filme filmeAlugado = new Filme("Orgulho e Preconceito", 0, 10.25);
		
		//deve ser declarada antes da a��o
	//	exception.expect(Exception.class);
	//	exception.expectMessage("Filme sem estoque");
	//					
		// a��o
	//	service.alugarFilme(usuarioTeste, filmeAlugado);	
	//}
	
}
