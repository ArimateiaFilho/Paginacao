/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhoso;


public class Arquivo {
	private String nome;
	private int blocoInicio;// bloco onde ira inicia o arquivo

	public Arquivo(String nome, int num) {
		setNome(nome);
		setBlocoInicio(num);
	}

	public String getNome() {
		return this.nome;
	}

	public int getBlocoInicio() {
		return this.blocoInicio;
	}

	public void setNome(String nome) {
		if (nome != null) {
			this.nome = nome;
		}
	}

	public void setBlocoInicio(int blocoInicio) {
		if (blocoInicio >= -1) {
			this.blocoInicio = blocoInicio;
		}
	}

}
