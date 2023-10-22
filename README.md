# An-liseTextualGrafos
A análise textual é um campo da linguística e da ciência da computação que se concentra na interpretação e compreensão de textos escritos. Ela envolve a aplicação de técnicas e métodos para extrair informações úteis, identificar padrões, analisar estruturas linguísticas e obter insights a partir de documentos de texto.




#rodar o codigo

javac -cp ".:mallet.jar:mallet-deps.jar" AnaliseTextual.java
java -cp ".:mallet.jar:mallet-deps.jar" AnaliseTextual


caso tenha necessidade para alterar os documentos tanto em csv tanto em txt para fazer alguns testes em outros tipos de teste segue os comandos, lembrando sempre esteja no Path principal onde esta o mallet.jar:

java -cp "mallet.jar:mallet-deps.jar" cc.mallet.classify.tui.Text2Vectors --input ./exemplos --output exemplos.mallet


java -cp "mallet.jar:mallet-deps.jar" cc.mallet.classify.tui.Csv2Vectors --input ./exemplos/exemplos.csv --output exemplos.mallet
