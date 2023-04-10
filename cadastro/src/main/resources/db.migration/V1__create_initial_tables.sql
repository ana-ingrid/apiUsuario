CREATE TABLE tb_usuario (

cpf VARCHAR(11) NOT NULL PRIMARY KEY,
nome VARCHAR(100) NOT NULL,
sexo VARCHAR(20) NOT NULL,
data DATE NOT NULL,
CONSTRAINT FOREIGN KEY(id_endereco) REFERENCES tb_endereco(id)

);

CREATE TABLE tb_endereco (

id int auto_increment PRIMARY KEY,
logradouro VARCHAR(100) NOT NULL,
numero int NOT NULL,
cidade VARCHAR(50) NOT NULL,
uf VARCHAR(2) NOT NULL

);

