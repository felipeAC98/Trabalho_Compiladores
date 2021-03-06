# Trabalho_Compiladores
Trabalho da disciplina de compiladores da UFSCAR - Professor Lucredio

### Instruçoes para buildar

- Abra o Netbeans, va em File -> OpenProject -> Selecione esta pasta

- Clique com o botao direito no projeto e então clique em "Build"

- Verifique se o resultado final na parte inferior do NetBeans é BUILD SUCCESS

### Instruçoes para rodar a aplicação que foi feita a build

- Abra o CMD do windows neste diretorio

- Execute o seguinte comando: java -jar target/TrabalhoCompiladores-1.0-SNAPSHOT-jar-with-dependencies.jar codigoEntrada.txt codigoSaida.txt

### Arquivo de gramatica LALexer.g4

- O caminho do arquivo de gramatica é: src/main/antlr4/br/ufscar/dc/compiladores/LA/lexico

- Sempre que alterar o arquivo de gramatica deve buildar novamente o projeto antes de rodar 