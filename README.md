Comandos necessarios para execução por linha de comando:

na pasta server, buildar o server: 
javac -d build src/com/jokenpo_simples/server/model/*.java src/com/jokenpo_simples/server/controller/*.java src/com/jokenpo_simples/server/service/*.java 


e então executar o arquivo: 
java -cp build com.jokenpo_simples.server.controller.GameController



E o mesmo para o programa client:
javac -d build src/com/jokenpo_simples/client/model/*.java src/com/jokenpo_simples/client/controller/*.java

E então executar o arquivo:
java -cp build com.jokenpo_simples.client.controller.GameController
