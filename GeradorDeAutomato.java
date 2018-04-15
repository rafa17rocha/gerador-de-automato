import java.io.*;
import java.io.IOException;

public class GeradorDeAutomato
{
	public static void main(String[] args) throws IOException
	{
		int simbolosQtd;
		char simbolos[];

		int estadosQtd;

		int estadosFinaisQtd;
		int estadosFinais[];

		int estadoInicial;

		String entrada;

		System.out.println("Quantos simbolos?");
		entrada = System.console().readLine();

		simbolosQtd = Integer.parseInt(entrada);
		simbolos = new char[simbolosQtd];

		for(int i = 0; i < simbolosQtd; i++)
		{
			System.out.println("Qual o simbolo " + i + "?");
			simbolos[i] = (System.console().readLine()).charAt(0);
		}

		System.out.println("Quantos estados?");
		entrada = System.console().readLine();
		estadosQtd = Integer.parseInt(entrada);
		

		System.out.println("Quantos estados finais?");
		entrada = System.console().readLine();
		estadosFinaisQtd = Integer.parseInt(entrada);
		estadosFinais = new int[estadosFinaisQtd];

		for(int i = 0; i < estadosFinaisQtd; i++)
		{
			System.out.println("Qual o estado final " + i + "?");
			estadosFinais[i] = Integer.parseInt(System.console().readLine());
		}

		System.out.println("Qual o estado inicial?");
		entrada = System.console().readLine();
		estadoInicial = Integer.parseInt(entrada);

		int transicoes[][] = new int[estadosQtd][simbolosQtd];

		for(int linha = 0; linha < transicoes.length; linha++)
		{
			for(int coluna = 0; coluna < transicoes[linha].length; coluna++)
			{
				System.out.println("Para o estado 'e" + linha +
									"' e simbolo '" + simbolos[coluna] +
									"', qual o proximo estado?");
				entrada = System.console().readLine();

				if(entrada.isEmpty())
					entrada = "-1";
				
				transicoes[linha][coluna] = Integer.parseInt(entrada);
			}
		}

		System.out.println("Qual o nome do programa?");
		String nomeClasse = System.console().readLine();

		String saida = "";

		saida +=	"\npublic class " + nomeClasse + "\n{" +
					"\n\tprivate static int estadosFinais[] = {" + estadosFinais[0];

		for(int i = 1; i < estadosFinais.length; i++)
			saida += ", " + estadosFinais[i];

		saida +=	"};";

		saida +=	"\n\tprivate static char fita[];" +
					"\n\tprivate static int count = 0;";

		saida +=	"\n\n\tpublic static void main(String[] args)\n\t{" +
					"\n\t\tSystem.out.println(\"Digite uma linha:\");" +
					"\n\t\tString entrada = System.console().readLine();" +
					"\n\t\tfita = entrada.toCharArray();" +
					"\n\t\te" + estadoInicial + "();" +
					"\n\t}";

		saida +=	"\n\tpublic static void fim(int estado)\n\t{" +
					"\n\t\tString saida = \"Recusado.\";" +
					"\n\n\t\tfor(int i = 0; i < estadosFinais.length; i++)" +
					"\n\t\t\tif(estado == estadosFinais[i])\n\t\t\t\tsaida = \"Aceito.\";" +
					"\n\n\t\tSystem.out.println(saida);\n\t}";

		int e = 0;
		int k;

		while(e < estadosQtd)
		{
			saida +=	"\n\n\tpublic static void e" + e + "()\n\t{" +
						"\n\t\tcount++;" +
						"\n\n\t\tif(fita.length <= count - 1)\n\t\t\tfim(" + e + ");";

			k = 0;

			while(k < simbolosQtd)
			{

				if(transicoes[e][k] > -1)
				{
					saida +=	"\n\n\t\telse if(fita[count - 1] == '" + simbolos[k] + "')" +
								"\n\t\t\te" + transicoes[e][k] + "();";
				}
				k++;
			}
			saida += "\n\n\t\telse\n\t\t\tfim(-1);\n\t}";

			e++;
		}
		
		saida += "\n}";

		try
		{
			File arquivo = new File(nomeClasse + ".java");
			FileWriter fw = new FileWriter(arquivo);
			BufferedWriter bw = new BufferedWriter(fw);

			bw.write(saida);
			bw.flush();
			bw.close();

			Runtime rt = Runtime.getRuntime();
			Process p1 = rt.exec("javac " + nomeClasse + ".java");
		}
		catch(Exception ex)
		{
			ex.getMessage();
		}
	}
}