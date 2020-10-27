#### TESTE PARA DESENVOLVEDOR

Proposta:  
Criar um sistema em JAVA para ler e analisar os dados e gerar relatório. Existem 3 tipos de dados dentro dos arquivos.  

1-Dados do Vendedor (id 001)  
exemplo:  
~~~
001çCPFçNameçSalary
~~~

2-Dados Cliente (id 002)  
exemplo:
~~~  
002çCNPJçNameçBusiness Area
~~~

3- Dados vendas (id 003)  
exemplo:  
~~~
003çSale IDç[Item ID-Item Quantity-Item Price]çSalesman name
~~~


Pastas do sistema: (conforme especificado no pdf)  
~~~
%HOMEPATH%/data/in  
%HOMEPATH%/data/out
~~~

Não é necessário criar nenhuma pasta, a mesma é criado no caminho configurado na variavel de ambiente conforme mencionado acima.  

Rodar teste:  
~~~
mvn clean test
~~~

Gerar .jar executavel:
~~~
mvn clean compile assembly:single
~~~

Executar .jar:
~~~
java -jar target\analysis-jar-with-dependencies.jar
~~~

