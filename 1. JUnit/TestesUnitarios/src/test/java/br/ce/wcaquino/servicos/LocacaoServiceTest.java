package br.ce.wcaquino.servicos;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.utils.DataUtils;

public class LocacaoServiceTest {
	
	@Test
	public void teste() {
		//cenário
		LocacaoService service = new LocacaoService();
		Usuario usuarioTeste = new Usuario("João");
		Filme filmeAlugado = new Filme("Orgulho e Preconceito", 5, 10.25);
		
		//ação
		Locacao aluguel = service.alugarFilme(usuarioTeste, filmeAlugado);	
		
		//verificação
		Assert.assertTrue(DataUtils.isMesmaData(aluguel.getDataLocacao(), new Date()));
		Assert.assertTrue(DataUtils.isMesmaData(aluguel.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)));
		Assert.assertEquals(10.25, aluguel.getValor(), 0.01); //valor esperado, valor real, margem erro
	
		//verificação com AssertThat
		assertThat(aluguel.getValor(), is(10.25)); //com import estatico
		assertThat(aluguel.getValor(), is(CoreMatchers.not(5.0)));
		assertThat(DataUtils.isMesmaData(aluguel.getDataLocacao(), new Date()), is(true));
		assertThat(DataUtils.isMesmaData(aluguel.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)), is(true));		
	}
}
