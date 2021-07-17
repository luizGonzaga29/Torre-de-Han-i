package torreHanoi;


import java.util.InputMismatchException;
import java.util.Scanner;

public class Hanoi {
	
	static int contador = 1;
	static boolean controle = true;

	// cria a matriz inicial
	static int[][] matrizInicial(int[][] matriz) {
		int qtd = 1;
		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < 3; j++) {
				if (j == 0) {
					matriz[i][j] = qtd;
				}
			}
			qtd++;
		}
		return matriz;
	}

	// imprimi a matriz
	static void printMatriz(int[][] matriz) {
		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < 3; j++) {
				System.out.print("    " + matriz[i][j] + "    ");
			}
			System.out.println();
			System.out.println();
		}
		System.out.println("---------------------------------------");
		System.out.println("    " + "A" + "    " + "    " + "B" + "    " + "    " + "C" + "    ");
	}

	// atualiza a matriz com as novas posições
	static int[][] updateMatriz(int disco, String col, int[][] matriz) {
		int coluna = converter(col);
		
		if (coluna < 0 || disco < 1 || disco > matriz.length) {
			System.out.println("Valor inválido!");
			contador--;
		} else {
			for (int i = 0; i < matriz.length; i++) {
				for (int j = 0; j < 3; j++) {
					
					if (!retirarDisco(matriz, disco,i, j, coluna)) {
						i = matriz.length;
						break;
					}
					
					if (checarResultado(matriz)) {
						System.out.println("==================Teste terminado==============");
						controle = false;
						i = matriz.length;
						break;
					}
				}
			}
		}
		return matriz;
	}

	// passa a string para inteiro para usar na matriz
	public static int converter(String a) {
		int x = -1;
		if (a.equalsIgnoreCase("a")) {
			x = 0;
		} else if (a.equalsIgnoreCase("b")) {
			x = 1;

		} else if (a.equalsIgnoreCase("c")) {
			x = 2;
		}
		return x;
	}

	// coloca o disco na nova posição
	public static void colocarDisco(int[][] matriz, int coluna, int aux, int i, int j) {
		int cont = matriz.length - 1;
		while (cont >= 0) {
			if (matriz[cont][coluna] == 0) {
				matriz[cont][coluna] = aux;
				cont = -1;
			} else if (aux > matriz[cont][coluna]) {
				System.out.println("Movimento inválido!");
				contador--;
				matriz[i][j] = aux;
				cont = -1;
			}
			cont--;
		}
	}

	// verifica se foi concluido o teste
	public static boolean checarResultado(int[][] matriz) {
		int qtd = 0;
		int qtd1 = 0;
		boolean result = false;
		int cont = 0;
		while (cont < matriz.length) {
			if (matriz[cont][1] == (cont+1)) {
				qtd++;
			}
			if (matriz[cont][2] == (cont+1)) {
				qtd1++;
			}
			cont++;
		}
		if (qtd == matriz.length || qtd1 == matriz.length) {
			result = true;
		}
		return result;
	}
	//varre a matriz para achar o disco informado
	public static boolean retirarDisco(int[][] matriz, int disco, int i, int j, int coluna) {
		boolean paraLaco = true;
		int aux = disco;
		if (matriz[i][j] == disco) {
			matriz[i][j] = 0;
			disco = -1;

			if (aux > 1 && matriz[i - 1][j] != 0) {
				System.out.println("Erro. disco menor em cima.");
				contador--;
				matriz[i][j] = aux;
				paraLaco = false;
			}
			if(paraLaco) {
			colocarDisco(matriz, coluna, aux, i, j);
			}
		}
		return paraLaco;
	}

	//garantir que a quantidade de discos seja entre 2 a 9
	public static int verificarDiscos(int discos, Scanner sc) {
		while(discos < 3 || discos > 12) {
			System.out.println("Quantidade de discos deve ser entre 3 a 12!");
			discos = sc.nextInt();
		}
		return discos;
	} 
	
	public static void main(String[] args) {

		int disco = 0;
		String coluna = "";
		while (controle) {
			try {
				Scanner entry = new Scanner(System.in);
				System.out.print("Informe a quantidade de discos: ");
				int[][] matriz = new int[verificarDiscos(entry.nextInt(), entry)][3];
				printMatriz(matrizInicial(matriz));
					while (controle) {
						System.out.println("==================================================");
						System.out.println("movimento " + contador);
						System.out.print("Informe o disco: ");
						entry.nextLine();
							try {
								 disco = entry.nextInt();
								 System.out.print("Informe a coluna: ");
								 coluna = entry.next();
								 System.out.println("==================================================");
								 printMatriz(updateMatriz(disco, coluna, matriz));
								 contador++;
								 
							}catch (InputMismatchException e) {
								System.out.println("Por favor digite apenas números inteiros!");
								printMatriz(updateMatriz(disco, coluna, matriz));
								contador++;
							}
						
					}
					
					if(!controle) {
						System.out.println("==================Você usou " + (contador - 1) + " movimentos=====================");
						entry.close();
					}
			}catch (InputMismatchException e) {
				System.out.println("Por favor digite apenas números inteiros!");
			}catch (Exception e) {
				System.out.println("Erro inesperado!");
			}
		}
	}
}