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

Exemplo usado no teste:  
~~~
001ç1234567891234çPedroç50000
001ç3245678865434çPauloç40000.99
002ç2345675434544345çJose da SilvaçRural
002ç2345675433444345çEduardo PereiraçRural
003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çPedro
003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çPaulo
~~~

Saida esperada:  
~~~
Quantidade de clientes no arquivo de entrada
Quantidade de vendedor no arquivo de entrada
ID da venda mais cara
O pior vendedor

Arquivo de saida: {flat_file_name} .done.dat
~~~

Gerar .jar executavel:
~~~
mvn clean compile assembly:single
~~~

Executar .jar:
~~~
java -jar target\analysis-jar-with-dependencies.jar
~~~

