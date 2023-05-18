CREATE TABLE tb_enderecos (

id int auto_increment PRIMARY KEY,
logradouro VARCHAR(100) NOT NULL,
numero int NOT NULL,
cidade VARCHAR(50) NOT NULL,
uf VARCHAR(2) NOT NULL

);

CREATE TABLE tb_usuarios (

cpf VARCHAR(11) NOT NULL PRIMARY KEY,
nome VARCHAR(100) NOT NULL,
sexo VARCHAR(20) NOT NULL,
data DATE NOT NULL,
id_endereco int,
CONSTRAINT FOREIGN KEY(id_endereco) REFERENCES tb_enderecos(id)

);


