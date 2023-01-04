package br.com.gabriel.sysagenda;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import br.com.gabriel.sysagenda.business.ContatoBss;
import br.com.gabriel.sysagenda.business.LigacaoBss;
import br.com.gabriel.sysagenda.domain.Contato;
import br.com.gabriel.sysagenda.domain.Ligacao;
import br.com.gabriel.sysagenda.domain.LigacaoId;
import br.com.gabriel.sysagenda.factory.ConnectionFactory;
import br.com.gabriel.sysagenda.util.Funcoes;
import br.com.gabriel.sysagenda.util.Titulo;

public class SysAgenda {

	private static int opcao;
	private static ContatoBss contatoBss = new ContatoBss();
	private static LigacaoBss ligacaoBss = new LigacaoBss();

	public static void main(String[] args) {

		try {

			Connection connection = ConnectionFactory.getConnection();

			Scanner sc = new Scanner(System.in);

			do {

				System.out.println("-------------------------------------");
				System.out.println("1 - MENU CONTATO");
				System.out.println("2 - MENU LIGAÇÃO");
				System.out.println("3 - SAIR");
				System.out.println("-------------------------------------");

				System.out.print("Digite a opção aqui : ");
				opcao = Integer.parseInt(sc.nextLine());

				switch (opcao) {
				case 1:

					mostraMenuContato(sc);
					break;

				case 2:

					mostraMenuLigacao(sc);
					break;

				case 3:

					break;

				default:
					System.out.println("\nLeia as opções por favor!!");
					break;
				}
			} while (opcao != 3);

			sc.close();
			connection.close();

			System.exit(0);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private static void mostraMenuContato(Scanner sc) {

		boolean sn;
		do {
			System.out.println("\n-------------------------------------");
			System.out.println("1 - LISTAR CONTATOS");
			System.out.println("2 - ADICIONA CONTATO");
			System.out.println("3 - ALTERAR CONTATO");
			System.out.println("4 - APAGAR CONTATO");
			System.out.println("5 - VOLTAR");
			System.out.println("-------------------------------------" + "\n");

			String op;
			do {
				System.out.print("Digite a opção aqui : ");
				op = sc.nextLine();
				sn = Funcoes.isNumeroInvalido(op);
			} while (sn);

			opcao = Integer.parseInt(op);

			switch (opcao) {

			case 1:
				listaContatos();
				break;

			case 2:
				addContato(sc);
				break;

			case 3:
				updateContato(sc);
				break;

			case 4:

				deleteContato(sc);
				break;

			default:
				break;
			}

			if (opcao > 5 && opcao <= 0) {
				System.out.println("\nVocê leu as opções??\n");
			}

		} while (opcao != 5);

	}

	private static void deleteContato(Scanner sc) {
		System.out.print("\nDigite o " + Titulo.COD_CONTATO + " do contato para deleta-lo : ");
		Integer numero = sc.nextInt();

		// verifica se o contato existe
		Contato contato = contatoBss.getContato(numero);
		if (contato == null) {
			System.out.println("\nO Contato não existe!!");
		} else {
			sc.nextLine();

			System.out.print("\nEssa ação podera apagar as ligações desse contato. Deseja Continuar [s/n] : ");

			String resp = sc.nextLine();

			if (resp.equalsIgnoreCase("s") || resp.equalsIgnoreCase("sim")) {
				contatoBss.deletaContato(numero);
				System.out.println("\nExcluido com Sucesso!\n");
			}
		}
	}

	private static void updateContato(Scanner sc) {
		System.out.print("\nDIGITE O " + Titulo.COD_CONTATO + " A SER ALTERADO : ");

		// Verifica se o contato existe
		String codContato = sc.nextLine();

		Contato contatoAltera = contatoBss.getContato(Integer.parseInt(codContato));

		if (contatoAltera == null) {

			System.out.print("\nESTE CONTATO NÃO EXISTE!!");

		} else {

			System.out.print("\nNOME [" + contatoAltera.getNome() + "] : ");

			String nom = sc.nextLine();

			if (nom != null & !nom.equals("")) {
				contatoAltera.setNome(nom);
			}

			System.out.print("\nTELEFONE [" + contatoAltera.getTelefone() + "] : ");

			long teste = 0;
			while (teste == 0) {
				String num = sc.nextLine();
				try {
					if (num.length() < 12) {
						teste = Long.parseLong(num);
						contatoAltera.setTelefone(teste);
					} else
						System.out.println("Número inválido! Digite novamente!");
				} catch (Exception e) {
					System.out.println("Número inválido! Digite novamente!");
				}
			}

			contatoBss.alteraContato(contatoAltera);

			System.out.println("\nContato alterado com Sucesso");
		}
	}

	private static void addContato(Scanner sc) {
		boolean sn;
		Contato contatoAdiciona = new Contato();
		String nome;
		String num;

		contatoAdiciona.setCodContato(contatoBss.getCodContato() + 1);

		do {

			System.out.print("\nDIGITE O NOME DO CONTATO : ");
			nome = sc.next();
			sn = Funcoes.verificaNome(nome);

			if (sn) {
				System.out.println("\nPOR FAVOR DIGITAR SO LETRAS!!\n");
			}

		} while (sn);

		do {

			System.out.print("\nDIGITE O TELEFONE DO CONTATO : ");
			num = sc.next();
			sn = Funcoes.isNumeroInvalido(num);

			if (num.length() >= 12) {
				System.out.println("numero acima do permitido");
				sn = false;
			}

		} while (sn);

		contatoAdiciona.setNome(nome);
		contatoAdiciona.setTelefone(Long.parseLong(num));

		contatoBss.adiciona(contatoAdiciona);

		System.out.println("\nCONTATO ADICIONADO\n");
	}

	private static void listaContatos() {
		List<Contato> contatolista = contatoBss.getList();

		System.out.println("-------------------------------------");

		System.out.print("|         Nome           ");
		System.out.print("| Cod Contato  ");
		System.out.print("|Telefone");
		System.out.println();
		for (Contato contato2 : contatolista) {
			System.out.print("|");
			System.out.print(String.format("%-24.24s", contato2.getNome()));
			System.out.print("|");
			System.out.print(String.format("%-14.14s", contato2.getCodContato()));
			System.out.print("|");
			System.out.print(contato2.getTelefone());
			System.out.println();
		}

		System.out.println("-------------------------------------");
	}

	public static void mostraMenuLigacao(Scanner sc) {
		String op;
		boolean sn;

		do {
			System.out.println("-------------------------------------");
			System.out.println("1 - LISTAR LIGAÇÕES");
			System.out.println("2 - ADICIONAR LIGAÇÃO");
			System.out.println("3 - ALTERAR LIGAÇÃO");
			System.out.println("4 - Apagar LIGAÇÃO");
			System.out.println("5 - voltar");
			System.out.println("-------------------------------------");

			do {
				System.out.print("Digite a opção aqui : ");
				op = sc.next();
				sn = Funcoes.isNumeroInvalido(op);
			} while (sn == true);

			System.out.println();
			opcao = Integer.parseInt(op);

			switch (opcao) {
			case 1:
				ligacaoLista();

				break;

			case 2:
				ligacaoAdiciona(sc);

				break;

			case 3:
				ligacaoAltera(sc);
				break;

			case 4:
				ligacaoApaga(sc);

				break;

			default:
				break;
			}

		} while (opcao != 5);
	}

	private static void ligacaoApaga(Scanner sc) {
		
		Ligacao ligacao = new Ligacao();
		LigacaoId id = new LigacaoId();

		sc.nextLine();

		System.out.print("Digite o " + Titulo.COD_CONTATO + " : ");
		id.setCodContato(Integer.parseInt(sc.nextLine()));
		System.out.print("Digite o " + Titulo.COD_LIGACAO + " a ser deletado : ");
		id.setCodLigacao(Integer.parseInt(sc.nextLine()));

		ligacao.setId(id);

		if (ligacaoBss.getLigacao(id.getCodContato(), id.getCodLigacao()) == null) {

			System.out.println("\nEsse Ligação não existe!!");

		} else {

			ligacaoBss.deletaLigacao(ligacao);

		}
	}

	private static void ligacaoAltera(Scanner sc) {
		
		boolean sn;
		String codigo;
		String data;
		String hora;
		String obs;
		Ligacao ligacao = new Ligacao();
		LigacaoId id = new LigacaoId();

		//recebe o cod_contato e verifica
		do {
			System.out.print("Digite o " + Titulo.COD_CONTATO + " para o qual você ligou : ");
			codigo = sc.next();
			sn = Funcoes.isNumeroInvalido(codigo);

		} while (sn);
		id.setCodContato(Integer.parseInt(codigo));

		// Verifica se o contato existe
		Contato contato1 = contatoBss.getContato(id.getCodContato());
		if (contato1 == null) {

			System.out.println("\n" + Titulo.COD_CONTATO + " não existe!!");

		} else {

			//recebe o cod_ligação e verifica
			do {
				System.out.print("Digite o " + Titulo.COD_LIGACAO + " a ser alterada : ");
				codigo = sc.next();
				sn = Funcoes.isNumeroInvalido(codigo);
			} while (sn);
			id.setCodLigacao(Integer.parseInt(codigo));

			// Verifica se a ligação existe
			ligacao = ligacaoBss.getLigacao(id.getCodContato(), id.getCodLigacao());
			if (ligacao == null) {
				System.out.println("\nLigação não existe!");
				
			} else {
				
				sc.nextLine();

				
				//recebe e verifica se foi digitada uma data
				System.out.print("Digite a data [dd/mm/yyyy] : ");
				data = sc.nextLine();
				if (data == null | data.equals("")) {
					data= Funcoes.separa(Funcoes.dateToStr(ligacao.getDataHora()), " ")[0];
				}

				// vai pedir a hora e minutos a ser alterados, depois passa a verifica a hora e minutos
				do {
					System.out.print("Digite a Hora [HH:MM] : ");
					hora = sc.nextLine();

					if (hora != null & !hora.equals("")) {
						String[] horas = Funcoes.separa(hora, ":");
						Integer h = Integer.parseInt(horas[0]);
						Integer m = Integer.parseInt(horas[1]);

						if (h >= 24 | m >= 60) {

							System.out.println("\nHoras ou Minutos acima do Limite");
							sn = true;
						} else {
							sn = false;
						}
					} else {
						hora = Funcoes.separa(Funcoes.dateToStr(ligacao.getDataHora()), " ")[1];
					}
				} while (sn);

				//seta data e hora
				ligacao.setDataHora(Funcoes.strToDH(data + " " + hora));

				//recebee verifica Se obs foi escrito e seta a observação
				System.out.print("Digite a observação : ");
				obs = sc.nextLine();
				if (obs != null & !obs.equals("")) {
					ligacao.setObs(obs);
				}

				ligacaoBss.alteraLigacao(ligacao);

				System.out.println();
				System.out.println("Ligação Alterada com sucesso!");
			}
		}
	}

	private static void ligacaoAdiciona(Scanner sc) {
		
		boolean sn;
		String cod;
		String data;
		String hora;
		String obs;
		Ligacao ligacao = new Ligacao();
		LigacaoId id = new LigacaoId();

		//recebe o cod_contato e verifica
		do {
			System.out.print("Digite o " + Titulo.COD_CONTATO + " : ");
			cod = sc.next();
			sn = Funcoes.isNumeroInvalido(cod);
		} while (sn);
		id.setCodContato(Integer.parseInt(cod));

		// verifica se contato existe
		if (contatoBss.getContato(Integer.parseInt(cod)) == null) {

			System.out.println("\n" + Titulo.COD_CONTATO + " não existe!!");

		} else {

			//recebe o cod_ligacao automaticamente
			id.setCodLigacao(ligacaoBss.getCodLigacao(id.getCodContato()) + 1);
			
			ligacao.setId(id);

			//recebe data
			System.out.print("Digite a data [dia/mes/ano] : ");
			data = sc.next();

			//recebe hora e verifica
			do {
				System.out.print("Digite a Hora [Hora:Minutos] : ");
				hora = sc.next();
				String[] horas = Funcoes.separa(hora, ":");
				Integer h = Integer.parseInt(horas[0]);
				Integer m = Integer.parseInt(horas[1]);

				if (horas.length < 3) {

					if (h >= 24 | m >= 60) {

						System.out.println("\nHoras ou Minutos acima do Limite");
						sn = true;

					} else {
						sn = false;
					}
				}
			} while (sn);

			ligacao.setDataHora(Funcoes.strToDH(data + " " + hora));

			sc.nextLine();
			System.out.print("Digite a observação {opcional} : ");
			obs = sc.nextLine();
			ligacao.setObs(obs);

			ligacaoBss.adicionaLigacao(ligacao);

			System.out.println("\nLigação adicionada");
		}
	}

	private static void ligacaoLista() {
		
		List<Ligacao> list = ligacaoBss.getlist();

		System.out.println("-------------------------------------\n");

		System.out.print("|Cod_ligação");
		System.out.print("|Cod_Contato");
		System.out.print("|    DATA_HORA     ");
		System.out.print("|Obs");
		System.out.println();
		for (Ligacao ligacao : list) {
			System.out.print("|");
			System.out.print(String.format("%-11.11s", ligacao.getId().getCodContato()));
			System.out.print("|");
			System.out.print(String.format("%-11.11s", ligacao.getId().getCodLigacao()));
			System.out.print("|");
			System.out.print(String.format("%-18.18s", Funcoes.dateToStr(ligacao.getDataHora())));
			System.out.print("|");
			System.out.print(ligacao.getObs());
			System.out.println();
		}

		System.out.println("-------------------------------------");
	}

}
