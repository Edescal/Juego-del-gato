package equipo;
import java.util.*;

public class Juego {
	//La matriz se pone fuera de las funciones, para que cualquier funci�n tenga acceso a ella
	private static int[][] matriz = new int[3][3];

	public static void main(String[] args) {
		//Declarar las variables
		int x = 0, y = 0;
		boolean turnoDeJugadorUno = true;
		Scanner scan = new Scanner(System.in);

		//Un ciclo para jugar todas las partidas que los jugadores quieran
		while(true)
		{
			iniciarJuego();
			
			//El m�ximo de jugadas que pueden haber es 9, cuando se llenan
			//todas las casillas, si eso pasa, hay empate. Si antes de las
			//9 jugadas alguien gana, el for de detiene antes con un break
			for(int i = 0; i < 9; i++) {
				String jugador = turnoDeJugadorUno ? "Jugador uno " : "Jugador dos ";
				String simbolo = turnoDeJugadorUno ? " (X)" : " (O)";
				System.out.println(">------Es turno del "+jugador+simbolo+"------<\n");			

				//Hacer un ciclo hasta validar que el jugador elija un cuadrante disponible
				boolean jugada = false;
				while (!jugada) {					
					//Leer las coordenadas X e Y
					System.out.print("Selecciona la fila: ");
					x = validarNumero(scan);
					System.out.print("Selecciona la columna: ");
					y = validarNumero(scan);			

					/*Se le pasa a la funci�n las coordenadas y si es el turno del jugador uno, es
					 *si turnoDeJugadorUno es falso, entonces es turno del jugador dos.
 					 *La funci�n devuelve false si el cuadrante elegido ya se eligi� antes, y se repite
					 *el ciclo hasta que devuelva true, que significa que s� se pudo elegir el cuadrante 
					 */
					jugada = dibujarMatriz(x-1, y-1, turnoDeJugadorUno);					
					
					//Si la funci�n devuelve false, avisar que ese cuadrante ya se eligi� antes
					if (!jugada)
						System.out.println("\n�El cuadrante ("+x+","+y+") ya est� ocupado! Elije otro...\n");
				}

				if (tresEnFila()) {
					//Si la funci�n es verdadera, el jugador actual ya gan�, se rompe el for
					System.out.println(">-----"+jugador+"gan� la partida-----<");
					break;
				} else if (i == 8) {
					//Si el jugador no gan�, pero ya se llenaron todas las casillas, fue empate
					System.out.println(">-----�EMPATE! Nadie gan� la partida-----<");
					break;
				} else {					
					//Si todavia no gana nadie o aun quedan casillas, se cambia el turno del jugador
					turnoDeJugadorUno = !turnoDeJugadorUno;
				}
			}
			
			//Cuando acaba el juego, se pregunta si quieren seguir jugando o ya no
			//Se valida que s�lo se pueda responder S o N
			System.out.println("\n�Iniciar otra partida? S/N");
			scan.nextLine();
			String res;
			do {
				res = scan.nextLine();
				if (!res.equalsIgnoreCase("S") && !res.equalsIgnoreCase("N"))
					System.out.println("�Responde s�lo con S o N!");
			} while(!res.equalsIgnoreCase("S") && !res.equalsIgnoreCase("N"));

			//Si quieren seguir jugando, se reinicia la matriz y el juego, sino, se rompe el while
			if (res.equalsIgnoreCase("N")) break;
		}

		//Aqu� acaba el programa
		System.out.println(">--------�Acab� el juego!--------<");
		scan.close();
	}

	private static void dibujarMatriz()
	{
		System.out.println("\n"); //Salto de l�nea
		String simbolo;
		for(int i = 0; i < 3; i++) {
			System.out.print("\t");  //Se pone un Tab a cada l�nea
			for(int j = 0; j < 3; j++) {
				//Si la casilla tiene un 1 dibuja "X", si tiene un 0 dibuja "O"
				//Si tiene otro n�mero (cuando se reinicia el juego), se dibuja un gui�n
				simbolo = matriz[i][j] == 1 ? "X" : matriz[i][j] == 0 ? "O" : "-"; 
				System.out.print(" "+simbolo+" ");
				//Se pone un | para separar los n�meros
				if (j != 2) System.out.print("|");
			}
			//Se pone un separados entre cada fila
			if (i != 2) System.out.println("\n\t-----------");
		}
		System.out.println("\n"); //Salto de l�nea
	}

	private static boolean dibujarMatriz(int x, int y, boolean jugadorUno)
	{
		//Si el n�mero en la casilla de esas coordenadas es 1 o 0, es porque ya hay una "X" o un "O"
		if (matriz[x][y] == 0 || matriz[x][y] == 1) return false;

		//Si no hay un 0 o 1, entonces est� libre la casilla y se asigna 1 o 0, dependiendo de 
		//qu� jugador sea el turno
		matriz[x][y] = jugadorUno ? 1 : 0;
		//Luego de cambiar el valor de la casilla, se dibuja la matriz actualizada
		dibujarMatriz();
		return true; //Si todo sali� bien, regresa verdadero
	}
	
	private static int validarNumero(Scanner scanner) {
		int n = 0;
		do {
			try {
				n = scanner.nextInt();
			} catch(InputMismatchException e) {
				n = 0;
				scanner.nextLine();
			}
			if (n<1 || n>3)
				System.out.println("�S�lo puedes elegir un n�mero entre 1 y 3!");
		} while(n<1 || n>3);
		return n;
	}
	
	private static void iniciarJuego()
	{
		System.out.println("------------------------------");
		System.out.println("|Bienvenido al juego del gato|");
		System.out.println("------------------------------");
		System.out.println("\t|Instrucciones|");
		System.out.println("Para seleccionar un cuadrante:");
		System.out.println("Escribe el n�mero de la fila (horizontal)");
		System.out.println("Escribe el n�mero de la columna (vertical)\n");
		
		System.out.print(">--------EL JUEGO COMENZ�--------<");
		matriz = new int[][]{
			{-1, -1, -1}, 
			{-1, -1, -1}, 
			{-1, -1, -1}, 
		};
		dibujarMatriz();
	}

	private static boolean tresEnFila()
	{
		//Aqu� se verifica si ya se hicieron tres en vertical, horizontal o diagonal
		boolean gano = false;
		for(int i = 0; i < 3; i++)
		{
			//Checar cada fila
			int uno = matriz[i][0];
			int dos = matriz[i][1];
			int tres = matriz[i][2];

			if (uno != -1 && uno == dos && dos == tres) {
				gano = true;
				break;
			}			

			//Checar cada columna
			uno = matriz[0][i];
			dos = matriz[1][i];
			tres = matriz[2][i];

			if (uno != -1 && uno == dos && dos == tres) {
				gano = true;
				break;
			}			

			//Checar una diagonal
			dos = matriz[1][1];
			uno = matriz[0][0];
			tres = matriz[2][2];

			if (uno != -1 && uno == dos && dos == tres) {
				gano = true;
				break;
			}			

			//Checar la otra diagonal
			uno = matriz[0][2];
			tres = matriz[2][0];

			if (uno != -1 && uno == dos && dos == tres)
				gano = true;
		}

		return gano;
	}
}
