# Projeto: Jokenpo Distribuído

## Autor do Projeto
- Nome : **Renan Santos Pires**
- RA : **125111370466**


## Disciplina
- **UC:** Sistemas Distribuídos e Mobile
- **Professor Responsável:** Prof. Elias P. Silva
- **Faculdade:** Universidade Anhembi Morumbi

## Avaliação A3

### Tema do Projeto
O projeto consiste em desenvolver um programa distribuído em Java para jogar Jokenpo (Pedra, Papel, Tesoura) entre dois jogadores, com duas modalidades de jogo: Jogador Vs CPU e Jogador Vs Jogador.

### Requisitos
- **Modalidades de Jogo:**
  - **Jogador Vs CPU:** O jogador enfrenta o computador, que escolhe aleatoriamente uma das três opções.
  - **Jogador Vs Jogador:** Dois jogadores jogam um contra o outro em máquinas diferentes.
- **Suporte a Múltiplos Jogos:** O servidor deve suportar vários jogos ocorrendo simultaneamente.
- **Estatísticas de Jogo:** O programa deve informar o vencedor de cada rodada e ao final da partida mostrar o número de vitórias, derrotas e empates.
- **Entrega:** Os projetos devem ser entregues até o dia 10/06. Será descontado 1 ponto por dia de atraso após essa data.

### Regras de Entrega
1. O projeto deve ser entregue via GitHub.
2. O projeto deve conter apenas os arquivos fonte (.java).
3. Deve haver um arquivo "README.md" contendo os nomes de todos os integrantes e seus RA's.
4. O grupo pode ter de 1 a 4 integrantes.

### Critérios de Avaliação
1. Correto funcionamento do programa.
2. Cumprimento dos requisitos solicitados.
3. Apresentação e participação dos alunos durante a exposição e perguntas do professor.

## Comandos para Execução por Linha de Comando

### Servidor
1. **Build do Servidor:**
```sh
javac -cp ".;lib/sqlite-jdbc-3.7.2.jar" -d build src/com/jokenpo_simples/server/model/*.java src/com/jokenpo_simples/server/controller/*.java src/com/jokenpo_simples/server/service/*.java src/com/jokenpo_simples/server/database/*.java
```
2. **Execução do Servidor:**
```sh
java -cp ".;lib/sqlite-jdbc-3.7.2.jar;build" com.jokenpo_simples.server.controller.GameController
```

### Cliente
1. **Build do Cliente:**

```sh
javac -cp ".;lib/sqlite-jdbc-3.7.2.jar" -d build src/com/jokenpo_simples/client/model/*.java src/com/jokenpo_simples/client/controller/*.java src/com/jokenpo_simples/client/view/*.java
```

2. **Execução do Cliente:**
```sh
java -cp ".;lib/sqlite-jdbc-3.7.2.jar;build" com.jokenpo_simples.client.view.GameUI
```