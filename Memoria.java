/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhoso;

public class Memoria {
	public int quantBlocos, tamBloco, tamMem;//variaveis para guardar a quantidade de blocos,tamanho e cada bloco e o tamanho total da memoria
	public Arquivo[] arquivos;// guarda os arquivos
	public char disco[];
	public int FAT[];//tabela fat
	public int numBlocoLivre;//quantidade de blocos livres

	public Memoria(int quantBlocos, int tamBloco) {
		this.quantBlocos = quantBlocos;
		this.numBlocoLivre = quantBlocos;
		this.tamBloco = tamBloco;
		tamMem = quantBlocos * tamBloco;// tamMem sera a quantidade de memoria do disco
		disco = new char[tamMem];// tamanho do disco sera o tamanho de blocos vezes o numero de blocos
		FAT = new int[quantBlocos];
		arquivos = new Arquivo[quantBlocos];
		for (int i = 0; i < quantBlocos; i++) {// inicializa a tabela fat com -2 para indicar que esta tudo livre (-2 == livre)
			FAT[i] = -2;
		}
		for (int i = 0; i < tamMem; i++) {// espaco livre é indicado por *
			disco[i] = '*';
		}

	}

	public void criarArquivo(String nomeArquivo) {// procura se tem espaço para o arquivo e aloca o arquivo caso contrario não é alocado
		int x = -1;
		for (int i = 0; i < arquivos.length; i++) {
			if (arquivos[i] == null) {
				x = i;
				break;
			}
		}
		if (nomeArquivo != null&&x!=-1) {
			arquivos[x] = new Arquivo(nomeArquivo, -1);
		}
	}

	public void apagarArquivo(String nomeArquivo) {// procura o arqivo pelo nome se o arquivo estiver na tabela ele sera retirado
		int x = verifica(nomeArquivo);
		if (x != -1) {
			int aux = arquivos[x].getBlocoInicio(), aux2, cont = 0;
			while (aux != -1) {
				for (int i = 0; i < tamBloco; i++) {// a posição do disco é preenchida com * para indicar que esta vago
					disco[aux * tamBloco + i] = '*';
				}
				aux2 = aux;
				aux = FAT[aux];
				FAT[aux2] = -2;// atualiza a tabela fat pra indicar que esta livre
				cont++;
			}
			arquivos[x] = null;
			numBlocoLivre += cont;
		} else {
			System.out.println("Arquivo não encontrado!");
		}

	}

	public void lerArquivo(String nomeArquivo) {// procura o arquivo pelo nome e printa seus blocos equanto for diferente de *
		int x = verifica(nomeArquivo);
		if (x != -1) {
			int aux = arquivos[x].getBlocoInicio();// pega o bloco onde inicia o arquivo
			while (aux != -1) {
				for (int i = 0; i < tamBloco; i++) {
					if (disco[aux * tamBloco + i] != '*') {
						System.out.print(disco[aux * tamBloco + i]);
					}
				}
				aux = FAT[aux];// pega o proximo bloco
			}
			System.out.println();
		} else {
			System.out.println("Arquivo não encontrado!");
		}

	}

	public void gravarArquivo(String nomeArquivo, String conteudo) {//procura o arquivo pelo nome depois calcula quantos blocos tera que ter para guardar aquele arquivo
		int x = verifica(nomeArquivo);                          // pega o teto da divisão
		if (x != -1&&conteudo!=null) {
			int aux = conteudo.length(), aux2 = conteudo.length();
			aux = (int) Math.ceil(((double) (aux) / (double) (tamBloco)));
			System.out.println(aux);
			if (aux <= numBlocoLivre) {// caso o numero de blocos seja menor ou igual o numero de blocos livre o arquivo sera alocado caso contrario não poderar ser alocado
				int blocoLivre = 0, cont = 0;
				int valor = 453534;// valor aleatorio para inicio
				for (int j = 0; j < aux; j++) {
					for (int i = 0; i < quantBlocos; i++) {
						if (FAT[i] == -2) {//verifica um bloco livre na tabela fat
							if (j == 0) {// se for a primeira vez é colocado o bloco inicial do arquivo
								arquivos[x].setBlocoInicio(i);
								FAT[i] = -1;
							} else { // caso contrario ia sendo alocado nas posições disponiveis e seu ultimo bloco tera -1 na tabela fat
                                                                // para indicar o final
								FAT[valor] = i;
								FAT[i] = -1;
							}
							valor = i;
							blocoLivre = i;
							break;
						}
					}
					for (int i = 0; i < tamBloco; i++) {// coloca o conteudo no disco de acordo com os blocos que foram designados
						if (cont < aux2) {
							disco[blocoLivre * tamBloco + i] = conteudo.charAt(cont);
							cont++;
						} else {
							break;
						}
					}
				}
				numBlocoLivre -= aux;
			} else {
				System.out.println("Memória com capacidade insuficiente para aloca este arquivo");
			}
		} else {
			System.out.println("Arquivo não encontrado!");
		}

	}

	public void inserirConteudo(String nomeArquivo, String conteudo) {
		int x = verifica(nomeArquivo);// procura o arquivo pelo nome depois pega seu bloco final e começa a adicionar a partir do * do final do arquivo
		if (x != -1&&conteudo!=null) {
			int aux = arquivos[x].getBlocoInicio(), numCarac = conteudo.length(), cont = 0;
			while (FAT[aux] != -1) {//procurando o final do arquivo
				aux = FAT[aux];
			}
			for (int i = 0; i < tamBloco; i++) {// colocando o começo do arquivo no final do proximo caso tenha espaço disponivel
				if (disco[aux * tamBloco + i] == '*') {
					cont++;
				}
			}
			int z = 0;
			if (numCarac <= cont) {// se o numero de caracteres for menor ou igual que os espaço que ainda tem * é alocado somente no final do arquivo sem alterar as tabela sozmente o disco
				for (int i = 0; i < tamBloco; i++) {
					if (disco[aux * tamBloco + i] == '*') {
						if (z < numCarac) {
							disco[aux * tamBloco + i] = conteudo.charAt(z);
							z++;
						}
					}
				}
			} else { //caso contrario tera que alocar no final do arquivo e depois procurar espaços vazios para alocar o restante do conteudo
				for (int i = 0; i < tamBloco; i++) {
					if (disco[aux * tamBloco + i] == '*') {
						if (z < numCarac) {
							disco[aux * tamBloco + i] = conteudo.charAt(z);
							z++;
						}
					}
				}
				for (int j = 0; j < quantBlocos; j++) {
					if (FAT[j] == -2) {
						for (int i = 0; i < tamBloco; i++) {
							if (z < numCarac) {
								disco[j * tamBloco + i] = conteudo.charAt(z);
								z++;
								FAT[aux] = j;
								FAT[j] = -1;
								aux = j;
							}
						}
					}
				}
			}
		} else {
			System.out.println("Arquivo não encontrado!");
		}

	}

	private int verifica(String nomeArquivo) {// pega o nome do arquivo e procura no array de arquivos se tem algum nome igual se tiver o indice daquele arquivo é retornado
		int x = -1;
                if(nomeArquivo!=null){
                    for (int i = 0; i < arquivos.length; i++) {
                            if (arquivos[i] != null) {
                                    if (arquivos[i].getNome().equals(nomeArquivo)) {
                                            x = i;
                                            break;
                                    }
                            }
                    }
                }
		return x;
	}

}
