package br.ce.wcaquino.servicos;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.utils.DataUtils;

public class LocacaoServiceTest {
	
	@Test
	public void teste() {
		//cen�rio
		LocacaoService service = new LocacaoService();
		Usuario usuarioTeste = new Usuario("Jo�o");
		Filme filmeAlugado = new Filme("Orgulho e Preconceito", 5, 10.25);
		
		//a��o
		Locacao aluguel = service.alugarFilme(usuarioTeste, filmeAlugado);	
		
		//verifica��o
		Assert.assertTrue(DataUtils.isMesmaData(aluguel.getDataLocacao(), new Date()));
		Assert.assertTrue(DataUtils.isMesmaData(aluguel.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)));
		Assert.assertTrue(aluguel.getValor() == 10.25);
	}
}
