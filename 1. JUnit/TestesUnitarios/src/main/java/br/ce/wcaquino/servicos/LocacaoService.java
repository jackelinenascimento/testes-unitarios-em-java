package br.ce.wcaquino.servicos;


import static br.ce.wcaquino.utils.DataUtils.adicionarDias;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;
import br.ce.wcaquino.utils.DataUtils;

public class LocacaoService {
	
	public Locacao alugarFilme(Usuario usuario, List<Filme> filmes) throws Exception {
		
		if(filmes == null || filmes.isEmpty() ) {
			throw new LocadoraException("Filme vazio");
		}
		
		for(Filme filme: filmes) {
			if(filme.getEstoque() == 0) {
				throw new FilmeSemEstoqueException();
			}			
		}
		
		if(usuario == null) {
			throw new LocadoraException("Usuário vazio");
		}
		
		Locacao locacao = new Locacao();
		locacao.setFilmes(filmes);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		Double valorTotal = 0d;
		for(int i=0; i< filmes.size(); i++) {
			Filme filme = filmes.get(i);
			switch (i) {
				case 2: valorTotal += filme.getPrecoLocacao() * 0.75; break;
				case 3: valorTotal += filme.getPrecoLocacao() * 0.50; break;
				case 4: valorTotal += filme.getPrecoLocacao() * 0.25; break;
				case 5: valorTotal += filme.getPrecoLocacao() * 0; break;
				default: valorTotal += filme.getPrecoLocacao();
			}
		}
		
		locacao.setValor(valorTotal);

		//Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		if(DataUtils.verificarDiaSemana(dataEntrega, Calendar.SUNDAY)) {
			dataEntrega = adicionarDias(dataEntrega, 2);
		}
		locacao.setDataRetorno(dataEntrega);
		
		//Salvando a locacao...	
		//TODO adicionar mÃ©todo para salvar
		
		return locacao;
	}
}