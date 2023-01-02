package br.com.ljr.sysagenda;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import br.com.ljr.sysagenda.business.ContatoBss;
import br.com.ljr.sysagenda.business.LigacaoBss;
import br.com.ljr.sysagenda.domain.Contato;
import br.com.ljr.sysagenda.domain.Ligacao;
import br.com.ljr.sysagenda.factory.ConnectionFactory;
import br.com.ljr.sysagenda.util.Funcoes;
import br.com.ljr.sysagenda.util.Titulo;

public class SysAgenda {

	static int opcao;
	static ContatoBss contatoBss = new ContatoBss();
	static LigacaoBss ligacaoBss = new LigacaoBss();

	public static void main(String[] args) {

		try {

			Connection connection = ConnectionFactory.getConnection();

			Scanner sc = new Scanner(System.in);
			;

			do {

				System.out.println("-------------------------------------");
				System.out.println("1 - MENU CONTATO");
				System.out.println("2 - MENU LIGAÇÃO");
				System.out.println("3 - SAIR");
				System.out.println("-------------------------------------");

				System.out.print("Digite a opção aqui : ");
				opcao = sc.nextInt();

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
				op = sc.next();
				sn = Funcoes.verificaNumero(op);
			} while (sn);

			opcao = Integer.parseInt(op);

			switch (opcao) {

			case 1:
				List<Contato> contatolista = contatoBss.getList();

				System.out.println("-------------------------------------");

				for (Contato contato2 : contatolista) {
					System.out.print("\n" + contato2 + "\n");
				}

				System.out.println("-------------------------------------");

				break;

			case 2:
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
					sn = Funcoes.verificaNumero(num);

					if (sn) {
						System.out.println("\nPOR FAVOR DIGITAR SO NÚMEROS!!\n");
					}

				} while (sn);

				contatoAdiciona.setNome(nome);
				contatoAdiciona.setTelefone(Long.parseLong(num));

				contatoBss.adiciona(contatoAdiciona);

				System.out.println("\nCONTATO ADICIONADO\n");
				break;

			case 3:

				System.out.print("\nDIGITE O " + Titulo.COD_CONTATO + " A SER ALTERADO : ");
				Contato contatoAltera = contatoBss.getContato(sc.nextInt());

				if (contatoAltera == null) {

					System.out.print("\nESTE CONTATO NÃO EXISTE!!");

				} else {

					System.out.print("\nNOME [" + contatoAltera.getNome() + "] : ");

					sc.nextLine();
					String nom = sc.nextLine();

					if (nom != null & !nom.equals("")) {
						contatoAltera.setNome(nom);
					}

					System.out.print("\nTELEFONE [" + contatoAltera.getTelefone() + "] : ");

					num = sc.nextLine();

					if (!num.equals("")) {
						contatoAltera.setTelefone(Long.parseLong(num));
					}

					contatoBss.alteraContato(contatoAltera);

					System.out.println("\nContato alterado com Sucesso");
				}
				break;

			case 4:

				System.out.print("\nDigite o " + Titulo.COD_CONTATO + " do contato para deleta-lo : ");
				Integer numero = sc.nextInt();

				Contato contato = contatoBss.getContato(numero);

				if (contato == null) {

					System.out.println("\nO Contato não existe!!");

				} else {
					sc.nextLine();

					System.out.print("\nEssa ação podera apagar as ligações desse contato. Deseja Continuar [s/n] : ");

					String boleano = sc.nextLine();

					if (boleano.equalsIgnoreCase("s") || boleano.equalsIgnoreCase("sim")) {
						sn = true;
						contatoBss.deletaContato(numero, sn);
						System.out.println("\nExcluido com Sucesso!\n");
					}

				}

				break;

			default:
				break;
			}

			if (opcao > 5 && opcao <= 0) {
				System.out.println("\nVocê leu as opções??\n");
			}

		} while (opcao != 5);
		main(null);

	}

	public static void mostraMenuLigacao(Scanner sc) {
		String op;
		boolean sn;
		String cod;
		String data = null;
		String hora;
		String obs = null;

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
				sn = Funcoes.verificaNumero(op);
			} while (sn == true);

			System.out.println();
			opcao = Integer.parseInt(op);

			switch (opcao) {
			case 1:
				List<Ligacao> list = ligacaoBss.getlist();

				System.out.println("-------------------------------------\n");

				for (Ligacao ligacao : list) {
					System.out.println(ligacao + "\n");
				}

				System.out.println("-------------------------------------");

				break;

			case 2:
				Ligacao ligacaoAdiciona = new Ligacao();

				do {
					System.out.print("Digite o " + Titulo.COD_CONTATO + " : ");
					cod = sc.next();
					sn = Funcoes.verificaNumero(cod);
				} while (sn);

				ligacaoAdiciona.setCodContato(Integer.parseInt(cod));

				Contato contato = contatoBss.getContato(Integer.parseInt(cod));

				if (contato == null) {

					System.out.println("\n" + Titulo.COD_CONTATO + " não existe!!");

				} else {

					ligacaoAdiciona.setCodLigacao(ligacaoBss.getCodLigacao(Integer.parseInt(cod))+ 1);

					do {

						System.out.print("Digite a data [dia/mes/ano] : ");
						data = sc.next();

						String[] dt = Funcoes.separa(data, "/");

						int num = Integer.parseInt(dt[1]);
						if (num >= 13) {
							sn = false;
						}

					} while (sn);

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

					ligacaoAdiciona.setData(data + " " + hora);

					System.out.print("Digite a observação {opcional} : ");

					sc.nextLine();
					obs = sc.nextLine();
					ligacaoAdiciona.setObs(obs);

					ligacaoBss.adicionaLigacao(ligacaoAdiciona);

					System.out.println("\nLigação adicionada");
				}

				break;

			case 3:
				Ligacao ligacaoAltera = new Ligacao();

				do {

					System.out.print("Digite o " + Titulo.COD_CONTATO + " para o qual você ligou : ");
					cod = sc.next();
					sn = Funcoes.verificaNumero(cod);

				} while (sn);

				ligacaoAltera.setCodContato(Integer.parseInt(cod));

				Contato contato1 = contatoBss.getContato(Integer.parseInt(cod));

				if (contato1 == null) {

					System.out.println("\n" + Titulo.COD_CONTATO + " não existe!!");

				} else {

					do {
						System.out.print("Digite o " + Titulo.COD_LIGACAO + " a ser alterada : ");
						cod = sc.next();
						sn = Funcoes.verificaNumero(cod);
					} while (sn);

					ligacaoAltera.setCodLigacao(Integer.parseInt(cod));

					ligacaoAltera = ligacaoBss.getLigacao(ligacaoAltera.getCodContato(), ligacaoAltera.getCodLigacao());

					if (ligacaoAltera == null) {

						System.out.println("\nLigação não existe!");

					} else {

						String[] array = Funcoes.separa(ligacaoAltera.getData(), " ");

						String[] dt = Funcoes.separa(array[0], "-");
						String dataFormat = dt[2] + "/" + dt[1] + "/" + dt[0];
						array[0] = dataFormat;

						String[] hr = Funcoes.separa(array[1], ":");
						String horaFormat = hr[0] + ":" + hr[1];
						array[1] = horaFormat;

						System.out.print("Digite a data [dia/mes/ano] : ");
						sc.nextLine();

						data = sc.nextLine();
						if (data != null & !data.equals("")) {
							array[0] = data;
						}

						do {
							System.out.print("Digite a Hora [Hora:Minutos] : ");

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

									if (horas.length < 3) {
										array[1] = hora;
									}
								}
							}
						} while (sn);

						ligacaoAltera.setData("'" + array[0] + " " + array[1] + "'");

						System.out.print("Digite a observação : ");
						obs = sc.nextLine();

						if (obs != null & !obs.equals("")) {
							ligacaoAltera.setObs(obs);
						}

						ligacaoBss.alteraLigacao(ligacaoAltera);

						System.out.println();
					}
				}
				break;

			case 4:
				Ligacao ligacaoApaga = new Ligacao();

				sc.nextLine();

				System.out.print("Digite o " + Titulo.COD_CONTATO + " : ");
				ligacaoApaga.setCodContato(Integer.parseInt(sc.nextLine()));
				System.out.print("Digite o " + Titulo.COD_LIGACAO + " a ser deletado : ");
				ligacaoApaga.setCodLigacao(Integer.parseInt(sc.nextLine()));

				ligacaoApaga = ligacaoBss.getLigacao(ligacaoApaga.getCodContato(), ligacaoApaga.getCodLigacao());

				if (ligacaoApaga == null) {
					System.out.println("\nEsse Ligação não existe!!");
				} else {
					ligacaoBss.deletaLigacao(ligacaoApaga);
				}

				break;

			default:
				break;
			}

		} while (opcao != 5);
		main(null);
	}

}
