// Jose Arimateia Fabriciio de Castro Filho // Matricula : 402774
// Alan Sousa da Silva // Matricula : 400261
package trabalhoso;


import java.util.*;

public class Main {
	public static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {

		int quantBloco, tamBloco;//variaveis pra guardar a quantidade de blocos e 
		System.out.print("Informe a quantidade de blocos: ");
		quantBloco = sc.nextInt();
		System.out.print("Informe o tamanho dos blocos: ");
		tamBloco = sc.nextInt();
		Memoria memoria = new Memoria(quantBloco, tamBloco);// instanciando Disco

		int op;
		do {
                        // Menu para escolher as opções 
			System.out.println("Menu");
			System.out.println("[1]Criar Arquivo");
			System.out.println("[2]Apagar Arquivo");
			System.out.println("[3]Ler Arquivo");
			System.out.println("[4]Gravar Arquivo");
			System.out.println("[5]Inserir conteudo");
			System.out.println("[6]Sair");
			op = sc.nextInt();
			sc.nextLine();
			switch (op) {
			case 1:
				System.out.print("Informe o nome do arquivo: ");
				memoria.criarArquivo(sc.nextLine());
				break;
			case 2:
				System.out.print("Informe o nome do arquivo: ");
				memoria.apagarArquivo(sc.nextLine());
				break;
			case 3:
				System.out.print("Informe o nome do arquivo: ");
				memoria.lerArquivo(sc.nextLine());
				break;
			case 4:
				System.out.print("Informe o nome do arquivo: ");
				String nomeArquivo = sc.nextLine();
				System.out.print("Informe o conteúdo do arquivo: ");
				memoria.gravarArquivo(nomeArquivo, sc.nextLine());
				break;
			case 5:
				System.out.print("Informe o nome do arquivo: ");
				nomeArquivo = sc.nextLine();
				System.out.print("Informe o conteúdo do arquivo: ");
				memoria.inserirConteudo(nomeArquivo, sc.nextLine());
			case 6:
				break;
			case 7:// caso para testes  // printa a tabela fat e o dados do disco
				for (int i = 0; i < memoria.quantBlocos; i++) {
					System.out.print(memoria.FAT[i] + " ");
				}
				for (int i = 0; i < memoria.tamMem; i++) {
					System.out.print(memoria.disco[i]);
				}
				System.out.println();
				break;
			default:
				System.out.println("Opção inválida");
				break;
			}

		} while (op != 6);
	}
}
