package br.com.caelum.ingresso.validacao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import br.com.caelum.ingresso.model.Sessao;

public class GerenciadorDeSessao {
	private List<Sessao> sessoes;
	
	public GerenciadorDeSessao(List<Sessao> sessoes){
		this.sessoes = sessoes;
	}

	private boolean horarioIsConflitante(Sessao sessaoExistente, Sessao sessaoNova){
		LocalDate hoje = LocalDate.now();
		
		LocalDateTime horarioSessaoExistente = sessaoExistente.getHorario().atDate(hoje);
		LocalDateTime horarioSessaoNova = sessaoNova.getHorario().atDate(hoje);
		
		LocalDateTime terminoSessaoNova = horarioSessaoNova.plus(sessaoNova.getFilme().getDuracao());
		LocalDateTime terminoSessaoExistente = horarioSessaoExistente.plus(sessaoExistente.getFilme().getDuracao());
		
		boolean terminaAntes = terminoSessaoNova.isBefore(horarioSessaoExistente);
		boolean comecaDepois = terminoSessaoExistente.isBefore(horarioSessaoNova);
		
		if (terminaAntes || comecaDepois){
			return false;
		}
		return true;
	}
	
	public boolean cabe(Sessao sessaoNova){
		return sessoes.stream().noneMatch(sessaoExistente -> horarioIsConflitante(sessaoExistente, sessaoNova));
	}
}
