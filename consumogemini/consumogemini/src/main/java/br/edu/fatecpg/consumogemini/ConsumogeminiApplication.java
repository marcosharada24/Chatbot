package br.edu.fatecpg.consumogemini;

import br.edu.fatecpg.consumogemini.service.ConsomeGemini;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileWriter;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

@SpringBootApplication
public class ConsumogeminiApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ConsumogeminiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Boolean loop_programa = true;
		System.out.println("Olá sou o Fatecano seu assistente virtual, pergunte-me e eu responderei :)![digite fatec_sair caso queria finalizar minha assistência]");
		Scanner entrada = new Scanner(System.in);
		String entradaUsuario;

		while(loop_programa){
			System.out.print("O que deseja saber? ");
			entradaUsuario = entrada.nextLine();

			if(entradaUsuario.equals("fatec_sair")){
				System.out.println("Até mais :)");
				loop_programa = false;
			}else{
				String resposta = ConsomeGemini.fazerPergunta(entradaUsuario);
				System.out.println(resposta);
				registrarArquivo(entradaUsuario, resposta);
			}
		}
	}

	protected void registrarArquivo(String pergunta, String resposta) throws IOException {
		try {
			FileWriter arquivo = new FileWriter("respostas.log", true);
			DateTimeFormatter formatarAgora = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
			ZonedDateTime zd = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo"));

			arquivo.write("[ "+String.valueOf(formatarAgora.format(zd))+" | Gemini ]\n");
			arquivo.write("Pergunta: "+pergunta);
			arquivo.write("\nResposta: "+resposta+"\n\n");
			arquivo.close();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}
